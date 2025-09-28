//package main.structures;

public class Treap<K extends Comparable<K>> {
    private Node root;

    private class Node {
        K key;
        int priority;
        Node left, right;
        Node(K key) {
            this.key = key;
            this.priority = (int)(Math.random() * Integer.MAX_VALUE);
        }
    }

    public Treap() {
        root = null;
    }

    public void insert(K key) {
        if (key == null) throw new IllegalArgumentException();
        root = insertRec(root, key);
    }

    public void remove(K key) {
        if (key == null) throw new IllegalArgumentException();
        root = removeRec(root, key);
    }

    public int countInRange(K low, K high) {
        if (low == null || high == null) throw new IllegalArgumentException();
        if (low.compareTo(high) > 0) return 0;
        return countLE(root, high) - countLT(root, low);
    }

    /** 
     * עכשיו מחזיר מערך של Object, כדי שנוכל לקלוט כל K ולשמור על בטיחות בזמן ריצה.
     */
    public Object[] keysInRange(K low, K high) {
        int cnt = countInRange(low, high);
        Object[] arr = new Object[cnt];
        int[] idx = new int[]{0};
        collect(root, low, high, arr, idx);
        return arr;
    }

    private Node insertRec(Node root, K key) {
        if (root == null) return new Node(key);
        int cmp = key.compareTo(root.key);
        if (cmp < 0) {
            root.left = insertRec(root.left, key);
            if (root.left.priority > root.priority) root = rotateRight(root);
        } else if (cmp > 0) {
            root.right = insertRec(root.right, key);
            if (root.right.priority > root.priority) root = rotateLeft(root);
        }
        return root;
    }

    private Node removeRec(Node root, K key) {
        if (root == null) return null;
        int cmp = key.compareTo(root.key);
        if (cmp < 0) root.left = removeRec(root.left, key);
        else if (cmp > 0) root.right = removeRec(root.right, key);
        else {
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

    private int countLT(Node root, K key) {
        if (root == null) return 0;
        if (root.key.compareTo(key) < 0) {
            return 1 + countLT(root.left, key) + countLT(root.right, key);
        } else {
            return countLT(root.left, key);
        }
    }

    private int countLE(Node root, K key) {
        if (root == null) return 0;
        if (root.key.compareTo(key) <= 0) {
            return 1 + countLE(root.left, key) + countLE(root.right, key);
        } else {
            return countLE(root.left, key);
        }
    }

    private void collect(Node root, K low, K high, Object[] arr, int[] idx) {
        if (root == null) return;
        if (root.key.compareTo(low) < 0) {
            collect(root.right, low, high, arr, idx);
        } else if (root.key.compareTo(high) > 0) {
            collect(root.left, low, high, arr, idx);
        } else {
            collect(root.left, low, high, arr, idx);
            arr[idx[0]++] = root.key;
            collect(root.right, low, high, arr, idx);
        }
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
