package main;

public class KeyValueLinkedList<Key, Value> {

    private int size;

    private KeyValueNode<Key, Value> head;
    private KeyValueNode<Key, Value> last;


    public void sendFront(final KeyValueNode<Key, Value> node) {
        if (node.getPrev() == null) {
            return;
        }

        var prevNode = node.getPrev();
        var nextNode = node.getNext();
        var isTail = nextNode == null;
        KeyValueNode.setLinks(node.getPrev(), node.getNext());
        size--;
        prepend(node);
        if (isTail) {
            last = prevNode;
        }
    }

    public void prepend(final KeyValueNode<Key, Value> node) {
        KeyValueNode.setLinks(node, head);
        if (size == 1) {
            last = head;
        }
        head = node;
        size++;
    }

    public void dequeFront() {
        assert size > 0;
        head = head.getNext();
        if (head != null) {
            head.setPrev(null);
        }
        size--;
    }

    public void dequeLast() {
        assert size > 0;
        if (size == 1) {
            dequeFront();
            return;
        }
        last = last.getPrev();
        last.setNext(null);
        size--;
    }

    public void remove(final KeyValueNode<Key, Value> node) {
        if (node == head) {
            dequeFront();
        } else if (node == last) {
            dequeLast();
        } else {
            KeyValueNode.setLinks(node.getPrev(), node.getNext());
            size--;
        }
    }

    public int getSize() {
        return size;
    }

    public KeyValueNode<Key, Value> getLast() {
        return last;
    }

    public void setLast(final KeyValueNode<Key, Value> last) {
        this.last = last;
    }

    public KeyValueNode<Key, Value> getHead() {
        return head;
    }

    public void setHead(final KeyValueNode<Key, Value> head) {
        this.head = head;
    }
}
