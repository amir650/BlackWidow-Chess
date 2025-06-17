package com.chess.pgn;

import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.player.Player;

import java.sql.*;
import java.util.ArrayList;
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

    private static Connection createDBConnection() {
        final String url = "jdbc:mysql://localhost:3306/chess_games";
        final String user = "root";
        final String pass = "powerpc123";
        try {
            return java.sql.DriverManager.getConnection(url, user, pass);
        } catch (java.sql.SQLException e) {
            throw new RuntimeException("Unable to connect to database!", e);
        }
    }

    private static Connection connectToSQl() {
        String url = "jdbc:sqlite:mydb.db";
        try {
            return java.sql.DriverManager.getConnection(url);
        } catch (java.sql.SQLException e) {
            throw new RuntimeException("Unable to connect to database!", e);
        }
    }

    private void createGameTable() {
        String sql = "CREATE TABLE IF NOT EXISTS games (" +
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
            e.printStackTrace();
        }
    }

    private void createMovesTable() {
        String sql = "CREATE TABLE IF NOT EXISTS moves (" +
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }

    private void createIndex(final String column,
                             final String indexName) {
        String sql = "CREATE INDEX IF NOT EXISTS " + indexName + " ON games(" + column + ")";
        try (PreparedStatement ps = dbConnection.prepareStatement(sql)) {
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
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
        } catch (Exception e) {
            try { dbConnection.rollback(); } catch (Exception ex) {}
            throw new RuntimeException("Failed to persist games batch", e);
        }
    }

    public Move getNextBestMove(Board board, Player player) {
        final String fen = FenUtilities.createFENFromGame(board);
        final String sql = "SELECT san, COUNT(*) AS times_played " +
                "FROM moves " +
                "WHERE fen_before = ? " +
                "GROUP BY san " +
                "HAVING times_played > 20 " +
                "ORDER BY times_played DESC " +
                "LIMIT 1";

        try (final PreparedStatement ps = dbConnection.prepareStatement(sql)) {
            ps.setString(1, fen);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    final String bestSAN = rs.getString("san");
                    for (final Move move : player.getLegalMoves()) {
                        // Use your engine's SAN function; fallback to toString if you have no better option
                        String moveSan = move.toString();
                        try {
                            // If your Move has a toSan(Board) method, use it!
                            moveSan = move.toString();
                        } catch (Exception ignored) {}
                        if (moveSan.equals(bestSAN)) {
                            return move;
                        }
                    }
                }
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return Move.MoveFactory.getNullMove();
    }

    // Save a ValidGame (tags and moves)
    public void saveValidGame(Game game) throws SQLException {
        String insertGameSql = "INSERT INTO games (event, site, date, round, white, black, result) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = this.dbConnection.prepareStatement(insertGameSql, Statement.RETURN_GENERATED_KEYS)) {
            PGNGameTags tags = game.getTags();
            ps.setString(1, tags.getTagOrDefault("Event", ""));
            ps.setString(2, tags.getTagOrDefault("Site", ""));
            ps.setString(3, tags.getTagOrDefault("Date", ""));
            ps.setString(4, tags.getTagOrDefault("Round", ""));
            ps.setString(5, tags.getTagOrDefault("White", ""));
            ps.setString(6, tags.getTagOrDefault("Black", ""));
            ps.setString(7, tags.getTagOrDefault("Result", ""));
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    long gameId = rs.getLong(1);
                    saveMoves(gameId, game.getMoves());
                } else {
                    throw new SQLException("Could not retrieve generated game ID.");
                }
            }
        }
    }

    // Save moves for a game
    private void saveMoves(long gameId, List<MoveRecord> moves) throws SQLException {
        String insertMoveSql = "INSERT INTO moves (game_id, move_number, player, san, fen_before, fen_after) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = this.dbConnection.prepareStatement(insertMoveSql)) {
            for (MoveRecord move : moves) {
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

    // Load a ValidGame (tags and moves)
    public ValidGame loadValidGame(long gameId) throws SQLException {
        String selectGameSql = "SELECT * FROM games WHERE id = ?";
        PGNGameTags tags;
        try (PreparedStatement ps = this.dbConnection.prepareStatement(selectGameSql)) {
            ps.setLong(1, gameId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Build tags from DB columns using the builder
                    PGNGameTags.TagsBuilder builder = new PGNGameTags.TagsBuilder();
                    builder.addTag("Event", rs.getString("event"));
                    builder.addTag("Site", rs.getString("site"));
                    builder.addTag("Date", rs.getString("date"));
                    builder.addTag("Round", rs.getString("round"));
                    builder.addTag("White", rs.getString("white"));
                    builder.addTag("Black", rs.getString("black"));
                    builder.addTag("Result", rs.getString("result"));
                    tags = builder.build();
                } else {
                    throw new SQLException("Game not found with id: " + gameId);
                }
            }
        }
        List<MoveRecord> moves = loadMoves(gameId);
        return new ValidGame(tags, moves);
    }

    // Load all moves for a game
    private List<MoveRecord> loadMoves(long gameId) throws SQLException {
        String selectMovesSql = "SELECT * FROM moves WHERE game_id = ? ORDER BY move_number ASC";
        List<MoveRecord> moves = new ArrayList<>();
        try (PreparedStatement ps = this.dbConnection.prepareStatement(selectMovesSql)) {
            ps.setLong(1, gameId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int moveNumber = rs.getInt("move_number");
                    String player = rs.getString("player");
                    String san = rs.getString("san");
                    String fenBefore = rs.getString("fen_before");
                    String fenAfter = rs.getString("fen_after");
                    MoveRecord move = new MoveRecord(moveNumber, player, san, fenBefore, fenAfter);
                    moves.add(move);
                }
            }
        }
        return moves;
    }
}
