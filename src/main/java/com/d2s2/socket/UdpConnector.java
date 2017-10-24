package com.d2s2.socket;

import com.d2s2.models.AbstractRequestModel;

import java.io.IOException;
import java.net.InetAddress;

/**
 * Created by Heshan Sandamal on 10/6/2017.
 */
public interface UdpConnector {

    void send(AbstractRequestModel message) throws IOException;

    void send(AbstractRequestModel message, InetAddress receiverAddress, int port) throws IOException;

    String receive() throws IOException;

}
