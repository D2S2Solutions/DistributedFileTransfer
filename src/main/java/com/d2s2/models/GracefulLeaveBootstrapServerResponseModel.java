package com.d2s2.models;

import com.d2s2.ui.GUIController;

public class GracefulLeaveBootstrapServerResponseModel extends AbstractRequestResponseModel {
    private int status;

    public GracefulLeaveBootstrapServerResponseModel(int value) {
        this.status = value;
    }

    @Override
    public void handle() {
        GUIController guiController = GUIController.getInstance();
        if (this.status == 0) {
            System.out.println("Successfully unregistered");
            guiController.displayMessage("Successfully Unregistered from Bootstrap server");
        } else {
            System.out.println("Error while unregistering");
            guiController.displayMessage("Error while unregistering");
        }
    }
}
