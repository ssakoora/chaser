package org.sasi.java.trees;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class NonEmptyTree<T extends Comparable<T>> extends BinarySearchTree<T> {

    public final T data;

    public final BinarySearchTree<T> lesser;

    public final BinarySearchTree<T> greater;

    public NonEmptyTree(T data, BinarySearchTree<T> lesser, BinarySearchTree<T> greater) {
        this.data = data;
        this.lesser = lesser;
        this.greater = greater;
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
            return new NonEmptyTree<T>(this.data, lesser.add(data), greater);
        else
            return new NonEmptyTree<T>(this.data, lesser, greater.add(data));
    }

    @Override
    public BinarySearchTree<T> remove(T data) {
        if( data.compareTo(this.data) == 0)
            if(!lesser.isEmpty())
                return new NonEmptyTree<T>(lesser.getData().get(), lesser.remove(lesser.getData().get()), greater);
            else if (!greater.isEmpty())
                return new NonEmptyTree<T>(greater.getData().get(), lesser, greater.remove(greater.getData().get()));
            else
                return new EmptyTree<T>();

//            return new EmptyTree<T>().addAll(lesser.toList()).addAll(greater.toList());
        else if (data.compareTo(this.data) < 0)
            return this.lesser.remove(data);
        else
            return this.greater.remove(data);
    }

    @Override
    public StringBuffer printSelf(String prefix, StringBuffer bufferToPrint) {
        return bufferToPrint
                .append(prefix+"|___ "+data)
                .append("\n")
                .append(lesser.printSelf(prefix+"\t|", new StringBuffer()))
                .append("\n")
                .append(greater.printSelf(prefix+"\t", new StringBuffer()));
    }

    @Override
    public List<T> toList() {
        List<T> items = new ArrayList<T>();
        items.addAll(lesser.toList());
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
}
