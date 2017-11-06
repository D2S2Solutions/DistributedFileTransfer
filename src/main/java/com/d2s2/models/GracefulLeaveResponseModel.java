package com.d2s2.models;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Heshan Sandamal on 10/24/2017.
 */
public class GracefulLeaveResponseModel  extends UnicastRemoteObject implements AbstractRequestResponseModel {

    private final String ip;
    private final int port;
    private int status;

    public GracefulLeaveResponseModel(String ip, int port, int status)throws RemoteException {
        this.ip=ip;
        this.port=port;
        this.status = status;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override

    public void handle() {
        if (this.status == 0) {
            System.out.println("Successfully Left");
        } else {
            System.out.println("Error while leaving");
        }
    }
}
