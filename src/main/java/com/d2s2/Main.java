package com.d2s2;

import com.d2s2.message.build.MessageBuilderImpl;
import com.d2s2.socket.UDPConnectorImpl;
import me.tongfei.progressbar.ProgressBar;

import java.io.IOException;
import java.net.DatagramSocket;

/**
 * Created by Heshan Sandamal on 10/6/2017.
 */
public class Main {

    public static void main(String[] args) {
        try {
            ProgressBar pb = new ProgressBar("Registering in BS server||", 100);
            pb.start();
            pb.stepTo(35);

            DatagramSocket socket = new DatagramSocket();
            UDPConnectorImpl udpConnector = new UDPConnectorImpl();
            String registerMessage = new MessageBuilderImpl.RegisterRequestMessageBuilder()
                    .setIp("129.82.123.45")
                    .setPort(5001)
                    .setUserName("sineth")
                    .build();
            udpConnector.send(registerMessage, socket);
            udpConnector.receive(socket);
            pb.stepTo(100);
            pb.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
