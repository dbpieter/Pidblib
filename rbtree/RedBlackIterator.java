package rbtree;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/*
 *
 * @author Pieter De Bruyne
 *
 */
final class RedBlackIterator<T extends Comparable<? super T>> implements Iterator<T> {

    private Stack<RedBlackNode> stack;
    private RedBlackTree<T> tree;

    public RedBlackIterator(RedBlackTree<T> tree) {
        this.tree = tree;
        stack = new Stack<>();
        RedBlackNode<T> node = tree.getRoot();
        while (node != null) {
            stack.push(node);
            node = node.getLeft();
        }
    }

    @Override
    public boolean hasNext() {
        return !stack.empty();
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        RedBlackNode<T> toReturn = stack.pop();
        RedBlackNode<T> node = toReturn.getRight();
        while (node != null) {
            stack.push(node);
            node = node.getLeft();
        }
        return toReturn.getData();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported");
    }
}
