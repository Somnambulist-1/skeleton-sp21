package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    private T[] items;
    private int nextFirst, nextLast, size, maxSize;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 4;
        nextLast = 5;
        maxSize = 8;
    }

    private void checkResize() {
        if (size == maxSize) {
            T[] tmp = (T[]) new Object[size * 2];
            int ptr = (nextFirst + 1) % maxSize;
            for (int i = 0; i < size; i++) {
                tmp[i] = items[ptr];
                ptr += 1;
                if (ptr >= maxSize) {
                    ptr = 0;
                }
            }
            items = tmp;
            maxSize *= 2;
            nextFirst = maxSize - 1;
            nextLast = size;
            return;
        }
        if (size < maxSize / 4 && maxSize >= 16) {
            T[] tmp = (T[]) new Object[maxSize / 4];
            int ptr = (nextFirst + 1) % maxSize;
            for (int i = 0; i < size; i++) {
                tmp[i] = items[ptr];
                ptr += 1;
                if (ptr >= maxSize) {
                    ptr = 0;
                }
            }
            items = tmp;
            maxSize /= 4;
            nextFirst = maxSize - 1;
            nextLast = size;
        }
    }

    @Override
    public void addFirst(T item) {
        items[nextFirst] = item;
        nextFirst -= 1;
        if (nextFirst == -1) {
            nextFirst = maxSize - 1;
        }
        size += 1;
        checkResize();
    }

    @Override
    public void addLast(T item) {
        items[nextLast] = item;
        nextLast += 1;
        size += 1;
        checkResize();
        if (nextLast == maxSize) {
            nextLast = 0;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        int ptr = nextFirst + 1;
        for (int i = 0; i < size; i++) {
            System.out.print(items[ptr] + " ");
            ptr += 1;
            if (ptr >= maxSize) {
                ptr = 0;
            }
        }
        System.out.print("\n");
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        int pos = (nextFirst + 1) % maxSize;
        nextFirst = pos;
        T result = items[pos];
        items[pos] = null;
        size -= 1;
        checkResize();
        return result;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        int pos = (nextLast - 1 + maxSize) % maxSize;
        nextLast = pos;
        T result = items[pos];
        items[pos] = null;
        size -= 1;
        checkResize();
        return result;
    }

    @Override
    public T get(int index) {
        return items[(nextFirst + index + 1) % maxSize];
    }

    public Iterator<T> iterator() {
        return new ADequeIter();
    }

    private class ADequeIter implements Iterator<T> {
        int pos;
        int count = 0;

        ADequeIter() {
            pos = (nextFirst + 1) % maxSize;
        }

        public boolean hasNext() {
            return count < size;
        }

        public T next() {
            T result = items[pos];
            count += 1;
            pos = (pos + 1) % maxSize;
            return result;
        }
    }

    public boolean equals(Object o) {
        if (!(o instanceof Deque)) {
            return false;
        }
        Deque<?> other = (Deque<?>) o;
        if (this.size() != other.size()) {
            return false;
        }
        Iterator<T> me = this.iterator();
        Iterator<?> he = other.iterator();
        while (me.hasNext()) {
            if (me.next() != he.next()) {
                return false;
            }
        }
        return true;
    }
}
