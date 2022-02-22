package com.example.file;

import java.util.ArrayList;
import java.util.List;

public class IdGenerator {

    private static List<Integer> listId;
    private static List<Integer> removedId;
    private static int key;

    public IdGenerator() {
        listId = new ArrayList<>();
        removedId = new ArrayList<>();
        key = 0;
    }

    private void addKey() {
        listId.add(key);
    }


    private boolean isReserved() {
        return listId.contains(key);
    }


    public synchronized int generateKey() {
        if (!removedId.isEmpty()) {
            int temp = key;
            key = removedId.get(removedId.size() - 1);
            addKey();
            int x = key;
            key = temp;
            return x;
        }

        while (isReserved()) {
            key++;
        }
        addKey();
        return key;
    }

    public synchronized void deleteKey(int key) {
        removedId.add(key);
        listId.removeIf(integer -> integer == key);
    }

}