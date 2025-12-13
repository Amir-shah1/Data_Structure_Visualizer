package core.structures;

import core.structures.CircularNode;

public class CircularLinkedList {
    private CircularNode head;
    private int size;

    public CircularLinkedList() {
        head = null;
        size = 0;
    }

    public boolean delete(int value) {
        if (head == null)
            return false;

        // Case 1: Head is the node to delete
        if (head.data == value) {
            deleteFirst();
            return true;
        }

        // Case 2: Search in the rest of the list
        CircularNode current = head.next;
        CircularNode prev = head;

        // Traverse until we comeback to head
        while (current != head) {
            if (current.data == value) {
                prev.next = current.next;
                size--;
                return true;
            }
            prev = current;
            current = current.next;
        }

        return false;
    }

    public void insertFirst(int value) {
        CircularNode newNode = new CircularNode(value);
        if (head == null) {
            head = newNode;
            newNode.next = head;
        } else {
            CircularNode temp = head;
            while (temp.next != head) {
                temp = temp.next;
            }
            newNode.next = head;
            temp.next = newNode;
            head = newNode;
        }
        size++;
    }

    public void insertLast(int value) {
        CircularNode newNode = new CircularNode(value);
        if (head == null) {
            head = newNode;
            newNode.next = head;
        } else {
            CircularNode temp = head;
            while (temp.next != head) {
                temp = temp.next;
            }
            temp.next = newNode;
            newNode.next = head;
        }
        size++;
    }

    public void insertAt(int index, int value) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("Invalid index");
        if (index == 0) {
            insertFirst(value);
            return;
        }

        CircularNode newNode = new CircularNode(value);
        CircularNode current = head;
        for (int i = 0; i < index - 1; i++) {
            current = current.next;
        }
        newNode.next = current.next;
        current.next = newNode;
        size++;
    }

    public void deleteFirst() {
        if (head == null)
            return;
        if (head.next == head) {
            head = null;
        } else {
            CircularNode temp = head;
            while (temp.next != head)
                temp = temp.next;
            head = head.next;
            temp.next = head;
        }
        size--;
    }

    public void deleteLast() {
        if (head == null)
            return;
        if (head.next == head) {
            head = null;
        } else {
            CircularNode current = head;
            CircularNode prev = null;
            while (current.next != head) {
                prev = current;
                current = current.next;
            }
            prev.next = head;
        }
        size--;
    }

    public int search(int value) {
        if (head == null)
            return -1;
        CircularNode current = head;
        int index = 0;
        do {
            if (current.data == value)
                return index;
            current = current.next;
            index++;
        } while (current != head);
        return -1;
    }

    public void clear() {
        head = null;
        size = 0;
    }

    public CircularNode getHead() {
        return head;
    }

    public int getSize() {
        return size;
    }
}
