package com.chess.pgn;

import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.board.MoveTransition;
import com.chess.engine.classic.player.Player;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PGNUtilities {

    private static final Pattern PGN_PATTERN = Pattern.compile("\\[(\\w+)\\s+\"(.*?)\"\\]$");

    private PGNUtilities() {
        throw new RuntimeException("Not Instantiable!");
    }

    public static void persistPGNFile(final File pgnFile,
                                      final MySqlGamePersistence persistence) throws IOException {
        int count = 0;
        int validCount = 0;
        final int BATCH_SIZE = 10000;

        try (final BufferedReader br = new BufferedReader(new FileReader(pgnFile))) {
            String line;
            List<Game> games = new ArrayList<>();
            PGNGameTags.TagsBuilder tagsBuilder = new PGNGameTags.TagsBuilder();
            StringBuilder gameTextBuilder = new StringBuilder();

            while ((line = br.readLine()) != null) {
                if (line.isEmpty()) continue;

                if (isTag(line)) {
                    handleTagLine(line, tagsBuilder);
                } else if (isEndOfGame(line)) {
                    handleEndOfGameLine(line, tagsBuilder, gameTextBuilder, games);
                    count++;
                    Game lastGame = games.isEmpty() ? null : games.get(games.size() - 1);
                    if (lastGame != null && lastGame.isValid()) validCount++;
                    if (games.size() >= BATCH_SIZE) {
                        if (!persistBatchAndPrompt(games, persistence, count, validCount, pgnFile.getName())) {
                            break; // User chose not to continue
                        }
                    }
                    // Reset for next game
                    tagsBuilder = new PGNGameTags.TagsBuilder();
                    gameTextBuilder = new StringBuilder();
                } else {
                    gameTextBuilder.append(line).append(" ");
                }
            }
            if (!games.isEmpty()) {
                persistence.persistGames(games);
            }
        }

        System.out.printf("Finished building book from PGN: %s | Total games: %d, Valid: %d\n",
                pgnFile.getName(), count, validCount);
    }

    private static void handleTagLine(final String line,
                                      final PGNGameTags.TagsBuilder tagsBuilder) {
        final Matcher matcher = PGN_PATTERN.matcher(line);
        if (matcher.find()) {
            tagsBuilder.addTag(matcher.group(1), matcher.group(2));
        }
    }

    private static void handleEndOfGameLine(final String line,
                                            final PGNGameTags.TagsBuilder tagsBuilder,
                                            final StringBuilder gameTextBuilder,
                                            final List<Game> games) {
        final String[] tokens = line.trim().split("\\s+");
        final String outcome = tokens[tokens.length - 1];
        gameTextBuilder.append(line.replace(outcome, "")).append(" ");
        final String gameText = gameTextBuilder.toString().trim();

        if (!gameText.isEmpty() && gameText.length() > 20) {
            tagsBuilder.addTag("Result", outcome);
            final Game game = createGame(tagsBuilder.build(), gameText);
            System.out.printf("Finished parsing game: %s\n", game);
            if (game.isValid()) {
                games.add(game);
            } else {
                System.out.println("\tInvalid game: " +game);
            }
        }
    }

    private static boolean persistBatchAndPrompt(final List<Game> games,
                                                 final MySqlGamePersistence persistence,
                                                 final int count,
                                                 final int validCount,
                                                 final String fileName) {
        System.out.printf("\tBatch persisting %d games... (total so far: %d, valid: %d)\n", games.size(), count, validCount);
        persistence.persistGames(games);
        System.out.println("\tDone persisting batch.");
        games.clear();
        // Prompt user if they want to continue or exit early
        System.out.print("Continue importing? (Y to continue, N to stop): ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().trim().toUpperCase();
        if (!input.equals("Y")) {
            System.out.println("Import stopped early by user.");
            System.out.printf("Finished (early) building book from PGN: %s | Total games: %d, Valid: %d\n", fileName, count, validCount);
            return false;
        }
        return true;
    }


    public static Game createGame(final PGNGameTags tags,
                                  final String gameText) {
        Board board = FenUtilities.createStandardBoard();
        Player currentPlayer = board.currentPlayer();
        final List<MoveRecord> moveRecords = new ArrayList<>();
        int moveNumber = 1;
        for (final String san : PGNUtilities.processMoveText(gameText)) {
            final String fenBefore = FenUtilities.createFENFromGame(board);
            final Move move = PGNUtilities.createMove(board, san);
            if (!move.isAttack() && move.getMovedPiece() == null) {
                return new InvalidGame("bad fucking move!", tags);
            }
            final MoveTransition transition = currentPlayer.makeMove(move);
            if (!transition.getMoveStatus().isDone()) {
                return new InvalidGame("bad fucking move!!", tags);
            }
            board = transition.getToBoard();
            String fenAfter = FenUtilities.createFENFromGame(board);
            // Record the move: player is the currentPlayer before the move.
            moveRecords.add(new MoveRecord(moveNumber, currentPlayer.toString(), san, fenBefore, fenAfter));
            currentPlayer = board.currentPlayer();
            moveNumber++;
        }
        return new ValidGame(tags, moveRecords);
    }

    public static List<String> processMoveText(final String gameText) {
        if (!gameText.startsWith("1.")) return Collections.emptyList();
        String cleaned = removeParenthesis(gameText).replaceAll("\\$[0-9]+", "").replaceAll("[0-9]+\\s*\\.\\.\\.", "");
        String[] rawTurns = cleaned.split("\\s*[0-9]+\\.");
        List<String> result = new ArrayList<>();

        for (String turn : rawTurns) {
            String trimmed = turn.trim();
            if (trimmed.isEmpty()) continue;
            String[] tokens = trimmed.split("\\s+");
            for (String move : tokens) {
                if (!move.isEmpty() && !isGameEndToken(move)) {
                    result.add(move);
                }
            }
        }
        return result;
    }

    public static Move createMove(final Board board, final String pgnText) {
        String trimmedPGN = pgnText.replaceAll("[+#]", ""); // Remove check/mate markers
        for (Move move : board.currentPlayer().getLegalMoves()) {
            String moveString = move.toString().replaceAll("[+#]", "");
            if (moveString.equalsIgnoreCase(trimmedPGN)) {
                return move;
            }
        }
        return Move.MoveFactory.getNullMove();
    }

    private static boolean isTag(final String gameText) {
        return gameText.startsWith("[") && gameText.endsWith("]");
    }

    private static boolean isEndOfGame(final String gameText) {
        return gameText.endsWith("1-0") || gameText.endsWith("0-1") ||
                gameText.endsWith("1/2-1/2") || gameText.endsWith("*");
    }

    private static boolean isGameEndToken(final String token) {
        return token.equals("1-0") || token.equals("0-1") ||
                token.equals("1/2-1/2") || token.equals("*");
    }

    private static String removeParenthesis(final String gameText) {
        int depth = 0;
        StringBuilder result = new StringBuilder();
        for (char c : gameText.toCharArray()) {
            if (c == '(' || c == '{') {
                depth++;
            } else if (c == ')' || c == '}') {
                depth--;
            } else if (depth == 0) {
                result.append(c);
            }
        }
        return result.toString();
    }
}
