package com.d2s2.models;

import java.rmi.RemoteException;

public class GracefulLeaveBootstrapServerRequestModel extends AbstractRequestModel {

    String userName;

    public GracefulLeaveBootstrapServerRequestModel(String ip, int port, String userName) throws RemoteException {
        super(ip, port);
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public void handle() {

    }
}
