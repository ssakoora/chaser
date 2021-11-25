package org.sasi.java.trees;

import java.util.*;

public class SimpleBST {

    public static <T extends Comparable<T>> BinarySearchTree<T> of(T data) {
        return of(List.of(data));
    }

    public static <T extends Comparable<T>> BinarySearchTree<T> of(List<T> allData) {
        return new EmptyTree<T>().addAll(allData);
    }

    static class EmptyTree<T extends Comparable<T>> extends BinarySearchTree<T> {
        @Override
        public boolean isEmpty() { return true; }

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
        public StringBuffer printSelf(String prefix, StringBuffer bufferToPrint) {
            return bufferToPrint.append(prefix).append("|___ NONE ");
        }

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
        public BinarySearchTree<T> lesser() {
            return null;
        }

        @Override
        public BinarySearchTree<T> greater() {
            return null;
        }

        @Override
        public String toString() {
            return "NONE";
        }
    }

    static class NonEmptyTree<T extends Comparable<T>> extends BinarySearchTree<T> {

        public final T data;

        public final BinarySearchTree<T> lesser;

        public final BinarySearchTree<T> greater;

        public NonEmptyTree(T data, BinarySearchTree<T> lesser, BinarySearchTree<T> greater) {
            if (! areInvariantsValid(data, lesser, greater))
                throw new IllegalArgumentException(data+":"+lesser.getData()+":"+greater.getData());
            this.data = data;
            this.lesser = lesser;
            this.greater = greater;
        }

        private boolean areInvariantsValid(T data, BinarySearchTree<T> lesser, BinarySearchTree<T> greater) {
            return (isLesserInvariantValid(data, lesser) && isGreaterInvariantValid(data, greater));
        }

        private boolean isGreaterInvariantValid(T data, BinarySearchTree<T> greater) {
            return !greater.getData().isPresent() || greater.getData().get().compareTo(data) > 0;
        }

        private boolean isLesserInvariantValid(T data, BinarySearchTree<T> lesser) {
            return !lesser.getData().isPresent() || lesser.getData().get().compareTo(data) < 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public int size() {
            return 1+lesser.size()+greater.size();
        }

        @Override
        public Optional<T> getData() {
            return Optional.of(data);
        }

        @Override
        public boolean isPresent(T data) {
            if(this.data.compareTo(data) == 0)
                return true;
            else return this.lesser.isPresent(data) || this.greater.isPresent(data);
        }

        @Override
        public BinarySearchTree<T> add(T data) {
            if(data.compareTo(this.data) == 0)
                return this;
            else if(data.compareTo(this.data) < 0)
                return new NonEmptyTree<>(this.data, lesser.add(data), greater);
            else
                return new NonEmptyTree<>(this.data, lesser, greater.add(data));
        }

        @Override
        public BinarySearchTree<T> remove(T data) {
            if( data.compareTo(this.data) == 0)
                if(lesser.isEmpty() && greater.isEmpty())
                    return new EmptyTree<>();
                else if(!lesser.isEmpty() && greater.isEmpty())
                    return lesser;
                else if(lesser.isEmpty() && !greater.isEmpty())
                    return greater;
                else {
                    T biggestOnLesser = lesser.highest().get();
                    return new NonEmptyTree<>(biggestOnLesser, lesser.remove(biggestOnLesser), greater);
                }
            else if (data.compareTo(this.data) < 0)
                return this.lesser.remove(data);
            else
                return this.greater.remove(data);
        }

        @Override
        public StringBuffer printSelf(String prefix, StringBuffer bufferToPrint) {
            return bufferToPrint
                    .append(prefix).append("|___ ").append(data)
                    .append("\n")
                    .append(lesser.printSelf(prefix+"\t|", new StringBuffer()))
                    .append("\n")
                    .append(greater.printSelf(prefix+"\t", new StringBuffer()));
        }

        @Override
        public List<T> toList() {
            List<T> items = new ArrayList<>(lesser.toList());
            items.add(data);
            items.addAll(greater.toList());
            return items;
        }

        @Override
        public Optional<T> lowest() {
            return lesser.isEmpty() ? Optional.of(data) : lesser.lowest();
        }

        @Override
        public Optional<T> highest() {
            return greater.isEmpty() ? Optional.of(data) : greater.highest();
        }

        @Override
        public int height() {
            return Math.max(1+ lesser.height(), 1+greater.height());
        }

        @Override
        public BinarySearchTree<T> lesser() {
            return lesser;
        }

        @Override
        public BinarySearchTree<T> greater() {
            return greater;
        }
    }
}
