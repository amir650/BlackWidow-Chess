package com.chess.pgn;

public class MoveRecord {
    private int moveNumber;
    private String player;     // "White" or "Black"
    private String san;        // Standard Algebraic Notation
    private String fenBefore;  // FEN before this move
    private String fenAfter;   // FEN after this move

    public MoveRecord(int moveNumber, String player, String san, String fenBefore, String fenAfter) {
        this.moveNumber = moveNumber;
        this.player = player;
        this.san = san;
        this.fenBefore = fenBefore;
        this.fenAfter = fenAfter;
    }

    public int getMoveNumber() {
        return moveNumber;
    }

    public void setMoveNumber(int moveNumber) {
        this.moveNumber = moveNumber;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getSan() {
        return san;
    }

    public void setSan(String san) {
        this.san = san;
    }

    public String getFenBefore() {
        return fenBefore;
    }

    public void setFenBefore(String fenBefore) {
        this.fenBefore = fenBefore;
    }

    public String getFenAfter() {
        return fenAfter;
    }

    public void setFenAfter(String fenAfter) {
        this.fenAfter = fenAfter;
    }

    @Override
    public String toString() {
        return "MoveRecord{" +
                "moveNumber=" + moveNumber +
                ", player='" + player + '\'' +
                ", san='" + san + '\'' +
                ", fenBefore='" + fenBefore + '\'' +
                ", fenAfter='" + fenAfter + '\'' +
                '}';
    }
}
