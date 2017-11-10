package com.d2s2;

import com.d2s2.Handler.Handler;
import com.d2s2.Handler.HandlerImpl;
import com.d2s2.constants.ApplicationConstants;
import com.d2s2.files.FileHandler;
import com.d2s2.files.FileHandlerImpl;
import com.d2s2.heartbeater.HeartBeaterImpl;
import com.d2s2.socket.UDPConnectorImpl;
import com.d2s2.socket.UdpConnector;
import com.d2s2.ui.FileSearchInterface;
import com.d2s2.ui.GUIController;
import me.tongfei.progressbar.ProgressBar;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static com.d2s2.constants.ApplicationConstants.*;

/**
 * Created by Heshan Sandamal on 10/6/2017.
 */
public class Main {


    public static void main(String[] args) throws ExecutionException, InterruptedException, UnknownHostException, SocketException {
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

        /* For heart beating sending*/
        Runnable runnableHeartBeatSender = () -> {
            Handler handler = new HandlerImpl();
            Timer timer = new Timer();

            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    try {
                        System.out.println("Sending Hbeat");
                        if (IsOkTosendHeartBeat) {
                            handler.sendHeartBeatSignal();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, HEART_BEAT_SEND_THRESHOLD * 1000, HEART_BEAT_SEND_THRESHOLD * 1000);
        };
        Thread heartBeatSenderThread = new Thread(runnableHeartBeatSender);
        heartBeatSenderThread.start();

        /* For heart beating handling*/
        Runnable runnableHeartBeatHandler = () -> {
            HeartBeaterImpl heartBeater = HeartBeaterImpl.getInstance();
            Timer timer = new Timer();

            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    try {
                        System.out.println("Handling Hbeat");
                        heartBeater.handleBeat();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, HEART_BEAT_RECEIVE_THRESHOLD * 1000, HEART_BEAT_RECEIVE_THRESHOLD * 1000);
        };
        Thread heartBeatHandlerThread = new Thread(runnableHeartBeatHandler);
        heartBeatHandlerThread.start();
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
        ArrayList<Integer> randomList = new ArrayList<>();
        int length = fullLocalFileArray.length;
        for (int i = 0; i < randomWithRange(3, 5); i++) {
            int random = randomWithRange(0, length - 1);
            boolean contains = randomList.contains(random);
            if (contains) {
                --i;
                continue;
            } else {
                randomList.add(random);
            }
            String s = fullLocalFileArray[random];
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

}
