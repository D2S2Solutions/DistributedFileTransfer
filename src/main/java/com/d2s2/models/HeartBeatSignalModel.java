package com.d2s2.models;

import com.d2s2.constants.ApplicationConstants;
import com.d2s2.heartbeater.HeartBeaterImpl;
import com.d2s2.overlay.route.PeerTableImpl;
import com.d2s2.ui.GUIController;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Tharindu Diluksha on 10/25/2017.
 */
public class HeartBeatSignalModel extends UnicastRemoteObject implements AbstractRequestResponseModel {
    String ip;
    int port;
    String userName;

    public HeartBeatSignalModel(String ip, int port, String userName) throws RemoteException {
        this.userName = userName;
        this.port = port;
        this.ip = ip;
    }


    @Override
    public void handle() {
        Node beatedNode = new Node(this.ip, this.port);
        HeartBeaterImpl.getInstance().saveBeatedNodes(beatedNode);
<<<<<<< HEAD
        PeerTableImpl peerTable = PeerTableImpl.getInstance();
        if(!peerTable.getPeerNodeList().contains(beatedNode) && ApplicationConstants.isRegisterd){
            peerTable.insert(beatedNode);
        }
        GUIController.getInstance().populatePeerTable(peerTable.getPeerNodeList());
=======
        PeerTableImpl.getInstance().insert(beatedNode);
        //update GUI

        //GUIController.getInstance().populatePeerTable(PeerTableImpl.getInstance().getPeerNodeList());
>>>>>>> origin/RMI-HeartBeat
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
