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

    public static <T extends Comparable<T>> BinarySearchTree<T> getNewInstance() {
        return new EmptyTree<>();
    }

    public static <T extends Comparable<T>> BinarySearchTree<T> simpleBSTOf(T data) {
        return new NonEmptyTree<>(data, new EmptyTree<>(), new EmptyTree<>());
    }

    public static <T extends Comparable<T>> BinarySearchTree<T> simpleBSTOf(List<T> allData) {
        return new EmptyTree<T>().addAll(allData);
    }

    public static <T extends Comparable<T>> BinarySearchTree<T> heightMemoizedBSTOf(T data) {
        return new HeightAwareNonEmptyTree<>(data, new EmptyTree<>(), new EmptyTree<>(), 1);
    }

    public static <T extends Comparable<T>> BinarySearchTree<T> heightMemoizedBSTOf(List<T> allData) {
        HeightAwareEmptyTree<T> treeToAdd = new HeightAwareEmptyTree<>();
        return treeToAdd.addAll(allData);
    }

}

