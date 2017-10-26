package com.d2s2.models;

import com.d2s2.overlay.route.NeighbourTableImpl;
import com.d2s2.overlay.route.PeerTableImpl;

/**
 * Created by dimuth on 10/24/17.
 */
public class NotifyNeighbourRequestModel extends AbstractRequestModel {
    public NotifyNeighbourRequestModel(String ip, int port) {
        super(ip, port);

    }


    @Override
    public void handle() {
        NeighbourTableImpl neighbourTable = NeighbourTableImpl.getInstance();
        Node node = new Node(this.ip, this.port);
        neighbourTable.insert(node);
        PeerTableImpl peerTable = PeerTableImpl.getInstance();
        if (peerTable.getPeerNodeList().size() < 2) {
            peerTable.insert(node);
        }


    }
}
