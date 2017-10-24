package com.d2s2.models;

public class RegistrationRequestModel extends AbstractRequestModel {

    String userName;

    public RegistrationRequestModel(String ip, int port, String userName) {
        super(ip, port);
    }

    @Override
    public String toString() {
        int length = ip.length() + String.valueOf(port).length() + userName.length() + 4;
        final String requestFinalLength = String.format("%04d", length);
        return requestFinalLength + " REG " + ip + " " + port + " " + userName;
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
