public class ArvoreBinaria {
    private Node raiz;

    public ArvoreBinaria() {
        this.raiz = null;
    }

    public void setRaiz(Node raiz) {
        this.raiz = raiz;
    }

    public Node getRaiz() {
        return this.raiz;
    }

    public void order(Node node) {
        if (node != null) {
            order(node.left);
            System.out.print(node.key + " ");
            order(node.right);
        }
    }

    public void preOrder(Node node) {
        if (node != null) {
            System.out.print(node.key + " ");
            preOrder(node.left);
            preOrder(node.right);
        }
    }

    public void postOrder(Node node) {
        if (node != null) {
            postOrder(node.left);
            postOrder(node.right);
            System.out.print(node.key + " ");
        }
    }
}
