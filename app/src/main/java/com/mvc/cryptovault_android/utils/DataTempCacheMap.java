package com.mvc.cryptovault_android.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 临时缓存，仅存在于app开始到关闭
 * 后期根据场景做本地缓存
 */
public class DataTempCacheMap {
    private static HashMap<String, Node> cache = new HashMap<>();

    private static Node node;

    public static void put(String key, Object value) {
        if (node == null) {
            node = new Node(value, null);
            cache.put(key, node);
        } else {
            Node tempNode = cache.get(key);
            if (tempNode != null) {
                while (tempNode.next != null) {
                    tempNode.next = tempNode.next.next;
                }
                tempNode = new Node(value, null);
                cache.put(key, tempNode);
            } else {
                cache.put(key, new Node(value, null));
            }
        }
    }

    /**
     * Fuzzy Query
     *
     * @param key
     * @return
     */
    public static List<Node> getFuzzyQuery(String key) {
        List<Node> nodeList = new ArrayList<>();
        Increase:
        for (String lk : cache.keySet()) {
            if (lk.contains(key)) {
                for (Node newNode : nodeList) {
                    if (cache.get(lk).equals(newNode)) {
                        continue Increase;
                    }
                }
                nodeList.add(cache.get(lk));
            }
        }
        return nodeList;
    }

    /**
     * Precise inquery
     *
     * @param key
     * @return
     */
    public static Node getPreciseQuery(String key) {
        return cache.get(key);
    }

    public static void clear() {
        cache.clear();
    }

    public static class Node<V> {
        V value;
        Node<V> next;

        public Node(V value, Node<V> next) {
            this.value = value;
            this.next = next;
        }

        public V getValue() {
            return value;
        }

        public Node<V> getNext() {
            return next;
        }

        public V next() {
            return (V) next.next;
        }
    }
}

