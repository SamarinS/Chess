package ru.samarin.chess;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Stack;

public class MoveGenerator {
    private Board board;
    private Game.CastlingState castlingState;
    private static final int n = 8;
    private static final Piece[] promotionPieces = {Piece.QUEEN, Piece.KNIGHT, Piece.BISHOP, Piece.ROOK};
    
    
    public MoveGenerator(Board board, Game.CastlingState castlingState) {
        this.board = board;
        this.castlingState = castlingState;
    }


    public boolean isKingUnderAttack(Color color, Board board) {
        Color sideToMove = Color.getOppositeColor(color);
        AbstractList<Move> moveList = generateAllMoves(sideToMove);
        for(Move move: moveList) {
            if(move.isCapture &&
               board.getPiece(move.secondSquare) == Piece.KING
            ) {
                return true;
            }
        }
        return false;
    }
    
    
    public Stack<Move> generateAllMoves(Color color) {
        if(color == Color.NONE) {
            throw new RuntimeException("Argument color is NONE");
        }
        
        Stack<Move> moveList = new Stack<>();
        
        for(int i = 0;i<n;i++) {
            for(int j = 0;j<n;j++) {
                Square sq = new Square(i, j);
                Piece sqPiece = board.getPiece(sq);
                Color sqColor = board.getColor(sq);
                if(color == sqColor) {
                    switch(sqPiece) {
                        case PAWN:
                            if(sqColor == Color.WHITE) {
                                addWhitePawnMoves(moveList, sq);
                            }
                            if(sqColor == Color.BLACK) {
                                addBlackPawnMoves(moveList, sq);
                            }
                            break;
                        case BISHOP:
                            addBishopMoves(moveList, sq, sqColor);
                            break;
                        case KNIGHT:
                            addKnightMoves(moveList, sq, sqColor);
                            break;
                        case KING:
                            addKingMoves(moveList, sq, sqColor);
                            break;
                        case ROOK:
                            addRookMoves(moveList, sq, sqColor);
                            break;
                        case QUEEN:
                            addQueenMoves(moveList, sq, sqColor);
                            break;
                    }
                }
            }
        }
        
        addCastlingMoves(moveList, color);
        
        return moveList;
    }

    private void addWhitePawnMoves(AbstractList<Move> moveList, Square sq) {
        Square to;

        // Ход с начальной позиции на два поля вперед
        if( (sq.i == 1) && 
            (board.getColor(sq.goUp()) == Color.NONE) &&
            (board.getColor(sq.goUp().goUp()) == Color.NONE)
        ) {
            Move move = Move.getOrdinaryMove(Piece.PAWN, sq, sq.goUp().goUp());
            moveList.add(move);
        }
        
        // Ход на одно поле вперед
        if(board.getColor(sq.goUp()) == Color.NONE)
        {
            if(sq.i == 6) // Promotion
            {
                for(Piece item: promotionPieces) {
                    Move move = Move.getPromotionMove(sq, sq.goUp(), item);
                    moveList.add(move);
                }
            }
            else 
            {
                Move move = Move.getOrdinaryMove(Piece.PAWN, sq, sq.goUp());
                moveList.add(move);
            }
        }
        
        // Взятие справа
        to = sq.goUp().goRight();
        if(!sq.isNearRightBorder() && board.getColor(to) == Color.BLACK)
        {
            // Promotion
            if(sq.i==6) {
                for(Piece item: promotionPieces) {
                    Move move = Move.getPromotionWithCaptureMove(sq, to, item);
                    moveList.add(move);
                }
            } else { // Обычное взятие
                Move move = Move.getOrdinaryCaptureMove(Piece.PAWN, sq, to);
                moveList.add(move);
            }
        }
        
        // Взятие слева
        to = sq.goUp().goLeft();
        if(!sq.isNearLeftBorder() && board.getColor(to) == Color.BLACK)
        {
            // Promotion
            if(sq.i==6) {
                for(Piece item: promotionPieces) {
                    Move move = Move.getPromotionWithCaptureMove(sq, to, item);
                    moveList.add(move);
                }
            } else { // Обычное взятие
                Move move = Move.getOrdinaryCaptureMove(Piece.PAWN, sq, to);
                moveList.add(move);
            }
        }
    }
    
    private void addBlackPawnMoves(AbstractList<Move> moveList, Square sq) {
        Square to;

        // Ход с начальной позиции на два поля вперед
        if( (sq.i == 6) && 
            (board.getColor(sq.goDown()) == Color.NONE) &&
            (board.getColor(sq.goDown().goDown()) == Color.NONE)
        ) {
            Move move = Move.getOrdinaryMove(Piece.PAWN, sq, sq.goDown().goDown());
            moveList.add(move);
        }
        
        // Ход на одно поле вперед
        if(board.getColor(sq.goDown()) == Color.NONE)
        {
            if(sq.i == 1) // Promotion
            {
                Piece[] promotionPieces = {Piece.KNIGHT, Piece.BISHOP, Piece.ROOK, Piece.QUEEN};
                for(Piece item: promotionPieces) {
                    Move move = Move.getPromotionMove(sq, sq.goDown(), item);
                    moveList.add(move);
                }
            }
            else 
            {
                Move move = Move.getOrdinaryMove(Piece.PAWN, sq, sq.goDown());
                moveList.add(move);
            }
        }
        
        // Взятие справа
        to = sq.goDown().goRight();
        if(!sq.isNearRightBorder() && board.getColor(to) == Color.WHITE)
        {
            // Promotion
            if(sq.i==1) {
                for(Piece item: promotionPieces) {
                    Move move = Move.getPromotionWithCaptureMove(sq, to, item);
                    moveList.add(move);

                }
            } else { // Обычное взятие
                Move move = Move.getOrdinaryCaptureMove(Piece.PAWN, sq, to);
                moveList.add(move);
            }
        }
        
        // Взятие слева
        to = sq.goDown().goLeft();
        if(!sq.isNearLeftBorder() && board.getColor(to) == Color.WHITE)
        {
            // Promotion
            if(sq.i==1) {
                for(Piece item: promotionPieces) {
                    Move move = Move.getPromotionWithCaptureMove(sq, to, item);
                    moveList.add(move);
                }
            } else { // Обычное взятие
                Move move = Move.getOrdinaryCaptureMove(Piece.PAWN, sq, to);
                moveList.add(move);
            }
        }
    }
    
    
    private void addKingMoves(AbstractList<Move> moveList, Square square, Color color) {
        ArrayList<Square> squareList = new ArrayList<Square>();
        
        squareList.add(square.goUp().goLeft());
        squareList.add(square.goUp());
        squareList.add(square.goUp().goRight());
        
        squareList.add(square.goLeft());
        squareList.add(square.goRight());
        
        squareList.add(square.goDown().goLeft());
        squareList.add(square.goDown());
        squareList.add(square.goDown().goRight());
        
        tryAddFigureMoves(moveList, Piece.KING, square, squareList, color);
    }
    
    
    private void addCastlingMoves(AbstractList<Move> moveList, Color color) {
        if(color == Color.WHITE) {
            if(castlingState.isWhiteLongAllowed() && 
                board.getColor("b1") == Color.NONE &&
                board.getColor("c1") == Color.NONE &&
                board.getColor("d1") == Color.NONE
            ) {
                Move move = Move.getLongCastlingMove();
                moveList.add(move);
            }
            
            if(castlingState.isWhiteShortAllowed() && 
                board.getColor("f1") == Color.NONE &&
                board.getColor("g1") == Color.NONE
            ) {
                Move move = Move.getShortCastlingMove();
                moveList.add(move);
            }
        } else {
            if(castlingState.isBlackLongAllowed() && 
                board.getColor("b8") == Color.NONE &&
                board.getColor("c8") == Color.NONE &&
                board.getColor("d8") == Color.NONE
            ) {
                Move move = Move.getLongCastlingMove();
                moveList.add(move);
            }
            
            if(castlingState.isBlackShortAllowed() && 
                board.getColor("f8") == Color.NONE &&
                board.getColor("g8") == Color.NONE
            ) {
                Move move = Move.getShortCastlingMove();
                moveList.add(move);
            }
        }
    }
 
 
    private void addKnightMoves(AbstractList<Move> moveList, Square square, Color color) {
        ArrayList<Square> squareList = new ArrayList<>();
        
        squareList.add(square.goUp().goUp().goRight());
        squareList.add(square.goUp().goUp().goLeft());
        
        squareList.add(square.goDown().goDown().goRight());
        squareList.add(square.goDown().goDown().goLeft());
        
        squareList.add(square.goRight().goRight().goUp());
        squareList.add(square.goRight().goRight().goDown());
        
        squareList.add(square.goLeft().goLeft().goUp());
        squareList.add(square.goLeft().goLeft().goDown());
        
        tryAddFigureMoves(moveList, Piece.KNIGHT, square, squareList, color);
    }


    private void addBishopMoves(AbstractList<Move> moveList, Square square, Color color) {
        ArrayList<Square> squareList = new ArrayList<>();
        addBishopSquares(squareList, square);
        tryAddFigureMoves(moveList, Piece.BISHOP, square, squareList, color);
    }
    
    private void addBishopSquares(AbstractList<Square> squareList, Square square) {
        Square sq;

        sq = new Square(square);
        do {
            sq = sq.goRight().goUp();
            squareList.add(sq);
        } while(!sq.isOut() && board.getColor(sq) == Color.NONE);
        
        sq = new Square(square);
        do {
            sq = sq.goLeft().goUp();
            squareList.add(sq);
        } while(!sq.isOut() && board.getColor(sq) == Color.NONE);
        
        sq = new Square(square);
        do {
            sq = sq.goRight().goDown();
            squareList.add(sq);
        } while(!sq.isOut() && board.getColor(sq) == Color.NONE);
        
        sq = new Square(square);
        do {
            sq = sq.goLeft().goDown();
            squareList.add(sq);
        } while(!sq.isOut() && board.getColor(sq) == Color.NONE);
    }
    
    
    private void addRookMoves(AbstractList<Move> moveList, Square square, Color color) {
        ArrayList<Square> squareList = new ArrayList<>();
        addRookSquares(squareList, square);
        tryAddFigureMoves(moveList, Piece.ROOK, square, squareList, color);
    }
    
    private void addRookSquares(AbstractList<Square> squareList, Square square) {
        Square sq;

        sq = new Square(square);
        do {
            sq = sq.goUp();
            squareList.add(sq);
        } while(!sq.isOut() && board.getColor(sq) == Color.NONE);
        
        sq = new Square(square);
        do {
            sq = sq.goRight();
            squareList.add(sq);
        } while(!sq.isOut() && board.getColor(sq) == Color.NONE);
        
        sq = new Square(square);
        do {
            sq = sq.goDown();
            squareList.add(sq);
        } while(!sq.isOut() && board.getColor(sq) == Color.NONE);
        
        sq = new Square(square);
        do {
            sq = sq.goLeft();
            squareList.add(sq);
        } while(!sq.isOut() && board.getColor(sq) == Color.NONE);
    }
    
    private void addQueenMoves(AbstractList<Move> moveList, Square square, Color color) {
        ArrayList<Square> squareList = new ArrayList<>();
        addRookSquares(squareList, square);
        addBishopSquares(squareList, square);
        tryAddFigureMoves(moveList, Piece.QUEEN, square, squareList, color);
    }
    
    private void tryAddFigureMoves(AbstractList<Move> moveList, Piece piece, Square first,
                                   ArrayList<Square> secondList, Color color) {
                                       
        for(Square item: secondList) {
            if(!item.isOut()) {
                Color secondColor = board.getColor(item);
                if(secondColor == Color.NONE) {
                    // Пустое поле
                    Move move = Move.getOrdinaryMove(piece, first, item);
                    moveList.add(move);
                } else if(secondColor != color) {
                    // Capture
                    Move move = Move.getOrdinaryCaptureMove(piece, first, item);
                    moveList.add(move);
                }
            }
        }
    }
    
}