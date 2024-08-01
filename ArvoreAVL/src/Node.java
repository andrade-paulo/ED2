public class Node {
    int key;
    String data;
    int high;
    Node left, right;

    public Node(int key, String data) {
        this.key = key;
        this.data = data;
        this.high = 1;
    }
}
