package boardgame;

public class Board {
    private int rows;
    private int columns;
    private Piece [][] pieces;

    public Board(int rows, int columns) {
        if (rows < 1 || columns < 1){
            //defense programming, it's good to study
            throw new BoardException("Error to create a board. The board should have at least 1 row and 1 column :(");
        }
        this.rows = rows;
        this.columns = columns;
        pieces = new Piece[rows][columns]; // starting to construct board
    }

    public int getRows() {

        return rows;
    }

    public int getColumns() {

        return columns;
    }

    public Piece piece (int row, int column){
        if(!positionExists(row, column)){
            throw new BoardException("That position is not on the board :,(");
        }
        return pieces[row][column];
    }
    public Piece piece (Position position){
        if(!positionExists(position)){
            throw new BoardException("That position is not on the board :,(");
        }
        return pieces[position.getRow()][position.getColumn()]; //overload of method
    }

    public void placePiece(Piece piece, Position position){
        if (thereIsAPiece(position)){
            throw new BoardException("There's already a piece in the position" + position);
        }
        pieces[position.getRow()][position.getColumn()] = piece;
    }

    public Piece removePiece(Position postion){
        if (!positionExists(postion)){
            throw new BoardException("That position is not on the board :,(");
        }
        if (piece(postion) == null){
            return null;
        }

        Piece aux = piece(postion);
        aux.position = null;
        pieces[postion.getRow()][postion.getColumn()] = null;
        return aux;
    }

    private boolean positionExists(int row, int column){ // making for test inside Class
       return row >=0 && row < rows && column >= 0 && column < columns; // I forgot  a '=' sign =, the application crashed a lot.
        // my heart almost stop. LOL
    }

    public boolean positionExists(Position position){
        return positionExists(position.getRow(), position.getColumn());
    }

    public boolean thereIsAPiece(Position position){ // show if have a piece in that position
        if(!positionExists(position)){
            throw new BoardException("That position is not on the board :,(");
        }
        return piece(position) != null;
        }
    }

