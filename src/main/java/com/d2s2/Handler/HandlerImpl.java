package com.d2s2.Handler;

import com.d2s2.message.build.MessageBuilder;
import com.d2s2.message.build.MessageBuilderImpl;
import com.d2s2.message.tokenize.MessageTokenizer;
import com.d2s2.message.tokenize.MessageTokenizerImpl;
import com.d2s2.models.AbstractRequestResponseModel;
import com.d2s2.models.RegistrationRequestModel;
import com.d2s2.socket.UDPConnectorImpl;
import com.d2s2.socket.UdpConnector;

import java.io.IOException;

/**
 * Created by Heshan Sandamal on 10/24/2017.
 */
public class HandlerImpl implements Handler {

    MessageTokenizer messageTokenizer;
    MessageBuilder messageBuilder;
    UdpConnector udpConnector;

    public HandlerImpl() {
        this.udpConnector=new UDPConnectorImpl();
        this.messageTokenizer = new MessageTokenizerImpl();
        this.messageBuilder=new MessageBuilderImpl();
    }

    @Override
    public void handleResponse(String message) {
        AbstractRequestResponseModel abstractRequestResponseModel = messageTokenizer.tokenizeMessage(message);
        abstractRequestResponseModel.handle();
    }

    @Override
    public void registerInBS() throws IOException {
        RegistrationRequestModel registrationRequestModel = new RegistrationRequestModel("129.82.123.45","5002","sineth");
        String message = messageBuilder.buildRegisterRequestMessage(registrationRequestModel);
        udpConnector.send(message, null, 55555);
    }


}
