package org.sasi.java.tests;

import org.sasi.java.trees.BinarySearchTree;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

public class BinarySearchTreeBuilder {

    public static void main(String[] args) {
        List<String> data = IntStream.range(0, 10000).mapToObj(
                BinarySearchTreeBuilder::generateRandomString
        ).collect(toList());
        BinarySearchTree<String> some_other_data = BinarySearchTree.simpleBSTOf(data);
        System.out.println("Height of the tree : "+some_other_data.height());
        some_other_data = BinarySearchTree.simpleBSTOf(some_other_data.toList());
        System.out.println("Height of sorted tree : "+some_other_data.height());
        some_other_data = BinarySearchTree.heightMemoizedBSTOf(data);
        System.out.println("Height of height aware tree : "+some_other_data.height());
    }

    public static String generateRandomString(int i) {
        return new Random().ints(10, 65, 90)
                .mapToObj(x -> String.valueOf((char) x))
                .collect(joining());
    }
}
