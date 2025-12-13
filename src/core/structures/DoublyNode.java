package core.structures;

public class DoublyNode {
    public int data;
    public DoublyNode next;
    public DoublyNode prev;

    public DoublyNode(int data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }
}
