package org.sasi.java.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.sasi.java.trees.BinarySearchTree;
import org.sasi.java.trees.EmptyTree;

import java.util.*;
import java.util.stream.Collectors;

public class BinarySearchTreeTest {

    @Test
    void simpleOperationsTest(){
        BinarySearchTree<String> tree = new EmptyTree<>();
        assertTrue(tree.isEmpty());
        tree = tree.add("ABCD");
        assertFalse(tree.isEmpty());
        assertEquals(1,tree.size());
        tree = tree.add("EFGH");
        assertFalse(tree.isEmpty());
        assertEquals(2, tree.size());
    }

    @Test
    void toListOperationTest() {
        BinarySearchTree<String> tree = new EmptyTree<>();
        List<String> dataToAdd = List.of("ABC", "CDE", "FGH");
        for ( String data :dataToAdd) {
            tree = tree.add(data);
        }
        assertFalse(tree.isEmpty());
        assertEquals(3, tree.size());
        assertArrayEquals(dataToAdd.toArray(), tree.toList().toArray());
    }

    @Test
    void removeShouldRemoveAnElementFromTree() {
        BinarySearchTree<String> tree = new EmptyTree<>();
        List<String> dataToAdd = List.of("ABC", "CDE", "FGH");
        for ( String data :dataToAdd) {
            tree = tree.add(data);
        }
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
        ArrayList<String> randomList = new ArrayList<>();
        BinarySearchTree<String> some_other_data = new EmptyTree<>();
        for (int i=0; i<1000; i++) {
            String randomString = new Random().ints(10, 65, 90)
                    .mapToObj(x -> String.valueOf((char) x)).collect(Collectors.joining());
            some_other_data = some_other_data.add(randomString);
            randomList.add(randomString);
        }
        assertEquals(randomList.size(), some_other_data.size());
        Object[] expected = randomList.toArray();
        Arrays.sort(expected);
        assertArrayEquals(expected, some_other_data.toList().toArray());
    }

}
