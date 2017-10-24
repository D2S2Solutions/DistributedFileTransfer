package com.d2s2.Handler;

import com.d2s2.models.Node;
import com.d2s2.models.SearchRequestModel;

import java.io.IOException;
import java.util.ArrayList;

public interface Handler {
    public void handleResponse(String message);

    public void registerInBS() throws IOException;

    public void searchFile(String file);

    public void sendSearchRequest(SearchRequestModel model, ArrayList<Node> searchRequestList) throws IOException;
}
