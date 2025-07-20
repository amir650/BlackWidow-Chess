package com.chess.pgn;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.player.Player;

import java.sql.*;
import java.util.List;

public class MySqlGamePersistence {

    private final Connection dbConnection;

    public MySqlGamePersistence() {
        this.dbConnection = connectToSQl();
        createGameTable();
        createMovesTable();
        createIndex("moves", "idx_moves_game_id_move_number", "game_id", "move_number");
        createIndex("moves", "idx_moves_fen_before", "fen_before");
    }

    private static Connection connectToSQl() {
        final String url = "jdbc:sqlite:mydb.db";
        try {
            return java.sql.DriverManager.getConnection(url);
        } catch (final java.sql.SQLException e) {
            throw new RuntimeException("Unable to connect to database!", e);
        }
    }

    private void createGameTable() {
        String sql =
                "CREATE TABLE IF NOT EXISTS games (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "event TEXT," +
                "site TEXT," +
                "date TEXT," +
                "round TEXT," +
                "white TEXT," +
                "black TEXT," +
                "result TEXT)";
        try (PreparedStatement ps = dbConnection.prepareStatement(sql)) {
            ps.execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void createMovesTable() {
        String sql =
                "CREATE TABLE IF NOT EXISTS moves (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "game_id INTEGER NOT NULL, " +
                "move_number INTEGER NOT NULL, " +
                "player TEXT NOT NULL, " +
                "san TEXT NOT NULL, " +
                "fen_before TEXT NOT NULL, " +
                "fen_after TEXT NOT NULL, " +
                "FOREIGN KEY (game_id) REFERENCES games(id) ON DELETE CASCADE" +
                ")";
        try (PreparedStatement ps = dbConnection.prepareStatement(sql)) {
            ps.execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void createIndex(final String tableName,
                             final String indexName,
                             final String... columns) {
        if (columns == null || columns.length == 0) {
            throw new IllegalArgumentException("At least one column must be specified for indexing.");
        }
        final String columnList = String.join(",", columns);
        final String sql = "CREATE INDEX IF NOT EXISTS " + indexName + " ON " + tableName + "(" + columnList + ")";
        try (final PreparedStatement ps = dbConnection.prepareStatement(sql)) {
            ps.execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void persistGames(final List<Game> games) {
        try {
            this.dbConnection.setAutoCommit(false); // Begin transaction
            for (final Game game : games) {
                game.saveGame(this);
            }
            dbConnection.commit();
            dbConnection.setAutoCommit(true);
        } catch (final Exception e) {
            try {
                dbConnection.rollback();
            } catch (final Exception ex) {
                throw new RuntimeException("Failed to persist games batch", ex);
            }
            throw new RuntimeException(e);
        }
    }

    public Move getNextBestMove(final Board board,
                                final Player player) {
        final long startTime = System.nanoTime();

        final String fen = FenUtilities.createFENFromGame(board);
        final String sql = "SELECT san, COUNT(*) AS times_played " +
                "FROM moves " +
                "WHERE fen_before = ? " +
                "GROUP BY san " +
                "HAVING times_played > 20 " +
                "ORDER BY times_played DESC " +
                "LIMIT 1";

        System.out.println("Issuing book move query ...");
        try (final PreparedStatement ps = dbConnection.prepareStatement(sql)) {
            ps.setString(1, fen);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    final String bestSAN = rs.getString("san");
                    for (final Move move : player.getLegalMoves()) {
                        final String moveSan = move.toString();
                        if (moveSan.equals(bestSAN)) {
                            final long endTime = System.nanoTime();
                            final long durationMs = (endTime - startTime) / 1_000_000;
                            System.out.printf("Book move lookup completed in %d ms (found: %s)%n",
                                    durationMs, bestSAN);
                            return move;
                        }
                    }
                }
            }
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }

        final long endTime = System.nanoTime();
        final long durationMs = (endTime - startTime) / 1_000_000;
        System.out.printf("Book move lookup completed in %d ms (no move found)%n", durationMs);

        return Move.MoveFactory.getNullMove();
    }

    // Save a ValidGame (tags and moves)
    public void saveValidGame(Game game) throws SQLException {
        final String insertGameSql = "INSERT INTO games (event, site, date, round, white, black, result) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (final PreparedStatement ps = this.dbConnection.prepareStatement(insertGameSql, Statement.RETURN_GENERATED_KEYS)) {
            PGNGameTags tags = game.getTags();
            ps.setString(1, tags.getTagOrDefault("Event", ""));
            ps.setString(2, tags.getTagOrDefault("Site", ""));
            ps.setString(3, tags.getTagOrDefault("Date", ""));
            ps.setString(4, tags.getTagOrDefault("Round", ""));
            ps.setString(5, tags.getTagOrDefault("White", ""));
            ps.setString(6, tags.getTagOrDefault("Black", ""));
            ps.setString(7, tags.getTagOrDefault("Result", ""));
            ps.executeUpdate();
            try (final ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    final long gameId = rs.getLong(1);
                    saveMoves(gameId, game.getMoves());
                } else {
                    throw new SQLException("Could not retrieve generated game ID.");
                }
            }
        }
    }

    private void saveMoves(final long gameId,
                           final List<MoveRecord> moves) throws SQLException {
        final String insertMoveSql = "INSERT INTO moves (game_id, move_number, player, san, fen_before, fen_after) VALUES (?, ?, ?, ?, ?, ?)";
        try (final PreparedStatement ps = this.dbConnection.prepareStatement(insertMoveSql)) {
            for (final MoveRecord move : moves) {
                ps.setLong(1, gameId);
                ps.setInt(2, move.getMoveNumber());
                ps.setString(3, move.getPlayer());
                ps.setString(4, move.getSan());
                ps.setString(5, move.getFenBefore());
                ps.setString(6, move.getFenAfter());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

}
