package com.d2s2.overlay.route;

import com.d2s2.models.Node;

import java.util.ArrayList;

/**
 * Created by Heshan Sandamal on 10/6/2017.
 */
public class PeerTableImpl implements Table {

    private static final PeerTableImpl PEER_TABLE = new PeerTableImpl();
    private ArrayList<Node> peerNodeList;
    private PeerTableImpl(){
        peerNodeList = new ArrayList<>(2);
    }

    public static PeerTableImpl getInstance(){
        return PEER_TABLE;
    }

    @Override
    public void insert(Node node) {
        peerNodeList.add(node); // todo limit the number of node to 2
    }

    @Override
    public void remove() {

    }

    @Override
    public void search() {

    }
}
