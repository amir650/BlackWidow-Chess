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
        this.dbConnection = createDBConnection();
        createGameTable();
        createMovesTable();
        //createIndex("outcome", "OutcomeIndex");
        //createIndex("moves", "MoveIndex");
        // Optionally: createOutcomeIndex(); createMovesIndex();
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

    private void createGameTable() {
        String sql = "CREATE TABLE IF NOT EXISTS games (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                "event VARCHAR(255)," +
                "site VARCHAR(255)," +
                "date VARCHAR(50)," +
                "round VARCHAR(50)," +
                "white VARCHAR(100)," +
                "black VARCHAR(100)," +
                "result VARCHAR(20))";
        try (PreparedStatement ps = dbConnection.prepareStatement(sql)) {
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createMovesTable() {
        String sql = "CREATE TABLE IF NOT EXISTS moves (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "game_id BIGINT NOT NULL, " +
                "move_number INT NOT NULL, " +
                "player VARCHAR(10) NOT NULL, " +
                "san VARCHAR(20) NOT NULL, " +
                "fen_before VARCHAR(100) NOT NULL, " +
                "fen_after VARCHAR(100) NOT NULL, " +
                "FOREIGN KEY (game_id) REFERENCES games(id) ON DELETE CASCADE" +
                ")";
        try (PreparedStatement ps = dbConnection.prepareStatement(sql)) {
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createIndex(String column, String indexName) {
        String sql = "CREATE INDEX IF NOT EXISTS " + indexName + " ON games(" + column + ")";
        try (PreparedStatement ps = dbConnection.prepareStatement(sql)) {
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void persistGames(List<Game> games) {
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
        String fen = FenUtilities.createFENFromGame(board);
        String sql =
                "SELECT m.san, COUNT(*) AS times_played, " +
                        "(SUM(CASE WHEN (g.result = '1-0' AND m.player = 'White') OR " +
                        "               (g.result = '0-1' AND m.player = 'Black') THEN 1 ELSE 0 END) * 1.0 / COUNT(*)) AS win_rate " +
                        "FROM moves m " +
                        "JOIN games g ON m.game_id = g.id " +
                        "WHERE m.fen_before = ? " + // <--- parameter marker
                        "GROUP BY m.san " +
                        "HAVING COUNT(*) > 0 " +
                        "ORDER BY win_rate DESC, times_played DESC " +
                        "LIMIT 1";
        try (PreparedStatement ps = dbConnection.prepareStatement(sql)) {
            ps.setString(1, fen);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String bestSAN = rs.getString("san");
                    for (Move move : player.getLegalMoves()) {
                        if (move.toString().equals(bestSAN)) {
                            return move;
                        }
                    }
                }
            }
        } catch (Exception e) {
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
