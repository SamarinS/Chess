package ru.samarin.chess;

public enum Piece {
    NONE   (".", "", -1),
    PAWN   ("p", "pawn", 0),
    KNIGHT ("N", "knight", 1),
    BISHOP ("B", "bishop", 2),
    ROOK   ("R", "rook", 3),
    QUEEN  ("Q", "queen", 4),
    KING   ("K", "king", 5);
    
    public final String string;
    public final String fullString;
    public final int index;
    
    private Piece(String s, String fs, int i) {
        string = s;
        fullString = fs;
        index = i;
    }
}