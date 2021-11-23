package org.sasi.java.trees;

import java.util.List;

public class SimpleBalancedBST<T extends Comparable<T>> {

    public static <T extends Comparable<T>> BinarySearchTree<T> of(T data) {
        return of(List.of(data));
    }

    public static <T extends Comparable<T>> BinarySearchTree<T> of(List<T> allData) {
        SimpleBalancedBinarySearchEmptyTree<T> treeToAdd = new SimpleBalancedBinarySearchEmptyTree<>();
        return treeToAdd.addAll(allData);
    }

    static class SimpleBalancedBinarySearchEmptyTree<T extends Comparable<T>> extends HeightAwareBST.HeightAwareEmptyTree<T> {

        @Override
        public BinarySearchTree<T> add(T data) {
            return new SimpleBalancedBinarySearchNonEmptyTree<>(data,
                    new SimpleBalancedBinarySearchEmptyTree<>(),
                    new SimpleBalancedBinarySearchEmptyTree<>(),
                    1);
        }
    }

    static class SimpleBalancedBinarySearchNonEmptyTree<T extends Comparable<T>> extends SimpleBST.NonEmptyTree<T> {

        private final int height;

        public SimpleBalancedBinarySearchNonEmptyTree(T data, BinarySearchTree<T> lesser, BinarySearchTree<T> greater, int height) {
            super(data, lesser, greater);
            this.height = height;
        }

        @Override
        public SimpleBalancedBinarySearchNonEmptyTree<T> add(T data) {
            return balanced(addToTree(data));
        }

        @Override
        public BinarySearchTree<T> remove(T data) {
            if( data.compareTo(this.data) == 0)
                if(!lesser.isEmpty()) {
                    BinarySearchTree<T> newLesser = lesser.remove(lesser.getData().get());
                    return new SimpleBalancedBinarySearchNonEmptyTree<>(
                            lesser.getData().get(),
                            newLesser,
                            greater,
                            Math.max(newLesser.height(), greater.height())+1
                    );
                } else if (!greater.isEmpty()) {
                    BinarySearchTree<T> newGreater = greater.remove(greater.getData().get());
                    return new SimpleBalancedBinarySearchNonEmptyTree<>(
                            greater.getData().get(),
                            lesser,
                            newGreater,
                            Math.max(lesser.height(), newGreater.height())+1
                    );
                } else
                    return new SimpleBalancedBinarySearchEmptyTree<>();
            else if (data.compareTo(this.data) < 0)
                return this.lesser.remove(data);
            else
                return this.greater.remove(data);
        }

        private SimpleBalancedBinarySearchNonEmptyTree<T> addToTree(T data) {
            if(data.compareTo(this.data) == 0)
                return this;
            else if(data.compareTo(this.data) < 0) {
                BinarySearchTree<T> newLesser = lesser.add(data);
                return new SimpleBalancedBinarySearchNonEmptyTree<>(
                        this.data,
                        newLesser,
                        greater,
                        Math.max(newLesser.height(), greater.height())+1
                );
            } else {
                BinarySearchTree<T> newGreater = greater.add(data);
                return new SimpleBalancedBinarySearchNonEmptyTree<>(
                        this.data,
                        lesser,
                        newGreater,
                        Math.max(lesser.height(), newGreater.height())+1
                );
            }
        }

        private SimpleBalancedBinarySearchNonEmptyTree<T> balanced(SimpleBalancedBinarySearchNonEmptyTree<T> added) {
            while(added.lesser.height() - added.greater.height() < -1   || added.lesser.height() - added.greater.height() > 1) {
                if (added.lesser.height() - added.greater.height() < -1 ){
                        BinarySearchTree<T> newLesser = added.lesser.add(added.getData().get());
                        BinarySearchTree<T> newGreater = added.greater.remove(added.greater.getData().get());
                        added = new SimpleBalancedBinarySearchNonEmptyTree<>(
                                added.greater.getData().get(),
                                newLesser,
                                newGreater,
                                Math.max(newLesser.height(), newGreater.height())+1
                        );
                    }
                else {
                        BinarySearchTree<T> newLesser = added.lesser.remove(added.lesser.getData().get());
                        BinarySearchTree<T> newGreater = added.greater.add(added.getData().get());
                        added = new SimpleBalancedBinarySearchNonEmptyTree<>(
                                added.lesser.getData().get(),
                                newLesser,
                                newGreater,
                                Math.max(newLesser.height(), newGreater.height())+1
                        );
                }
            }
            return added;
        }

        @Override
        public int height() {
            return height;
        }
    }
}
