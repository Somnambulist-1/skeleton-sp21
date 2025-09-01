package deque;

import java.util.Comparator;
import java.util.Iterator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {

    private Comparator<T> pred;

    public MaxArrayDeque(Comparator<T> c) {
        pred = c;
    }

    public T max() {
        return max(pred);
    }

    public T max(Comparator<T> c) {
        if (this.isEmpty()) {
            return null;
        }
        Iterator<T> ptr = this.iterator();
        T result = ptr.next();
        while (ptr.hasNext()) {
            T tmp = ptr.next();
            result = c.compare(result, tmp) > 0 ? result : tmp;
        }
        return result;
    }
}
