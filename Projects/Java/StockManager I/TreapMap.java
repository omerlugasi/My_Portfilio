//package main.structures;

public class TreapMap<K extends Comparable<K>, V> {
    private Node root;

    private class Node {
        K key;
        V value;
        int priority;
        Node left, right;
        Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.priority = (int)(Math.random() * Integer.MAX_VALUE);
        }
    }

    public TreapMap() {
        root = null;
    }

    public boolean containsKey(K key) {
        return getNode(root, key) != null;
    }

    public V get(K key) {
        Node n = getNode(root, key);
        if (n == null) throw new IllegalArgumentException();
        return n.value;
    }

    public void put(K key, V value) {
        if (key == null || value == null) throw new IllegalArgumentException();
        root = insertRec(root, key, value);
    }

    public void remove(K key) {
        if (key == null) throw new IllegalArgumentException();
        root = removeRec(root, key);
    }

    private Node getNode(Node root, K key) {
        if (root == null) return null;
        int cmp = key.compareTo(root.key);
        if (cmp == 0) return root;
        else if (cmp < 0) return getNode(root.left, key);
        else return getNode(root.right, key);
    }

    private Node insertRec(Node root, K key, V value) {
        if (root == null) return new Node(key, value);
        int cmp = key.compareTo(root.key);
        if (cmp < 0) {
            root.left = insertRec(root.left, key, value);
            if (root.left.priority > root.priority) root = rotateRight(root);
        } else if (cmp > 0) {
            root.right = insertRec(root.right, key, value);
            if (root.right.priority > root.priority) root = rotateLeft(root);
        } else {
            root.value = value;
        }
        return root;
    }

    private Node removeRec(Node root, K key) {
        if (root == null) return null;
        int cmp = key.compareTo(root.key);
        if (cmp < 0) {
            root.left = removeRec(root.left, key);
        } else if (cmp > 0) {
            root.right = removeRec(root.right, key);
        } else {
            if (root.left == null) return root.right;
            else if (root.right == null) return root.left;
            else {
                if (root.left.priority > root.right.priority) {
                    root = rotateRight(root);
                    root.right = removeRec(root.right, key);
                } else {
                    root = rotateLeft(root);
                    root.left = removeRec(root.left, key);
                }
            }
        }
        return root;
    }

    private Node rotateRight(Node y) {
        Node x = y.left;
        y.left = x.right;
        x.right = y;
        return x;
    }

    private Node rotateLeft(Node x) {
        Node y = x.right;
        x.right = y.left;
        y.left = x;
        return y;
    }
}
