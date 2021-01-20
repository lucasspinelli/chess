package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {


    public Pawn(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "P"; //piece that will be showed in terminal
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position p = new Position(0, 0);

        if (getColor() == Color.WHITE){

            p.setValues(position.getRow() -1, position.getColumn()); //general move
            if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
                mat[p.getRow()][p.getColumn()] = true;
            }
            Position p2 = new Position(position.getRow() -1, position.getColumn());
            p.setValues(position.getRow() -2, position.getColumn()); //first move
            if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)&& getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() ==0){
                mat[p.getRow()][p.getColumn()] = true;
            }
            p.setValues(position.getRow() -1, position.getColumn() - 1); //lateral move left
            if (getBoard().positionExists(p) && isThereOpponentPiece(p)){
                mat[p.getRow()][p.getColumn()] = true;
            }
            p.setValues(position.getRow() -1, position.getColumn() + 1); //lateral move right
            if (getBoard().positionExists(p) && isThereOpponentPiece(p)){
                mat[p.getRow()][p.getColumn()] = true;
            }
        } else { //Black piece with the same logic
            p.setValues(position.getRow() +1, position.getColumn()); //general move
            if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
                mat[p.getRow()][p.getColumn()] = true;
            }
            Position p2 = new Position(position.getRow() +1, position.getColumn());
            p.setValues(position.getRow() +2, position.getColumn()); //first move
            if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)&& getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() ==0){
                mat[p.getRow()][p.getColumn()] = true;
            }
            p.setValues(position.getRow() +1, position.getColumn() - 1); //lateral move left
            if (getBoard().positionExists(p) && isThereOpponentPiece(p)){
                mat[p.getRow()][p.getColumn()] = true;
            }
            p.setValues(position.getRow() +1, position.getColumn() + 1); //lateral move right
            if (getBoard().positionExists(p) && isThereOpponentPiece(p)){
                mat[p.getRow()][p.getColumn()] = true;
            }
        }

        return mat;
    }
}
