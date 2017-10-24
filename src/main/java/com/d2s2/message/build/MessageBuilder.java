package com.d2s2.message.build;

import com.d2s2.models.RegistrationRequestModel;

/**
 * Created by Heshan Sandamal on 10/6/2017.
 */
public interface MessageBuilder {

    String buildRegisterRequestMessage(RegistrationRequestModel model);

    String buildUnregisterRequestMessage();

    String buildJoinMessage();

    String buildLeaveMessage();

    String buildSearchMessage(String fileName);

}
