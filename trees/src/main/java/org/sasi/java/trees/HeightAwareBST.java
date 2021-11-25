package org.sasi.java.trees;

import java.util.List;

public class HeightAwareBST {

    public static <T extends Comparable<T>> BinarySearchTree<T> of(T data) {
        return of(List.of(data));
    }

    public static <T extends Comparable<T>> BinarySearchTree<T> of(List<T> allData) {
        HeightAwareEmptyTree<T> treeToAdd = new HeightAwareEmptyTree<>();
        return treeToAdd.addAll(allData);
    }

    static class HeightAwareEmptyTree<T extends Comparable<T>> extends SimpleBST.EmptyTree<T> {

        @Override
        public BinarySearchTree<T> add(T data) {
            return new HeightAwareNonEmptyTree<>(data, new HeightAwareEmptyTree<>(), new HeightAwareEmptyTree<>());
        }
    }

    static class HeightAwareNonEmptyTree<T extends Comparable<T>> extends SimpleBST.NonEmptyTree<T> {

        private final int height;

        public HeightAwareNonEmptyTree(T data, BinarySearchTree<T> lesser, BinarySearchTree<T> greater) {
            super(data, lesser, greater);
            this.height = Math.max(lesser.height(), greater.height())+1;
        }

        @Override
        public BinarySearchTree<T> add(T data) {
            if(data.compareTo(this.data) == 0)
                return this;
            else if(data.compareTo(this.data) < 0) {
                BinarySearchTree<T> newLower = lesser.add(data);
                return new HeightAwareNonEmptyTree<>(
                        this.data,
                        newLower,
                        greater
                );
            } else {
                BinarySearchTree<T> newGreater = greater.add(data);
                return new HeightAwareNonEmptyTree<>(
                        this.data,
                        lesser,
                        newGreater
                );
            }
        }

        @Override
        public BinarySearchTree<T> remove(T data) {
            if( data.compareTo(this.data) == 0)
                if(lesser.isEmpty() && greater.isEmpty())
                    return new HeightAwareEmptyTree<>();
                else if(!lesser.isEmpty() && greater.isEmpty())
                    return lesser;
                else if(lesser.isEmpty() && !greater.isEmpty())
                    return greater;
                else {
                    T biggestOnLesser = lesser.highest().get();
                    BinarySearchTree<T> newLesser = lesser.remove(biggestOnLesser);
                    return new HeightAwareNonEmptyTree<>(biggestOnLesser, newLesser, greater);
                }
            else if (data.compareTo(this.data) < 0)
                return this.lesser.remove(data);
            else
                return this.greater.remove(data);
        }

        @Override
        public int height() {
            return this.height;
        }
    }
}