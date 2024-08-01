public class AVL {
    Node raiz = null;

    public AVL() {}

    public void order(Node tree) {
        if (tree == null) {
            return;
        }

        order(tree.left);
        System.out.print(tree.data + " ");
        order(tree.right);
    }

    private Node insert(Node tree, int key, String data) {
        if (tree == null) return new Node(key, data);
        
        if (key < tree.key) {
            tree.left = insert(tree.left, key, data);
        }
        else if (key > tree.key) {
            tree.right = insert(tree.right, key, data);
        }
        
        // Update the height of the node
        tree.high = 1 + biggest(high(tree.left), high(tree.right));

        tree = balanceNode(tree);
        
        return tree;
    }

    public Node remove(int key) {
        return remove(raiz, key);
    }

    public Node remove(Node tree, int key) {
        if (tree == null) return null;

        if (key < tree.key) {
            tree.left = remove(tree.left, key);
        } else if (key > tree.key) {
            tree.right = remove(tree.right, key);
        } else {
            if (tree.left == null && tree.right == null) {  // If it is a leaf
                tree = null;
            } else if (tree.left == null || tree.right == null) {  // If it has only one child
                tree = (tree.left == null) ? tree.right : tree.left;
            } else {  // If it has two children
                // Find the smallest node in the right subtree
                Node temp;
                int bf = getBalanceFactor(tree);
                
                // Remove from the side with the biggest height
                if (bf < 0) {
                    temp = tree.left;

                    // Search for the biggest node in the left subtree
                    while (temp.right != null) {
                        temp = temp.right;
                    }

                    // Update the node
                    tree.key = temp.key;
                    tree.data = temp.data;
                    tree.left = remove(tree.left, temp.key);  // Remove duplicated node
                } else {
                    temp = tree.right;

                    // Search for the smallest node in the right subtree
                    while (temp.left != null) {
                        temp = temp.left;
                    }

                    tree.key = temp.key;
                    tree.data = temp.data;
                    tree.right = remove(tree.right, temp.key);  // Remove duplicated node
                }
            }
        }

        if (tree == null) return null;  // Node was not found

        // Update the height of the node
        tree.high = 1 + biggest(high(tree.left), high(tree.right));

        // Balance the tree
        tree = balanceNode(tree);

        return tree;
    }

    private Node balanceNode(Node tree) {
        int bf = getBalanceFactor(tree);
        int bfLeft = getBalanceFactor(tree.left);
        int bfRight = getBalanceFactor(tree.right);

        // Simple Rotations
        if (bf > 1 && bfLeft >= 0) {
            return rightRotate(tree);
        }

        if (bf < -1 && bfRight <= 0) {
            return leftRotate(tree);
        }

        // Double Rotations
        if (bf > 1 && bfLeft < 0) {
            tree.left = leftRotate(tree.left);
            return rightRotate(tree);
        }

        if (bf < -1 && bfRight > 0) {
            tree.right = rightRotate(tree.right);
            return leftRotate(tree);
        }

        return tree;
    }

    private Node leftRotate(Node tree) {
        Node right = tree.right;
        Node left = right.left;

        right.left = tree;
        tree.right = left;

        tree.high = 1 + biggest(high(tree.left), high(tree.right));
        right.high = 1 + biggest(high(right.left), high(right.right));

        return right;
    }

    private Node rightRotate(Node tree) {
        Node left = tree.left;
        Node right = left.right;

        left.right = tree;
        tree.left = right;

        tree.high = 1 + biggest(high(tree.left), high(tree.right));
        left.high = 1 + biggest(high(left.left), high(left.right));

        return left;
    }

    private int getBalanceFactor(Node right) {
        return (right == null) ? 0 : high(right.left) - high(right.right);
    }

    private int biggest(int a, int b) {
        return (a > b) ? a : b;
    }

    public void insert(int key, String data) {
        raiz = insert(raiz, key, data);
    }

    public int high(Node tree) {
        if (tree == null) return -1;
        return tree.high;
    }
}
