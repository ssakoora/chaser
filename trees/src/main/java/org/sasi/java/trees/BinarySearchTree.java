package org.sasi.java.trees;

public abstract class BinarySearchTree<T extends  Comparable<T>> {
    public abstract boolean isEmpty();
    public abstract BinarySearchTree<T> add(T data);
    public abstract StringBuffer printSelf(String prefix, StringBuffer bufferToPrint);
}

