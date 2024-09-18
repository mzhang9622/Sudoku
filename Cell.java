/*
 * Ming Zhang
 * 10/13/2022
 */
import java.awt.Graphics;
import java.awt.Color;

// the Cell class
public class Cell {
    
    // Integer fields for index, columnIndex, and value
    protected int index;
    protected int columnIndex;
    protected int value;

    // Boolean for locked, defaulted true
    protected boolean locked;

    // Constructor for Cell
    // Initiates the index, columnIndex, and value to be 0
    public Cell(){
        this.index = 0;
        this.columnIndex = 0;
        this.value = 0;
    }

    // Constructor for Cell that gives the Cell a row index, column index, and a value
    /**
     * @param row the row index for the Cell
     * @param col the column index for the Cell
     * @param value the value of the Cell
     */
    public Cell(int row, int col, int value){
        this.index = row;

        this.columnIndex = col;

        this.value = value;

        // Sets the Cell to not be locked
        this.locked = false;
    }

    // Sets a Cell to be Locked or not Locked
    /**
     * @param row the row index for the Cell
     * @param col the column index for the Cell
     * @param value the value of the Cell
     * @param locked boolean for when the Cell is locked or not locked
     */
    public Cell(int row, int col, int value, boolean locked){
        this.index = row;

        this.columnIndex = col;

        this.value = value;

        this.locked = locked;
    }

    /**
     * @return returns the row index of Cell
     */
    public int getRow(){
        return this.index;
    }

    /**
     * @return returns the column index of Cell
     */
    public int getCol(){
        return this.columnIndex;
    }

    /**
     * @return returns the value of Cell
     */
    public int getValue(){
        return this.value;
    }

    /**
     * @param newVal sets a new value to Cell
     */
    public void setValue(int newVal){
        this.value = newVal;
    }

    /**
     * @return returns locked status of Cell
     */
    public boolean isLocked(){
        return this.locked;
    }

    /**
     * @param lock locks or unlocks Cell
     */
    public void setLocked(boolean lock){
        this.locked = lock;
    }

    // the toString of the Cell's value
    public String toString(){
        return " " + this.value;
    }

    // Draws the Cell in the Board
    public void draw(Graphics g, int x, int y, int scale){
        String toDraw = Integer.toString(value);
        g.setColor(isLocked()? Color.BLUE : Color.RED);
        g.drawString(toDraw, x, y);
    }

    public static void main(String[] args){
        // Creates a cell and tests its methods
        Cell cell1 = new Cell(1, 1, 7, true);
        System.out.println("Row of cell: " + cell1.getRow());
        System.out.println("Col of cell: " + cell1.getCol());
        System.out.println("Value of cell:" + cell1);
        System.out.println("Locked Status: " + cell1.isLocked());

        cell1.setLocked(false);
        cell1.setValue(4);

        
        System.out.println("Value of Cell:" + cell1.getValue());
        System.out.println("Locked Status: " + cell1.isLocked());
    }
}

