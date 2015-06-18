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
        boolean flag = true;
        if(s.length() != 2) {
            flag = false;
        }
        
        
        j = chessLetters.indexOf(s.charAt(0));
        i = chessDigits.indexOf(s.charAt(1));
        
        if( (i == -1) || (j == -1) ) {
            flag = false;
        }
        
        if(flag == false) {
            throw new RuntimeException("Invalid string argument for class Square constructor");
        }
    }



    boolean equals(Square square) {
        if(i == square.i && j == square.j) {
            return true;
        } else {
            return false;
        }
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
        if(i<0 || n<=i || j<0 || n<=j) {
            return true;
        }
        
        return false;
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