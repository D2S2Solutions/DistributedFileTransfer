package com.d2s2.overlay.route;

import com.d2s2.models.Node;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Heshan Sandamal on 10/6/2017.
 */

public interface Table {

    void insert(Node node);

    void remove();

    ConcurrentLinkedQueue search(String query);

}
