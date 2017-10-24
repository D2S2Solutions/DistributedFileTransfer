package com.d2s2.models;

public class RegistrationRequestModel extends AbstractRequestModel {


    public RegistrationRequestModel(String ip, String port, String userName) {
        super(ip, port, userName);
    }

    @Override
    public String toString() {
        int length = ip.length() + port.length() + userName.length() + 4;
        final String requestFinalLength = String.format("%04d", length);
        return requestFinalLength + " REG " + ip + " " + port + " " + userName;
    }

    @Override
    public void handle() {

    }
}
