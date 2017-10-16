package com.d2s2.socket;

import com.d2s2.message.tokenize.MessageTokenizerImpl;
import com.d2s2.models.RequestModel;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Heshan Sandamal on 10/6/2017.
 */
public class UDPConnectorImpl implements UdpConnector {

    private static DatagramSocket socket;
    private ExecutorService executorService;

    static {
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void send(RequestModel message) throws IOException {

    }

    @Override
    public void send(RequestModel message, InetAddress receiverAddress, int port) throws IOException {
        byte[] buffer = message.toString().getBytes();
        receiverAddress = InetAddress.getLocalHost();
        DatagramPacket packet = new DatagramPacket(
                buffer, buffer.length, receiverAddress, port);
        socket.send(packet);
    }

    /*
    Each request that comes to the node will be handed off to a separate async thread
    via the executor service.
     */
    @Override
    public Future<String> receive() throws IOException {
        byte[] bufferIncoming = new byte[100];
        DatagramPacket incomingPacket = new DatagramPacket(bufferIncoming, bufferIncoming.length);
        socket.receive(incomingPacket);
        String incomingMessage = new String(bufferIncoming);
        executorService = Executors.newFixedThreadPool(10);
//        executorService.execute(() -> {
//            MessageTokenizerImpl tokenizer = new MessageTokenizerImpl();
//            tokenizer.tokenizeMessage(incomingMessage);
//        });
        return executorService.submit(() -> {
            MessageTokenizerImpl tokenizer = new MessageTokenizerImpl();
            tokenizer.tokenizeMessage(incomingMessage);
            return "DONE";
        });
//        executorService.shutdown(); // To keep the client alive comment out this line when necessary
    }
    public void killExecutorService(){
        executorService.shutdown();
    }
}
