package com.d2s2;

import com.d2s2.socket.UDPConnectorImpl;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by Heshan Sandamal on 10/6/2017.
 */
public class Main {

    public static void main(String[] args) throws SocketException {
        DatagramSocket socket = new DatagramSocket();
        UDPConnectorImpl udpConnector = new UDPConnectorImpl();
        try {
            udpConnector.send("0036 REG 129.82.123.45 5001 1234abcd",socket);
            udpConnector.receive(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
