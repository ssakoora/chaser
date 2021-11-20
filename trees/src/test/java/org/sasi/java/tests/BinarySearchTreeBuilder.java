package org.sasi.java.tests;

import org.sasi.java.trees.BinarySearchTree;
import org.sasi.java.trees.EmptyTree;

import java.util.Random;
import java.util.stream.Collectors;

public class BinarySearchTreeBuilder {

    public static void main(String[] args) {
        BinarySearchTree<String> some_other_data = new EmptyTree<>();
        for (int i=0; i<1000; i++) {
            String randomString = new Random().ints(10, 65, 90)
                    .mapToObj(x -> String.valueOf((char) x)).collect(Collectors.joining());
            some_other_data = some_other_data.add(randomString);
            System.out.println(some_other_data.printSelf("\t", new StringBuffer()).toString());
        }
    }
}
