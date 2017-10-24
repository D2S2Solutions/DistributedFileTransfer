package com.d2s2.message.build;

import com.d2s2.models.GracefulLeaveRequestModel;
import com.d2s2.models.RegistrationRequestModel;
import com.d2s2.models.UnregistrationRequestModel;

/**
 * Created by Heshan Sandamal on 10/6/2017.
 */
public class MessageBuilderImpl implements MessageBuilder {

    private static final String IP_ADDRESS = "";
    private static final String PORT = "";


    @Override
    public String buildRegisterRequestMessage(RegistrationRequestModel model) {
        int length = model.getIp().length() + model.getPort().length() + model.getUserName().length() + 4 +4;
        final String requestFinalLength = String.format("%04d", length);
        return requestFinalLength + " REG " + model.getIp() + " " + model.getPort() + " " + model.getUserName();
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

        public RegistrationRequestModel build() { // todo -- Change the return type from String to an object of type Request
            return new RegistrationRequestModel(ip, port, userName);
        }
    }

    public static class GracefulLeaveRequestMessageBuilder {
        private String ip;
        private String port;
        private String userName;

        public GracefulLeaveRequestMessageBuilder setIp(String ip) {
            this.ip = ip;
            return this;
        }

        public GracefulLeaveRequestMessageBuilder setPort(int port) {
            this.port = String.valueOf(port);
            return this;
        }

        public GracefulLeaveRequestMessageBuilder setUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public GracefulLeaveRequestModel build() { // todo -- Change the return type from String to an object of type Request
            return new GracefulLeaveRequestModel(ip, port, userName);
        }
    }

    public static class UnregisterRequestMessageBuilder {
        private String ip;
        private String port;
        private String userName;

        public UnregisterRequestMessageBuilder setIp(String ip) {
            this.ip = ip;
            return this;
        }

        public UnregisterRequestMessageBuilder setPort(int port) {
            this.port = String.valueOf(port);
            return this;
        }

        public UnregisterRequestMessageBuilder setUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public UnregistrationRequestModel build() { // todo -- Change the return type from String to an object of type Request
            return new UnregistrationRequestModel(ip, port, userName);
        }
    }
}
