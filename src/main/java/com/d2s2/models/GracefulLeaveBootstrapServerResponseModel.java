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

import static com.d2s2.constants.ApplicationConstants.IsOkTosendHeartBeat;

public class GracefulLeaveBootstrapServerResponseModel extends AbstractRequestResponseModel {
    private int status;
    private static Handler handler = new HandlerImpl();

    public GracefulLeaveBootstrapServerResponseModel(int value) {
        this.status = value;
    }

    @Override
    public void handle() {
        Set<Node> neighbourNodeList = NeighbourTableImpl.getInstance().getNeighbourNodeList();
        IsOkTosendHeartBeat = false;
        neighbourNodeList.forEach(node -> {
            GracefulLeaveRequestModel gracefulLeaveRequestModel = new GracefulLeaveRequestModel(ApplicationConstants.IP, ApplicationConstants.PORT);
            String neighbourLeaveMessage = new MessageBuilderImpl().buildLeaveMessage(gracefulLeaveRequestModel);
            try {
                handler.notifyNeighbourLeave(neighbourLeaveMessage,node);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        PeerTableImpl.getInstance().getPeerNodeList().forEach(node -> PeerTableImpl.getInstance().remove(node));
        NeighbourTableImpl.getInstance().getNeighbourNodeList().forEach(node -> NeighbourTableImpl.getInstance().remove(node));
        StatTableImpl.getStatTable().clear();

        GUIController guiController = GUIController.getInstance();
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
