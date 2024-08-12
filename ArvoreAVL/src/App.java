public class App {
    public static void main(String[] args) throws Exception {
        AVL tree = new AVL();

        tree.insert(21, "21");
        tree.insert(20, "20");
        tree.insert(24, "24");
        tree.insert(22, "22");
        tree.insert(25, "25");
        tree.insert(27, "27");

        tree.order(tree.raiz);

        System.out.println();

        tree.remove(30);
        tree.order(tree.raiz);
    }
}
