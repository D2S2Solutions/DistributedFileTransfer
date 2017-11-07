package com.d2s2;

import com.d2s2.Handler.Handler;
import com.d2s2.Handler.HandlerImpl;
import com.d2s2.constants.ApplicationConstants;
import com.d2s2.files.FileHandler;
import com.d2s2.files.FileHandlerImpl;
import com.d2s2.socket.UDPConnectorImpl;
import com.d2s2.socket.UdpConnector;
import com.d2s2.ui.FileSearchInterface;
import com.d2s2.ui.GUIController;
import me.tongfei.progressbar.ProgressBar;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static com.d2s2.constants.ApplicationConstants.randomWithRange;

/**
 * Created by Heshan Sandamal on 10/6/2017.
 */
public class Main {


    public static void main(String[] args) throws ExecutionException, InterruptedException, UnknownHostException, SocketException {
        Enumeration enumeration = NetworkInterface.getNetworkInterfaces();
        while(enumeration.hasMoreElements())
        {
            NetworkInterface n = (NetworkInterface) enumeration.nextElement();
            Enumeration ee = n.getInetAddresses();
            while (ee.hasMoreElements())
            {
                InetAddress i = (InetAddress) ee.nextElement();
                if (i.toString().startsWith("10.10")){
                    ApplicationConstants.IP = i.getHostAddress();
                }
                System.out.println(i.getHostAddress());
            }
        }
        ProgressBar pb = new ProgressBar("Registering in BS server||", 100);
        pb.start();
        pb.stepTo(35);
        pb.stepTo(100);
        pb.stop();
        System.out.println("This node operates in " + ApplicationConstants.IP + " and the port is " + ApplicationConstants.PORT);
        initLocalFileStorage();
        UdpConnector udpConnector = new UDPConnectorImpl();
//
//        Handler handler = new HandlerImpl();
//        try {
//            handler.registerInBS(BsServerIp);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        new Thread(() -> {
            while (true) {
                System.out.println(">>>>>>>>>> WAITING FOR REQUEST <<<<<<<<<<<<\n");
                Future<String> stringFuture = null;
                try {
                    stringFuture = udpConnector.receive();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                while (!stringFuture.isDone()) {
//                    System.out.println(++i);
                }
//                    handler.sendHeartBeatSignal();

            }
        }
        ).start();
        Thread.sleep(1000); // Wait until the system acknowledges the node
//        handler.searchFile("American");
//        handler.gracefulLeaveRequest();

        /* For heart beating*/
        Handler handler = new HandlerImpl();
        Runnable runnable = () -> {
            try {
                String name = Thread.currentThread().getName();
                System.out.println("Foo " + name);
                TimeUnit.SECONDS.sleep(10);
                //System.out.println("Bar " + name);
                handler.sendHeartBeatSignal();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

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
        System.out.println("This node has :");
        ArrayList<String> fileList = new ArrayList<>();

        int length = fullLocalFileArray.length;
        for (int i = 0; i < randomWithRange(3, 5); i++) {
            String s = fullLocalFileArray[randomWithRange(0, length - 1)];
            System.out.println("\t" + s);
            String saltedName = s.replace(" ", "@");
            fileHandler.initializeFileStorage(saltedName);
            fileList.add(saltedName);
        }

        GUIController guiController = GUIController.getInstance();
        FileSearchInterface fileSearchInterface = new FileSearchInterface(guiController, fileList);
        guiController.setUIinstance(fileSearchInterface);
        fileSearchInterface.setVisible(true);

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
//                udpConnector.send(gracefulLeaveRequest.toString(), InetAddress.getByAddress(node.getNodeIp().getBytes()), node.getPort());
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
//        udpConnector.send(unregistrationRequest.toString(), InetAddress.getLocalHost(), 55555);
//
//        ((UDPConnectorImpl) udpConnector).killExecutorService();
//        System.exit(0);
//
//
//    }
}
