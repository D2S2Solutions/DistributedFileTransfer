package com.d2s2.models;

import com.d2s2.Handler.Handler;
import com.d2s2.Handler.HandlerImpl;
import com.d2s2.constants.ApplicationConstants;
import com.d2s2.overlay.route.PeerTableImpl;
import com.d2s2.overlay.route.StatTableImpl;
import com.d2s2.socket.UDPConnectorImpl;
import com.d2s2.ui.GUIController;

import java.io.IOException;

public class GracefulLeaveRequestModel extends AbstractRequestModel {

    private static Handler handler = new HandlerImpl();
    private Node node;
    public GracefulLeaveRequestModel(String ip, int port) {
        super(ip, port);
        node = new Node(this.ip, this.port);
    }

    @Override
    public void handle() {
        PeerTableImpl peerTable = PeerTableImpl.getInstance();
        boolean peerTableRemoved = peerTable.remove(node);
        Boolean statTableRemoved = StatTableImpl.getInstance().remove(node);
        if (statTableRemoved || peerTableRemoved) {
            try {
                handler.sendLeaveOkToSource(new GracefulLeaveResponseModel(ip,port,0));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Node getNode() {
        return node;
    }
}
