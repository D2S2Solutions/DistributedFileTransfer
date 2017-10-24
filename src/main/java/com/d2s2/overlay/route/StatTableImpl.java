package com.d2s2.overlay.route;

import com.d2s2.models.Node;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Heshan Sandamal on 10/6/2017.
 */
public class StatTableImpl implements Table {

    private static ConcurrentHashMap<String,LinkedList<Node>> statTable = new ConcurrentHashMap();

    @Override
    public void insert(Node node) {
        //statTable.get()
    }

    @Override
    public void remove() {

    }

    @Override
    public void search() {

    }
}
