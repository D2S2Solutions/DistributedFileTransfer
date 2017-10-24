package com.d2s2.overlay.route;

import com.d2s2.models.Node;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Heshan Sandamal on 10/6/2017.
 */
public class StatTableImpl {

    private static ConcurrentHashMap<String, ConcurrentLinkedQueue<Node>> statTable = new ConcurrentHashMap();


    public void insert(Node node) {

    }


    public void remove() {

    }


    public ConcurrentLinkedQueue search(String query) {
        ConcurrentLinkedQueue concurrentLinkedQueues = new ConcurrentLinkedQueue();
        statTable.keySet().stream().filter(s -> s.contains(query)).forEach(s -> concurrentLinkedQueues.add(statTable.get(s)));
        return concurrentLinkedQueues;
    }


}
