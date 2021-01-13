package chess;

import boardgame.Board;
import chess.pieces.*;

public class ChessMatch {
    private Board board;

    public ChessMatch() {
        board = new Board(8,8);//The chess match who has to know the board size
        inicialSetup();
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
    private void placeNewPiece(char column, int row, ChessPiece piece ){// will receive te chess position already, and them put the piece
        board.placePiece(piece, new ChessPosition(column, row).toPosition()); //call chess position and converted to matrix position
    }
    private void inicialSetup(){ // put the pieces in rigth position when the game begun
        placeNewPiece('b', 6 ,new Rook(board, Color.WHITE));
        placeNewPiece('e', 8, new King(board, Color.BLACK));
        placeNewPiece('e', 1, new King(board, Color.WHITE));
    }
}
