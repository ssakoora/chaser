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
                    new SimpleBalancedBinarySearchEmptyTree<>());
        }
    }

    static class SimpleBalancedBinarySearchNonEmptyTree<T extends Comparable<T>> extends SimpleBST.NonEmptyTree<T> {

        public SimpleBalancedBinarySearchNonEmptyTree(T data, BinarySearchTree<T> lesser, BinarySearchTree<T> greater) {
            super(data, lesser, greater);
        }

        @Override
        public SimpleBalancedBinarySearchNonEmptyTree<T> add(T data) {
            return balanced(addToTree(data));
        }

        @Override
        public BinarySearchTree<T> remove(T data) {
            if( data.compareTo(this.data) == 0)
                if(lesser.isEmpty() && greater.isEmpty())
                    return new SimpleBalancedBinarySearchEmptyTree<>();
                else if(!lesser.isEmpty() && greater.isEmpty())
                    return lesser;
                else if(lesser.isEmpty() && !greater.isEmpty())
                    return greater;
                else {
                    T biggestOnLesser = lesser.highest().get();
                    return new SimpleBalancedBinarySearchNonEmptyTree<>(biggestOnLesser, lesser.remove(biggestOnLesser), greater);
                }
            else if (data.compareTo(this.data) < 0)
                return new SimpleBalancedBinarySearchNonEmptyTree<>(this.data, this.lesser.remove(data), this.greater);
            else
                return new SimpleBalancedBinarySearchNonEmptyTree<>(this.data, this.lesser, this.greater.remove(data));
        }

        private SimpleBalancedBinarySearchNonEmptyTree<T> addToTree(T data) {
            if(data.compareTo(this.data) == 0)
                return this;
            else if(data.compareTo(this.data) < 0)
                return new SimpleBalancedBinarySearchNonEmptyTree<>(this.data, lesser.add(data), greater);
            else
                return new SimpleBalancedBinarySearchNonEmptyTree<>(this.data, lesser, greater.add(data));
        }

        public SimpleBalancedBinarySearchNonEmptyTree<T> balanced(SimpleBalancedBinarySearchNonEmptyTree<T> added) {
                if (added.lesser.height() - added.greater.height() < -1 ){
                        T toBeBumped = added.greater.lowest().get();
                        BinarySearchTree<T> newLesser = added.lesser.add(added.getData().get());
                        BinarySearchTree<T> newGreater = added.greater.remove(toBeBumped);
                        return new SimpleBalancedBinarySearchNonEmptyTree<>(
                                toBeBumped,
                                newLesser,
                                newGreater
                        );
                    }
                else if (added.lesser.height() - added.greater.height() > 1 ){
                        T toBeBumped = added.lesser.highest().get();
                        BinarySearchTree<T> newLesser = added.lesser.remove(toBeBumped);
                        BinarySearchTree<T> newGreater = added.greater.add(added.getData().get());
                        return new SimpleBalancedBinarySearchNonEmptyTree<>(
                                toBeBumped,
                                newLesser,
                                newGreater
                        );
                }
                else
                    return added;
        }
    }
}
