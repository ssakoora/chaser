package org.sasi.java.trees;

public class EmptyTree<T extends Comparable<T>> extends BinarySearchTree<T> {
    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public BinarySearchTree<T> add(T data) {
        return new NonEmptyTree<T>(data, new EmptyTree<T>(), new EmptyTree<T>());
    }

    @Override
    public StringBuffer printSelf(String prefix, StringBuffer bufferToPrint) {
        return bufferToPrint.append(prefix+"|___ NONE ");
    }

    @Override
    public String toString() {
        return "NONE";
    }
}
