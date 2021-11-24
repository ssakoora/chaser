package org.sasi.java.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.sasi.java.trees.BinarySearchTree;
import org.sasi.java.trees.HeightAwareBST;
import org.sasi.java.trees.SimpleBST;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HeightAwareEmptyTreeTest {

    @Test
    public void memoizedHeight_and_calculatedHeight_should_be_the_same_for_add_operations(){
        List<String> data = List.of("DFDFDFD","ERERERER","FFFFF", "AAAAA", "DDDDD", "RRRRRR");
        BinarySearchTree<String> simpleBST = SimpleBST.of(data);
        BinarySearchTree<String> heightMemoizedBST = HeightAwareBST.of(data);
        System.out.println(simpleBST.printSelf("\t", new StringBuffer()).toString());
        System.out.println("\n\n\n");
        System.out.println(heightMemoizedBST.printSelf("\t\t", new StringBuffer()).toString());
        Assertions.assertEquals(simpleBST.height(), heightMemoizedBST.height());
    }

    @Test
    public void memoizedHeight_and_calculatedHeight_should_be_the_same_for_remove_operations(){
        List<String> data = List.of("DFDFDFD","ERERERER","FFFFF", "AAAAA", "DDDDD", "RRRRRR");
        BinarySearchTree<String> simpleBST = SimpleBST.of(data);
        BinarySearchTree<String> heightMemoizedBST = HeightAwareBST.of(data);
        Assertions.assertEquals(simpleBST.height(), heightMemoizedBST.height());
        for (String d : data) {
            System.out.println(simpleBST.printSelf("\t", new StringBuffer()).toString());
            System.out.println("To remove - "+d);
            Assertions.assertEquals(simpleBST.remove(d).height(), heightMemoizedBST.remove(d).height());
        }
        for (String d : data) {
            simpleBST = simpleBST.remove(d);
            heightMemoizedBST = heightMemoizedBST.remove(d);
            Assertions.assertEquals(simpleBST.height(), heightMemoizedBST.height());
        }
    }

    @Test
    void removeShouldRemoveAnElementFromTree() {
        List<String> dataToAdd = List.of("ABC", "CDE", "FGH");
        BinarySearchTree<String> tree = HeightAwareBST.of(dataToAdd);
        assertFalse(tree.isEmpty());
        assertEquals(3, tree.size());
        assertEquals(2, tree.remove("ABC").size());
        assertTrue(tree.isPresent("ABC"));
        assertFalse(tree.remove("ABC").isPresent("ABC"));
        assertEquals(1, tree.remove("ABC").remove("CDE").size());
        assertEquals(0, tree.remove("ABC").remove("CDE").remove("FGH").size());
    }

}
