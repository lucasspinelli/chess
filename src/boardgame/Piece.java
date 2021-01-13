package boardgame;

public class Piece {
    protected Position position;
    private Board board;

    public Piece(Board board) { //constructor just with board, because Piece first position is null
        this.board = board;
    }

    protected Board getBoard() { // just getter, the board will not change

        return board;
    }
}
/* To avoid errors and protect the board and pieces in
* the middle of the game, I put piece and board protected
* I'll just change that in boardgame package and subclasses
* like the Bishop, Tower, etc...*/