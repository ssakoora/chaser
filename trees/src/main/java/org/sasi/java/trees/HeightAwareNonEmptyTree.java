package org.sasi.java.trees;

public class HeightAwareNonEmptyTree<T extends Comparable<T>> extends NonEmptyTree<T> {

    private final int height;

    public HeightAwareNonEmptyTree(T data, BinarySearchTree<T> lesser, BinarySearchTree<T> greater, int height) {
        super(data, lesser, greater);
        this.height = height;
    }

    public int getHeight() {
        return this.height;
    }

    @Override
    public BinarySearchTree<T> add(T data) {
        if(data.compareTo(this.data) == 0)
            return this;
        else if(data.compareTo(this.data) < 0)
            return new HeightAwareNonEmptyTree<>(
                    this.data,
                    lesser.add(data),
                    greater,
                    this.height+1
            );
        else
            return new HeightAwareNonEmptyTree<>(
                    this.data,
                    lesser,
                    greater.add(data),
                    this.height+1
            );
    }

    @Override
    public BinarySearchTree<T> remove(T data) {
        if( data.compareTo(this.data) == 0)
            if(!lesser.isEmpty())
                return new HeightAwareNonEmptyTree<>(
                        lesser.getData().get(),
                        lesser.remove(lesser.getData().get()),
                        greater,
                        Math.max(lesser.height()-1, greater.height())
                );
            else if (!greater.isEmpty())
                return new HeightAwareNonEmptyTree<>(
                        greater.getData().get(),
                        lesser,
                        greater.remove(greater.getData().get()),
                        Math.max(lesser.height(), greater.height()-1)
                );
            else
                return new HeightAwareEmptyTree<>();
        else if (data.compareTo(this.data) < 0)
            return this.lesser.remove(data);
        else
            return this.greater.remove(data);
    }
}
