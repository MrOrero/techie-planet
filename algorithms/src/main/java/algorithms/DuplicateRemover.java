package main.java.algorithms;

import java.util.Arrays;

/**
 * I went with a simple hash map implementation to track duplicates.
 * This avoids using Java's built-in collections and I have implemented
 * a basic hash map previously while learning data structures. so it
 * felt appropriate to use it here.
 * For the time complexity, each insertion and lookup in the hash map is O(1)
 * on average, making the overall time complexity O(n) [LINEAR] for each row. To be more
 * precise, it's 0(n*m) where n is the number of rows and m is the number of elements
 * in each row.
 */

class SimpleHashMap {
    private static final int SIZE = 10007; 
    private Node[] nodes;
    
    private static class Node {
        int key;
        Node next;
        
        Node(int key) {
            this.key = key;
            this.next = null;
        }
    }
    
    public SimpleHashMap() {
        nodes = new Node[SIZE];
    }
    
    private int hash(int key) {
        // Handle negative numbers
        int hash = key % SIZE;
        if (hash < 0) {
            hash += SIZE;
        }
        return hash;
    }
    
    public void put(int key) {
        int hashedKey = hash(key);
        
        Node current = nodes[hashedKey];
        while (current != null) {
            if (current.key == key) {
                return;
            }
            current = current.next;
        }
        
        Node newNode = new Node(key);
        newNode.next = nodes[hashedKey];
        nodes[hashedKey] = newNode;
    }
    
    public boolean has(int key) {
        int hashedKey = hash(key);
        Node current = nodes[hashedKey];
        
        while (current != null) {
            if (current.key == key) {
                return true;
            }
            current = current.next;
        }
        return false;
    }
}

public class DuplicateRemover {
    public static int[][] removeDuplicates(int[][] arr) {
        int[][] updatedArray = new int[arr.length][];
        
        for (int i = 0; i < arr.length; i++) {
            int[] row = arr[i];
            updatedArray[i] = new int[row.length];
            
            SimpleHashMap map = new SimpleHashMap();
            
            for (int j = 0; j < row.length; j++) {
                if (map.has(row[j])) {
                    updatedArray[i][j] = 0;
                } else {
                    updatedArray[i][j] = row[j];
                    map.put(row[j]);
                }
            }
        }
        
        return updatedArray;
    }
    
    /**
     * Helper method to print 2D array
     */
    public static void print2DArray(int[][] arr) {
        System.out.println("[");
        for (int i = 0; i < arr.length; i++) {
            System.out.print("  {");
            for (int j = 0; j < arr[i].length; j++) {
                System.out.print(arr[i][j]);
                if (j < arr[i].length - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println("}");
        }
        System.out.println("]");
    }
    
    public static void main(String[] args) {
        int[][] input = {
            {1, 3, 1, 2, 3, 4, 4, 3, 5},
            {1, 1, 1, 1, 1, 1, 1}
        };
        
        System.out.println("Input:");
        print2DArray(input);
        
        int[][] output = removeDuplicates(input);
        
        System.out.println("\nOutput:");
        print2DArray(output);
    }
}