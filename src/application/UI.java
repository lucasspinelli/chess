package application;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UI {
    // https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";


    public static void clearScreen(){
            System.out.print("\033[H\033[2J");
            System.out.flush();
    }

    public static ChessPosition readChessPosition(Scanner sc){
        try{ // avoid type error
            String s = sc.nextLine();
            char column = s.charAt(0);
            int row = Integer.parseInt(s.substring(1));

            return new ChessPosition(column, row);
        }
        catch (RuntimeException e){
            throw new InputMismatchException("Error reading chess position"); // native input error from Java
        }
    }

    public static void printMatch(ChessMatch chessMatch){ //show the turn data
        printBoard(chessMatch.getPieces());
        System.out.println();
        System.out.println("Turn: " + chessMatch.getTurn());
        System.out.println("Waiting player: " + chessMatch.getCurrentPlayer());
    }

    public static void printBoard(ChessPiece[][] pieces) {
        for (int i = 0; i < pieces.length; i++) {
            System.out.print((8 - i)+ " "); // to enumerate te rows
            for (int j = 0; j < pieces.length ; j++) {
                printPiece(pieces[i][j], false); //print the pieces
            }
            System.out.println();//break line
        }
        System.out.println("  a b c d e f g h");//board structure to play
    }

    public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {
        for (int i = 0; i < pieces.length; i++) {
            System.out.print((8 - i)+ " "); // to enumerate te rows
            for (int j = 0; j < pieces.length ; j++) {
                printPiece(pieces[i][j], possibleMoves[i][j]); //print the pieces based on Possible moves, if possibleMoves == TRUE, the color of background changes
            }
            System.out.println();//break line
        }
        System.out.println("  a b c d e f g h");//board structure to play
    }

    public static void printPiece(ChessPiece piece, boolean background) { //aux method
        if (background){
            System.out.print(ANSI_GREEN_BACKGROUND);
        }
        if (piece == null) {
            System.out.print("-"+ ANSI_RESET);
        }
        else {
            if (piece.getColor() == Color.WHITE) {
                System.out.print(ANSI_BLUE + piece + ANSI_RESET);
            }
            else {
                System.out.print(ANSI_RED + piece + ANSI_RESET);
            }
        }
        System.out.print(" ");
    }
}
