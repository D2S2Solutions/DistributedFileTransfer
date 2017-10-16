package com.d2s2.socket;

import com.d2s2.models.Model;

import java.io.IOException;
import java.util.concurrent.Future;

/**
 * Created by Heshan Sandamal on 10/6/2017.
 */
public interface UdpConnector {

    void send(Model message) throws IOException;

    Future<String> receive() throws IOException;

}
