package com.d2s2.socket;

import com.d2s2.models.RequestModel;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.Future;

/**
 * Created by Heshan Sandamal on 10/6/2017.
 */
public interface UdpConnector {

    void send(RequestModel message) throws IOException;

    void send(RequestModel message, InetAddress receiverAddress, int port) throws IOException;

    Future<String> receive() throws IOException;

}
