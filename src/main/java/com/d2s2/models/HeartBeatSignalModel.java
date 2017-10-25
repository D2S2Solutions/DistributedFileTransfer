package com.d2s2.models;

/**
 * Created by Tharindu Diluksha on 10/25/2017.
 */
public class HeartBeatSignalModel extends AbstractRequestModel {
    String userName;

    public HeartBeatSignalModel(String ip, int port, String userName) {
        super(ip, port);
        this.userName = userName;
    }

    @Override
    public String toString() {
        int length = ip.length() + String.valueOf(port).length() + userName.length() + 4;
        final String requestFinalLength = String.format("%04d", length);
        return requestFinalLength + " HBEAT " + ip + " " + port + " " + userName;
    }

    @Override
    public void handle() {

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
