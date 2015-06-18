package ru.samarin.chess;

public enum Piece {
    NONE   (".", ""),
    PAWN   ("p", "pawn"),
    KNIGHT ("N", "knight"),
    BISHOP ("B", "bishop"),
    ROOK   ("R", "rook"),
    QUEEN  ("Q", "queen"),
    KING   ("K", "king");
    
    public final String string;
    public final String fullString;

    private Piece(String s, String fs) {
        string = s;
        fullString = fs;
    }
}