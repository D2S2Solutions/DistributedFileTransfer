package com.d2s2.Handler;

import com.d2s2.models.*;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public interface Handler {
    void handleResponse(String message);

    void registerInBS(String bsServerIp) throws IOException;

    void searchFile(String file);

    void notifyNeighbours(String ip, int port) throws IOException;

    void sendSearchRequest(SearchRequestModel model, ConcurrentLinkedQueue<Node> concurrentLinkedQueue) throws IOException;

    void sendLocalSearchToSource(SearchResponseModel searchResponseModel, List<String> list) throws IOException;

    void sendHeartBeatSignal();

    void gracefulLeaveRequest();

    void sendLeaveOkToSource(GracefulLeaveResponseModel gracefulLeaveResponseModel) throws IOException;
}
