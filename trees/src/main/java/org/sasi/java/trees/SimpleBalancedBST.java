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

    static class SimpleBalancedBinarySearchNonEmptyTree<T extends Comparable<T>> extends HeightAwareBST.HeightAwareNonEmptyTree<T> {

        public SimpleBalancedBinarySearchNonEmptyTree(T data, BinarySearchTree<T> lesser, BinarySearchTree<T> greater) {
            super(data, lesser, greater);
        }

        @Override
        public BinarySearchTree<T> add(T data) {
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
                    BinarySearchTree<T> newLesser = lesser.remove(biggestOnLesser);
                    return new SimpleBalancedBinarySearchNonEmptyTree<>(biggestOnLesser, newLesser, greater);
                }
            else if (data.compareTo(this.data) < 0) {
                BinarySearchTree<T> newLesser = this.lesser.remove(data);
                return new SimpleBalancedBinarySearchNonEmptyTree<>(this.data, newLesser, this.greater);
            } else {
                BinarySearchTree<T> newGreater = this.greater.remove(data);
                return new SimpleBalancedBinarySearchNonEmptyTree<>(this.data, this.lesser, newGreater);
            }
        }

        private SimpleBalancedBinarySearchNonEmptyTree<T> addToTree(T data) {
            if(data.compareTo(this.data) == 0)
                return this;
            else if(data.compareTo(this.data) < 0) {
                BinarySearchTree<T> newLesser = lesser.add(data);
                return new SimpleBalancedBinarySearchNonEmptyTree<>(this.data, newLesser, greater);
            } else {
                BinarySearchTree<T> newGreater = greater.add(data);
                return new SimpleBalancedBinarySearchNonEmptyTree<>(this.data, lesser, newGreater);
            }
        }

        public SimpleBalancedBinarySearchNonEmptyTree<T> balanced(SimpleBalancedBinarySearchNonEmptyTree<T> added) {
                if (isRightHeavy(added)){
                        T toBeBumped = added.greater.lowest().get();
                        BinarySearchTree<T> newLesser = added.lesser.add(added.getData().get());
                        BinarySearchTree<T> newGreater = added.greater.remove(toBeBumped);
                        return new SimpleBalancedBinarySearchNonEmptyTree<>(
                                toBeBumped,
                                newLesser,
                                newGreater
                        );
                    }
                else if (isLeftHeavy(added)){
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

        public boolean isLeftHeavy(SimpleBalancedBinarySearchNonEmptyTree added) {
            return added.lesser().height() - added.greater().height() > 1;
        }

        public boolean isRightHeavy(SimpleBalancedBinarySearchNonEmptyTree added) {
            return added.lesser().height() - added.greater().height() < -1;
        }

    }

}
