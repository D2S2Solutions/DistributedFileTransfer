package com.d2s2.message.build;

/**
 * Created by Heshan Sandamal on 10/6/2017.
 */
public interface MessageBuilder {

    String buildRegisterRequestMessage();

    String buildUnregisterRequestMessage();

    String buildJoinMessage();

    String buildLeaveMessage();

    String buildSearchMessage(String fileName);

}
