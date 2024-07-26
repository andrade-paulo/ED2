public class Node {
    int key;
    Node left, right;

    public Node(int k) {
        this.key = k;
        this.left = this.right = null;
    }

    public Node(Node left, Node right) {
        this.left = left;
        this.right = right;
    }

    public Node(int k, Node left, Node right) {
        this.key = k;
        this.left = left;
        this.right = right;
    }
}