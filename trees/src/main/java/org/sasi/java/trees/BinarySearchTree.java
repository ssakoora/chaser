package org.sasi.java.trees;

import java.util.List;
import java.util.Optional;

public abstract class BinarySearchTree<T extends  Comparable<T>> {
    public abstract boolean isEmpty();
    public abstract int size();
    public abstract Optional<T> getData();
    public abstract boolean isPresent(T data);
    public abstract BinarySearchTree<T> add(T data);
    public abstract BinarySearchTree<T> remove(T data);
    public abstract StringBuffer printSelf(String prefix, StringBuffer bufferToPrint);
    public abstract List<T> toList();
    public abstract Optional<T> lowest();
    public abstract Optional<T> highest();
    public abstract int height();
    public BinarySearchTree<T> addAll(List<T> allData) {
        BinarySearchTree<T> addedTree = this;
        for(T data: allData){
            addedTree = addedTree.add(data);
        }
        return addedTree;
    }
}

