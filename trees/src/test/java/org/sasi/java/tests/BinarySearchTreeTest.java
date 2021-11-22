package org.sasi.java.tests;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.sasi.java.trees.BinarySearchTree;
import org.sasi.java.trees.SimpleBST;

import java.util.*;
import java.util.stream.IntStream;

public class BinarySearchTreeTest {

    @Test
    void simpleOperationsTest(){
        BinarySearchTree<String> tree = SimpleBST.of("ABCD");
        assertFalse(tree.isEmpty());
        assertEquals(1,tree.size());
        tree = tree.add("EFGH");
        assertFalse(tree.isEmpty());
        assertEquals(2, tree.size());
    }

    @Test
    void toListOperationTest() {
        List<String> dataToAdd = List.of("ABC", "CDE", "FGH");
        BinarySearchTree<String> tree = SimpleBST.of(dataToAdd);
        assertFalse(tree.isEmpty());
        assertEquals(3, tree.size());
        assertArrayEquals(dataToAdd.toArray(), tree.toList().toArray());
    }

    @Test
    void removeShouldRemoveAnElementFromTree() {
        List<String> dataToAdd = List.of("ABC", "CDE", "FGH");
        BinarySearchTree<String> tree = SimpleBST.of(dataToAdd);
        assertFalse(tree.isEmpty());
        assertEquals(3, tree.size());
        assertEquals(2, tree.remove("ABC").size());
        assertTrue(tree.isPresent("ABC"));
        assertFalse(tree.remove("ABC").isPresent("ABC"));
        assertEquals(1, tree.remove("ABC").remove("CDE").size());
        assertEquals(0, tree.remove("ABC").remove("CDE").remove("FGH").size());
    }

    @Test
    void toListMustReturnASortedList() {
        List<String> randomList = IntStream.range(0, 10000).mapToObj(BinarySearchTreeBuilder::generateRandomString).collect(toList());
        BinarySearchTree<String> some_other_data = SimpleBST.of(randomList);
        assertEquals(randomList.size(), some_other_data.size());
        Object[] expected = randomList.toArray();
        Arrays.sort(expected);
        assertArrayEquals(expected, some_other_data.toList().toArray());
    }

}
