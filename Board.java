/*
 * Ming Zhang
 * 10/13/2022
 */

import java.io.*;
import java.util.Random;
import java.awt.Color;
import java.awt.Graphics;


public class Board {
    
    // ArrayList for the Board
    private Cell[][] grid;

    // sets the size of the Board to be 9 indices
    public static final int SIZE = 9;

    /**
     * Constructor for Board
     * Creates a 9x9 board
     */
    public Board(){
        this.grid = new Cell[this.SIZE][this.SIZE];

        for (int i = 0; i < this.SIZE; i++){
            for (int j = 0; j < this.SIZE; j++){
                this.grid[i][j] = new Cell(i, j, 0);
            }
        }
    }

    /**
     * Reads a textfile of a Sudoku board and constructs the board
     * @param filename the file name of the sudoku board
     */
    public Board(String filename){
        this.grid = new Cell[this.SIZE][this.SIZE];

        for (int i = 0; i < this.SIZE; i++){
            for (int j = 0; j < this.SIZE; j++){
                this.grid[i][j] = new Cell(i, j, 0);
            }
        }

        read(filename);
    }

    /**
     * Creates a board with a set number of locked Cells
     * @param lockedCells int of how many lockedCells
     */
    public Board(int lockedCells){
        this.grid = new Cell[this.SIZE][this.SIZE];
        Random ran = new Random();
        int count = 0;
        
        for (int i = 0; i < this.SIZE; i++){
            for (int j = 0; j < this.SIZE; j++){
                this.grid[i][j] = new Cell(i, j, 0);
            }
        }

        

        // this whileloop will iterate until the number of counts is equal to the lockedCells
        while (count < lockedCells){
            // creates a random cell object within the board
            Cell cell = this.grid[ran.nextInt(SIZE)][ran.nextInt(SIZE)];

            // each iteration will have a different random value
            int ranVal = ran.nextInt(1, 9);

            // if the value is valid at the location, the value will be set
            if (cell.getValue() == 0 && validValue(cell.getRow(), cell.getCol(), ranVal)){
                cell.setValue(ranVal);
                cell.setLocked(true);
                count++;
            }
        } 

    }

    // toString that prints out the 9x9 Board
    public String toString(){
        
        String theBoard = "";

        for (int i = 0; i < this.SIZE; i++){
            for (int j = 0; j < this.SIZE; j++){
                theBoard += this.grid[i][j];

                // uses remainders to make dividing lines that separates the board into 3 columns
                if (j % 3 == 2){
                    theBoard += "|";
                }
            // uses remainders to make dividing lines the separates the board into 3 rows
            } if (i % 3 == 2) {
                theBoard += "\n";
                theBoard += "-".repeat(21);
            }

            theBoard += "\n";

        }

        // returns the board
        return theBoard;
    }

    // boolean for whether solve() has finished
    public boolean finished;

    // Draws each Cell in a board
    // if solve() is finished, where finished == true, will print "No Solution" if solution cannot be found
    // will print "Hurray" if a solution is found
    public void draw(Graphics g, int scale){
        
        for(int i = 0; i<9; i++){
            for(int j = 0; j<9; j++){
                this.grid[i][j].draw(g, j*scale+5, i*scale+10, scale);
            }

        } if(this.finished){
            if(validSolution()){
                g.setColor(new Color(0, 127, 0));
                g.drawChars("Hurray!".toCharArray(), 0, "Hurray!".length(), scale*3+5, scale*10+10);
            } else {
                g.setColor(new Color(127, 0, 0));
                g.drawChars("No solution!".toCharArray(), 0, "No Solution!".length(), scale*3+5, scale*10+10);
            }
        }
    }

    /**
     * @return returns column size of the Board
     */
    public int getCols(){
        return this.SIZE;
    }

    /**
     * @return returns row size of the Board
     */
    public int getRows(){
        return this.SIZE;
    }

    /**
     * Gets the Cell at row = r and column = c
     * @param r row value of Cell
     * @param c column value of Cell
     * @return returns the Cell at r, c
     */
    public Cell get(int r, int c){
        return this.grid[r][c];
    }

    /**
     * Gets the Cell's locked boolean
     * @param r row value of Cell
     * @param c column value of Cell
     * @return returns the locked boolean of Cell at r, c
     */
    public boolean isLocked(int r, int c){
        return this.grid[r][c].locked;
    }

    /**
     * @return returns the number of locked Cells in a Board
     */
    public int numLocked(){
        int lockedCells = 0;

        // looks through the board and checks the locked status of each Cell
        for (int i = 0; i < this.SIZE; i++){
            for (int j = 0; j < this.SIZE; j++){
                if (this.grid[i][j].locked == true){
                    lockedCells += 1;
                } else {
                    continue;
                }
            }
        }
        return lockedCells;
    }

    /** Returns the value of Cell at r, c
     * @param r row of Cell
     * @param c column of Cell
     * @return returns value of Cell
     */
    public int value(int r, int c){
        return this.grid[r][c].value;
    }

    /**
     * Sets value of Cell at r, c
     * @param r row of Cell
     * @param c column of Cell
     * @param value value being set
     */
    public void set(int r, int c, int value){
        this.grid[r][c].value = value;
    }

    /**
     * Sets value and locked status of Cell at r, c
     * @param r row of Cell
     * @param c column of Cell
     * @param value value being set
     * @param locked Locked Status being set
     */
    public void set(int r, int c, int value, boolean locked){
        this.grid[r][c].value = value;

        this.grid[r][c].locked = locked;
    }

    /**
     * Method that checks for whether a value is valid in its location
     * Applies with the rules of Sudoku, where the numbers in each row, column, and 3x3 square is relevant
     * @param row row of Cell
     * @param col column of Cell
     * @param value value of Cell
     * @return returns validity of Cell (boolean)
     */
    public boolean validValue(int row, int col, int value){
        // if the value is 0, automatically return false
        if (value < 1 || value > this.SIZE){
            return false;
        }

        // checks for repeating values in rows
        for (int i = 0; i < this.SIZE; i++){
            if (i != row && this.grid[i][col].getValue() == value){
                return false; 
            }
        }
        
        // checks for repeating values in columns
        for (int j = 0; j < this.SIZE; j++){
            if (j != col && this.grid[row][j].getValue() == value){
                return false;
            }
        }

        // uses integer division and check for repeating values in 3x3 squares
        for (int r = (row / 3) * 3; r < ((row / 3) * 3) + 3; r++){
            for (int c = (col / 3) * 3;  c < ((col / 3) * 3) + 3; c++){

                // checks if there are identical numbers within the 3x3
                if (!(r == row && c == col) && this.grid[r][c].getValue() == value) {
                    return false;
                } 
            }
        }
        return true;
    }

    /** 
     * Checks if the board is all filled in and contains valid solutions
     * @return returns true if all values are valid and filled in
     */
    public boolean validSolution(){
        for (int i = 0; i < this.SIZE; i++){
            for (int j = 0; j < this.SIZE; j++){

                // if values of the grid are not valid or the grid contains Cells = 0, then return false
                if (!(validValue(i, j, this.grid[i][j].getValue())) || this.grid[i][j].getValue() == 0){
                    return false;
                }
            }
        }
        return true;
    }

    
    /**
     * Will read a textfile of a sudoku board and make a Board with values of the textfile
     * @param filename a textfile with a sudoku board
     * @return returns true if reading is a success
     * @return returns false if unable to read
     */
    public boolean read(String filename) {
        try {
            // assign to a variable of type FileReader a new FileReader object, passing filename to the constructor
            FileReader fr = new FileReader(filename);
            // assign to a variable of type BufferedReader a new BufferedReader, passing the FileReader variable to the constructor
            BufferedReader br = new BufferedReader(fr);
            
            // assign to a variable of type String line the result of calling the readLine method of your BufferedReader object.
            String line = br.readLine();
            int row = 0;
            
            // start a while loop that loops while line isn't null
            while(line != null){
                // assign to an array of type String the result of calling split on the line with the argument "[ ]+"
                String[] arr = line.split("[ ]+");
                // print the String (line)
                for (int i = 0; i < 9; i++){
                    this.grid[row][i].setValue(Integer.parseInt(arr[i]));
                }

                
                // print the size of the String array (you can use .length)
                // System.out.println("Length: " +  arr.length);
                // assign to line the result of calling the readLine method of your BufferedReader object.
                line = br.readLine();
                row++;

            }
            
            // Sets each Cell that contains value of textfile 
            for (int i = 0; i < this.SIZE; i++){
                for (int j = 0; j < this.SIZE; j++){
                    if (this.grid[i][j].getValue() != 0){
                        this.grid[i][j].setLocked(true);
                    }
                }
            }
            
            // call the close method of the BufferedReader
            br.close();
            return true;
        }
        catch(FileNotFoundException ex) {
          System.out.println("Board.read():: unable to open file " + filename);
        }
        catch(IOException ex) {
          System.out.println("Board.read():: error reading file " + filename);
        }
    return false;
    }

    public static void main(String[] args){
        // Testing a standard board
        Board board = new Board(17);    

        // Gets the column size and row size
        System.out.println("Column size: " + board.getCols());
        System.out.println("Row size: " + board.getRows());
        // board.read(args[0]);
        // board.read(args[1]);

        // Checking for validSoltuion
        if (board.validSolution() == false){
            System.out.println("board is not solved");
        } else {
            System.out.println("board is solved");
        }
        // Prints the board (toString)
        System.out.println(board);

        

        // Testing read method
        Board board2 = new Board("board1.txt");
        if (board2.validSolution() == false){
            System.out.println("board2 is not solved");
        } else {
            System.out.println("board2 is solved");
        }
        System.out.println(board2);
        
        
        // Testing validSolution with a completed board
        Board board3 = new Board("board3.txt");
        if (board3.validSolution() == false){
            System.out.println("board3 is not solved");
        } else {
            System.out.println("board3 is solved");
        }
        System.out.println(board3);
        

        // Testing set and get methods
        Board board4 = new Board();
        board4.set(4, 6, 8);
        board4.set(2, 8, 5, false);
        board4.get(4, 6); // Should get 8 as the value
        if (board4.validSolution() == false){
            System.out.println("board4 is not solved");
        } else {
            System.out.println("board4 is solved");
        }
        System.out.println(board4);
        
        
    }
}
