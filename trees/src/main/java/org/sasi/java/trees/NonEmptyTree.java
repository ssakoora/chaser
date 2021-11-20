package org.sasi.java.trees;

class NonEmptyTree<T extends Comparable<T>> extends BinarySearchTree<T> {

    public final T data;

    public final BinarySearchTree<T> lesser;

    public final BinarySearchTree<T> greater;

    public NonEmptyTree(T data, BinarySearchTree<T> lesser, BinarySearchTree<T> greater) {
        this.data = data;
        this.lesser = lesser;
        this.greater = greater;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public BinarySearchTree<T> add(T data) {
        if(data.compareTo(this.data) == 0)
            return this;
        else if(data.compareTo(this.data) < 0)
            return new NonEmptyTree<T>(this.data, lesser.add(data), greater);
        else
            return new NonEmptyTree<T>(this.data, lesser, greater.add(data));
    }

    @Override
    public StringBuffer printSelf(String prefix, StringBuffer bufferToPrint) {
        return bufferToPrint
                .append(prefix+"|___ "+data)
                .append("\n")
                .append(lesser.printSelf(prefix+"\t|", new StringBuffer()))
                .append("\n")
                .append(greater.printSelf(prefix+"\t", new StringBuffer()));
    }
}
