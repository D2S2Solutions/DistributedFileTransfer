package com.d2s2.models;

/**
 * Created by Tharindu Diluksha on 10/25/2017.
 */
public class HeartBeatSignalModel extends AbstractRequestResponseModel {
    String ip;
    int port;
    String userName;

    public HeartBeatSignalModel(String ip, int port, String userName) {
        this.userName = userName;
        this.port = port;
        this.ip =ip;
    }


    @Override
    public void handle() {
        //todo handle when response received
        System.out.println("Heart Beat received from "+getIp()+" "+ String.valueOf(getPort()));
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public String getUserName() {
        return userName;
    }
}
