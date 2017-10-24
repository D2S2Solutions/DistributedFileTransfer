package com.d2s2.models;

public class UnregistrationRequestModel extends AbstractRequestModel {

    String userName;

    public UnregistrationRequestModel(String ip, String port, String userName) {
        super(ip, port);
    }

    @Override
    public String toString() {
        int length = ip.length() + port.length() + userName.length() + 4;
        final String requestFinalLength = String.format("%04d", length);
        return requestFinalLength + " UNREG " + ip + " " + port + " " + userName;
    }

    @Override
    public void handle() {

    }
}
