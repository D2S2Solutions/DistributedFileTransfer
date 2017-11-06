package com.d2s2.socket;

import com.d2s2.Handler.Handler;
import com.d2s2.Handler.HandlerImpl;
import com.d2s2.constants.ApplicationConstants;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Heshan Sandamal on 10/6/2017.
 */
public class UDPConnectorImpl implements UdpConnector {

    private static DatagramSocket socket;
    private static Handler handler;

    static {
        try {
            socket = new DatagramSocket(ApplicationConstants.PORT);
            DatagramSocket socket1 = new DatagramSocket();
            socket1.connect(InetAddress.getByName("10.10.29.237"),ApplicationConstants.PORT);
            ApplicationConstants.IP = socket1.getLocalAddress().getHostAddress();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        handler = new HandlerImpl();
    }

    private ExecutorService executorService;


//    @Override
//    public void send(AbstractRequestModel message) throws IOException {
//
//    }

    @Override
    public void send(String message, InetAddress receiverAddress, int port) throws IOException {
        byte[] buffer = message.getBytes();
        DatagramPacket packet = new DatagramPacket(
                buffer, buffer.length, receiverAddress, port);
        socket.send(packet);
    }

    @Override
    public Future<String> receive() throws IOException {
        byte[] bufferIncoming = new byte[55000];
        DatagramPacket incomingPacket = new DatagramPacket(bufferIncoming, bufferIncoming.length);
        socket.receive(incomingPacket);
        String incomingMessage = new String(bufferIncoming);
        executorService = Executors.newFixedThreadPool(10);
        return (Future<String>) executorService.submit(() -> handler.handleResponse(incomingMessage));
    }

    public void killExecutorService() {
        executorService.shutdown();
    }
}
