package application;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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

    public static void printMatch(ChessMatch chessMatch, List<ChessPiece> captured){ //show the turn data
        printBoard(chessMatch.getPieces());
        System.out.println();
        printCapturedPiece(captured);
        System.out.println();
        System.out.println("Turn: " + chessMatch.getTurn());
        if (!chessMatch.getCheckMate()){
            System.out.println("Waiting player: " + chessMatch.getCurrentPlayer());
            if(chessMatch.getCheck()){
                System.out.println("YOU'RE IN CHECK!");
            }
        } else {
            System.out.println("CHECKMATE!!!!");
            System.out.println("Winner Winner Chiken Dinner, " + chessMatch.getCurrentPlayer());
        }

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

    private static void printCapturedPiece(List<ChessPiece> captured){
        // list to save all capture white pieces
        List<ChessPiece> white = captured.stream().filter(x -> x.getColor() == Color.WHITE).collect(Collectors.toList());
        // list to save all capture black pieces
        List<ChessPiece> black = captured.stream().filter(x -> x.getColor() == Color.BLACK).collect(Collectors.toList());

        System.out.println("Captured Pieces:");
        System.out.print("White: ");
        System.out.print(ANSI_BLUE); // color white pieces
        System.out.println(Arrays.toString(white.toArray())); //printing an array of values
        System.out.println(ANSI_RESET); // reset the print
        System.out.print("Black: ");
        System.out.print(ANSI_RED); // color black pieces
        System.out.println(Arrays.toString(black.toArray())); //printing an array of values
        System.out.println(ANSI_RESET); // reset the print

    }

}
