public class App {
    public static void main(String[] args) throws Exception {
        ABB abb = new ABB();
        abb.inserir(new int[] { 5, 3, 7, 2, 4, 6, 8 });

        System.out.println("Altura: " + abb.altura(abb.raiz));

        buscar(abb, abb.raiz, 6);

        System.out.print("Ordem: ");
        abb.ordem(abb.raiz);
    }

    private static void buscar(ABB abb, Node node, int key) {
        Node r = abb.buscar(node, key);

        if (r == null) {
            System.out.println("Não encontrado");
        } else {
            System.out.println("Chave encontrada: " + r.key);
        } 
    }
}
