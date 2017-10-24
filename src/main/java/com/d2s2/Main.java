package com.d2s2;

import com.d2s2.Handler.Handler;
import com.d2s2.Handler.HandlerImpl;
import com.d2s2.files.FileHandler;
import com.d2s2.files.FileHandlerImpl;
import com.d2s2.message.build.MessageBuilderImpl;
import com.d2s2.models.GracefulLeaveRequestModel;
import com.d2s2.models.Node;
import com.d2s2.models.UnregistrationRequestModel;
import com.d2s2.overlay.route.PeerTableImpl;
import com.d2s2.socket.UDPConnectorImpl;
import com.d2s2.socket.UdpConnector;
import me.tongfei.progressbar.ProgressBar;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by Heshan Sandamal on 10/6/2017.
 */
public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        try {
            ProgressBar pb = new ProgressBar("Registering in BS server||", 100);
            pb.start();
            pb.stepTo(35);
            pb.stepTo(100);
            pb.stop();
            initLocalFileStorage();
            UdpConnector udpConnector = new UDPConnectorImpl();

            Handler handler = new HandlerImpl();
            handler.registerInBS();


            while (true) {
                System.out.println("WAITING");
                Future<String> stringFuture = udpConnector.receive();
                int i = 0;
                while (!stringFuture.isDone()) {
//                    System.out.println(++i);
                }
                search("American");
                performGracefulDeparture(udpConnector);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void search(String query) {
        FileHandler fileHandler = FileHandlerImpl.getInstance();
        List<String> documents = fileHandler.searchLocalFileList(query);
        for (String s :
                documents) {
            System.out.println(s);
        }
    }

    private static void initLocalFileStorage() {
        FileHandler fileHandler = FileHandlerImpl.getInstance();
        String[] fullLocalFileArray = new String[]{
                "Adventures of Tintin",
                "Jack and Jill",
                "Glee",
                "The Vampire Diarie",
                "King Arthur",
                "Windows XP",
                "Harry Potter",
                "Kung Fu Panda",
                "Lady Gaga",
                "Twilight",
                "Windows 8",
                "Mission Impossible",
                "Turn Up The Music",
                "Super Mario",
                "American Pickers",
                "Microsoft Office 2010",
                "Happy Feet",
                "Modern Family",
                "American Idol",
                "Hacking for Dummies"
        };
        Arrays.stream(fullLocalFileArray).filter(s -> (s.length() > randomWithRange(5,20) ))
                .forEach(fileHandler::initializeFileStorage);

    }

    private static int randomWithRange(int min, int max)
    {
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }

    public static void performGracefulDeparture(UdpConnector udpConnector) throws IOException {
        GracefulLeaveRequestModel gracefulLeaveRequest = new MessageBuilderImpl.GracefulLeaveRequestMessageBuilder()
                .setIp("129.82.123.45")
                .setPort(5002)
                .setUserName("--")
                .build();
        Set<Node> peerNodeList = PeerTableImpl.getInstance().getPeerNodeList();
        peerNodeList.forEach(node -> {
            try {
                udpConnector.send(gracefulLeaveRequest.toString(), InetAddress.getByAddress(node.getNodeIp().getBytes()), node.getPort());
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
        udpConnector.send(unregistrationRequest.toString(), InetAddress.getLocalHost(), 55555);

        ((UDPConnectorImpl) udpConnector).killExecutorService();
        System.exit(0);


    }
}
