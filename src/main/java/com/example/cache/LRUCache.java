package com.example.cache;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class LRUCache<K, V> implements Cache<K, V> {
    private final int capacity;
    protected Map<K, CacheElement<K, V>> elementsMap;
    protected LinkedList<CacheElement<K, V>> elementsList;
    private static LRUCache myCache = new LRUCache(10);
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private LRUCache(int capacity) {
        this.capacity = capacity;
        this.elementsMap = new HashMap<>();
        this.elementsList = new LinkedList<>();
    }

    public static LRUCache getInstance() {
        return myCache;
    }


    @Override
    public Optional<V> get(K key) {
        this.lock.writeLock().lock();
        try {
            Optional<V> result = Optional.empty();
            if (containsKey(key)) {
                final CacheElement<K, V> cacheElement = elementsMap.get(key);
                result = Optional.of(cacheElement.getValue());
                elementsList.remove(cacheElement);
                elementsList.addFirst(cacheElement);
            }
            return result;
        }finally {
            this.lock.readLock().unlock();
        }
    }

    @Override
    public void put(K key, V value) {
        this.lock.writeLock().lock();
        try {
            if (containsKey(key)) {
                elementsList.remove(elementsMap.get(key));
            } else {
                ensureCapacity();
            }
            final CacheElement<K, V> newCacheElement = new CacheElement<>(key, value);
            elementsMap.put(key, newCacheElement);
            elementsList.addFirst(newCacheElement);
        }
        finally {
            this.lock.writeLock().unlock();
        }
    }

    @Override
    public boolean containsKey(K key) {
        return elementsMap.containsKey(key);
    }


    @Override
    public int size() {
        this.lock.readLock().lock();
        try {
            return elementsList.size();
        } finally {
            this.lock.readLock().unlock();
        }
    }


    private boolean removeElement() {
        this.lock.writeLock().lock();
        try {
            if (this.isEmpty()) {
                return false;
            }
            elementsMap.remove(elementsMap.get(elementsMap.size()-1));
            return true;
        } finally {
            this.lock.writeLock().unlock();
        }
    }
    private boolean isSizeExceeded() {
        return size() == capacity;
    }

    private void ensureCapacity() {
        if (isSizeExceeded()) {
            final CacheElement<K, V> removedCacheElement = elementsList.removeLast();
            elementsMap.remove(removedCacheElement.getKey());
        }
    }

    @Override
    public boolean isEmpty(){
        return elementsList.size() == 0;
    }

    @Override
    public void clear() {
        this.lock.writeLock().lock();
        try {
            elementsList.clear();
            elementsMap.clear();
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    public Map<K, CacheElement<K, V>> getElementsMap() {
        return elementsMap;
    }
}