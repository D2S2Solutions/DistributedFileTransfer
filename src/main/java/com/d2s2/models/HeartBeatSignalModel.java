package com.d2s2.models;

import com.d2s2.heartbeater.HeartBeaterImpl;

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
        this.ip = ip;
    }


    @Override
    public void handle() {
        Node beatedNode = new Node(this.ip, this.port);
        HeartBeaterImpl.getInstance().handleBeat(beatedNode);
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
