package com.d2s2.models;

import com.d2s2.constants.ApplicationConstants;
import com.d2s2.heartbeater.HeartBeaterImpl;
import com.d2s2.overlay.route.PeerTableImpl;
import com.d2s2.ui.GUIController;

/**
 * Created by Tharindu Diluksha on 10/25/2017.
 */
public class HeartBeatSignalModel extends AbstractRequestResponseModel {
    String ip;
    int port;
    String userName;

    public HeartBeatSignalModel(String ip, int port, String userName) {
        this.userName = userName;
        this.port = port;
        this.ip = ip;
    }


    @Override
    public void handle() {
        Node beatedNode = new Node(this.ip, this.port);
        HeartBeaterImpl.getInstance().saveBeatedNodes(beatedNode);
        PeerTableImpl peerTable = PeerTableImpl.getInstance();
        if(!peerTable.getPeerNodeList().contains(beatedNode) && ApplicationConstants.isRegisterd){
            peerTable.insert(beatedNode);
        }
        GUIController.getInstance().populatePeerTable(peerTable.getPeerNodeList());
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public String getUserName() {
        return userName;
    }
}
