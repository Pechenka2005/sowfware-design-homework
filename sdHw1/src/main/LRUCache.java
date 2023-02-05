package main;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class LRUCache<K, V> implements Map<K, V> {

    private final int capacity;
    private final Map<K, KeyValueNode<K, V>> store;
    private final KeyValueLinkedList<K, V> orderNode = new KeyValueLinkedList<>();

    public LRUCache(final int capacity) {
        assert capacity > 0;
        this.capacity = capacity;
        store = new HashMap<>(capacity);
    }

    @Override
    public int size() {
        return store.size();
    }

    @Override
    public boolean isEmpty() {
        return store.isEmpty();
    }

    @Override
    public boolean containsKey(final Object key) {
        assert key != null;
        return store.containsKey(key);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean containsValue(final Object value) {
        assert value != null;
        return values().contains((V) value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public V get(final Object key) {
        if (!store.containsKey((K) key)) {
            return null;
        }
        final var node = store.get(key);
        orderNode.sendFront(node);
        return node.getValue();
    }

    @Override
    public V put(final K key, final V value) {
        assert key != null;
        assert value != null;

        if (store.containsKey(key)) {
            final var node = store.get(key);
            orderNode.sendFront(node);
            final var old = node.getValue();
            node.setValue(value);
            return old;
        }
        final var newNode = new KeyValueNode<>(key, value);
        if (capacity == size()) {
            final var lastNode = orderNode.getLast();
            store.remove(lastNode.getKey());
            orderNode.dequeLast();
        }
        orderNode.prepend(newNode);
        store.put(key, newNode);
        return value;
    }

    @SuppressWarnings("unchecked")
    @Override
    public V remove(final Object key) {
        if (!store.containsKey((K) key)) {
            return null;
        }
        final var node = store.get((K) key);
        store.remove(key);
        orderNode.remove(node);
        return node.getValue();
    }

    @Override
    public void putAll(final Map<? extends K, ? extends V> m) {
        assert m.size() <= capacity;
        m.forEach(this::put);
    }

    @Override
    public void clear() {
        store.clear();
        orderNode.setHead(null);
        orderNode.setLast(null);
    }

    @Override
    public Set<K> keySet() {
        return store.keySet();
    }

    @Override
    public Collection<V> values() {
        return store.values().stream().map(KeyValueNode::getValue).collect(Collectors.toList());
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return store.entrySet()
                .stream()
                .map(e -> Map.entry(e.getKey(), e.getValue().getValue()))
                .collect(Collectors.toSet());
    }
}
