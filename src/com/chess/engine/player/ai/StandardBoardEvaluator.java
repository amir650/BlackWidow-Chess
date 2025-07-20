package com.chess.engine.player.ai;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.Piece;
import com.chess.engine.player.Player;

import java.util.List;
import java.util.Map;


public final class StandardBoardEvaluator implements BoardEvaluator {

    private static final int CHECK_MATE_BONUS = 10000;
    private static final int CHECK_BONUS = 45;
    private static final int CASTLE_BONUS = 35;
    private static final int CASTLING_RIGHTS_BONUS = 15;
    private static final int MOBILITY_MULTIPLIER = 2;
    private static final int ATTACK_MULTIPLIER = 2;
    private static final int TWO_BISHOPS_BONUS = 25;
    private static final int CRAMPING_MULTIPLIER = 1;
    private static final int NO_BONUS = 0;

    private static final Map<GamePhase, List<ScalarFeature>> PHASE_FACTORS =
            Map.of(
                    GamePhase.OPENING,
                        List.of(
                            Feature.MATERIAL,
                            Feature.CHECK_AND_MATE,
                            Feature.MOBILITY,
                            Feature.PAWN_STRUCTURE,
                            Feature.CASTLE
                        ),
                        GamePhase.MIDDLEGAME, List.of(
                            Feature.MATERIAL,
                            Feature.CHECK_AND_MATE,
                            Feature.MOBILITY,
                            Feature.ATTACKS,
                            Feature.KING_SAFETY
                        ),
                        GamePhase.ENDGAME, List.of(
                            Feature.MATERIAL,
                            Feature.CHECK_AND_MATE,
                            Feature.KING_SAFETY,
                            Feature.CRAMPING,
                            Feature.PIECE_SAFETY
                        ),
                        GamePhase.DEBUG, List.of(
                            Feature.MOBILITY,
                            Feature.CRAMPING,
                            Feature.CHECK_AND_MATE,
                            Feature.ATTACKS,
                            Feature.CASTLE,
                            Feature.MATERIAL,
                            Feature.PAWN_STRUCTURE,
                            Feature.KING_SAFETY
                        )
            );

    private static final StandardBoardEvaluator INSTANCE = new StandardBoardEvaluator();

    private StandardBoardEvaluator() {}

    public static StandardBoardEvaluator get() {
        return INSTANCE;
    }

    @Override
    public int evaluate(final Board board, final int depth) {
        return score(board.whitePlayer(), depth) - score(board.blackPlayer(), depth);
    }

    private static int score(final Player player,
                             final int depth) {
        final GamePhase phase = detectPhase(player.getBoard(), true);
        final List<ScalarFeature> features = PHASE_FACTORS.get(phase);
        int total = 0;
        for (final ScalarFeature feature : features) {
            total += feature.apply(player, depth);
        }
        return total;
    }

    private static GamePhase detectPhase(final Board board,
                                         final boolean isDebug) {
        final int totalPieces = board.getAllPieces().size();
        if(isDebug) {
            return GamePhase.DEBUG;
        }
        if (totalPieces > 24) {
            return GamePhase.OPENING;
        } else if (totalPieces > 12) {
            return GamePhase.MIDDLEGAME;
        } else {
            return GamePhase.ENDGAME;
        }
    }

    private static int mobility(final Player player) {
        return MOBILITY_MULTIPLIER * (player.getLegalMoves().size() - player.getOpponent().getLegalMoves().size());
    }

    private static int cramping(final Player player) {
        final int own = player.getLegalMoves().size();
        final int opp = player.getOpponent().getLegalMoves().size();
        if (opp == 0) {
            return 100 * CRAMPING_MULTIPLIER;
        }
        final float ratio = (float) own / opp;
        return ratio > 2.0f ? (int) ((ratio - 2.0f) * 5) * CRAMPING_MULTIPLIER : NO_BONUS;
    }

    private static int check_or_checkmate(final Player player,
                                          final int depth) {
        return player.getOpponent().isInCheckMate() ? CHECK_MATE_BONUS * depthBonus(depth) : check(player);
    }

    private static int check(final Player player) {
        return player.getOpponent().isInCheck() ? CHECK_BONUS : NO_BONUS;
    }

    private static int depthBonus(final int depth) {
        return depth == 0 ? 1 : 100 * depth;
    }

    private static int castle(final Player player) {
        if (player.isCastled()) {
            return CASTLE_BONUS;
        } else if (player.hasCastlingRights()) {
            return CASTLING_RIGHTS_BONUS;
        }
        return NO_BONUS;
    }

    private static int attacks(final Player player) {
        int score = 0;
        for (final Move move : player.getLegalMoves()) {
            if (move.isAttack()) {
                final Piece mover = move.getMovedPiece();
                final Piece victim = move.getAttackedPiece();
                if (mover.getPieceValue() <= victim.getPieceValue()) {
                    score += 2;
                }
            }
        }
        return score * ATTACK_MULTIPLIER;
    }

    private static int material(final Player player) {
        int score = 0;
        int bishopCount = 0;
        int knightCount = 0;
        int pawnCount = 0;
        for (final int index : player.getActivePieces()) {
            final Piece p = player.getBoard().getPiece(index);
            score += p.getPieceValue() + p.locationBonus();
            switch(p.getPieceType()) {
                case BISHOP: bishopCount++; break;
                case KNIGHT: knightCount++; break;
                case PAWN: pawnCount++; break;
            }
        }
        final int bishopBonus = (bishopCount == 2 ? TWO_BISHOPS_BONUS : NO_BONUS);
        final int knightPairPenalty = knightCount >= 2 && pawnCount < 5 ? -10 : 0;
        return score + bishopBonus + knightPairPenalty;
    }

    private static int pawnStructure(final Player player) {
        return PawnStructureAnalyzer.get().pawnStructureScore(player);
    }

    private static int kingSafety(final Player player) {
        final int tropism = KingSafetyAnalyzer.get().calculateKingTropism(player);
        final int safety = KingSafetyAnalyzer.get().gptKingSafety(player);
        return tropism + safety;
    }

    private static int pieceSafety(final Player player) {
        int penalty = 0;
        final Board board = player.getBoard();
        for (final int pos : player.getActivePieces()) {
            final Piece piece = board.getPiece(pos);
            // Find cheapest attacker
            int cheapestAttacker = Integer.MAX_VALUE;
            boolean isAttacked = false;
            for (final Move move : player.getOpponent().getLegalMoves()) {
                if (move.getDestinationCoordinate() == pos && move.isAttack()) {
                    isAttacked = true;
                    cheapestAttacker = Math.min(cheapestAttacker, move.getMovedPiece().getPieceValue());
                }
            }
            if (!isAttacked) {
                continue; // Piece is safe
            }
            // Count defenders and find cheapest defender
            int defenders = 0;
            int cheapestDefender = Integer.MAX_VALUE;
            for (Move move : player.getLegalMoves()) {
                if (move.getDestinationCoordinate() == pos &&
                        move.getMovedPiece().getPiecePosition() != pos) {
                    defenders++;
                    cheapestDefender = Math.min(cheapestDefender, move.getMovedPiece().getPieceValue());
                }
            }

            // Apply penalty based on exchange outcome
            if (defenders == 0) {
                // Hanging piece - full penalty
                penalty += piece.getPieceValue();
            } else if (cheapestAttacker < piece.getPieceValue()) {
                // Unfavorable exchange likely
                final int exchangeValue = piece.getPieceValue() - cheapestDefender;
                if (exchangeValue > 0) {
                    penalty += exchangeValue;
                }
            }
        }
        return -penalty;
    }

    public String evaluationDetails(final Board board, final int depth) {
        final StringBuilder sb = new StringBuilder();
        final Feature[] allFeatures = Feature.values();
        // White player details
        for (final Feature feature : allFeatures) {
            sb.append("White ").append(formatFeatureName(feature))
                    .append(" : ").append(feature.apply(board.whitePlayer(), depth))
                    .append("\n");
        }
        sb.append("---------------------\n");
        // Black player details
        for (final Feature feature : allFeatures) {
            sb.append("Black ").append(formatFeatureName(feature))
                    .append(" : ").append(feature.apply(board.blackPlayer(), depth))
                    .append("\n");
        }
        sb.append("\nFinal Score = ").append(evaluate(board, depth));
        return sb.toString();
    }

    private static String formatFeatureName(final Feature feature) {
        return feature.name()
                      .replace("_", " ")
                      .toLowerCase()
                      .replaceFirst("^.", String.valueOf(feature.name().charAt(0)));
    }

    private enum GamePhase {
        OPENING,
        MIDDLEGAME,
        ENDGAME,
        DEBUG
    }

    @FunctionalInterface
    interface ScalarFeature {
        int apply(final Player player, int depth);
    }

    enum Feature implements ScalarFeature {
        MOBILITY    {
            public int apply(final Player p,
                             final int d) {
                return mobility(p);
            }
        },
        CRAMPING    {
            public int apply(final Player p,
                             final int d) {
                return cramping(p);
            }
        }, CHECK_AND_MATE {
            public int apply(final Player p,
                             final int d) {
                return check_or_checkmate(p, d);
            }
        }, ATTACKS {
            public int apply(final Player p,
                             final int d) {
                return attacks(p);
            }
        }, CASTLE {
            public int apply(final Player p,
                             final int d) {
                return castle(p);
            }
        }, MATERIAL {
            public int apply(final Player p,
                             final int d) {
                return material(p);
            }
        }, PAWN_STRUCTURE {
            public int apply(final Player p,
                             final int d) {
                return pawnStructure(p);
            }
        }, KING_SAFETY {
            public int apply(final Player p,
                             final int d) {
                return kingSafety(p);
            }
        }, PIECE_SAFETY {
            public int apply(final Player p,
                             final int d) {
                return pieceSafety(p);
            }
        };
    }

}