package hashmap;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

/**
 *  A hash table-backed Map implementation.
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
    private static final int capacity = 16;
    private static final double factor = .75;
    private final double loadFactor;
    private int size;

    /** Constructors */
    public MyHashMap() {
        this(capacity, factor);
    }

    public MyHashMap(int initialCapacity) {
        this(initialCapacity, factor);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        this.buckets = (Collection<Node>[]) new Collection[initialCapacity];
        for (int i = 0; i < initialCapacity; i++) {
            this.buckets[i] = createBucket();
        }
        this.size = 0;
        this.loadFactor = loadFactor;
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *  Note that that this is referring to the hash table bucket itself,
     *  not the hash map itself.
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
    private int getBucketIndex(K key) {
        return Math.floorMod(key.hashCode(), buckets.length);
    }

    private void resize() {
        if ((double) size / buckets.length > loadFactor) {
            Collection<Node>[] oldB = buckets;
            buckets = (Collection<Node>[]) new Collection [oldB.length * 2];
            for (int i = 0; i < buckets.length; i++) {
                buckets[i] = createBucket();
            }
            size = 0;
            for (Collection<Node> bucket : oldB){
                for (Node node : bucket) {
                    put(node.key, node.value);
                }
            }
        }
    }

    @Override
    public void put(K key, V value) {
        resize();
        int indexB = getBucketIndex(key);
        for (Node node : buckets[indexB]) {
            if (node.key.equals(key)) {
                node.value = value;
                return;
            }
        }
        buckets[indexB].add(new Node(key, value));
        size ++;
    }

    @Override
    public V get(K key) {
        int index = getBucketIndex(key);
        for (Node node : buckets[index]) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        int index = getBucketIndex(key);
        for (Node node : buckets[index]) {
            if (node.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        for (int i = 0; i < buckets.length; i++) {
            buckets[i].clear();
        }
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

}
