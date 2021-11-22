package org.sasi.java.trees;

public class SimpleBalancedBinarySearchEmptyTree<T extends Comparable<T>> extends HeightAwareEmptyTree<T> {

    @Override
    public BinarySearchTree<T> add(T data) {
        return new SimpleBalancedBinarySearchTree<>(data,
                new SimpleBalancedBinarySearchEmptyTree<>(),
                new SimpleBalancedBinarySearchEmptyTree<>(),
                1);
    }
}
