package deque;

import java.util.Iterator;

public class ArrayDeque<T> {
    public T[] items;
    public int nextFirst, nextLast, size, Maxsize;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 4;
        nextLast = 5;
        Maxsize = 8;
    }

    private void check_resize() {
        if (size == Maxsize) {
            T[] tmp = (T[]) new Object[size * 2];
            int ptr = (nextFirst + 1) % Maxsize;
            for (int i = 0; i < size; i++) {
                tmp[i] = items[ptr];
                ptr += 1;
                if (ptr >= Maxsize) {
                    ptr = 0;
                }
            }
            items = tmp;
            Maxsize *= 2;
            nextFirst = Maxsize - 1;
            nextLast = size;
            return;
        }
        if (size < Maxsize / 4 && Maxsize >= 16) {
            T[] tmp = (T[]) new Object[Maxsize / 4];
            int ptr = (nextFirst + 1) % Maxsize;
            for (int i = 0; i < size; i++) {
                tmp[i] = items[ptr];
                ptr += 1;
                if (ptr >= Maxsize) {
                    ptr = 0;
                }
            }
            items = tmp;
            Maxsize /= 4;
            nextFirst = Maxsize - 1;
            nextLast = size;
        }
    }

    public void addFirst(T item) {
        items[nextFirst] = item;
        nextFirst -= 1;
        if (nextFirst == -1) {
            nextFirst = size - 1;
        }
        size += 1;
        check_resize();
    }

    public void addLast(T item) {
        items[nextLast] = item;
        nextLast += 1;
        size += 1;
        check_resize();
        if (nextLast == Maxsize) {
            nextLast = 0;
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void printDeque() {
        int ptr = nextFirst + 1;
        for (int i = 0; i < size; i++) {
            System.out.print(items[ptr] + " ");
            ptr += 1;
            if (ptr >= Maxsize) {
                ptr = 0;
            }
        }
        System.out.print("\n");
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        int pos = (nextFirst + 1) % Maxsize;
        nextFirst = pos;
        T result = items[pos];
        size -= 1;
        check_resize();
        return result;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        int pos = (nextLast - 1 + Maxsize) % Maxsize;
        nextLast = pos;
        T result = items[pos];
        size -= 1;
        check_resize();
        return result;
    }

    public T get(int index) {
        return items[(nextFirst + index + 1) % Maxsize];
    }

    public Iterator<T> iterator() {
        return new ADequeIter();
    }

    public class ADequeIter implements Iterator<T> {
        int pos;

        public ADequeIter() {
            pos = (nextFirst + 1) % Maxsize;
        }

        public boolean hasNext() {
            return pos != nextLast;
        }

        public T next() {
            T result = get(pos);
            pos = (pos + 1) % Maxsize;
            return result;
        }
    }

    public boolean equals(Object o) {
        if (!(o instanceof ArrayDeque)) {
            return false;
        }
        ArrayDeque<T> other = (ArrayDeque<T>) o;
        if (other.size() != size) {
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
