package org.sasi.java.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.sasi.java.trees.*;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

public class BinarySearchTreeBuilder {

    List<String> largeRandomData = IntStream.range(0, 10000).mapToObj(BinarySearchTreeBuilder::generateRandomString).collect(toList());
    List<Integer> largeSortedData = IntStream.range(0, 10000).boxed().collect(toList());

    @Test
    public void log_time_simple_BST_large_sorted_data() {
        BinarySearchTree<Integer> heightAwareTree = HeightAwareBST.of(largeSortedData);
        System.out.println("Height of height aware tree - for sorted values : "
                + heightAwareTree.height()+":"+heightAwareTree.size());
        Assertions.assertArrayEquals(largeSortedData.toArray(new Integer[0]), heightAwareTree.toList().toArray(new Integer[0]));
    }

    @Test
    public void log_time_balanced_BST_large_sorted_data() {
        BinarySearchTree<Integer> simpleBalancedBST = SimpleBalancedBST.of(largeSortedData);
        System.out.println("Height of Simple Balanced Tree - for sorted values : "
                + simpleBalancedBST.height()+":"+simpleBalancedBST.size());
        Assertions.assertArrayEquals(largeSortedData.toArray(new Integer[0]), simpleBalancedBST.toList().toArray(new Integer[0]));
    }

    @Test
    public void log_time_balanced_AVL_BST_large_sorted_data() {
        BinarySearchTree<Integer> avl = AVLBalancedBST.of(largeSortedData);
        System.out.println("Height of Simple Balanced Tree - for sorted values : "
                + avl.height());
        Assertions.assertArrayEquals(largeSortedData.toArray(new Integer[0]), avl.toList().toArray(new Integer[0]));
    }

    @Test
    public void log_time_simple_BST_large_random_data() {
        BinarySearchTree<String> heightAwareTree = HeightAwareBST.of(largeRandomData);
        System.out.println("Height of height aware tree - for sorted values : "
                + heightAwareTree.height());
        Assertions.assertArrayEquals(largeRandomData.stream().sorted().toArray(), heightAwareTree.toList().toArray(new String[0]) );
    }

    @Test
    public void log_time_balanced_BST_large_random_data() {
        BinarySearchTree<String> simpleBalancedBST = SimpleBalancedBST.of(largeRandomData);
        System.out.println("Height of Simple Balanced Tree - for sorted values : "
                + simpleBalancedBST.height());
        Assertions.assertArrayEquals(largeRandomData.stream().sorted().toArray(), simpleBalancedBST.toList().toArray(new String[0]) );
    }

    @Test
    public void log_time_balanced_AVL_BST_large_random_data() {
        BinarySearchTree<String> avl = AVLBalancedBST.of(largeRandomData);
        System.out.println("Height of AVL Tree - for sorted values : "
                + avl.height());
        Assertions.assertArrayEquals(largeRandomData.stream().sorted().toArray(), avl.toList().toArray(new String[0]) );
    }

    @Test
    public void randomTest() {
        BinarySearchTree<Integer> bbst = SimpleBalancedBST.of(0);
        System.out.println(bbst.printSelf("\t", new StringBuffer()).toString());
        List<Integer> largeSortedData = IntStream.range(1, 10).boxed().collect(toList());
        for (Integer i : largeSortedData) {
            System.out.println("Adding : "+i);
            bbst = bbst.add(i);
        }
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
