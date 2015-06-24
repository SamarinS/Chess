package ru.samarin.chess;


public class Board {
    private static final int n = 8; //board size
    
    private Color[][] colors = new Color[n][n];
    private Piece[][] pieces = new Piece[n][n];
    
    
    public Board() {
        clear();
    }
    
    public void setSquare(Square sq, Piece p, Color c) {
        pieces[sq.i][sq.j] = p;
        colors[sq.i][sq.j] = c;
    }
    
    public void setSquare(String s, Piece p, Color c) {
        Square sq = new Square(s);
        pieces[sq.i][sq.j] = p;
        colors[sq.i][sq.j] = c;
    }

    public void setSquareEmpty(String s) {
        setSquare(s, Piece.NONE, Color.NONE);
    }

    public void setSquareEmpty(Square sq) {
        setSquare(sq, Piece.NONE, Color.NONE);
    }
    
    public Piece getPiece(Square sq) {
        return pieces[sq.i][sq.j];
    }
    
    public Piece getPiece(String sqString) {
        Square sq = new Square(sqString);
        return pieces[sq.i][sq.j];
    }
    
    public Color getColor(Square sq) {
        return colors[sq.i][sq.j];
    }
    
    public Color getColor(String sqString) {
        Square sq = new Square(sqString);
        return colors[sq.i][sq.j];
    }

    public void clear() {
        for(int i = 0;i < n;i++) {
            for(int j = 0;j < n;j++) {
                setSquareEmpty(new Square(i, j));
            }
        }
    }
    
// --Commented out by Inspection START (18.06.2015 14:32):
//    public void print() {
//        System.out.println("Board colors:");
//        for(int i = n-1;i >= 0;i--) {
//            for(int j = 0;j < n;j++) {
//                String s = ".";
//                Color c = colors[i][j];
//                if(c == Color.WHITE) {
//                    s = "w";
//                } else if(c == Color.BLACK){
//                    s = "b";
//                }
//                System.out.printf("%s ", s);
//            }
//            System.out.printf("\n");
//        }
//
//        System.out.println("Board pieces:");
//        for(int i = n-1;i >= 0;i--) {
//            for(int j = 0;j < n;j++) {
//                System.out.printf("%s ", pieces[i][j].string);
//            }
//            System.out.printf("\n");
//        }
//    }
// --Commented out by Inspection STOP (18.06.2015 14:32)

    
    
}