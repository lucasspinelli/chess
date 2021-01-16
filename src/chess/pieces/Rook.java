package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece {
    public Rook(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "R"; //piece that will be showed in terminal
    }
    @Override //implement move method
    public boolean[][] possibleMoves() {
        // initial matrix starting with every position FALSE and same dimensions of the board
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position p = new Position(0, 0);

        //above
        p.setValues(position.getRow() - 1, position.getColumn());
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
            mat[p.getRow()][p.getColumn()] = true; // Changing the matrix value to true, for the Piece can move itself
            p.setRow(p.getRow() - 1); // to go up until theres empty places
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)){
            mat[p.getRow()][p.getColumn()] = true; // Go up until find an enemy piece
        }

        //left
        p.setValues(position.getRow() , position.getColumn() - 1);
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
            mat[p.getRow()][p.getColumn()] = true; // Changing the matrix value to true, for the Piece can move itself
            p.setColumn(p.getColumn() - 1); // to go left until theres empty places
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)){
            mat[p.getRow()][p.getColumn()] = true; // Go left until find an enemy piece
        }

        //right
        p.setValues(position.getRow() , position.getColumn() + 1);
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
            mat[p.getRow()][p.getColumn()] = true; // Changing the matrix value to true, for the Piece can move itself
            p.setColumn(p.getColumn() + 1); // to go left until theres empty places
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)){
            mat[p.getRow()][p.getColumn()] = true; // Go left until find an enemy piece
        }

        //below
        p.setValues(position.getRow() + 1, position.getColumn());
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
            mat[p.getRow()][p.getColumn()] = true; // Changing the matrix value to true, for the Piece can move itself
            p.setRow(p.getRow() + 1); // to go up until theres empty places
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)){
            mat[p.getRow()][p.getColumn()] = true; // Go up until find an enemy piece
        }

        return mat;

    }
}
