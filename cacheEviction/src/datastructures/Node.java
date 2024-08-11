package datastructures;

import java.io.Serializable;

public class Node<T> implements Serializable{
    int key;
    T data;
    int high;
    Node<T> left, right;

    public Node(int key, T data) {
        this.key = key;
        this.data = data;
        this.high = 1;
    }
}
