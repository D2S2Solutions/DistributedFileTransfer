package com.d2s2.Handler;

import java.io.IOException;

public interface Handler {
    public void handleResponse(String message);
    public void registerInBS() throws IOException;
}
