package com.d2s2.models;

import com.d2s2.ui.GUIController;

/**
 * Created by Heshan Sandamal on 10/24/2017.
 */
public class UnregistrationResponseModel extends AbstractRequestResponseModel {

    private int status;

    public UnregistrationResponseModel(int status) {
        this.status = status;
    }

    @Override
    public void handle() {
        GUIController guiController = GUIController.getInstance();
        if (this.status == 0) {
            System.out.println("Successfully unregistered");
            guiController.displayMessage("Successfully registered");
        } else {
            System.out.println("Error while unregistering");
            guiController.displayMessage("Error while unregistering");
        }
    }
}
