package bstmap;

import java.util.*;

/** Try to implement a LLRB-BST */

public class BSTMap<K extends Comparable, V> implements Map61B<K, V> {

    private class BSTNode {
        K key;
        V value;
        BSTNode parent = null, leftChild = null, rightChild = null;
        /** Type == true is equivalent to "the edge connecting this and parent is Red". */
        boolean Type;
        /** this is a left or right child? -1 = left, 1 = right, 0 = root. */
        int parent_dir = 0;

        public BSTNode(K key, V value, BSTNode parent, boolean type, int parent_dir) {
            this.key = key;
            this.value = value;
            this.parent = parent;
            this.Type = type;
            this.parent_dir = parent_dir;
        }

        private void addEdge(K key, V value) {
            if (this.key.compareTo(key) > 0) {
                this.leftChild = new BSTNode(key, value ,this, true, -1);
            } else {
                this.rightChild = new BSTNode(key, value, this, true, 1);
            }
        }

        private void rotateRight() {
            boolean tmp = this.leftChild.Type;
            this.leftChild.Type = this.Type;
            this.Type = tmp;
            this.leftChild.parent_dir = this.parent_dir;
            if (this.parent_dir ==0) {
                root = this.leftChild;
                this.leftChild = root.rightChild;
                root.rightChild = this;

                root.parent = null;
                this.parent = root;
            } else if (this.parent_dir == -1) {
                this.parent.leftChild = this.leftChild;
                this.leftChild = this.parent.leftChild.rightChild;
                this.parent.leftChild.rightChild = this;

                this.parent.leftChild.parent = this.parent;
                this.parent = this.parent.leftChild;
            } else {
                this.parent.rightChild = this.leftChild;
                this.leftChild = this.parent.rightChild.rightChild;
                this.parent.rightChild.rightChild = this;

                this.parent.rightChild.parent = this.parent;
                this.parent = this.parent.rightChild;
            }
            this.parent_dir = 1;
        }

        private void rotateLeft() {
            boolean tmp = this.rightChild.Type;
            this.rightChild.Type = this.Type;
            this.Type = tmp;
            this.rightChild.parent_dir = this.parent_dir;
            if (this.parent_dir ==0) {
                root = this.rightChild;
                this.rightChild = root.leftChild;
                root.leftChild = this;

                root.parent = null;
                this.parent = root;
            } else if (this.parent_dir == 1) {
                this.parent.rightChild = this.rightChild;
                this.rightChild = this.parent.rightChild.leftChild;
                this.parent.rightChild.leftChild = this;

                this.parent.rightChild.parent = this.parent;
                this.parent = this.parent.rightChild;
            } else {
                this.parent.leftChild = this.rightChild;
                this.rightChild = this.parent.leftChild.leftChild;
                this.parent.leftChild.leftChild = this;

                this.parent.leftChild.parent = this.parent;
                this.parent = this.parent.leftChild;
            }
            this.parent_dir = -1;
        }

        private void flip() {
            this.Type = !this.Type;
            this.leftChild.Type = !this.leftChild.Type;
            this.rightChild.Type = !this.rightChild.Type;
        }

        /** There are three kinds of balancing moves:
         *  1. Continuous left red edges --> rotate right (and then flip the new root)
         *  2. Both children are red --> flip the root.
         *  3. Right red edge --> rotate left. */
        private void balance() {
            if (this.leftChild != null && this.leftChild.leftChild != null
            && this.leftChild.Type && this.leftChild.leftChild.Type) {
                this.rotateRight();
                this.parent.balance();
            } else if (this.leftChild != null && this.rightChild != null
                    && this.leftChild.Type && this.rightChild.Type) {
                this.flip();
                if (this.parent != null) {
                    if (this.parent.parent != null) {
                        this.parent.parent.balance();
                    }
                    this.parent.balance();
                }
            } else if (this.rightChild != null && this.rightChild.Type) {
                this.rotateLeft();
                this.parent.balance();
            }
        }

        private BSTNode findReplace() {
            BSTNode result = this;
            while (result.leftChild != null) {
                result = result.leftChild;
            }
            return result;
        }

        private void delete(K key) {
            if (this.key.compareTo(key) == 0) {
                this.parent.leftChild = null;
                return;
            } else if (this.key.compareTo(key) > 0) {
                if (!this.leftChild.Type && (this.leftChild.leftChild != null && !this.leftChild.leftChild.Type)) {
                    this.flip();
                    if (this.rightChild != null && this.rightChild.leftChild != null
                            && this.rightChild.leftChild.Type) {
                        this.rightChild.rotateRight();
                        this.rotateLeft();
                        this.flip();
                        this.delete(key);
                        this.balance();
                        return;
                    }
                }
                this.leftChild.delete(key);
            } else {
                if (!this.rightChild.Type && (this.rightChild.leftChild != null && !this.rightChild.leftChild.Type)) {
                    this.flip();
                    if (this.leftChild.leftChild != null
                            && this.leftChild.leftChild.Type) {
                        this.rotateRight();
                        this.parent.flip();
                        this.rotateLeft();
                        this.parent.delete(key);
                        this.balance();
                        return;
                    }
                }
                this.delete(key);
            }
            this.balance();
        }
    }

    private BSTNode root;
    private int size = 0;

    private class BSTMapIter implements Iterator<K>{
        Stack<BSTNode> stack;
        public BSTMapIter() {
            BSTNode cur = root;
            while (cur != null) {
                stack.push(cur);
                cur = cur.leftChild;
            }
        }
        public boolean hasNext() {
            return !stack.empty();
        }
        public K next() {
            BSTNode cur = stack.pop();
            K result = cur.key;
            if (cur.rightChild != null) {
                cur = cur.rightChild;
                while (cur != null) {
                    stack.push(cur);
                    cur = cur.leftChild;
                }
            }
            return result;
        }
    }

    /** returns the closest Node to the given key*/
    private BSTNode trace(K key, BSTNode tmp) {
        if (tmp == null) {
            return null;
        }
        if ((tmp.leftChild == null && tmp.rightChild == null) || tmp.key.compareTo(key) == 0) {
            return tmp;
        } else if (tmp.key.compareTo(key) > 0) {
            if (tmp.leftChild != null) {
                return trace(key, tmp.leftChild);
            } else {
                return tmp;
            }
        } else {
            if (tmp.rightChild != null) {
                return trace(key, tmp.rightChild);
            } else {
                return tmp;
            }
        }
    }

    public void clear() {
        root = null;
        size = 0;
    }

    public boolean containsKey(K key) {
        BSTNode result = trace(key, root);
        if (result == null) {
            return false;
        }
        return result.key.compareTo(key) == 0;
    }

    public V get(K key) {
        BSTNode result = trace(key, root);
        if (result == null || result.key.compareTo(key) != 0) {
            return null;
        } else {
            return result.value;
        }
    }

    public int size() {
        return this.size;
    }

    public void put(K key, V value) {
        size += 1;
        BSTNode nearest = trace(key, root);
        if (nearest == null) {
            root = new BSTNode(key, value, null, false, 0);
            return;
        }
        if (nearest.key.compareTo(key) == 0) {
            nearest.value = value;
            return;
        }
        nearest.addEdge(key, value);
        nearest.balance();
    }

    public Set<K> keySet() {
        Set<K> result = new HashSet<>();
        for (K k : this) {
            result.add(k);
        }
        return result;
    }

    public V remove(K key) {
        BSTNode target = this.trace(key, root);
        if (target.key.compareTo(key) != 0) {
            return null;
        }
        V result = target.value;
        if (target.rightChild == null) {
            target.rotateRight();
        }
        BSTNode scapeGoat = target.rightChild.findReplace();
        target.key = scapeGoat.key;
        target.value = scapeGoat.value;
        root.delete(scapeGoat.key);
        return result;
    }

    public V remove(K key, V value) {
        if (get(key) == value) {
            return remove(key);
        }
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return new BSTMapIter();
    }
}
