package deque;

import java.util.Iterator;

public class LinkedListDeque<T>implements Deque<T>, Iterable<T> {
    private int size;
    private Tnode sentinel;

    private class Tnode {
        private T item;
        private Tnode previous, next;

        Tnode(T f) {
            item = f;
            previous = next = null;
        }

        private T getRecursive(int index) {
            if (index == 0) {
                return item;
            } else {
                return next.getRecursive(index - 1);
            }
        }
    }

    public LinkedListDeque() {
        this.sentinel = new Tnode(null);
        this.sentinel.previous = this.sentinel;
        this.sentinel.next = this.sentinel;
        this.size = 0;
    }

    @Override
    public void addFirst(T item) {
        Tnode tmp = new Tnode(item);
        tmp.next = this.sentinel.next;
        this.sentinel.next.previous = tmp;
        this.sentinel.next = tmp;
        tmp.previous = this.sentinel;
        this.size += 1;
    }

    @Override
    public void addLast(T item) {
        Tnode tmp = new Tnode(item);
        tmp.previous = this.sentinel.previous;
        this.sentinel.previous.next = tmp;
        this.sentinel.previous = tmp;
        tmp.next = this.sentinel;
        this.size += 1;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void printDeque() {
        Tnode tmp = this.sentinel;
        while (tmp.next != this.sentinel) {
            tmp = tmp.next;
            System.out.print(tmp.item + " ");
        }
        System.out.print("\n");
    }

    @Override
    public T removeFirst() {
        if (this.size == 0) {
            return null;
        }
        Tnode tmp = this.sentinel.next;
        this.sentinel.next = tmp.next;
        tmp.next.previous = this.sentinel;
        this.size -= 1;
        return tmp.item;
    }

    @Override
    public T removeLast() {
        if (this.size == 0) {
            return null;
        }
        Tnode tmp = this.sentinel.previous;
        this.sentinel.previous = tmp.previous;
        tmp.previous.next = this.sentinel;
        this.size -= 1;
        return tmp.item;
    }

    @Override
    public T get(int index) {
        if (this.size <= index) {
            return null;
        }
        Tnode result = this.sentinel.next;
        for (int i = 0; i < index; i++) {
            result = result.next;
        }
        return result.item;
    }

    public T getRecursive(int index) {
        if (this.size <= index) {
            return null;
        } else {
            return this.sentinel.next.getRecursive(index);
        }
    }

    public Iterator<T> iterator() {
        return new LLDequeIter();
    }

    private class LLDequeIter implements Iterator<T> {
        int pos;

        LLDequeIter() {
            pos = 0;
        }

        public boolean hasNext() {
            return this.pos < size - 1;
        }

        public T next() {
            T result = get(pos);
            pos += 1;
            return result;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Deque)) {
            return false;
        }
        Deque<T> other = (Deque<T>) o;
        if (this.size() != other.size()) {
            return false;
        }
        Iterator<T> me = this.iterator();
        Iterator<T> he = other.iterator();
        while (me.hasNext()) {
            if (me.next() != he.next()) {
                return false;
            }
        }
        return true;
    }
}
