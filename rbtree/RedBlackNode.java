

package rbtree;

/*
 *
 * @author Pieter De Bruyne
 *
 */
final class RedBlackNode<T extends Comparable<? super T>> {
    
    private T data;
    
    enum Color {RED,BLACK};
    
    private Color color;
    
    private RedBlackNode<T> left,right,parent;

    public RedBlackNode(T data, Color color, RedBlackNode<T> left, RedBlackNode<T> right, RedBlackNode<T> parent) {
        this.data = data;
        this.color = color;
        this.left = left;
        this.right = right;
        this.parent = parent;
    }
    
    public RedBlackNode(T data){
        this(data,Color.RED,null,null,null);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public RedBlackNode<T> getLeft() {
        return left;
    }

    /*
     * Also sets parent
     */
    public void setLeft(RedBlackNode<T> left) {
        this.left = left;
        if(left!=null){
            left.parent = this;
        }
    }

    public RedBlackNode<T> getParent() {
        return parent;
    }

    public void setParent(RedBlackNode<T> parent) {
        this.parent = parent;
    }

    public RedBlackNode<T> getRight() {
        return right;
    }

    /*
     * Also sets parent
     */
    public void setRight(RedBlackNode<T> right) {
        this.right = right;
        if(right!=null){
            right.parent = this;
        }
    }
    
    public boolean isLeaf(){
        return left == null && right == null;
    }
    
    public boolean isLeftChild(){
        if(parent == null) return false;
        return parent.getLeft() == this;
    }
    
    public boolean isRightChild(){
        if(parent == null) return false;
        return parent.getRight() == this;
    }

    public T getData() {
        return data;
    }
    
    public void setData(T newData){
        this.data = newData;
    }
    
}
