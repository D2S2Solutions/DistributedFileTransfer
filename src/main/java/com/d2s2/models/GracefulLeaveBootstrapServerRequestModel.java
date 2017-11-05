package com.d2s2.models;

public class GracefulLeaveBootstrapServerRequestModel extends AbstractRequestModel {

    private String userName;

    public GracefulLeaveBootstrapServerRequestModel(String ip, int port, String userName) {
        super(ip, port);
        this.userName = userName;
    }

    @Override
    public String toString() {
        int length = ip.length() + String.valueOf(port).length() + userName.length() + 4 + 5;
        final String requestFinalLength = String.format("%04d", length);
        return requestFinalLength + " UNREG " + ip + " " + port + " " + userName;
    }

    @Override
    public void handle() {
        System.out.println("SUCCESSFULLY UNREG from the bootstrap server");
    }
}