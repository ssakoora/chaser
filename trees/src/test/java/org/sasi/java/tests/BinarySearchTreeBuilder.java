package org.sasi.java.tests;

import org.junit.jupiter.api.Test;
import org.sasi.java.trees.BinarySearchTree;
import org.sasi.java.trees.HeightAwareBST;
import org.sasi.java.trees.SimpleBalancedBST;
import org.sasi.java.trees.SimpleBST;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

public class BinarySearchTreeBuilder {

    @Test
    public void height_test_for_Balanced_Binary_Search_Tree() {
        List<Integer> data = IntStream.range(0, 10000).boxed().collect(toList());
        BinarySearchTree<Integer> heightAwareTree = HeightAwareBST.of(data);
        System.out.println("Height of height aware tree - for sorted values : "
                + heightAwareTree.height());
        BinarySearchTree<Integer> simpleBalancedBST = SimpleBalancedBST.of(data);
        System.out.println("Height of Simple Balanced Tree - for sorted values : "
                + simpleBalancedBST.height());
    }

    @Test
    public void height_test_for_Balanced_Binary_Search_Tree_String_values() {
        List<String> data = IntStream.range(0, 10000).mapToObj(BinarySearchTreeBuilder::generateRandomString).collect(toList());
        BinarySearchTree<String> heightAwareTree = HeightAwareBST.of(data);
        System.out.println("Height of height aware tree - for sorted values : "
                + heightAwareTree.height());
        BinarySearchTree<String> simpleBalancedBST = SimpleBalancedBST.of(data);
        System.out.println("Height of Simple Balanced Tree - for sorted values : "
                + simpleBalancedBST.height());
    }

    public static void main(String[] args) {
        List<String> data = IntStream.range(0, 10000).mapToObj(
                BinarySearchTreeBuilder::generateRandomString
        ).collect(toList());
        BinarySearchTree<String> some_other_data = SimpleBST.of(data);
        System.out.println("Height of the tree : "+some_other_data.height());
        some_other_data = SimpleBST.of(some_other_data.toList());
        System.out.println("Height of sorted tree : "+some_other_data.height());
        some_other_data = HeightAwareBST.of(data);
        System.out.println("Height of height aware tree : "+some_other_data.height());
    }

    public static String generateRandomString(int i) {
        return new Random().ints(10, 65, 90)
                .mapToObj(x -> String.valueOf((char) x))
                .collect(joining());
    }
}
