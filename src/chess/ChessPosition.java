package chess;

import boardgame.Position;

public class ChessPosition {
    private char column;
    private int row;

    public ChessPosition(char column, int row) {
        if (column < 'a' || column > 'h' || row < 1 || row > 8) {
            throw new ChessException("Error! Valid values are from a1 to h8");
        }
        this.column = column;
        this.row = row;
    }
// just getter because column and row are not to open to change
    public char getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    protected Position toPosition(){
        return new Position(8 - row, column - 'a'); // General way to make a move in chess.
        // 'a'- 'a' = 0 in UniCode, and with 8 - row we can find the right spot on the matrix
    }

    protected static ChessPosition fromPosition(Position position){
        return new ChessPosition((char)('a'+ position.getColumn()), 8 - position.getRow()); // inverse way of toPosition
    }

    @Override
    public String toString() {
        return ""   //white space to automatically concatenate
                +column
                +row;
    }
}
