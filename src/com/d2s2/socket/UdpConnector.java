package com.d2s2.socket;

/**
 * Created by Heshan Sandamal on 10/6/2017.
 */
public interface UdpConnector {

    void send(String message);
    void receive();

}
