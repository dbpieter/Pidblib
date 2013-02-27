
package rbtree;

/*
 *
 * @author Pieter De Bruyne
 *  export a tree to the "dot" graphml format
 */
public final class RedBlackTreeDotExporter {
    
    private RedBlackTree tree;

    public RedBlackTreeDotExporter(RedBlackTree tree) {
        this.tree = tree;
    }

    public void export() {
        System.out.println("digraph BST {");
        System.out.println("node [shape=record] ;");
        exportHelper(tree.getRoot());
        System.out.println("}");
    }

    private void exportHelper(RedBlackNode node) {
        if (node.getColor() == RedBlackNode.Color.BLACK) {
            System.out.println("\"" + node.getData().toString() + "\"" + " [ label=\"<f0> | <f1> " + node.getData().toString() + " | <f2> \",color=black];");
        } else {
            System.out.println("\"" + node.getData().toString() + "\"" + " [ label=\"<f0> | <f1> " + node.getData().toString() + " | <f2> \" ,color=red];");
        }
        if (node.getLeft() != null) {
            System.out.println("\"" + node.getData().toString() + "\":f0 -> \"" + node.getLeft().getData().toString() + "\":f1");
            exportHelper(node.getLeft());
        }
        if (node.getRight() != null) {
            System.out.println("\"" + node.getData().toString() + "\":f2 -> \"" + node.getRight().getData().toString() + "\":f1");
            exportHelper(node.getRight());
        }
    }
}

