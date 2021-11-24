package org.sasi.java.trees;

import java.util.List;

public class AVLBalancedBST<T extends Comparable<T>> {

    public static <T extends Comparable<T>> BinarySearchTree<T> of(T data) {
        return of(List.of(data));
    }

    public static <T extends Comparable<T>> BinarySearchTree<T> of(List<T> allData) {
        AVLBalancedEmptyBST<T> treeToAdd = new AVLBalancedEmptyBST<>();
        return treeToAdd.addAll(allData);
    }

    static class AVLBalancedEmptyBST<T extends Comparable<T>> extends SimpleBalancedBST.SimpleBalancedBinarySearchEmptyTree<T> {
        @Override
        public BinarySearchTree<T> add(T data) {
            return new AVLBalancedNonEmptyBST<>(data, new AVLBalancedEmptyBST<>(), new AVLBalancedEmptyBST<>());
        }
    }

    static class AVLBalancedNonEmptyBST<T extends Comparable<T>> extends SimpleBalancedBST.SimpleBalancedBinarySearchNonEmptyTree<T> {

        public AVLBalancedNonEmptyBST(T data, BinarySearchTree<T> lesser, BinarySearchTree<T> greater) {
            super(data, lesser, greater);
        }
    }
}
