import org.checkerframework.framework.qual.JavaExpression;

import java.util.ArrayList;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T> {
    private Node sentinel;
    private int size;
    private int rIndex;

    private class Node {
        T item;
        Node prev;
        Node next;

        Node(Node p, T i, Node n) {
            prev = p;
            item = i;
            next = n;
        }
    }

    public LinkedListDeque61B() {
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    @Override
    public void addFirst(T x) {
        Node newn = new Node(sentinel, x, sentinel.next);
        sentinel.next.prev = newn;
        sentinel.next = newn;
        size++;

    }

    @Override
    public void addLast(T x) {
        Node newn = new Node(sentinel.prev, x, sentinel);
        sentinel.prev.next = newn;
        sentinel.prev = newn;
        size++;

    }

    @Override
    public List<T> toList() {
        List<T> list = new ArrayList<>();
        Node current = sentinel.next;
        while (current != sentinel) {
            list.add(current.item);
            current = current.next;
        }
        return list;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        Node firstt = sentinel.next;
        sentinel.next = firstt.next;
        firstt.next.prev = sentinel;
        size--;
        return firstt.item;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        Node lastt = sentinel.prev;
        sentinel.prev = lastt.prev;
        lastt.prev.next = sentinel;
        size--;
        return lastt.item;
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        Node current = sentinel.next;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.item;
    }

    @JavaExpression
    public T helper(int index, Node used) {
        if (used == sentinel) {
            return null;
        }
        if (index == 0) {
            return used.item;
        } else {
            return helper(index - 1, used.next);
        }
    }

    @Override
    public T getRecursive(int index) {
        return helper(index, sentinel.next);
    }
}

