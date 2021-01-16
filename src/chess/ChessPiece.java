package chess;

import boardgame.Board;
import boardgame.Piece;

public abstract class ChessPiece extends Piece { //abstract becausa whe dont know the move Method, because ChessPiece could be anyone
    private Color color;

    public ChessPiece(Board board, Color color) {
        super(board);
        this.color = color;
    }

    public Color getColor() { //just for access and not to chance
        return color;
    }
}

