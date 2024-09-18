/*
 * Ming Zhang
 * CS231
 * Section A
 * 10/13/2022
 */

public class Sudoku {
    public static final int SIZE = 9;

    // Declaring a Board object
    public Board board;

    // determines how many locked values
    int numLocked;

    // Landscape field
    LandscapeDisplay ld;

    // Constructor for Sudoku class
    // Creates a board and sets default number of locked values
    public Sudoku(){
        
        this.numLocked = 10;
        board = new Board(numLocked);
        ld = new LandscapeDisplay(board);
        

    }

    /**
     * Creates a board with fixed number of locked Cells
     * @param fixedCells the number of locked Cells in the board
     */
    public Sudoku(int fixedCells){

        this.numLocked = fixedCells;
        board = new Board(numLocked);
        ld = new LandscapeDisplay(board);

    }



    /** Checks each Cell to find valid numbers
     * @return returns null if there are no more values to check
     */
    public Cell findNextCell(){
        // Loops through the board
        for (int i = 0; i < this.SIZE; i++){
            for (int j = 0; j < this.SIZE; j++){

                // if the Cell's value is 0, then it checks for valid values
                // and sets the valid value to the Cell
                if (this.board.value(i, j) == 0){

                    for (int k = 1; k < this.SIZE + 1; k++){

                        if (this.board.validValue(i, j, k) == true){
                            this.board.set(i, j, k);
                            return this.board.get(i, j);
                        }
                    } return null;
                }
            }
        } return null;
    }

        
    /**
     * Will check through every value to search for a solution where the conditions satisfy the Sudoku Game
     * @param delay execution time of the method
     * @return returns true if a solution is found
     * @return returns false if a solution cannot be found (stack.empty() == true)
     * @throws InterruptedException
     */
    public boolean solve(int delay) throws InterruptedException{
        // creates a stack object
        CellStack stack = new CellStack();
        
        // while the stack size is less than the total number of Cells minus locked Cells (Cells with value = 0)
        while (stack.size() < (this.SIZE * this.SIZE - numLocked)){
            // timer
            if (delay > 0){
                Thread.sleep(delay);
            }
            if (ld != null){
                ld.repaint();
            }

            // Creates a new Cell that will replace the Cell when value is changed
            Cell nextCell = findNextCell();

            // if findNextCell() does have a valid value, adds the Cell to the top of the stack  
            if (nextCell != null){
                stack.push(nextCell); 
            } else {
                // for when the stack is not empty, removes the takes the Cell at the top of the stack
                while (stack.empty() != true){
                    
                    nextCell = stack.pop();

                    // boolean for whether the value is advancing
                    boolean advanced = false;

                    // for the next value of the current value (it will check every possible value from 1-9)
                    for (int i = nextCell.getValue() + 1; i < this.SIZE + 1; i++){

                        // if the value is valid at the row and column, the set the new value and advances
                        // pushes the Cell to the top of the Stack
                        if(this.board.validValue(nextCell.getRow(), nextCell.getCol(), i)) {
                            nextCell.setValue(i);
                            advanced = true;
                            stack.push(nextCell);
                            break;
                        }
                    } 
                    // breaks out of while loop if advancing
                    if (advanced) {
                        break;
                    } else {
                        // sets value to 0 if none of the cellls worked
                        nextCell.setValue(0);
                    }
                    
                } 
                // if the stack is empty, the board is finished and returns false (no solution)
                if (stack.empty() == true){
                    board.finished = true;
                    return false; // No solution
                }
            }
        }
        // if every value is filled in and valid, then set finished to true and return true (there is at least one solution)
        board.finished = true;
        return true; // There is at least one solution
    }


    


    public static void main(String[] args) throws InterruptedException{
        // Solves a given board
        // Tests read method and numLocked methods
        Sudoku sudoku1 = new Sudoku(0);

        // Input file name
        sudoku1.board.read("test1.txt");
        System.out.println("Lockedcells:" + sudoku1.board.numLocked());

        sudoku1.numLocked = sudoku1.board.numLocked();

        System.out.println(sudoku1.board);

        // solves and prints the board
        sudoku1.solve(1);

        System.out.println(sudoku1.board);

    }

}

