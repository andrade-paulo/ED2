public class ABB {
    Node raiz;

    public ABB() {
        this.raiz = null;
    }

    public int altura(Node node) {
        if (node == null) {
            return -1;
        }

        int alturaEsquerda = altura(node.left);
        int alturaDireita = altura(node.right);
        
        if (alturaEsquerda > alturaDireita) {
            return alturaEsquerda + 1;
        } else {
            return alturaDireita + 1;
        }
    }

    public Node buscar(Node node, int key) {
        if (node == null)
            return null;
        
        if (node.key == key)
            return node;
        
        if (key < node.key)
            return buscar(node.left, key);
        
        return buscar(node.right, key);
    }

    public void inserir (int key) {
        raiz = inserir(raiz, key);
    }

    public Node inserir(Node node, int key) {
        if (node == null)
            return new Node(key);

        if (node.key < key)
            node.right = inserir(node.right, key);
            
        if (node.key > key)
            node.left = inserir(node.left, key);
        
        return node;
    }

    public void inserir (int[] keys) {
        for (int key : keys) {
            inserir(key);
        }
    }

    public void ordem(Node node) {
        if (node != null) {
            ordem(node.left);
            System.out.print(node.key + " ");
            ordem(node.right);
        }
    }
}
