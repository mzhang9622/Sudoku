/*
 * Ming Zhang
 * 10/13/2022
 */
public class CellStack {
    
    // Node class for Stacks
    private class Node{
        Cell cell;
        Node next;

        // initates cell and next
        public Node(Cell cell){
            this.cell = cell;
            this.next = null;
        }
    }

    // Field for head and size of stack
    private Node head; // this should always be the top of my stack
    private int size;

    
    /**
     * initiates head to be null and size to be 0 (empty Stack)
     */
    public CellStack(){
        this.head = null;
        this.size = 0;
    }

    /**
     * @param c adds a new Cell to the top of the Stack
     */
    public void push(Cell c){
        Node newNode = new Node(c);
        newNode.next = this.head;
        this.head = newNode;
        this.size++;
    }

    /**
     * @return returns the top of the Stack
     */
    public Cell peek(){
        return this.head.cell;
    }

    /**
     * removes and returns the top element from the stack; return null if stack is empty
     * @return returns top element from Stack
     */
    public Cell pop(){
        
        Cell temp = new Cell();

        // returns null if Stack is empty
        if (size == 0){
            return null;

        // removes the top of stack and sets its next to be the new top
        } else {
            temp = head.cell;
            this.head = this.head.next;
            size--;
        }
        
        // returns the removed Cell
        return temp;
    }

    
    /**
     * @return returns size of the Stack
     */
    public int size(){
        return size;
    }
    
    /**
     * @return return an empty Stack
     */
    public boolean empty(){
        return size == 0;
    }

}
