package com.d2s2;

import com.d2s2.Handler.Handler;
import com.d2s2.Handler.HandlerImpl;
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
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

            UdpConnector udpConnector=new UDPConnectorImpl();

            Handler handler=new HandlerImpl();
            handler.registerInBS();


            while (true) {
                System.out.println("WAITING");
                String stringFuture = udpConnector.receive();
                int i = 0;
                System.out.println(stringFuture);
//                performGracefulDeparture(udpConnector);
            }




        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public static void performGracefulDeparture(UdpConnector udpConnector) throws IOException {
//        GracefulLeaveRequestModel gracefulLeaveRequest = new MessageBuilderImpl.GracefulLeaveRequestMessageBuilder()
//                .setIp("129.82.123.45")
//                .setPort(5002)
//                .setUserName("--")
//                .build();
//        Set<Node> peerNodeList = PeerTableImpl.getInstance().getPeerNodeList();
//        peerNodeList.forEach(node -> {
//            try {
//                udpConnector.send(gracefulLeaveRequest, InetAddress.getByAddress(node.getNodeIp().getBytes()), node.getPort());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
//
//        //Say goodbye to BS server
//
//        UnregistrationRequestModel unregistrationRequest = new MessageBuilderImpl.UnregisterRequestMessageBuilder()
//                .setIp("129.82.123.45")
//                .setPort(5002)
//                .setUserName("--")
//                .build();
//        udpConnector.send(unregistrationRequest, InetAddress.getLocalHost(), 55555);
//
//        ((UDPConnectorImpl) udpConnector).killExecutorService();
//        System.exit(0);
//
//
//    }
}
