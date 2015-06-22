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
    
    
    public static Move getPromotionWithCaptureMove(Square first, Square second, Piece piece) {
        Move move = getPromotionMove(first, second, piece);
        move.isCapture = true;
        return move;
    }

    
    public static Move getShortCastlingMove() {
        Move move = new Move();
        move.type = Type.SHORT_CASTLING;
        return move;
    }
    
    public static Move getLongCastlingMove() {
        Move move = new Move();
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
    
    public static Move getOrdinaryCaptureMove(Piece piece, Square first, Square second) {
        Move move = getOrdinaryMove(piece, first, second);
        move.isCapture = true;

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


    public boolean equals(Square from, Square to, Color sideToMove) {
        boolean flag = false;
        switch(type) {
            case SHORT_CASTLING:
                if(sideToMove == Color.WHITE && from.equals("e1") && to.equals("g1")) {
                    flag = true;
                }
                if(sideToMove == Color.BLACK && from.equals("e8") && to.equals("g8")) {
                    flag = true;
                }
                break;
            case LONG_CASTLING:
                if(sideToMove == Color.WHITE && from.equals("e1") && to.equals("c1")) {
                    flag = true;
                }
                if(sideToMove == Color.BLACK && from.equals("e8") && to.equals("c8")) {
                    flag = true;
                }
                break;
            case PROMOTION:
            case ORDINARY:
                if (from.equals(firstSquare) && to.equals(secondSquare)) {
                    flag = true;
                }
                break;
        }
        return flag;
    }

    public static Move parse(String s) {
        Move move = null;
        if(s.equals("0-0")) {
            move = Move.getShortCastlingMove();
        } else if(s.equals("0-0-0")) {
            move = Move.getLongCastlingMove();
        } else {
            Piece piece;
            switch(s.charAt(0)) {
                case 'N':
                    piece = Piece.KNIGHT;
                    break;
                case 'B':
                    piece = Piece.BISHOP;
                    break;
                case 'R':
                    piece = Piece.ROOK;
                    break;
                case 'Q':
                    piece = Piece.QUEEN;
                    break;
                case 'K':
                    piece = Piece.KING;
                    break;
                default:
                    piece = Piece.PAWN;
            }

            int i = 1;
            if(piece == Piece.PAWN) {
                i = 0;
            }
            String first = s.substring(i+0, i+2);
            String second = s.substring(i+3, i+5);
            Square firstSq = Square.parse(first);
            Square secondSq = Square.parse(second);

            char ch = s.charAt(i+2);
            if(piece==Piece.PAWN && s.length()==6) {
                switch (ch) {
                    case '-':
                        move = Move.getPromotionMove(firstSq, secondSq, Piece.QUEEN);
                        break;
                    case ':':
                        move = Move.getPromotionWithCaptureMove(firstSq, secondSq, Piece.QUEEN);
                        break;
                }
            } else {
                switch (ch) {
                    case '-':
                        move = Move.getOrdinaryMove(piece, firstSq, secondSq);
                        break;
                    case ':':
                        move = Move.getOrdinaryCaptureMove(piece, firstSq, secondSq);
                        break;
                }
            }
        }
//        } else if(s.length() == 6) {
//            String figureLetters = "NBRQK";
//            if(figureLetters.contains(s.substring(0, 1))) {
//                // Figure move
//                String first = s.substring(1,3);
//                String second = s.substring(4, s.length());
//                Square firstSq = Square.parse(first);
//                Square secondSq = Square.parse(second);
//            } else {
//                // Promotion
//            }
//        }
        return move;
    }
    
}