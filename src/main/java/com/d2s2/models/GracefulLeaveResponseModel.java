package com.d2s2.models;

import com.d2s2.ui.GUIController;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Heshan Sandamal on 10/24/2017.
 */
public class GracefulLeaveResponseModel  extends UnicastRemoteObject implements AbstractRequestResponseModel {

    private int status;

    public GracefulLeaveResponseModel(int status)throws RemoteException {
        this.status = status;
    }

    @Override
    public void handle() {
        GUIController guiController = GUIController.getInstance();
        if (this.status == 0) {
            System.out.println("Successfully Left");
        } else {
            System.out.println("Error while leaving");
        }
    }
}
