package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

    private ChessMatch chessMatch; // To make castling move

    public King(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }

    @Override
    public String toString() {
        return "K";
    }

    private boolean canMove(Position position) { // returns if King can move to some position
        ChessPiece p = (ChessPiece) getBoard().piece(position); //Downcasting
        return p == null || p.getColor() != getColor(); // If there's an opponent piece and a unocuppaded field, king can move itself
    }

    private boolean testRookCastling(Position position){ //test if i can make castling with this tower
        ChessPiece p = (ChessPiece)getBoard().piece(position);
        return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0;
    }

    @Override //implement move method
    public boolean[][] possibleMoves() {
        // initial matrix starting with every position FALSE and same dimensions of the board
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position p = new Position(0, 0); // aux position

        //above
        p.setValues(position.getRow() - 1, position.getColumn());
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        //below
        p.setValues(position.getRow() + 1, position.getColumn());
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        //left
        p.setValues(position.getRow(), position.getColumn() - 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        //right
        p.setValues(position.getRow(), position.getColumn() + 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // DIAGONALS

        //nw
        p.setValues(position.getRow() - 1, position.getColumn() - 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        //ne
        p.setValues(position.getRow() - 1, position.getColumn() + 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        //sw
        p.setValues(position.getRow() + 1, position.getColumn() - 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        //se
        p.setValues(position.getRow() + 1, position.getColumn() + 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // Special Move : Castling

        if (getMoveCount() == 0 && !chessMatch.getCheck()) {
            //king side castling
            Position posR1 = new Position(position.getRow(), position.getColumn() + 3); // Is aways 3 columns in king side
            if (testRookCastling(posR1)){
                Position p1 = new Position(position.getRow(), position.getColumn() + 1); //one column near King
                Position p2 = new Position(position.getRow(), position.getColumn() + 2);

                if (getBoard().piece(p1) == null && getBoard().piece(p2) == null){
                    mat[position.getRow()][position.getColumn() + 2] = true;
                }
            }
            //queen side castling
            Position posR2 = new Position(position.getRow(), position.getColumn() - 4); // Is aways 4 columns in queen side
            if (testRookCastling(posR2)){
                Position p1 = new Position(position.getRow(), position.getColumn() - 1); //one column near King
                Position p2 = new Position(position.getRow(), position.getColumn() - 2);
                Position p3 = new Position(position.getRow(), position.getColumn() - 3);

                if (getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null){
                    mat[position.getRow()][position.getColumn() - 2] = true;
                }
            }
        }

        return mat;
    }
}


