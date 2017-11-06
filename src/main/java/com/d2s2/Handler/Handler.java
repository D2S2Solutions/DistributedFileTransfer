package com.d2s2.Handler;

import com.d2s2.models.*;

import java.io.IOException;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public interface Handler {
    void handleResponse(String message) throws RemoteException, NotBoundException;

    void registerInBS(String bsServerIp) throws IOException;

    void searchFile(String file) throws RemoteException, NotBoundException;

    void notifyNeighbours(String ip, int port) throws IOException, NotBoundException;

    void sendSearchRequest(SearchRequestModel model, ConcurrentLinkedQueue<Node> concurrentLinkedQueue) throws IOException, NotBoundException;

    void sendLocalSearchToSource(SearchResponseModel searchResponseModel, List<String> list) throws IOException, NotBoundException;

    void sendHeartBeatSignal();

    void gracefulLeaveRequest() throws RemoteException;

    void sendLeaveOkToSource(GracefulLeaveResponseModel node) throws IOException, NotBoundException;
}
