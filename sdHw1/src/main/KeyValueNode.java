package main;

import java.util.Objects;

public class KeyValueNode<K, V> {

    private KeyValueNode<K, V> prev;

    private KeyValueNode<K, V> next;

    private V value;

    private final K key;

    public KeyValueNode(K key, V value) {
        this(null, null, key, value);
    }

    public KeyValueNode(final KeyValueNode<K, V> prev, final KeyValueNode<K, V> next, final K key, final V value) {
        this.prev = prev;
        this.next = next;
        this.key = key;
        this.value = value;
    }

    public void setPrev(final KeyValueNode<K, V> prev) {
        this.prev = prev;
    }

    public void setNext(final KeyValueNode<K, V> next) {
        this.next = next;
    }

    public KeyValueNode<K, V> getPrev() {
        return prev;
    }

    public KeyValueNode<K, V> getNext() {
        return next;
    }

    public V getValue() {
        return value;
    }

    public void setValue(final V value) {
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public static <Key, Value> void setLinks(final KeyValueNode<Key, Value> first, final KeyValueNode<Key, Value> second) {
        if (Objects.nonNull(first)) {
            first.next = second;
        }
        if (Objects.nonNull(second)) {
            second.prev = first;
        }
    }
}
