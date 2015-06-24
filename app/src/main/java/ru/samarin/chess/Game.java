package ru.samarin.chess;


import android.util.Log;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Stack;

public class Game {
    public enum State {
        PROCESS, WIN, DRAW
    }

    private Board board;
    private State state;
    private Color sideToMove;
    private MoveGenerator moveGenerator;
    private CastlingState castlingState;
    private Stack<Move> moveHistory;
    private Stack<Piece> capturedPieceHistory;

    
    public class CastlingState {
        private boolean blackShort;
        private boolean whiteShort;
        private boolean blackLong;
        private boolean whiteLong;

        int blackShortBreakingMove;
        int whiteShortBreakingMove;
        int blackLongBreakingMove;
        int whiteLongBreakingMove;

        private CastlingState() {
            clear();
        }

        private void clear() {
            blackShort = true;
            whiteShort = true;
            blackLong = true;
            whiteLong = true;

            blackShortBreakingMove = -1;
            whiteShortBreakingMove = -1;
            blackLongBreakingMove = -1;
            whiteLongBreakingMove = -1;
        }

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
        state = State.PROCESS;
        board = new Board();
        castlingState = new CastlingState();
        capturedPieceHistory = new Stack<>();
        moveGenerator = new MoveGenerator(board, castlingState);
        moveHistory = new Stack<>();

//        //======test============
//        String[] moveStrings = {"0-0-0", "0-0", "a3:b4", "a2-a4", "f7:g8Q", "d7-d8Q", "Rf4-b4", "Ba3:c5"};
//        for(String s: moveStrings) {
//            Log.d("GAME", s + " - " + Move.parse(s));
//        }
    }

    public Color getSideToMove() {
        return sideToMove;
    }

    public State getState() {
        return state;
    }

    public boolean hasPositionChanged() {
        return !moveHistory.isEmpty();
    }

    public ArrayList<String> getMoveHistory() {
        ArrayList<String> moveStrings = new ArrayList<>();
        for(Move move: moveHistory) {
            moveStrings.add(move.toString());
        }
        return moveStrings;
    }

    public void setPosition(ArrayList<String> moveHistory) {
        setInitialPosition();
        for(String s: moveHistory) {
            Move move = Move.parse(s);
            makeMove(move);
        }
        updateState();
    }
    
    public void setTestPosition() {
        //state = State.PROCESS;
        sideToMove = Color.WHITE;

        //Promotion
//        board.setSquare("g7", Piece.PAWN, Color.WHITE);
//        board.setSquare("h8", Piece.KNIGHT, Color.BLACK);
//        board.setSquare("f8", Piece.BISHOP, Color.BLACK);

        //Castling
//        board.setSquare("a1", Piece.ROOK, Color.WHITE);
//        board.setSquare("e1", Piece.KING, Color.WHITE);
//        board.setSquare("h1", Piece.ROOK, Color.WHITE);
//
//        board.setSquare("a8", Piece.ROOK, Color.BLACK);
//        board.setSquare("e8", Piece.KING, Color.BLACK);
//        board.setSquare("h8", Piece.ROOK, Color.BLACK);

        //Mate
        board.setSquare("a7", Piece.ROOK, Color.BLACK);
        board.setSquare("b7", Piece.ROOK, Color.BLACK);
        board.setSquare("e8", Piece.KING, Color.WHITE);
    }
    
    public void setInitialPosition() {
        moveHistory.clear();
        capturedPieceHistory.clear();
        castlingState.clear();
        board.clear();

        state = State.PROCESS;
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

        Move move = moveHistory.pop();

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
                if(move.isCapture) {
                    Piece capturedPiece = capturedPieceHistory.pop();
                    board.setSquare(move.secondSquare, capturedPiece, Color.getOppositeColor(sideToMove));
                } else {
                    board.setSquare(move.secondSquare, Piece.NONE, Color.NONE);
                }
                break;
        }


        // updating castling state
        int moveNumber = moveHistory.size();
        castlingState.onUnMakeMove(moveNumber);
        state = State.PROCESS;

        return true;
    }

    private void makeMove(Move move) {

        moveHistory.push(move);
        if(move.isCapture) {
            Piece capturedPiece = board.getPiece(move.secondSquare);
            capturedPieceHistory.push(capturedPiece);
        }

        Square from = move.firstSquare;
        Square to = move.secondSquare;
        switch(move.type) {
            case SHORT_CASTLING:
                if(sideToMove == Color.WHITE) {
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
                break;
            case LONG_CASTLING:
                if(sideToMove == Color.WHITE) {
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
                break;
            case PROMOTION:
                board.setSquareEmpty(from);
                board.setSquare(to, Piece.QUEEN, sideToMove);
                break;
            case ORDINARY:
                board.setSquareEmpty(from);
                board.setSquare(to, move.piece, sideToMove);
                break;
        }


        // updating sideToMove
        sideToMove = Color.getOppositeColor(sideToMove);


        // updating castling state
        int moveNumber =  moveHistory.size()-1;
        if(sideToMove == Color.WHITE) {

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
    }
    
    public boolean verifyAndMakeMove(Square from, Square to) {
        if(from.isOut() || to.isOut()) {
            throw new RuntimeException("<from> or <to> square is out of the board");
        }

        if(state != State.PROCESS) {
            return false;
        }

        Color color = board.getColor(from);
        if(color != sideToMove) {
            return false;
        }


        Stack<Move> moveList = moveGenerator.generateAllMoves(color);
        for(Move move: moveList) {
            if(move.equals(from, to, sideToMove)) {
                makeMove(move);
                Color oppositeSide = Color.getOppositeColor(sideToMove);
                if(moveGenerator.isKingUnderAttack(oppositeSide, board)) {
                    unmakeMove();
                    return false;
                }

                // detecting checkmate and stalemate
                updateState();

                return true;
            }
        }
        
        return false;
    }

    private void updateState() {
        if(!isTherePossibleMove()) {
            if(moveGenerator.isKingUnderAttack(sideToMove, board)) {
                // mate
                state = State.WIN;
            } else {
                // stalemate
                state = State.DRAW;
            }
        }
    }

    private boolean isTherePossibleMove() {
        AbstractList<Move> moveList = moveGenerator.generateAllMoves(sideToMove);
        boolean flag = false;
        for(Move move: moveList) {
            makeMove(move);
            Color oppositeSide = Color.getOppositeColor(sideToMove);
            if(!moveGenerator.isKingUnderAttack(oppositeSide, board)) {
               flag = true;
            }
            unmakeMove();
        }
        return flag;
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