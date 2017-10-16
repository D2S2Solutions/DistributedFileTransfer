package com.d2s2.models;

public class RegistrationRequestModel extends Model {
    private String ip;
    private String port;
    private String userName;

    public RegistrationRequestModel(String ip, String port, String userName) {
        this.ip = ip;
        this.port = port;
        this.userName = userName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        int length = ip.length() + port.length() + userName.length() + 4;
        final String requestFinalLength = String.format("%04d", length);
        return requestFinalLength + " REG " + ip + " " + port + " " + userName;
    }
}
