package com.d2s2.models;

import com.d2s2.Handler.Handler;
import com.d2s2.Handler.HandlerImpl;
import com.d2s2.constants.ApplicationConstants;
import com.d2s2.overlay.route.PeerTableImpl;
import com.d2s2.ui.GUIController;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;

/**
 * Created by Heshan Sandamal on 10/24/2017.
 */
public class RegistrationResponseModel extends UnicastRemoteObject implements AbstractRequestResponseModel {
    HashSet<Node> nodeset;
    int nodeCount;
    Handler handler;

    public RegistrationResponseModel(int nodeCount, HashSet<Node> nodeset) throws RemoteException {
        this.nodeCount = nodeCount;
        this.nodeset = nodeset;
        handler = new HandlerImpl();
    }

    @Override
    public void handle() {
        PeerTableImpl peerTable = PeerTableImpl.getInstance();
        nodeset.forEach((node) -> {
            peerTable.insert(node);
            try {
                handler.notifyNeighbours(node.getNodeIp(), node.getPort());
            } catch (IOException | NotBoundException io) {
                io.printStackTrace();
            }
        });
        GUIController guiController = GUIController.getInstance();
        guiController.populatePeerTable(peerTable.getPeerNodeList());
        guiController.displayMessage("Successfully registered");
        ApplicationConstants.isRegisterd = true;
        guiController.handleRegisteration();
    }
}
