package application;

import chess.ChessPiece;

public class UI {
    public static void printBoard(ChessPiece[][] pieces) {
        for (int i = 0; i < pieces.length; i++) {
            System.out.print((8 - i)+ " "); // to enumerate te rows
            for (int j = 0; j < pieces.length ; j++) {
                printPiece(pieces[i][j]); //print the pieces
            }
            System.out.println();//break line
        }
        System.out.println("  a b c d e f g h");//board structure to play
    }

    public static void printPiece(ChessPiece piece) { //aux method
        if (piece == null){
            System.out.print("-");
        } else {
            System.out.print(piece);
        }
        System.out.print(" ");
    }
}
