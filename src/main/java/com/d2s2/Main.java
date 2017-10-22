package com.d2s2;

import com.d2s2.message.build.MessageBuilderImpl;
import com.d2s2.models.GracefulLeaveRequestModel;
import com.d2s2.models.Node;
import com.d2s2.models.RegistrationRequestModel;
import com.d2s2.models.UnregistrationRequestModel;
import com.d2s2.overlay.route.PeerTableImpl;
import com.d2s2.socket.UDPConnectorImpl;
import com.d2s2.socket.UdpConnector;
import me.tongfei.progressbar.ProgressBar;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by Heshan Sandamal on 10/6/2017.
 */
public class Main {

    public static void main(String[] args) {
        try {
            ProgressBar pb = new ProgressBar("Registering in BS server||", 100);
            pb.start();
            pb.stepTo(35);
            pb.stepTo(100);
            pb.stop();


            UDPConnectorImpl udpConnector = new UDPConnectorImpl();
            RegistrationRequestModel registerMessage = new MessageBuilderImpl.RegisterRequestMessageBuilder()
                    .setIp("129.82.123.45")
                    .setPort(5002)
                    .setUserName("sineth")
                    .build();
            udpConnector.send(registerMessage, null, 55555);
            while (true) {
                System.out.println("WAITING");
                Future<String> stringFuture = udpConnector.receive();
                int i = 0;

                try {
                    System.out.println(stringFuture.get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }

                performGracefulDeparture(udpConnector);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void performGracefulDeparture(UdpConnector udpConnector) throws IOException {
        GracefulLeaveRequestModel gracefulLeaveRequest = new MessageBuilderImpl.GracefulLeaveRequestMessageBuilder()
                .setIp("129.82.123.45")
                .setPort(5002)
                .setUserName("--")
                .build();
        ArrayList<Node> peerNodeList = PeerTableImpl.getInstance().getPeerNodeList();
        peerNodeList.forEach(node -> {
            try {
                udpConnector.send(gracefulLeaveRequest, InetAddress.getByAddress(node.getNodeIp().getBytes()), node.getPort());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //Say goodbye to BS server

        UnregistrationRequestModel unregistrationRequest = new MessageBuilderImpl.UnregisterRequestMessageBuilder()
                .setIp("129.82.123.45")
                .setPort(5002)
                .setUserName("--")
                .build();
        udpConnector.send(unregistrationRequest, InetAddress.getLocalHost(), 55555);

        ((UDPConnectorImpl) udpConnector).killExecutorService();
        System.exit(0);


    }
}
