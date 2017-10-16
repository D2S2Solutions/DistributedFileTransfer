package com.d2s2.models;

public class Node {
    private String nodeIp;
    private String port;
    private String id;


    public Node(String nodeIp, String port) {
        this.nodeIp = nodeIp;
        this.port = port;
        this.id = nodeIp + " " + port; // Todo Find a proper Id schema
    }

    public String getNodeIp() {
        return nodeIp;
    }

    public void setNodeIp(String nodeIp) {
        this.nodeIp = nodeIp;
    }

    public int getPort() {
        return Integer.parseInt(port);
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getId() {
        return id;
    }
}
