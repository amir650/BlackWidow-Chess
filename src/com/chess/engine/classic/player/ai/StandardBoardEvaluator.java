package com.chess.engine.classic.player.ai;

import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.pieces.Piece;
import com.chess.engine.classic.player.Player;

import static com.chess.engine.classic.pieces.Piece.PieceType.BISHOP;

public final class StandardBoardEvaluator implements BoardEvaluator {

    private static final int CHECK_MATE_BONUS = 10000;
    private static final int CHECK_BONUS = 45;
    private static final int CASTLE_BONUS = 25;
    private static final int MOBILITY_MULTIPLIER = 5;
    private static final int ATTACK_MULTIPLIER = 1;
    private static final int TWO_BISHOPS_BONUS = 25;
    private static final int CRAMPING_MULTIPLIER = 2;

    private static final StandardBoardEvaluator INSTANCE = new StandardBoardEvaluator();

    private StandardBoardEvaluator() {}

    public static StandardBoardEvaluator get() {
        return INSTANCE;
    }

    @Override
    public int evaluate(final Board board, final int depth) {
        return score(board.whitePlayer(), depth) - score(board.blackPlayer(), depth);
    }

    private static int score(final Player player, final int depth) {
        return mobility(player) +
               cramping(player) +
               check_or_checkmate(player, depth) +
               attacks(player) +
               castle(player) +
               pieceEvaluations(player) +
               pawnStructure(player) +
               kingSafety(player);
    }

    private static int mobility(final Player player) {
        return MOBILITY_MULTIPLIER * player.getLegalMoves().size();
    }

    private static int cramping(final Player player) {
        int own = player.getLegalMoves().size();
        int opp = player.getOpponent().getLegalMoves().size();
        if (opp == 0) return 0;
        float ratio = (float) own / opp;
        return ratio > 1.5f ? (int) ((ratio - 1.5f) * 10) * CRAMPING_MULTIPLIER : 0;
    }

    private static int check_or_checkmate(final Player player, final int depth) {
        return player.getOpponent().isInCheckMate() ? CHECK_MATE_BONUS * depthBonus(depth) : check(player);
    }

    private static int check(final Player player) {
        return player.getOpponent().isInCheck() ? CHECK_BONUS : 0;
    }

    private static int depthBonus(final int depth) {
        return depth == 0 ? 1 : 100 * depth;
    }

    private static int castle(final Player player) {
        return player.isCastled() ? CASTLE_BONUS : 0;
    }

    private static int attacks(final Player player) {
        int score = 0;
        for (final Move move : player.getLegalMoves()) {
            if (move.isAttack()) {
                Piece mover = move.getMovedPiece();
                Piece victim = move.getAttackedPiece();
                if (mover.getPieceValue() <= victim.getPieceValue()) {
                    score++;
                }
            }
        }
        return score * ATTACK_MULTIPLIER;
    }

    private static int pieceEvaluations(final Player player) {
        int score = 0;
        int bishops = 0;
        for (int index : player.getActivePieces()) {
            Piece p = player.getBoard().getPiece(index);
            score += p.getPieceValue() + p.locationBonus();
            if (p.getPieceType() == BISHOP) bishops++;
        }
        return score + (bishops == 2 ? TWO_BISHOPS_BONUS : 0);
    }

    private static int pawnStructure(final Player player) {
        return PawnStructureAnalyzer.get().pawnStructureScore(player);
    }

    private static int kingSafety(final Player player) {
        int f1 = KingSafetyAnalyzer.get().calculateKingTropism(player);
        //int f2 = KingSafetyAnalyzer.get().gptKingSafety(player);
        //int f3 = KingSafetyAnalyzer.get().pawnShieldPenalty(player);
        //return 0;
        //return f1 + f2;
        return f1;
    }

    public String evaluationDetails(final Board board, final int depth) {
        return
                ("White Mobility : " + mobility(board.whitePlayer()) + "\n") +
                        "White kingThreats : " + check_or_checkmate(board.whitePlayer(), depth) + "\n" +
                        "White attacks : " + attacks(board.whitePlayer()) + "\n" +
                        "White castle : " + castle(board.whitePlayer()) + "\n" +
                        "White pieceEval : " + pieceEvaluations(board.whitePlayer()) + "\n" +
                        "White pawnStructure : " + pawnStructure(board.whitePlayer()) + "\n" +
                        "---------------------\n" +
                        "Black Mobility : " + mobility(board.blackPlayer()) + "\n" +
                        "Black kingThreats : " + check_or_checkmate(board.blackPlayer(), depth) + "\n" +
                        "Black attacks : " + attacks(board.blackPlayer()) + "\n" +
                        "Black castle : " + castle(board.blackPlayer()) + "\n" +
                        "Black pieceEval : " + pieceEvaluations(board.blackPlayer()) + "\n" +
                        "Black pawnStructure : " + pawnStructure(board.blackPlayer()) + "\n\n" +
                        "Final Score = " + evaluate(board, depth);
    }

}
