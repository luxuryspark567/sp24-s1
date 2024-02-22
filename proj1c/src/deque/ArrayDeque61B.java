package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrayDeque61B<T> implements Deque61B<T> {
    public static final int CHECK = 16;
    private T[] items;
    private int size;
    private int nextF;
    private int nextL;


    @Override
    public void addFirst(T x) {
        if (size == items.length) {
            resize(items.length * 2);
        }
        items[nextF] = x;
        nextF = Math.floorMod(nextF - 1, items.length);
        size++;

    }

    @Override
    public void addLast(T x) {
        if (size == items.length) {
            resize(items.length * 2);
        }
        items[nextL] = x;
        nextL = Math.floorMod(nextL + 1, items.length);
        size++;

    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            returnList.add(get(i));
        }
        return returnList;
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
        nextF = Math.floorMod(nextF + 1, items.length);
        T item = items[nextF];
        size--;
        if (size > 0 && items.length >= CHECK && size == items.length / 4) {
            resize(items.length / 2);
        }
        return item;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        nextL = Math.floorMod(nextL - 1, items.length);
        T item = items[nextL];
        size--;
        if (size > 0 && items.length >= CHECK && size == items.length / 4) {
            resize(items.length / 2);
        }
        return item;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        int aIndex = Math.floorMod(nextF + 1 + index, items.length);
        return items[aIndex];
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }

    public ArrayDeque61B() {
        items = (T[]) new Object[8];
        size = 0;
        nextF = 7;
        nextL = 0;
    }

    private void resize(int nSize) {
        T[] newItems = (T[]) new Object[nSize];
        for (int i = 0; i < size; i++) {
            int oldIndex = Math.floorMod(nextF + 1 + i, items.length);
            newItems[i] = items[oldIndex];
        }
        items = newItems;
        nextF = nSize - 1;
        nextL = size;
    }

    private class ArrayIterator<T> implements Iterator<T> {
        private int position;

        public ArrayIterator() {
            position = 0;
        }

        @Override
        public boolean hasNext() {
            return position < size;
        }

        @Override
        public T next() {
            T returnItem = (T) items[position];
            position += 1;
            return returnItem;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayIterator<>();
    }

    @Override
    public String toString() {
        return toList().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Deque61B<?> otherDeck)) {
            return false;
        }

        if (this.size() != otherDeck.size()) {
            return false;
        }

        Iterator<T> thisIter = this.iterator();
        Iterator<?> otherIter = otherDeck.iterator();

        while (thisIter.hasNext() && otherIter.hasNext()) {
            T thisElement = thisIter.next();
            Object otherElement = otherIter.next();

            if (!thisElement.equals(otherElement)) {
                return false;
            }
        }

        return true;
    }
}
