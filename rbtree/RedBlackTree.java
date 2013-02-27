package rbtree;

import rbtree.RedBlackNode.Color;
import java.util.Iterator;

/*
 *
 * @author Pieter De Bruyne
 *
 */
public final class RedBlackTree<T extends Comparable<? super T>> implements Iterable<T>{

    private RedBlackNode<T> root;
    private int size;

    public RedBlackTree() {
        root = null;
        size = 0;
    }

    RedBlackNode<T> getRoot() {
        return root;
    }

    private void fixBalance(RedBlackNode<T> node) {
        if (node.getParent() != null) { //node is wortel
            if (node.getParent().getColor() == Color.RED) {
                if (node.isLeftChild() && node.getParent().isLeftChild()) {
                    node = rebalanceLL(node);
                } else if (node.isLeftChild() && node.getParent().isRightChild()) {
                    node = rebalanceRL(node);
                } else if (node.isRightChild() && node.getParent().isLeftChild()) {
                    node = rebalanceLR(node);
                } else if (node.isRightChild() && node.getParent().isRightChild()) {
                    node = rebalanceRR(node);
                }
                fixBalance(node);
            }
        }
    }

    private void fixBalance2(RedBlackNode<T> node) {
        while (node.getParent() != null && node.getParent().getColor() == Color.RED) {
            if (node.isLeftChild() && node.getParent().isLeftChild()) {
                node = rebalanceLL(node);
            } else if (node.isLeftChild() && node.getParent().isRightChild()) {
                node = rebalanceRL(node);
            } else if (node.isRightChild() && node.getParent().isLeftChild()) {
                node = rebalanceLR(node);
            } else if (node.isRightChild() && node.getParent().isRightChild()) {
                node = rebalanceRR(node);
            } else {
                assert (false);
            }

        }
    }

    /*
     * returns false if element is already present
     */
    public boolean add(T data) {
        if (root == null) {
            size = 1;
            root = new RedBlackNode(data);
            root.setColor(Color.BLACK);
            return true;
        } else {
            RedBlackNode<T> node = root;
            RedBlackNode<T> newNode = new RedBlackNode(data, Color.RED, null, null, null);
            int cmp = 0;
            for (;;) {
                cmp = data.compareTo(node.getData());
                if (cmp < 0) {
                    if (node.getLeft() == null) {
                        node.setLeft(newNode);
                        break;
                    }
                    node = node.getLeft();
                } else if (cmp > 0) {
                    if (node.getRight() == null) {
                        node.setRight(newNode);
                        break;
                    }
                    node = node.getRight();
                } else {
                    return false;
                }
            }
            size++;
            fixBalance2(newNode);
            return true;
        }
    }
    
    public boolean isEmpty(){
        return root == null;
    }
    
    public int size(){
        return size;
    }
    
    public void clear(){
        root = null;
        
    }
    
    public boolean contains(T data) {
        return search(data) != null;
    }

    private RedBlackNode<T> search(T data) {
        RedBlackNode<T> node = root;
        int cmp;
        while (node != null) {
            cmp = data.compareTo(node.getData());
            if (cmp == 0) {
                return node;
            } else if (cmp < 0) {
                node = node.getLeft();
            } else {
                node = node.getRight();
            }
        }
        return null;
    }

    /*              Voor: A,B en C resp. zwart,rood en rood. d,e,f,g zijn buitenbomen ...
     *             A                      B
     *            / \                   /    \
     *           B   g                 C      A
     *          / \         ----->    / \    / \
     *         C   f                 d   e  f   g
     *        / \
     *       d   e
     *              Na: A,B en C resp. zwart,rood, zwart. d,e,f,g, zijn buitenbomen
     */
    private RedBlackNode<T> rebalanceLL(RedBlackNode<T> node) {
        RedBlackNode<T> c = node;
        RedBlackNode<T> b = c.getParent();
        RedBlackNode<T> a = b.getParent();
        c.setColor(Color.BLACK);
        a.setColor(Color.BLACK);
        b.setColor(Color.RED);
        if (a.isLeftChild()) {
            a.getParent().setLeft(b);
        } else if (a.isRightChild()) {
            a.getParent().setRight(b);
        } else {
            root = b;
            b.setParent(null);
            b.setColor(Color.BLACK);
        }
        a.setLeft(b.getRight());
        b.setRight(a);
        return b;
    }

    /*        Voor: A,B en C resp. zwart,rood en rood. d,e,f,g zijn buitenbomen ...
     * 
     *             A                      C
     *            / \                   /    \
     *           B   g                 B      A
     *          / \     ---->>        / \    / \
     *         f   C                 f   d  e   g
     *            / \
     *           d   e
     *              Na: A,B en C resp. zwart,zwart, rood. d,e,f,g, zijn buitenbomen
     */
    private RedBlackNode<T> rebalanceLR(RedBlackNode<T> node) {
        RedBlackNode<T> c = node;
        RedBlackNode<T> b = c.getParent();
        RedBlackNode<T> a = b.getParent();
        a.setColor(Color.BLACK);
        c.setColor(Color.RED);
        b.setColor(Color.BLACK);
        if (a.isLeftChild()) {
            a.getParent().setLeft(c);
        } else if (a.isRightChild()) {
            a.getParent().setRight(c);
        } else {
            root = c;
            c.setParent(null);
            c.setColor(Color.BLACK);
        }
        b.setRight(c.getLeft());
        a.setLeft(c.getRight());
        c.setLeft(b);
        c.setRight(a);
        return c;
    }

    /*
     *               Voor: A,B en C resp. zwart,rood en rood. d,e,f,g zijn buitenbomen ...
     *             A                      C
     *            / \                   /    \
     *           g   B                 A      B
     *              / \ ---->>        / \    / \
     *             C   f             g   d  e   f
     *            / \
     *           d   e
     *              Na: A,B en C resp. zwart,zwart, rood. d,e,f,g, zijn buitenbomen
     */
    private RedBlackNode<T> rebalanceRL(RedBlackNode<T> node) {
        RedBlackNode<T> c = node;
        RedBlackNode<T> b = c.getParent();
        RedBlackNode<T> a = b.getParent();
        a.setColor(Color.BLACK);
        c.setColor(Color.RED);
        b.setColor(Color.BLACK);
        if (a.isLeftChild()) {
            a.getParent().setLeft(c);
        } else if (a.isRightChild()) {
            a.getParent().setRight(c);
        } else {
            root = c; // c is de nieuwe wortel en heeft dus geen ouder
            c.setParent(null);
            c.setColor(Color.BLACK);
        }
        a.setRight(c.getLeft());
        b.setLeft(c.getRight());
        c.setLeft(a);
        c.setRight(b);
        return c;
    }

    /*      Voor: A,B en C resp. zwart,rood en rood. d,e,f,g zijn buitenbomen ...
     *            A                        B
     *           / \                      /  \
     *          g   B                    A    C
     *             / \    ---->>        /\    /\
     *            f   C                g  f  d  e
     *               / \
     *              d   e
     *              Na: A,B en C resp. zwart,zwart, rood. d,e,f,g, zijn buitenbomen
     */
    private RedBlackNode<T> rebalanceRR(RedBlackNode<T> node) {
        RedBlackNode<T> c = node;
        RedBlackNode<T> b = c.getParent();
        RedBlackNode<T> a = b.getParent();
        c.setColor(Color.BLACK);
        a.setColor(Color.BLACK);
        b.setColor(Color.RED);
        if (a.isLeftChild()) {
            a.getParent().setLeft(b);
        } else if (a.isRightChild()) {
            a.getParent().setRight(b);
        } else { //a was wortel, nieuwe wortel wordt b
            root = b;
            b.setParent(null);
            b.setColor(Color.BLACK);
        }
        a.setRight(b.getLeft());
        b.setLeft(a);
        return b;
    }

    /*
     * Geeft de zustertop van een top terug
     */
    private RedBlackNode<T> getSibling(RedBlackNode<T> node) {
        if (node.isLeftChild()) {
            return node.getParent().getRight();
        } else if (node.isRightChild()) {
            return node.getParent().getLeft();
        } else {
            return null;
        }
    }

    public boolean remove(T data) {
        RedBlackNode<T> node = search(data);
        if (node == null) {
            return false;
        }
        removeHelper(node);
        size--;
        return true;
    }

    private void removeHelper(RedBlackNode<T> toRemove) {
        if (toRemove.isLeaf()) {
            if (toRemove.getColor() == Color.RED) { //removing a red leaf is never a problem
                delete(toRemove);
            } else { //it is a black leaf
                if (toRemove == root) { //there is only one node in the whole tree
                    root = null;
                } else { // we are removing a black leaf, this is the hard case
                    fixBlackLeaf(toRemove);
                    delete(toRemove);
                }
            }
        } else if (toRemove.getRight() != null && toRemove.getLeft() != null) { //the node is not a leaf so we swap it's data with it's predecessor and remove that one (always a leaf)
            RedBlackNode<T> predecessor = findPredecessor(toRemove);  
            assert(predecessor != null);
            toRemove.setData(predecessor.getData());
            removeHelper(predecessor);

        } else if (toRemove.getLeft() != null) { //right branch is empty, left is not
            assert (toRemove.getColor() == Color.BLACK);
            assert (toRemove.getLeft().getColor() == Color.RED);
            toRemove.getLeft().setColor(Color.BLACK);
            replaceNode(toRemove, toRemove.getLeft());
            delete(toRemove);
        } else if (toRemove.getRight() != null) { //left branch is empty, right is not
            assert (toRemove.getColor() == Color.BLACK);
            assert (toRemove.getRight().getColor() == Color.RED);
            toRemove.getRight().setColor(Color.BLACK);
            replaceNode(toRemove, toRemove.getRight());
            delete(toRemove);
        }
    }

    //      P           P
    //      |    ->     |
    //      T           R
    //     / \         / \
    //    L   R       T   b
    //       / \     / \ 
    //      a   b   L   a 
    //
    private void singleRotateLeft(RedBlackNode<T> node) {
        RedBlackNode<T> t = node;
        RedBlackNode<T> p = node.getParent();
        RedBlackNode<T> r = node.getRight();
        if (t.isLeftChild()) {
            p.setLeft(r);
        } else if (t.isRightChild()) {
            p.setRight(r);
        } else {
            root = r;
            root.setParent(null);
            root.setColor(Color.BLACK);
        }
        t.setRight(r.getLeft());
        r.setLeft(t);
    }

    //
    //      P         P
    //      |   ->    |
    //      T         L
    //     / \       / \
    //    L   R     a   T
    //   / \           / \ 
    //  a   b         b   R 
    //
    private void singleRotateRight(RedBlackNode<T> node) {
        RedBlackNode<T> t = node;
        RedBlackNode<T> p = node.getParent();
        RedBlackNode<T> l = node.getLeft();
        if (t.isLeftChild()) {
            p.setLeft(l);
        } else if (t.isRightChild()) {
            p.setRight(l);
        } else {
            root = l;
            root.setParent(null);
            root.setColor(Color.BLACK);
        }
        t.setLeft(l.getRight());
        l.setRight(t);
    }

    /*
     * Opwaartse percolatie van "zwart", 2 keer 4 symmetrische gevallen
     * 
     */
    private void fixBlackLeaf(RedBlackNode<T> node) {
        assert (node != null);
        while (node != root && node.getColor() == Color.BLACK) {
            if (node.isLeftChild()) { //node is een linkerkind

                RedBlackNode<T> sibling = node.getParent().getRight();
                assert (sibling != null);

                //geval 1 : sibling is rood --> wordt omgezet in een van de onderstaande gevallen
                if (sibling.getColor() == Color.RED) {
                    assert (sibling.getLeft() != null && sibling.getRight() != null);
                    sibling.setColor(Color.BLACK);
                    node.getParent().setColor(Color.RED);
                    singleRotateLeft(node.getParent());
                    sibling = node.getParent().getRight();
                }

                RedBlackNode<T> sibLeft = sibling.getLeft();
                RedBlackNode<T> sibRight = sibling.getRight();

                //geval 2 : sibling heeft 2 zwarte kinderen (nullpointers zijn hier zwart)
                if ((sibLeft == null || sibLeft.getColor() == Color.BLACK)
                        && (sibRight == null || sibRight.getColor() == Color.BLACK)) {
                    sibling.setColor(Color.RED);
                    node = node.getParent(); //ga verder naar boven, node kan nu rood (we maken deze dan zwart)of wortel zijn in welk geval we klaar zijn, de extra zwarte is dan geabsorbeerd of uit de boom gekegeld
                } else {
                    //geval 3 -> omzetten naar 4 
                    if (sibRight == null || sibRight.getColor() == Color.BLACK) {
                        //sibleft kan niet null zijn
                        sibLeft.setColor(Color.BLACK);
                        sibling.setColor(Color.RED);
                        singleRotateRight(sibling);
                        sibling = node.getParent().getRight();
                    }
                    // geval 4, balans hersteld, probleem opgelost
                    sibling.setColor(node.getParent().getColor());
                    node.getParent().setColor(Color.BLACK);
                    sibling.getRight().setColor(Color.BLACK);
                    singleRotateLeft(node.getParent());
                    node = root; // stop lus
                    break;
                }
            } else if (node.isRightChild()) { //node is een rechterkind: volledig analoog aan het if-gedeelte (gespiegeld)

                RedBlackNode<T> sibling = node.getParent().getLeft();

                //geval 1 : sibling is rood --> wordt omgezet in een van de onderstaande gevallen
                if (sibling.getColor() == Color.RED) {
                    sibling.setColor(Color.BLACK);
                    node.getParent().setColor(Color.RED);
                    singleRotateRight(node.getParent());
                    sibling = node.getParent().getLeft();
                }

                RedBlackNode<T> sibLeft = sibling.getLeft();
                RedBlackNode<T> sibRight = sibling.getRight();

                //geval 2 : sibling heeft 2 zwarte kinderen (nullpointers zijn hier zwart)
                if ((sibLeft == null || sibLeft.getColor() == Color.BLACK)
                        && (sibRight == null || sibRight.getColor() == Color.BLACK)) {
                    sibling.setColor(Color.RED);
                    node = node.getParent();
                } else {

                    //geval 3 -> omzetten naar 4 
                    if (sibLeft == null || sibLeft.getColor() == Color.BLACK) {
                        //sibright kan niet null zijn
                        sibRight.setColor(Color.BLACK);
                        sibling.setColor(Color.RED);
                        singleRotateLeft(sibling);
                        sibling = node.getParent().getLeft();
                    }

                    // geval 4, balans hersteld, probleem opgelost
                    sibling.setColor(node.getParent().getColor());
                    node.getParent().setColor(Color.BLACK);
                    sibling.getLeft().setColor(Color.BLACK);
                    singleRotateRight(node.getParent());
                    node = root;
                    break;
                }
            }
        }
        node.setColor(Color.BLACK); // als de lus gestopt is door een rode top dan moeten we deze zwart maken
    }

    /*
     * Zoekt grootste in de linkerdeelboom met 'node' als wortel
     */
    private RedBlackNode<T> findPredecessor(RedBlackNode<T> node) {
        node = node.getLeft();
        while (node.getRight() != null) {
            node = node.getRight();
        }
        return node;
    }

    /*
     * Verwijdert alle 'referenties' naar een top
     */
    private void delete(RedBlackNode<T> node) {
        if (node.isLeftChild()) {
            node.getParent().setLeft(null);
        } else if (node.isRightChild()) {
            node.getParent().setRight(null);
        }
        node.setParent(null);
    }

    /*
     * Vervangt de verwijzingen naar original door newNode, 
     */
    private void replaceNode(RedBlackNode<T> original, RedBlackNode<T> newNode) {
        if (original.isLeftChild()) {
            original.getParent().setLeft(newNode);
        } else if (original.isRightChild()) {
            original.getParent().setRight(newNode);
        } else {
            root = newNode;
            root.setParent(null);
            root.setColor(Color.BLACK);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new RedBlackIterator<>(this);
    }
}
