package com.d2s2.Handler;

import com.d2s2.models.HeartBeatSignalModel;
import com.d2s2.models.Node;
import com.d2s2.models.SearchRequestModel;
import com.d2s2.models.SearchResponseModel;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public interface Handler {
    public void handleResponse(String message);

    public void registerInBS() throws IOException;

    public void searchFile(String file);

    public void sendSearchRequest(SearchRequestModel model, ConcurrentLinkedQueue<Node> concurrentLinkedQueue) throws IOException;

    public void sendLocalSearchToSource(SearchResponseModel searchResponseModel, List<String> list) throws IOException;

    public void sendHeartBeatSignal();
}
