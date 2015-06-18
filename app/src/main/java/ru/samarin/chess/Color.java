package ru.samarin.chess;

public enum Color {
    WHITE ("white"),
    BLACK ("black"),
    NONE ("");

    public final String fullString;

    private Color(String fs) {
        fullString = fs;
    }

    public static Color getOppositeColor(Color color) {
        
        if(color == WHITE) {
            return BLACK;
        } else if(color == BLACK){
            return WHITE;
        } else {
            throw new RuntimeException("Invalid argument value <NONE>");
        }
    }
}