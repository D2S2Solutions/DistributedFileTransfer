package com.d2s2;

import com.d2s2.message.build.MessageBuilderImpl;
import com.d2s2.models.RegistrationRequestModel;
import com.d2s2.socket.UDPConnectorImpl;
import me.tongfei.progressbar.ProgressBar;

import java.io.IOException;
import java.net.DatagramSocket;
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
            udpConnector.send(registerMessage);
            while (true) {
                System.out.println("WAITING");
                Future<String> stringFuture = udpConnector.receive();
                int i = 0;
                while (!stringFuture.isDone()) System.out.println("Processing" + i++);

                try {
                    System.out.println(stringFuture.get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
