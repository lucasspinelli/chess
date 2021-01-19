package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece { //abstract becausa whe dont know the move Method, because ChessPiece could be anyone
    private Color color;
    private int moveCount;

    public ChessPiece(Board board, Color color) {
        super(board);
        this.color = color;
    }

    public ChessPosition getChessPosition(){
        return ChessPosition.fromPosition(position); //returning position on the board
    }

    protected boolean isThereOpponentPiece(Position position) {
        ChessPiece p = (ChessPiece)getBoard().piece(position);// p receives the position, with downcasting to work
        return p != null && p.getColor() != color; // If is an opposite color, will return true.
    }

    public Color getColor() { //just for access and not to chance
        return color;
    }

    public int getMoveCount(){
        return moveCount;
    }

    public void increaseMoveCount(){
        moveCount++;
    }

    public void decreaseMoveCount(){
        moveCount--;
    }

}

