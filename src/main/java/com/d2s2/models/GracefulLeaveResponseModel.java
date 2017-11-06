package com.d2s2.models;

import com.d2s2.ui.GUIController;

/**
 * Created by Heshan Sandamal on 10/24/2017.
 */
public class GracefulLeaveResponseModel extends AbstractRequestResponseModel {

    private int status;

    public GracefulLeaveResponseModel(int status) {
        this.status = status;
    }

    @Override
    public void handle() {
        GUIController guiController = GUIController.getInstance();
        if (this.status == 0) {
            System.out.println("Successfully Left");
            guiController.displayMessage("Successfully left");
        } else {
            System.out.println("Error while leaving");
            guiController.displayMessage("Error while leaving");
        }
    }
}
