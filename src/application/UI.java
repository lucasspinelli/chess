package application;

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
        if (piece == null) {
            System.out.print("-");
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
