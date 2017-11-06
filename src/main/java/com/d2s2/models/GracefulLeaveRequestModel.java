package com.d2s2.models;

import com.d2s2.constants.ApplicationConstants;
import com.d2s2.overlay.route.PeerTableImpl;
import com.d2s2.overlay.route.StatTableImpl;
import com.d2s2.ui.GUIController;

public class GracefulLeaveRequestModel extends AbstractRequestModel {

    public GracefulLeaveRequestModel(String ip, int port) {
        super(ip, port);
    }

    @Override
    public void handle() {
        PeerTableImpl peerTable = PeerTableImpl.getInstance();
        Node node = new Node(this.ip, this.port);
        boolean peerTableRemoved = peerTable.remove(node);
        Boolean statTableRemoved = StatTableImpl.getInstance().remove(node);
        if (statTableRemoved || peerTableRemoved) {
            GUIController.getInstance().displayMessage("A neighbour left the system " + ApplicationConstants.PORT);
        }
    }
}
