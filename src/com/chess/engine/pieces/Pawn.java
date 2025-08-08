package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.*;

import java.util.*;

public final class Pawn extends Piece {

    private static final Map<Integer, PawnMoveStrategy> MOVE_STRATEGIES = createMoveStrategies();

    private static Map<Integer, PawnMoveStrategy> createMoveStrategies() {
        return Map.of(8, PawnMoveStrategy.FORWARD,
                      16, PawnMoveStrategy.DOUBLE_FORWARD,
                      7, PawnMoveStrategy.ATTACK_LEFT,
                      9, PawnMoveStrategy.ATTACK_RIGHT);
    }

    private enum PawnMoveStrategy {

        FORWARD(8) {
            @Override
            List<Move> createPawnMove(final Pawn pawn,
                                      final Board board,
                                      final int destination) {
                return board.getPiece(destination) == null ?
                        pawn.addMoveOrPromotion(new PawnMove(board, pawn, destination), destination):
                        Collections.emptyList();
            }
        },

        DOUBLE_FORWARD(16) {
            @Override
            List<Move> createPawnMove(final Pawn pawn,
                                      final Board board,
                                      final int destination) {
                if (pawn.canPerformDoubleMove()) {
                    final int behindDestination = pawn.calculateDestination(8);
                    if (board.getPiece(destination) == null && board.getPiece(behindDestination) == null) {
                        return Collections.singletonList(new PawnJump(board, pawn, destination));
                    }
                }
                return Collections.emptyList();
            }
        },

        ATTACK_LEFT (7){
            @Override
            List<Move> createPawnMove(final Pawn pawn,
                                      final Board board,
                                      final int destination) {
                if (pawn.isInvalidDiagonalMove(true)) {
                    return Collections.emptyList();
                }
                return pawn.createDiagonalAttack(board, destination, true);
            }
        },

        ATTACK_RIGHT (9){
            @Override
            List<Move> createPawnMove(final Pawn pawn,
                                      final Board board,
                                      final int destination) {
                if (pawn.isInvalidDiagonalMove(false)) {
                    return Collections.emptyList();
                }
                return pawn.createDiagonalAttack(board, destination, false);
            }
        };

        private final int offset;

        PawnMoveStrategy(final int offset) {
            this.offset = offset;
        }

        abstract List<Move> createPawnMove(Pawn pawn, Board board, int destination);

        private int getOffset() {
            return this.offset;
        }
    }

    Pawn(final Alliance alliance,
         final int piecePosition,
         final boolean isFirstMove) {
        super(PieceType.PAWN, alliance, piecePosition, isFirstMove);
    }

    @Override
    public int locationBonus() {
        return this.pieceAlliance.pawnBonus(this.piecePosition);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final PawnMoveStrategy strategy : PawnMoveStrategy.values()) {
            final int candidateOffset = strategy.getOffset();
            final int candidateDestination = calculateDestination(candidateOffset);
            if (BoardUtils.isValidTileCoordinate(candidateDestination)) {
                final PawnMoveStrategy moveStrategy = MOVE_STRATEGIES.get(candidateOffset);
                legalMoves.addAll(moveStrategy.createPawnMove(this, board, candidateDestination));
            }
        }
        return Collections.unmodifiableList(legalMoves);
    }

    private int calculateDestination(final int offset) {
        return this.piecePosition + (this.pieceAlliance.getDirection() * offset);
    }

    private boolean canPerformDoubleMove() {
        return this.isFirstMove() &&
                ((BoardUtils.SECOND_ROW.get(this.piecePosition) && this.pieceAlliance.isBlack()) ||
                 (BoardUtils.SEVENTH_ROW.get(this.piecePosition) && this.pieceAlliance.isWhite()));
    }

    private boolean isInvalidDiagonalMove(final boolean isLeftAttack) {
        if (isLeftAttack) {
            return (BoardUtils.EIGHTH_COLUMN.get(this.piecePosition) && this.pieceAlliance.isWhite()) ||
                    (BoardUtils.FIRST_COLUMN.get(this.piecePosition) && this.pieceAlliance.isBlack());
        } else {
            return (BoardUtils.FIRST_COLUMN.get(this.piecePosition) && this.pieceAlliance.isWhite()) ||
                    (BoardUtils.EIGHTH_COLUMN.get(this.piecePosition) && this.pieceAlliance.isBlack());
        }
    }

    private List<Move> createDiagonalAttack(final Board board,
                                            final int destination,
                                            final boolean isLeftAttack) {
        final Piece targetPiece = board.getPiece(destination);
        return targetPiece != null ? createDirectAttack(board, destination, targetPiece) :
                                     createEnPassantAttack(board, destination, isLeftAttack);
    }

    private List<Move> createDirectAttack(final Board board,
                                          final int destination,
                                          final Piece targetPiece) {
        if (this.pieceAlliance == targetPiece.getPieceAllegiance()) {
            return Collections.emptyList();
        }
        return addMoveOrPromotion(new PawnAttackMove(board, this, destination, targetPiece), destination);
    }

    private List<Move> createEnPassantAttack(final Board board,
                                             final int destination,
                                             final boolean isLeftAttack) {
        final Piece enPassantPawn = board.getEnPassantPawn();
        if (enPassantPawn == null || this.pieceAlliance == enPassantPawn.getPieceAllegiance()) {
            return Collections.emptyList();
        }
        final int expectedEnPassantPosition = calculateEnPassantPosition(isLeftAttack);
        if (enPassantPawn.getPiecePosition() == expectedEnPassantPosition) {
            return Collections.singletonList(new PawnEnPassantAttack(board, this, destination, enPassantPawn));
        }
        return Collections.emptyList();
    }

    private int calculateEnPassantPosition(final boolean isLeftAttack) {
        final int direction = isLeftAttack ?
                this.pieceAlliance.getOppositeDirection() :
                -this.pieceAlliance.getOppositeDirection();
        return this.piecePosition + direction;
    }

    private List<Move> addMoveOrPromotion(final Move baseMove,
                                          final int destination) {
        final Alliance alliance = baseMove.getMovedPiece().getPieceAllegiance();
        if (this.pieceAlliance.isPawnPromotionSquare(destination)) {
            return List.of(
                    new PawnPromotion(baseMove, PieceUtils.INSTANCE.getQueen(alliance, destination, true)),
                    new PawnPromotion(baseMove, PieceUtils.INSTANCE.getRook(alliance, destination, true)),
                    new PawnPromotion(baseMove, PieceUtils.INSTANCE.getBishop(alliance, destination, true)),
                    new PawnPromotion(baseMove, PieceUtils.INSTANCE.getKnight(alliance, destination, true))
            );
        } else {
            return Collections.singletonList(baseMove);
        }
    }

    @Override
    public String toString() {
        return this.pieceType.toString();
    }

    @Override
    public Pawn getMovedPiece(final Move move) {
        return PieceUtils.INSTANCE.getPawn(move.getMovedPiece().getPieceAllegiance(), move.getDestinationCoordinate(), true);
    }
}