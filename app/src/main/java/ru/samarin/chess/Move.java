package ru.samarin.chess;

public class Move {
    
    public enum Type {
        ORDINARY, PROMOTION, /*EN_PASSANT,*/ SHORT_CASTLING, LONG_CASTLING
    }
    
    public Type type;
    public Piece piece;
    public Square firstSquare;
    public Square secondSquare;
    
    public boolean isCapture;
//	public Square enPassantSquare;
	public Piece promotionPiece;
    public Piece capturedPiece;
    
    
    public static Move getPromotionMove(Square first, Square second, Piece piece) {
        Move move = new Move();
        move.type = Type.PROMOTION;
        move.piece = Piece.PAWN;
        move.firstSquare = first;
        move.secondSquare = second;
        move.promotionPiece = piece;
        move.isCapture = false;
        return move;
    }
    
    
    public static Move getPromotionWithCaptureMove(Square first, Square second, Piece piece, Piece capturedPiece) {
        Move move = getPromotionMove(first, second, piece);
        move.isCapture = true;
        move.capturedPiece = capturedPiece;
        return move;
    }

    
    public static Move getShortCastlingMove(Color color) {
        Move move = new Move();
        switch(color) {
            case WHITE:
                move.firstSquare = new Square("e1");
                move.secondSquare = new Square("g1");
                break;
            case BLACK:
                move.firstSquare = new Square("e8");
                move.secondSquare = new Square("g8");
                break;
        }
        move.type = Type.SHORT_CASTLING;
        return move;
    }
    
    public static Move getLongCastlingMove(Color color) {
        Move move = new Move();
        switch(color) {
            case WHITE:
                move.firstSquare = new Square("e1");
                move.secondSquare = new Square("c1");
                break;
            case BLACK:
                move.firstSquare = new Square("e8");
                move.secondSquare = new Square("c8");
                break;
        }
        move.type = Type.LONG_CASTLING;
        return move;
    }
    
    public static Move getOrdinaryMove(Piece piece, Square first, Square second) {
        Move move = new Move();
        move.type = Type.ORDINARY;
        move.piece = piece;
        move.firstSquare = first;
        move.secondSquare = second;
        move.isCapture = false;
        
        return move;
    }
    
    public static Move getOrdinaryCaptureMove(Piece piece, Square first, Square second, Piece capturedPiece) {
        Move move = getOrdinaryMove(piece, first, second);
        move.isCapture = true;
        move.capturedPiece = capturedPiece;
        
        return move;
    }
    
    
    public String toString() {
        if(type == Type.SHORT_CASTLING) {
            return "0-0";
        }
        if(type == Type.LONG_CASTLING) {
            return "0-0-0";
        }
        
        String result = new String();
        if(piece != Piece.PAWN) {
            result += piece.string;
        }
        result += firstSquare.toString();
        if(isCapture) {
            result += ":";
        } else {
            result += "-";
        }
        result += secondSquare.toString();
        if(type == Type.PROMOTION) {
            result += promotionPiece.string;
        }
        return result;
    }
    
}