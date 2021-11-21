package org.sasi.java.trees;

public class HeightAwareEmptyTree<T extends Comparable<T>> extends EmptyTree<T> {

    @Override
    public BinarySearchTree<T> add(T data) {
        return new HeightAwareNonEmptyTree<>(data, new HeightAwareEmptyTree<>(), new HeightAwareEmptyTree<>(), 1);
    }
}
