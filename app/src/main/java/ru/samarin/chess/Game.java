package ru.samarin.chess;


import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private Board board;
    //private State state;
    private Color sideToMove;
    private MoveGenerator moveGenerator;
    private CastlingState castlingState;
    private ArrayList<Move> moveHistory;

    
    public class CastlingState {
        private boolean blackShort = true;
        private boolean whiteShort = true; 
        private boolean blackLong = true;
        private boolean whiteLong = true;

        int blackShortBreakingMove = -1;
        int whiteShortBreakingMove = -1;
        int blackLongBreakingMove = -1;
        int whiteLongBreakingMove = -1;

        private void onUnMakeMove(int moveNumber) {
            if(moveNumber == castlingState.whiteShortBreakingMove) {
                castlingState.whiteShort = true;
                castlingState.whiteShortBreakingMove = -1;
            }
            if(moveNumber == castlingState.whiteLongBreakingMove) {
                castlingState.whiteLong = true;
                castlingState.whiteLongBreakingMove = -1;
            }
            if(moveNumber == castlingState.blackShortBreakingMove) {
                castlingState.blackShort = true;
                castlingState.blackShortBreakingMove = -1;
            }
            if(moveNumber == castlingState.blackLongBreakingMove) {
                castlingState.blackLong = true;
                castlingState.blackLongBreakingMove = -1;
            }
        }
        
        public boolean isWhiteShortAllowed() {
            return whiteShort;
        }
        
        public boolean isBlackShortAllowed() {
            return blackShort;
        }
        
        public boolean isWhiteLongAllowed() {
            return whiteLong;
        }
        
        public boolean isBlackLongAllowed() {
            return blackLong;
        }
    }
    
    /*
    public enum State {
        PROCESS, BLACK_WIN, WHITE_WIN, DRAW
    }
    */
    
//    public static void main(String[] args) {
//        System.out.println("Hello chess!");
//
//        Game game = new Game();
//        //game.setTestPosition();
//        game.setInitialPosition();
//
//
//        Scanner in = new Scanner(System.in);
//        while(true) {
//            game.board.print();
//            Square from = new Square(in.next());
//            Square to = new Square(in.next());
//            System.out.println("you entered: from " + from + " to " + to);
//            boolean isMoveValid = game.makeMove(from, to);
//            if(!isMoveValid) {
//                System.out.println("Invalid move");
//            } else {
//                System.out.println("Valid move ");
//            }
//        }
//        /*
//        ArrayList<Move> list = new ArrayList<Move>();
//        list.add(Move.getLongCastlingMove());
//        list.add(Move.getShortCastlingMove());
//        list.add(Move.getOrdinaryMove(Piece.ROOK, new Square("b1"), new Square("b4")));
//        list.add(Move.getOrdinaryCaptureMove(Piece.ROOK, new Square("b1"), new Square("b4")));
//        list.add(Move.getPromotionMove(new Square("a7"), new Square("a8"), Piece.QUEEN));
//        list.add(Move.getPromotionWithCaptureMove(new Square("f7"), new Square("g8"), Piece.KNIGHT));
//        //list.add(Move.getOrdinaryCaptureMove(Piece.ROOK, new Square("b1"), new Square("b4"), Piece.KNIGHT));
//        */
//
//        /*
//        MoveGenerator moveGenerator = new MoveGenerator(game.board);
//        ArrayList<Move> whiteList = moveGenerator.generateAllMoves(Color.WHITE);
//        System.out.println("White moves:");
//        for(Move move: whiteList) {
//            System.out.println(move.toString());
//        }
//
//        ArrayList<Move> blackList = moveGenerator.generateAllMoves(Color.BLACK);
//        System.out.println("Black moves:");
//        for(Move move: blackList) {
//            System.out.println(move.toString());
//        }
//        */
//
//        /*
//        Square sq = new Square("c5");
//        System.out.println("sq = " + sq);
//        System.out.println("right = " + sq.goRight());
//        System.out.println("left = " + sq.goLeft());
//        System.out.println("up = " + sq.goUp());
//        System.out.println("down = " + sq.goDown());
//        */
//
//
//    }
    
    public Game() {
        board = new Board();
        castlingState = new CastlingState();
        moveGenerator = new MoveGenerator(board, castlingState);
        moveHistory = new ArrayList<Move>();
        
        
        
        //board.setSquare("b2", Piece.PAWN, Color.BLACK);
        //board.setSquare("a1", Piece.BISHOP, Color.WHITE);
        //board.setSquare("c1", Piece.ROOK, Color.WHITE);
        //board.setSquare("b3", Piece.ROOK, Color.BLACK);
        //board.setSquare("c2", Piece.PAWN, Color.WHITE);
        //board.setSquare("b4", Piece.PAWN, Color.WHITE);
        
        //board.setSquare("g7", Piece.PAWN, Color.BLACK);
        //board.setSquare("g4", Piece.QUEEN, Color.BLACK);
        //board.setSquare("h5", Piece.KING, Color.WHITE);
        //board.setSquare("h4", Piece.BISHOP, Color.WHITE);
        
        //board.setSquare("d2", Piece.KNIGHT, Color.BLACK);
        //board.setSquare("g2", Piece.PAWN, Color.BLACK);
        
    }

    public Color getSideToMove() {
        return sideToMove;
    }

    public boolean hasPositionChanged() {
        return (moveHistory.isEmpty() == false);
    }
    
    public void setTestPosition() {
        //state = State.PROCESS;
        sideToMove = Color.WHITE;
//        board.setSquare("g7", Piece.PAWN, Color.WHITE);
//        board.setSquare("h8", Piece.KNIGHT, Color.BLACK);
//        board.setSquare("f8", Piece.BISHOP, Color.BLACK);
        board.setSquare("a1", Piece.ROOK, Color.WHITE);
        board.setSquare("e1", Piece.KING, Color.WHITE);
        board.setSquare("h1", Piece.ROOK, Color.WHITE);

        board.setSquare("a8", Piece.ROOK, Color.BLACK);
        board.setSquare("e8", Piece.KING, Color.BLACK);
        board.setSquare("h8", Piece.ROOK, Color.BLACK);
    }
    
    public void setInitialPosition() {
        //state = State.PROCESS;
        sideToMove = Color.WHITE;
        
        for(int j = 0;j < 8;j++) {
            board.setSquare(new Square(1, j), Piece.PAWN, Color.WHITE);
            board.setSquare(new Square(6, j), Piece.PAWN, Color.BLACK);
        }
        
        board.setSquare("a1", Piece.ROOK, Color.WHITE);
        board.setSquare("b1", Piece.KNIGHT, Color.WHITE);
        board.setSquare("c1", Piece.BISHOP, Color.WHITE);
        board.setSquare("d1", Piece.QUEEN, Color.WHITE);
        board.setSquare("e1", Piece.KING, Color.WHITE);
        board.setSquare("f1", Piece.BISHOP, Color.WHITE);
        board.setSquare("g1", Piece.KNIGHT, Color.WHITE);
        board.setSquare("h1", Piece.ROOK, Color.WHITE);
        
        board.setSquare("a8", Piece.ROOK, Color.BLACK);
        board.setSquare("b8", Piece.KNIGHT, Color.BLACK);
        board.setSquare("c8", Piece.BISHOP, Color.BLACK);
        board.setSquare("d8", Piece.QUEEN, Color.BLACK);
        board.setSquare("e8", Piece.KING, Color.BLACK);
        board.setSquare("f8", Piece.BISHOP, Color.BLACK);
        board.setSquare("g8", Piece.KNIGHT, Color.BLACK);
        board.setSquare("h8", Piece.ROOK, Color.BLACK);
    }

    public boolean unmakeMove() {
        if(moveHistory.isEmpty()) {
            return false;
        }

        sideToMove = Color.getOppositeColor(sideToMove);

        int lastIndex = moveHistory.size()-1;
        Move move = moveHistory.get(lastIndex);
        moveHistory.remove(lastIndex);

        switch(move.type) {
            case SHORT_CASTLING:
                if(sideToMove == Color.WHITE) {
                    board.setSquareEmpty("g1");
                    board.setSquareEmpty("f1");
                    board.setSquare("e1", Piece.KING, Color.WHITE);
                    board.setSquare("h1", Piece.ROOK, Color.WHITE);
                } else { // sideToMove == Color.BLACK
                    board.setSquareEmpty("g8");
                    board.setSquareEmpty("f8");
                    board.setSquare("e8", Piece.KING, Color.BLACK);
                    board.setSquare("h8", Piece.ROOK, Color.BLACK);
                }
                break;
            case LONG_CASTLING:
                if(sideToMove == Color.WHITE) {
                    board.setSquareEmpty("c1");
                    board.setSquareEmpty("d1");
                    board.setSquare("e1", Piece.KING, Color.WHITE);
                    board.setSquare("a1", Piece.ROOK, Color.WHITE);
                } else { // sideToMove == Color.BLACK
                    board.setSquareEmpty("c8");
                    board.setSquareEmpty("d8");
                    board.setSquare("e8", Piece.KING, Color.BLACK);
                    board.setSquare("a8", Piece.ROOK, Color.BLACK);
                }
                break;
            case ORDINARY:
            case PROMOTION:
                board.setSquare(move.firstSquare, move.piece, sideToMove);
                Piece capturedPiece;
                if(move.isCapture) {
                    board.setSquare(move.secondSquare, move.capturedPiece, Color.getOppositeColor(sideToMove));
                } else {
                    board.setSquare(move.secondSquare, Piece.NONE, Color.NONE);
                }
                break;
        }


        // updating castling state
        int moveNumber = moveHistory.size();
        castlingState.onUnMakeMove(moveNumber);

        return true;
    }
    
    public boolean makeMove(Square from, Square to) {
        if(from.isOut() || to.isOut()) {
            throw new RuntimeException("<from> or <to> square is out of the board");
        }
        
        Color color = board.getColor(from);
        if(color != sideToMove) {
            return false;
        }
        //Piece piece = board.getPiece(from);
        
        ArrayList<Move> moveList = moveGenerator.generateAllMoves(color);
//        System.out.println(moveList);
        for(Move move: moveList) {
            
            if(from.equals(move.firstSquare) && to.equals(move.secondSquare)) {
                switch(move.type) {
                    case SHORT_CASTLING:
                        if(color == Color.WHITE) {
                            board.setSquareEmpty("e1");
                            board.setSquare("g1", Piece.KING, Color.WHITE);
                            board.setSquareEmpty("h1");
                            board.setSquare("f1", Piece.ROOK, Color.WHITE);
                        } else { // color == Color.BLACK
                            board.setSquareEmpty("e8");
                            board.setSquare("g8", Piece.KING, Color.BLACK);
                            board.setSquareEmpty("h8");
                            board.setSquare("f8", Piece.ROOK, Color.BLACK);
                        }
                        board.setSquare(to, Piece.KING, color);
                        break;
                    case LONG_CASTLING:
                        if(color == Color.WHITE) {
                            board.setSquareEmpty("e1");
                            board.setSquare("c1", Piece.KING, Color.WHITE);
                            board.setSquareEmpty("a1");
                            board.setSquare("d1", Piece.ROOK, Color.WHITE);
                        } else { // color == Color.BLACK
                            board.setSquareEmpty("e8");
                            board.setSquare("c8", Piece.KING, Color.BLACK);
                            board.setSquareEmpty("a8");
                            board.setSquare("d8", Piece.ROOK, Color.BLACK);
                        }
                        board.setSquare(to, Piece.KING, color);
                        break;
                    case PROMOTION:
                        board.setSquareEmpty(from);
                        board.setSquare(to, Piece.QUEEN, color);
                        break;
                    case ORDINARY:
                        board.setSquareEmpty(from);
                        board.setSquare(to, move.piece, color);
                        break;
                }


                sideToMove = Color.getOppositeColor(color);
                moveHistory.add(move);

                // updating castling state
                int moveNumber =  moveHistory.size()-1;
                if(color == Color.WHITE) {
                    
                    if(board.getPiece("a1") != Piece.ROOK || 
                       board.getColor("a1") != Color.WHITE
                    ) {
                        castlingState.whiteLong = false;
                        castlingState.whiteLongBreakingMove = moveNumber;
                    }
                    
                    if(board.getPiece("h1") != Piece.ROOK || 
                       board.getColor("h1") != Color.WHITE
                    ) {
                        castlingState.whiteShort = false;
                        castlingState.whiteShortBreakingMove = moveNumber;
                    }
                    
                    if(board.getPiece("e1") != Piece.KING || 
                       board.getColor("e1") != Color.WHITE
                    ) {
                        castlingState.whiteShort = false;
                        castlingState.whiteLong = false;
                        castlingState.whiteLongBreakingMove = moveNumber;
                        castlingState.whiteShortBreakingMove = moveNumber;
                    }
                } else { // color == Color.BLACK
                
                    if(board.getPiece("a8") != Piece.ROOK || 
                       board.getColor("a8") != Color.BLACK
                    ) {
                        castlingState.blackLong = false;
                        castlingState.blackLongBreakingMove = moveNumber;
                    }
                    
                    if(board.getPiece("h8") != Piece.ROOK || 
                       board.getColor("h8") != Color.BLACK
                    ) {
                        castlingState.blackShort = false;
                        castlingState.blackShortBreakingMove = moveNumber;
                    }
                    
                    if(board.getPiece("e8") != Piece.KING || 
                       board.getColor("e8") != Color.BLACK
                    ) {
                        castlingState.blackShort = false;
                        castlingState.blackLong = false;
                        castlingState.blackShortBreakingMove = moveNumber;
                        castlingState.blackLongBreakingMove = moveNumber;
                    }
                }
                
                return true;
            }
        }
        
        return false;
    }
    
    public Color getColor(Square square) {
        if(square.isOut()) {
            throw new RuntimeException("argument <square> is out of the board");
        }
        return board.getColor(square);
    }
    
    public Piece getPiece(Square square) {
        if(square.isOut()) {
            throw new RuntimeException("argument <square> is out of the board");
        }
        return board.getPiece(square);
    }
}