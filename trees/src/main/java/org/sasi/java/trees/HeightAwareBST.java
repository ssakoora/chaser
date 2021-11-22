package org.sasi.java.trees;

import java.util.List;

public class HeightAwareBST<T extends Comparable<T>> {

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
            return new HeightAwareNonEmptyTree<>(data, new HeightAwareEmptyTree<>(), new HeightAwareEmptyTree<>(), 1);
        }
    }

    static class HeightAwareNonEmptyTree<T extends Comparable<T>> extends SimpleBST.NonEmptyTree<T> {

        private final int height;

        public HeightAwareNonEmptyTree(T data, BinarySearchTree<T> lesser, BinarySearchTree<T> greater, int height) {
            super(data, lesser, greater);
            this.height = height;
        }

        public int getHeight() {
            return this.height;
        }

        @Override
        public HeightAwareNonEmptyTree<T> add(T data) {
            if(data.compareTo(this.data) == 0)
                return this;
            else if(data.compareTo(this.data) < 0) {
                BinarySearchTree<T> newLesser = lesser.add(data);
                return new HeightAwareNonEmptyTree<>(
                        this.data,
                        newLesser,
                        greater,
                        Math.max(newLesser.height(), greater.height())+1
                );
            } else {
                BinarySearchTree<T> newGreater = greater.add(data);
                return new HeightAwareNonEmptyTree<>(
                        this.data,
                        lesser,
                        newGreater,
                        Math.max(lesser.height(), newGreater.height())+1
                );
            }
        }

        @Override
        public BinarySearchTree<T> remove(T data) {
            if( data.compareTo(this.data) == 0)
                if(!lesser.isEmpty()) {
                    BinarySearchTree<T> newLesser = lesser.remove(lesser.getData().get());
                    return new HeightAwareNonEmptyTree<>(
                            lesser.getData().get(),
                            newLesser,
                            greater,
                            Math.max(newLesser.height(), greater.height())+1
                    );
                } else if (!greater.isEmpty()) {
                    BinarySearchTree<T> newGreater = greater.remove(greater.getData().get());
                    return new HeightAwareNonEmptyTree<>(
                            greater.getData().get(),
                            lesser,
                            newGreater,
                            Math.max(lesser.height(), newGreater.height())+1
                    );
                } else
                    return new HeightAwareEmptyTree<>();
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
