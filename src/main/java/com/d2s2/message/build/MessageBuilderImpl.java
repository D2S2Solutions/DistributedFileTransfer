package com.d2s2.message.build;

import com.d2s2.overlay.request.Request;

import java.util.StringTokenizer;

/**
 * Created by Heshan Sandamal on 10/6/2017.
 */
public class MessageBuilderImpl implements MessageBuilder {

    private static final String IP_ADDRESS = "";
    private static final String PORT = "";


    @Override
    public String buildRegisterRequestMessage() {
        return null;
    }

    @Override
    public String buildUnregisterRequestMessage() {
        return null;
    }

    @Override
    public String buildJoinMessage() {
        return null;
    }

    @Override
    public String buildLeaveMessage() {
        return null;
    }

    @Override
    public String buildSearchMessage(String fileName) {
        return null;
    }

    public static class RegisterRequestMessageBuilder {
        private String ip;
        private String port;
        private String userName;

        public RegisterRequestMessageBuilder setIp(String ip) {
            this.ip = ip;
            return this;
        }

        public RegisterRequestMessageBuilder setPort(int port) {
            this.port = String.valueOf(port);
            return this;
        }

        public RegisterRequestMessageBuilder setUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public String build() { // todo -- Change the return type from String to an object of type Request
            int length = ip.length() + port.length() + userName.length() + 4;
            final String requestFinalLength = String.format("%04d",length);
            return requestFinalLength + " REG " + ip + " " + port + " " +userName;
        }
    }
}
