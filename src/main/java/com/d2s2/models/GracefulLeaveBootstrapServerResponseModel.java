package com.d2s2.models;

import com.d2s2.overlay.route.PeerTableImpl;

public class GracefulLeaveBootstrapServerResponseModel extends AbstractRequestResponseModel {
    private String ip;
    private String port;

    public GracefulLeaveBootstrapServerResponseModel(String ip, String port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void handle() {
        PeerTableImpl peerTable = PeerTableImpl.getInstance();
        Node node = new Node(this.ip,Integer.valueOf(this.port));
        peerTable.remove(node);

    }
}
