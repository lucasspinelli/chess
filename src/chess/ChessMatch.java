package chess;

import boardgame.Board;

public class ChessMatch {
    private Board board;

    public ChessMatch() {
        board = new Board(8,8);//The chess match who has to know the board size
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
}
