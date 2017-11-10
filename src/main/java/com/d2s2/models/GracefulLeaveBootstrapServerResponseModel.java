package com.d2s2.models;

import com.d2s2.Handler.Handler;
import com.d2s2.Handler.HandlerImpl;
import com.d2s2.constants.ApplicationConstants;
import com.d2s2.message.build.MessageBuilderImpl;
import com.d2s2.overlay.route.NeighbourTableImpl;
import com.d2s2.overlay.route.PeerTableImpl;
import com.d2s2.overlay.route.StatTableImpl;
import com.d2s2.ui.GUIController;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;


public class GracefulLeaveBootstrapServerResponseModel extends AbstractRequestResponseModel {
    private int status;
    private static Handler handler = new HandlerImpl();

    public GracefulLeaveBootstrapServerResponseModel(int value) {
        this.status = value;
    }

    @Override
    public void handle() {
        Set<Node> neighbourNodeList = NeighbourTableImpl.getInstance().getNeighbourNodeList();
        ApplicationConstants.isRegisterd = false;
        try {
            handler.notifyNeighbourLeave(neighbourNodeList);
        } catch (IOException e) {

        }
        Set<Node> peerTable = PeerTableImpl.getInstance().getPeerNodeList();
        ConcurrentHashMap<String, ConcurrentLinkedQueue<Node>> statTable = StatTableImpl.getStatTable();

        peerTable.forEach(node -> peerTable.remove(node));
        NeighbourTableImpl.getInstance().getNeighbourNodeList().forEach(node -> NeighbourTableImpl.getInstance().remove(node));
        statTable.clear();

        GUIController guiController = GUIController.getInstance();
        guiController.populatePeerTable(peerTable);
        guiController.populateStatTable(statTable);

        if (this.status == 0) {
            System.out.println("Successfully unregistered");
            guiController.handleUnRegistration();
            guiController.displayMessage("Successfully Unregistered from Bootstrap server");

        } else {
            System.out.println("Error while unregistering");
            guiController.displayMessage("Error while unregistering");
        }

    }
}
