package ru.samarin.chess;

public class Square {
    public int i;
    public int j;
    
    private static String chessLetters = "abcdefgh";
    private static String chessDigits = "12345678";
    private static final int n = 8;
    
    public Square(int i, int j) {
        /*
        if(i<0 || n<=i || j<0 || n<=j) {
            throw new RuntimeException("Invalid arguments i, j for class Square constructor");
        }
        */
        this.i = i;
        this.j = j;
    }
    
    public Square(Square sq) {
        this.i = sq.i;
        this.j = sq.j;
    }
    
    public Square(String s) {
        Square sq = parse(s);
        if(sq == null) {
            throw new RuntimeException("Invalid string argument for class Square constructor");
        }
        this.i = sq.i;
        this.j = sq.j;
    }



    public boolean equals(Square square) {
        return i == square.i && j == square.j;
    }

    public boolean equals(String s) {
        return equals(new Square(s));
    }

    public static Square parse(String s) {
        if(s.length() != 2) {
            return null;
        }

        int j = chessLetters.indexOf(s.charAt(0));
        int i = chessDigits.indexOf(s.charAt(1));
        if( (i == -1) || (j == -1) ) {
            return null;
        }

        return new Square(i, j);
    }
    
    
    public boolean isNearRightBorder() {
        return (j == n-1);
    }
    
    public boolean isNearLeftBorder() {
        return (j == 0);
    }
    
    public boolean isNearUpBorder() {
        return (i == n-1);
    }
    
    public boolean isNearDownBorder() {
        return (i == 0);
    }
    
    
    public boolean isOut() {
        return i < 0 || n <= i || j < 0 || n <= j;

    }
    
    public Square goRight() {
        return new Square(i, j+1);
    }
    
    public Square goLeft() {
        return new Square(i, j-1);
    }
    
    public Square goUp() {
        return new Square(i+1, j);
    }
    
    public Square goDown() {
        return new Square(i-1, j);
    }
    
    
    public String toString() {
        return chessLetters.substring(j,j+1) + chessDigits.substring(i,i+1);
    }
}