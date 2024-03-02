import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private Node root;
    private int size;

    public BSTMap() {
        root = null;
        size = 0;
    }

    @Override
    public void put(K key, V value) {
        root = put(root, key, value, true);
    }

    private Node put(Node node, K key, V value, boolean hasValue) {
        if (node == null) {
            size++;
            return new Node(key, value, hasValue);
        }
        int compareThis = key.compareTo(node.key);
        if (compareThis < 0) node.left = put(node.left, key, value, hasValue);
        else if (compareThis > 0) node.right = put(node.right, key, value, hasValue);
        else {
            node.value = value;
            node.hasValue = hasValue;
        }
        return node;
    }

    @Override
    public V get(K key) {
        return get(root, key);
    }

    @Override
    public boolean containsKey(K key) {
        return containsKey(root, key);
    }

    private V get(Node node, K key) {
        if (node == null) return null;
        int compareThis = key.compareTo(node.key);
        if (compareThis < 0) return get(node.left, key);
        else if (compareThis > 0) return get(node.right, key);
        else return node.value;
    }

    public boolean containsKey(Node node, K key) {
        if (node == null) return false;
        int compareThis = key.compareTo(node.key);
        if (compareThis < 0) return containsKey(node.left, key);
        else if (compareThis > 0) return containsKey(node.right, key);
        else return node.hasValue;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException("keySet not used");
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException("remove not used");
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException("iterator not used");
    }

    private class Node {
        K key;
        V value;
        boolean hasValue;
        Node left, right;

        public Node(K key, V value, boolean hasValue) {
            this.key = key;
            this.value = value;
            this.hasValue = hasValue;
        }
    }
}
