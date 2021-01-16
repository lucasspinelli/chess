package boardgame;

public abstract class Piece {
    protected Position position;
    private Board board;

    public Piece(Board board) { //constructor just with board, because Piece first position is null
        this.board = board;
    }

    protected Board getBoard() { // just getter, the board will not change

        return board;
    }
    public abstract boolean[][] possibleMoves(); //We will put every possible move as TRUE and the other places will be FALSE

    public boolean possibleMove(Position position){ //hook method = when concrete method returns an abstract one
        return possibleMoves()[position.getRow()][position.getColumn()];
    }

    public boolean isThereAnyPossibleMove(){
        boolean[][] mat = possibleMoves();
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat.length ; j++) {
                if (mat[i][j]){ //if that position was TRUE
                    return true;
                }
            }
        }
        return false;
    }
}
/* To avoid errors and protect the board and pieces in
* the middle of the game, I put piece and board protected
* I'll just change that in boardgame package and subclasses
* like the Bishop, Tower, etc...*/