package application;

import boardgame.Board;
import boardgame.Position;
import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.*;

public class Program {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ChessMatch chessMatch = new ChessMatch();
        List<ChessPiece> captured = new ArrayList<>();

        while (!chessMatch.getCheckMate()){
            try{
                UI.clearScreen(); //clearing screen
                UI.printMatch(chessMatch, captured); //to print the board User Interface
                System.out.println();
                System.out.print("Source: ");
                ChessPosition source = UI.readChessPosition(sc);
                boolean[][] possibleMoves = chessMatch.possibleMoves(source);
                UI.clearScreen(); //clearing screen
                UI.printBoard(chessMatch.getPieces(), possibleMoves); //print and coloring board with possibleMoves, Overload

                System.out.println();
                System.out.print("Target: ");
                ChessPosition target = UI.readChessPosition(sc);

                ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
                if (capturedPiece != null){
                    captured.add(capturedPiece);
                }
                if (chessMatch.getPromoted() !=null){
                    System.out.print("Enter piece for promotion ( Q / B / N / R ): ");
                    String type = sc.nextLine().toUpperCase();
                    while (!type.equals("B") && !type.equals("N") && !type.equals("R") && !type.equals("Q")){
                        System.out.print("Invalid Value! Please, Enter a piece for promotion ( Q / B / N / R ): ");
                        type = sc.nextLine().toUpperCase();
                    }
                    chessMatch.replacePromotedPiece(type);
                }
            }
            catch (ChessException e){
                System.out.println(e.getMessage());
                sc.nextLine(); // Program do not stop process after that, wait the user enter
            }
            catch (InputMismatchException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }

        }
        UI.clearScreen();
        UI.printMatch(chessMatch, captured);

    }
}

