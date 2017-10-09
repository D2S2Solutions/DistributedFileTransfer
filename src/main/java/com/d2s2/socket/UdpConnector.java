package com.d2s2.socket;

import java.io.IOException;
import java.net.DatagramSocket;

/**
 * Created by Heshan Sandamal on 10/6/2017.
 */
public interface UdpConnector {

    void send(String message, DatagramSocket datagramSocket) throws IOException;
    void receive(DatagramSocket socket) throws IOException;

}
