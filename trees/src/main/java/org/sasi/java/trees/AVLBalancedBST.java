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

    static class AVLBalancedEmptyBST<T extends Comparable<T>> extends HeightAwareBST.HeightAwareEmptyTree<T> {
        @Override
        public BinarySearchTree<T> add(T data) {
            return new AVLBalancedNonEmptyBST<>(data,
                    new AVLBalancedEmptyBST<>(),
                    new AVLBalancedEmptyBST<>());
        }
    }

    static class AVLBalancedNonEmptyBST<T extends Comparable<T>> extends HeightAwareBST.HeightAwareNonEmptyTree<T> {

        public AVLBalancedNonEmptyBST(T data, BinarySearchTree<T> lesser, BinarySearchTree<T> greater) {
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
                    return new AVLBalancedEmptyBST<>();
                else if(!lesser.isEmpty() && greater.isEmpty())
                    return lesser;
                else if(lesser.isEmpty() && !greater.isEmpty())
                    return greater;
                else {
                    T biggestOnLesser = lesser.highest().get();
                    BinarySearchTree<T> newLesser = lesser.remove(biggestOnLesser);
                    return new AVLBalancedNonEmptyBST<>(biggestOnLesser, newLesser, greater);
                }
            else if (data.compareTo(this.data) < 0) {
                BinarySearchTree<T> newLesser = this.lesser.remove(data);
                return new AVLBalancedNonEmptyBST<>(this.data, newLesser, this.greater);
            } else {
                BinarySearchTree<T> newGreater = this.greater.remove(data);
                return new AVLBalancedNonEmptyBST<>(this.data, this.lesser, newGreater);
            }
        }

        private AVLBalancedNonEmptyBST<T> addToTree(T data) {
            if(data.compareTo(this.data) == 0)
                return this;
            else if(data.compareTo(this.data) < 0) {
                BinarySearchTree<T> newLesser = lesser.add(data);
                return new AVLBalancedNonEmptyBST<>(this.data, newLesser, greater);
            } else {
                BinarySearchTree<T> newGreater = greater.add(data);
                return new AVLBalancedNonEmptyBST<>(this.data, lesser, newGreater);
            }
        }

        public BinarySearchTree<T> balanced(AVLBalancedNonEmptyBST<T> added) {
            if (isRightHeavy(added))
                if (isLeftHeavy((AVLBalancedNonEmptyBST<T>) added.greater()))
                    return leftRotate(new AVLBalancedNonEmptyBST<>(added.getData().get(), added.lesser(), rightRotate(added.greater())));
                else
                    return leftRotate(added);
            else if (isLeftHeavy(added))
                if(isRightHeavy((AVLBalancedNonEmptyBST<T>) added.lesser()))
                    return rightRotate(new AVLBalancedNonEmptyBST<>(added.getData().get(), leftRotate(added.lesser()), added.greater()));
                else
                    return rightRotate(added);
            else
                return added;
        }

        private BinarySearchTree<T> leftRotate(BinarySearchTree<T> added) {
            AVLBalancedNonEmptyBST<T> leftToTop = new AVLBalancedNonEmptyBST<>(
                    added.getData().get(), added.lesser(), added.greater().lesser()
            );
            AVLBalancedNonEmptyBST<T> topLevel = new AVLBalancedNonEmptyBST<>(
                    added.greater().getData().get(), leftToTop, added.greater().greater()
            );
            return topLevel;
        }

        private BinarySearchTree<T> rightRotate(BinarySearchTree<T> added) {
            AVLBalancedNonEmptyBST<T> rightToTop = new AVLBalancedNonEmptyBST<>(
                    added.getData().get(), added.lesser().greater(), added.greater()
            );
            AVLBalancedNonEmptyBST<T> topLevel = new AVLBalancedNonEmptyBST<>(
                    added.lesser().getData().get(), added.lesser().lesser(), rightToTop
            );
            return topLevel;
        }

        public boolean isLeftHeavy(AVLBalancedNonEmptyBST<T> added) {
            return added.lesser().height() - added.greater().height() > 1;
        }

        public boolean isRightHeavy(AVLBalancedNonEmptyBST<T> added) {
            return added.lesser().height() - added.greater().height() < -1;
        }

    }


}
