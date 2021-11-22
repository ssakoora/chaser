package org.sasi.java.trees;

public class SimpleBalancedBinarySearchTree<T extends Comparable<T>> extends NonEmptyTree<T> {

    private final int height;

    public SimpleBalancedBinarySearchTree(T data, BinarySearchTree<T> lesser, BinarySearchTree<T> greater, int height) {
        super(data, lesser, greater);
        this.height = height;
    }

    @Override
    public SimpleBalancedBinarySearchTree<T> add(T data) {
        return balanced(addToTree(data));
    }

    @Override
    public BinarySearchTree<T> remove(T data) {
        if( data.compareTo(this.data) == 0)
            if(!lesser.isEmpty()) {
                BinarySearchTree<T> newLesser = lesser.remove(lesser.getData().get());
                return new SimpleBalancedBinarySearchTree<>(
                        lesser.getData().get(),
                        newLesser,
                        greater,
                        Math.max(newLesser.height(), greater.height())+1
                );
            } else if (!greater.isEmpty()) {
                BinarySearchTree<T> newGreater = greater.remove(greater.getData().get());
                return new SimpleBalancedBinarySearchTree<>(
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

    private SimpleBalancedBinarySearchTree<T> addToTree(T data) {
        if(data.compareTo(this.data) == 0)
            return this;
        else if(data.compareTo(this.data) < 0) {
            BinarySearchTree<T> newLesser = lesser.add(data);
            return new SimpleBalancedBinarySearchTree<>(
                    this.data,
                    newLesser,
                    greater,
                    Math.max(newLesser.height(), greater.height())+1
            );
        } else {
            BinarySearchTree<T> newGreater = greater.add(data);
            return new SimpleBalancedBinarySearchTree<>(
                    this.data,
                    lesser,
                    newGreater,
                    Math.max(lesser.height(), newGreater.height())+1
            );
        }
    }

    private SimpleBalancedBinarySearchTree<T> balanced(SimpleBalancedBinarySearchTree<T> added) {
        while(added.lesser.height() - added.greater.height() < -1   || added.lesser.height() - added.greater.height() > 1) {
            if (added.lesser.height() - added.greater.height() < -1 ){
                    BinarySearchTree<T> newLesser = added.lesser.add(added.getData().get());
                    BinarySearchTree<T> newGreater = added.greater.remove(added.greater.getData().get());
                    added = new SimpleBalancedBinarySearchTree<T>(
                            added.greater.getData().get(),
                            newLesser,
                            newGreater,
                            Math.max(newLesser.height(), newGreater.height())+1
                    );
                }
            else {
                    BinarySearchTree<T> newLesser = added.lesser.remove(added.lesser.getData().get());
                    BinarySearchTree<T> newGreater = added.greater.add(added.getData().get());
                    added = new SimpleBalancedBinarySearchTree<T>(
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
        return Math.max(lesser.height(), greater.height())+1;
    }
}
