package com.d2s2.overlay.route;

import com.d2s2.models.Node;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Heshan Sandamal on 10/6/2017.
 */
public class PeerTableImpl implements Table {

    private static final PeerTableImpl PEER_TABLE = new PeerTableImpl();
    private static Set<Node> peerNodeList;

    static  {
        peerNodeList = ConcurrentHashMap.newKeySet();
    }

    public static PeerTableImpl getInstance() {
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

    public Set<Node> getPeerNodeList() {
        return peerNodeList;
    }
}
