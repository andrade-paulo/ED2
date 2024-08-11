package datastructures;

import java.io.Serializable;

public class Node<T> implements Serializable{
    long key;
    T data;
    int high;
    Node<T> left, right;
    private static final long serialVersionUID = 3L;

    public Node(long key, T data) {
        this.key = key;
        this.data = data;
        this.high = 1;
    }
}
