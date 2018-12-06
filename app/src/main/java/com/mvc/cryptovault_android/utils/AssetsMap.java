package com.mvc.cryptovault_android.utils;

import java.util.HashMap;

/**
 * Very simple map structure
 * Only add removal and traverse
 *
 * @param <K,V>
 */
public class AssetsMap<K, V> {
    private HashMap<K, V> mMap;
    private int size;

    public AssetsMap() {
        mMap = new HashMap<>();
    }

    public void put(K key, V value) {
        mMap.put(key, value);
        size++;
    }


    public boolean remove(K key) {
        V t = mMap.get(key);
        if (t != null) {
            mMap.remove(key);
            size--;
            return true;
        } else {
            return false;
        }
    }

    public HashMap<K, V> getMap() {
        return mMap;
    }

    public int size() {
        return size;
    }

}
