package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private int bucketSize;
    private int defaultSize = 16;
    private double maxLoad;
    private int size = 0;
    private HashSet<K> keys = new HashSet<>();
    // You should probably define some more!

    /** Constructors */
    public MyHashMap() {
        bucketSize = defaultSize;
        maxLoad = 0.75;
        buckets = createTable(bucketSize);
    }

    public MyHashMap(int bucketSize) {
        this.bucketSize = defaultSize = bucketSize;
        maxLoad = 0.75;
        buckets = createTable(bucketSize);
    }

    /**
     * MyHashMap constructor that creates a backing array of bucketSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param bucketSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int bucketSize, double maxLoad) {
        this.bucketSize = defaultSize = bucketSize;
        this.maxLoad = maxLoad;
        buckets = createTable(bucketSize);
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        Collection<Node>[] table = new Collection[tableSize];
        for (int i = 0; i < tableSize; i++) {
            table[i] = createBucket();
        }
        return table;
    }

    private int mod(int a, int b) {
        int result = a % b;
        return result >= 0 ? result : result + Math.abs(b);
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!
    public void clear() {
        this.buckets = createTable(defaultSize);
        this.bucketSize = defaultSize;
        this.keys.clear();
        this.size = 0;
    }

    public boolean containsKey(K key) {
        int bucketId = mod(key.hashCode(), bucketSize);
        for (Node curr : this.buckets[bucketId]) {
            if (curr.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    public int size() {
        return size;
    }

    private void resize() {
        int newSize = 2 * bucketSize;
        Collection<Node>[] tmp = createTable(newSize);
        for (int i = 0; i < bucketSize; i++) {
            for (Node curr : this.buckets[i]) {
                tmp[mod(curr.key.hashCode(), newSize)].add(curr);
            }
        }
        bucketSize = newSize;
        this.buckets = tmp;
    }

    public void put(K key, V value) {
        int bucketId = mod(key.hashCode(), bucketSize);
        Node init = this.getNode(key);
        if (init == null) {
            size += 1;
            keys.add(key);
            if ((double) size / bucketSize > maxLoad) {
                resize();
            }
            bucketId = mod(key.hashCode(), bucketSize);
        } else {
            this.buckets[bucketId].remove(init);
        }
        this.buckets[bucketId].add(createNode(key, value));
    }

    private Node getNode(K key) {
        int bucketId = mod(key.hashCode(), bucketSize);
        for (Node curr : this.buckets[bucketId]) {
            if (curr.key.equals(key)){
                return curr;
            }
        }
        return null;
    }

    public V get(K key) {
        Node result = getNode(key);
        if (result == null) {
            return null;
        } else {
            return result.value;
        }
    }

    public Set<K> keySet() {
        return keys;
    }

    @Override
    public Iterator<K> iterator() {
        return keys.iterator();
    }

    public V remove(K key) {
        return null;
    }

    public V remove(K key, V value) {
        return null;
    }
}
