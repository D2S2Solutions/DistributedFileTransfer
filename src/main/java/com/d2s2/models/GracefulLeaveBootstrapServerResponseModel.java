package com.d2s2.models;

import com.d2s2.ui.GUIController;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class GracefulLeaveBootstrapServerResponseModel extends UnicastRemoteObject implements AbstractRequestResponseModel {
    private int status;

    public GracefulLeaveBootstrapServerResponseModel(int value) throws RemoteException{
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
