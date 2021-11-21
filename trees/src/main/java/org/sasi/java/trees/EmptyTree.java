package org.sasi.java.trees;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class EmptyTree<T extends Comparable<T>> extends BinarySearchTree<T> {
    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Optional<T> getData() {
        return Optional.empty();
    }

    @Override
    public boolean isPresent(T data) {
        return false;
    }

    @Override
    public BinarySearchTree<T> add(T data) {
        return new NonEmptyTree<>(data, new EmptyTree<>(), new EmptyTree<>());
    }

    @Override
    public BinarySearchTree<T> remove(T data) {
        return this;
    }

    @Override
    public StringBuffer printSelf(String prefix, StringBuffer bufferToPrint) { return bufferToPrint.append(prefix).append("|___ NONE "); }

    @Override
    public List<T> toList() {
        return new ArrayList<>();
    }

    @Override
    public Optional<T> lowest() {
        return Optional.empty();
    }

    @Override
    public Optional<T> highest() {
        return Optional.empty();
    }

    @Override
    public int height() {
        return 0;
    }

    @Override
    public String toString() {
        return "NONE";
    }
}
