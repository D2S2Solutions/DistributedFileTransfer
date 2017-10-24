package com.d2s2.message.build;

import com.d2s2.message.MessageConstants;
import com.d2s2.models.GracefulLeaveRequestModel;
import com.d2s2.models.RegistrationRequestModel;
import com.d2s2.models.SearchRequestModel;
import com.d2s2.models.UnregistrationRequestModel;

/**
 * Created by Heshan Sandamal on 10/6/2017.
 */
public class MessageBuilderImpl implements MessageBuilder {

    private static final String IP_ADDRESS = "";
    private static final String PORT = "";


    @Override
    public String buildRegisterRequestMessage(RegistrationRequestModel model) {
        int length = MessageConstants.REG_MESSAGE.length() + model.getIp().length() + String.valueOf(model.getPort()).length() + model.getUserName().length() + 4 + 4;
        final String requestFinalLength = String.format("%04d", length);
        return requestFinalLength + " "+ MessageConstants.REG_MESSAGE +" " + model.getIp() + " " + model.getPort() + " " + model.getUserName();
    }

    @Override
    public String buildUnregisterRequestMessage( ) {
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
    public String buildSearchRequestMessage(SearchRequestModel model) {
        int length = MessageConstants.SER_MESSAGE.length()+ model.getIp().length() + String.valueOf(model.getPort()).length() + model.getFileName().length() + String.valueOf(model.getHops()).length()+ 5 +4;
        final String requestFinalLength = String.format("%04d", length);
        return requestFinalLength + " "+ MessageConstants.SER_MESSAGE +" " + model.getIp() + " " + model.getPort() + " " + model.getFileName();
    }

}
