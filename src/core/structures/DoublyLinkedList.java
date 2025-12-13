package core.structures;

import core.structures.DoublyNode;

public class DoublyLinkedList {
    private DoublyNode head;
    private DoublyNode tail;
    private int size;

    public DoublyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    public boolean delete(int value) {
        if (head == null) return false;

        DoublyNode current = head;
        while (current != null) {
            if (current.data == value) {
                if (current == head) {
                    deleteFirst();
                } else if (current == tail) {
                    deleteLast();
                } else {
                    current.prev.next = current.next;
                    current.next.prev = current.prev;
                    size--;
                }
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public void insertFirst(int value) {
        DoublyNode newNode = new DoublyNode(value);
        if (head == null) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    public void insertLast(int value) {
        DoublyNode newNode = new DoublyNode(value);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
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
        if (index == size) {
            insertLast(value);
            return;
        }

        DoublyNode newNode = new DoublyNode(value);
        DoublyNode current = head;
        for (int i = 0; i < index - 1; i++) {
            current = current.next;
        }
        newNode.next = current.next;
        newNode.prev = current;
        current.next.prev = newNode;
        current.next = newNode;
        size++;
    }

    public void deleteFirst() {
        if (head == null)
            return;
        if (head == tail) {
            head = tail = null;
        } else {
            head = head.next;
            head.prev = null;
        }
        size--;
    }

    public void deleteLast() {
        if (head == null)
            return;
        if (head == tail) {
            head = tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }
        size--;
    }

    public int search(int value) {
        DoublyNode current = head;
        int index = 0;
        while (current != null) {
            if (current.data == value)
                return index;
            current = current.next;
            index++;
        }
        return -1;
    }

    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    public DoublyNode getHead() {
        return head;
    }

    public DoublyNode getTail() {
        return tail;
    } // Optional, but good for O(1) inserts

    public int getSize() {
        return size;
    }
}
