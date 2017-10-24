package com.d2s2.Handler;

import com.d2s2.message.tokenize.MessageTokenizer;
import com.d2s2.message.tokenize.MessageTokenizerImpl;
import com.d2s2.models.AbstractRequestResponseModel;

/**
 * Created by Heshan Sandamal on 10/24/2017.
 */
public class HandlerImpl implements Handler {

    MessageTokenizer messageTokenizer;

    public HandlerImpl() {
        this.messageTokenizer = new MessageTokenizerImpl();
    }

    @Override
    public void Handle(String message) {
        AbstractRequestResponseModel abstractRequestResponseModel = messageTokenizer.tokenizeMessage(message);
        abstractRequestResponseModel.handle();

    }
}
