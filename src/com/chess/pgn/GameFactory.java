package com.chess.pgn;

import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.board.MoveTransition;
import com.chess.engine.classic.player.Player;
import java.util.ArrayList;
import java.util.List;

public class GameFactory {

    public static Game createGame(final PGNGameTags tags,
                                  final String gameText,
                                  final String outcome) {

        List<MoveRecord> moveRecords = new ArrayList<>();

        Board board = FenUtilities.createStandardBoard();
        Player currentPlayer = board.currentPlayer();
        int moveNumber = 1;

        for (final String san : PGNUtilities.processMoveText(gameText)) {
            final String fenBefore = FenUtilities.createFENFromGame(board);
            final Move move = PGNUtilities.createMove(board, san);

            if (!move.isAttack() && move.getMovedPiece() == null) break;

            final MoveTransition transition = currentPlayer.makeMove(move);
            if (!transition.getMoveStatus().isDone()) break;

            board = transition.getToBoard();
            final String fenAfter = FenUtilities.createFENFromGame(board);

            // Note: The player for the move is the one who just played it (the opponent of currentPlayer after the move)
            String player = currentPlayer.toString();

            MoveRecord record = new MoveRecord(moveNumber, player, san, fenBefore, fenAfter);
            moveRecords.add(record);

            currentPlayer = board.currentPlayer();
            moveNumber++;
        }

        return new ValidGame(tags, moveRecords);
    }
}
