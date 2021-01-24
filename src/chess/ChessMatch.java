package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChessMatch {
    private int turn;
    private Color currentPlayer;
    private Board board;
    private boolean check; // standard false
    private boolean checkMate;
    private ChessPiece enPassantVulnerable; // that move that a pawn takes another in line
    private ChessPiece promoted; // to promote some pawn that reaches the other side pf the board

    private List<Piece> piecesOnTheBoard = new ArrayList<>();
    private List<Piece> capturedPieces = new ArrayList<>();

    public ChessMatch() {
        board = new Board(8,8);//The chess match who has to know the board size
        turn = 1;
        currentPlayer = Color.WHITE; // White is the 1rst to play
        inicialSetup();
    }

    public int getTurn(){
        return turn;
    }

    public Color getCurrentPlayer(){
        return currentPlayer;
    }
    public boolean getCheck(){
        return check;
    }
    public boolean getCheckMate(){
        return checkMate;
    }
    public ChessPiece getEnPassantVulnerable() {
        return enPassantVulnerable;
    }
    public ChessPiece getPromoted(){
        return promoted;
    }


    public ChessPiece[][] getPieces(){ //making a layer to program dont have full access in to the Pieces, just to ChessPiece
        ChessPiece[][] piece = new ChessPiece[board.getRows()][board.getColumns()];//temporly use to output board
        for (int i = 0; i < board.getRows(); i++){
            for (int j = 0; j < board.getColumns(); j++){
                piece[i][j] = (ChessPiece) board.piece(i,j); //Downcasting to compiling
            }
        }
        return piece;
    }

    public boolean[][] possibleMoves(ChessPosition sourcePosition){
        Position position = sourcePosition.toPosition();
        validateSourcePosition(position);
        return board.piece(position).possibleMoves();
    }

    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition){
        Position source = sourcePosition.toPosition();// receiving matrix poition
        Position target = targetPosition.toPosition();
        validateSourcePosition(source);
        validateTargetPosition(source, target);
        Piece capturedPiece = makeMove(source, target);
        //test if the player made some move that put himself in check

        if (testCheck(currentPlayer)) {
            undoMove(source, target, capturedPiece);
            throw new ChessException("Hey, lookout! You can't put yourself in check!!");
        }

        ChessPiece movedPiece = (ChessPiece)board.piece(target);

        //Special move - Promotion
        // Testing before Check, because if Pawn is promoted to a powerfull piece. that piece can put the king in Check
        promoted = null;
        if (movedPiece instanceof Pawn){
            if((movedPiece.getColor() == Color.WHITE && target.getRow() == 0) || (movedPiece.getColor() == Color.BLACK && target.getRow() == 7)){ //it means that white or black pawn reaches te end
                promoted = (ChessPiece)board.piece(target);
                promoted = replacePromotedPiece("Q"); //default to promote to Queen
            }
        }

        check = (testCheck(oponnent(currentPlayer))) ? true : false; // testing if current player made a move thats put opponent in check

        if(testCheckMate(oponnent(currentPlayer))){
            checkMate = true;
        } else {
            nextTurn();
        }

        // En Passant - Special Move
        if (movedPiece instanceof Pawn && (target.getRow() == source.getRow() - 2 || target.getRow() == source.getRow() + 2)) {
            enPassantVulnerable = movedPiece;
        }
        else {
            enPassantVulnerable = null;
        }
        return (ChessPiece)capturedPiece; //Downcasting
    }

    public ChessPiece replacePromotedPiece(String type){
        if(promoted == null){ // there's no piece to be promoted
            throw new IllegalStateException("There's no piece to be promoted");
        }
        if (!type.equals("B") && !type.equals("N") && !type.equals("R") && !type.equals("Q")){
            return promoted; //Will return a Queen
        }
        //remove pawn
        Position pos = promoted.getChessPosition().toPosition();
        Piece p = board.removePiece(pos);
        piecesOnTheBoard.remove(p);

        //place the new piece
        ChessPiece newPiece = newPiece(type, promoted.getColor());
        board.placePiece(newPiece, pos);
        piecesOnTheBoard.add(newPiece);

        return newPiece;
    }

    private ChessPiece newPiece(String type, Color color){ //aux method
        if (type.equals("B")) return new Bishop(board, color);
        if (type.equals("N")) return new Knight(board, color);
        if (type.equals("Q")) return new Queen(board, color);
        return new Rook(board, color); //if isn't anyone else
    }

    private Piece makeMove(Position source, Position target){
        ChessPiece p = (ChessPiece) board.removePiece(source);
        p.increaseMoveCount();
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(p, target);

        if (capturedPiece != null){
            piecesOnTheBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }
        //Special move - Castling King side
        if (p instanceof King && target.getColumn() == source.getColumn() + 2){
            Position sourceR = new Position(source.getRow(), source.getColumn() + 3);
            Position targetT = new Position(source.getRow(), source.getColumn() + 1);
            ChessPiece rook = (ChessPiece)board.removePiece(sourceR);
            board.placePiece(rook, targetT);
            rook.increaseMoveCount();
        }
        //Special move - Castling Queen side
        if (p instanceof King && target.getColumn() == source.getColumn() - 2){
            Position sourceR = new Position(source.getRow(), source.getColumn() - 4);
            Position targetR = new Position(source.getRow(), source.getColumn() - 1);
            ChessPiece rook = (ChessPiece)board.removePiece(sourceR);
            board.placePiece(rook, targetR);
            rook.increaseMoveCount();
        }

        // Special Moove - En Passant
        if (p instanceof Pawn) {
            if (source.getColumn() != target.getColumn() && capturedPiece == null) {
                Position pawnPosition;
                if (p.getColor() == Color.WHITE) {
                    pawnPosition = new Position(target.getRow() + 1, target.getColumn());
                }
                else {
                    pawnPosition = new Position(target.getRow() - 1, target.getColumn());
                }
                capturedPiece = board.removePiece(pawnPosition);
                capturedPieces.add(capturedPiece);
                piecesOnTheBoard.remove(capturedPiece);
            }
        }


        return capturedPiece;
    }

    private void undoMove(Position source, Position target, Piece capturedPiece){ //opposite of Make a move
        ChessPiece p = (ChessPiece) board.removePiece(target);
        p.decreaseMoveCount();
        board.placePiece(p, source);

        if (capturedPiece != null){
            board.placePiece(capturedPiece, target);
            capturedPieces.remove(capturedPiece);
            piecesOnTheBoard.add(capturedPiece);
        }

        //Special move - Castling King side
        if (p instanceof King && target.getColumn() == source.getColumn() + 2){
            Position sourceR = new Position(source.getRow(), source.getColumn() + 3);
            Position targetR = new Position(source.getRow(), source.getColumn() + 1);
            ChessPiece rook = (ChessPiece)board.removePiece(targetR);
            board.placePiece(rook, sourceR);
            rook.decreaseMoveCount();
        }
        //Special move - Castling Queen side
        if (p instanceof King && target.getColumn() == source.getColumn() - 2){
            Position sourceR = new Position(source.getRow(), source.getColumn() - 4);
            Position targetR = new Position(source.getRow(), source.getColumn() - 1);
            ChessPiece rook = (ChessPiece)board.removePiece(targetR);
            board.placePiece(rook, sourceR);
            rook.decreaseMoveCount();
        }

        // #specialmove en passant
        if (p instanceof Pawn) {
            if (source.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable) {
                ChessPiece pawn = (ChessPiece)board.removePiece(target);
                Position pawnPosition;
                if (p.getColor() == Color.WHITE) {
                    pawnPosition = new Position(3, target.getColumn());
                }
                else {
                    pawnPosition = new Position(4, target.getColumn());
                }
                board.placePiece(pawn, pawnPosition);
            }
        }

    }

    private void validateSourcePosition(Position position){
        if (!board.thereIsAPiece(position)){
            throw new ChessException("There's is nothing here on source position");
        }

        if(currentPlayer != ((ChessPiece)board.piece(position)).getColor()){ //DownCasting for use getColor
            throw new ChessException("Hey you! There's not your piece!");
        }
        if(!board.piece(position).isThereAnyPossibleMove()){
            throw new ChessException("Learn Chess! Theres no possible move for this Piece!");
        }
    }

    private void validateTargetPosition(Position source, Position target) {
        if (!board.piece(source).possibleMove(target)){
            throw new ChessException("That's is not a possible move for this piece.");
        }
    }

    private void nextTurn(){
        turn ++;
        currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE; //invert Colors every turn
    }

    private Color oponnent(Color color){
        return (color == Color.WHITE) ? Color.BLACK : Color.WHITE; // To see which are opponents
    }

    private ChessPiece king(Color color){
        List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
        for (Piece p : list){
            if (p instanceof King) { // To see if there's an opponent king in the field
                return (ChessPiece) p; //DownCasting
            }
        }
        throw new IllegalStateException("There is no " + color + "king on the board"); //Please god, never throw this on the screen LOL
    }

    private boolean testCheck(Color color){ //Will run all the list of opponent pieces and validate if theres a possible move with the king in some target position
        Position kingPosition = king(color).getChessPosition().toPosition(); //chess position of the king
        //filter all actual king opponent  pieces
        List<Piece> oponnentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == oponnent(color)).collect(Collectors.toList());
        for(Piece p : oponnentPieces) {
            boolean[][] mat = p.possibleMoves(); // possible Moves matrix
            if(mat[kingPosition.getRow()][kingPosition.getColumn()]) { //testing if the piece can move to the king position
                return true;
            }
        }
        return false;
    }

    private boolean testCheckMate(Color color){
        if(!testCheck(color)){
            return false;
        }

        List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());

        for (Piece p : list) {
            boolean[][] mat = p.possibleMoves();
            for (int i = 0; i < board.getRows(); i++) {
                for (int j = 0; j < board.getColumns() ; j++) {
                    if(mat[i][j]){
                        Position source = ((ChessPiece)p).getChessPosition().toPosition();
                        Position target = new Position(i, j);
                        Piece capturedPiece = makeMove(source, target); //Possible capturedPiece
                        boolean testCheck = testCheck(color);
                        undoMove(source, target, capturedPiece); //to not make our board crazy
                        if (!testCheck){ //moves take out of check
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private void placeNewPiece(char column, int row, ChessPiece piece ){// will receive te chess position already, and them put the piece
        board.placePiece(piece, new ChessPosition(column, row).toPosition()); //call chess position and converted to matrix position
        piecesOnTheBoard.add(piece);
    }
    private void inicialSetup(){ // put the pieces in rigth position when the game begun

        //white pieces
        placeNewPiece('a', 1, new Rook(board, Color.WHITE));
        placeNewPiece('b', 1, new Knight(board, Color.WHITE));
        placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('d', 1, new Queen(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE, this));
        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('g', 1, new Knight(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE, this));


        //black pieces
        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('b', 8, new Knight(board, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK, this)); //self-reference
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this));
    }
}
