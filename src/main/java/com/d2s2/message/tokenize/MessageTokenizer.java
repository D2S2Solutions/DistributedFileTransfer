package com.d2s2.message.tokenize;

import com.d2s2.models.AbstractRequestResponseModel;

/**
 * Created by Heshan Sandamal on 10/6/2017.
 */
public interface MessageTokenizer {

    AbstractRequestResponseModel tokenizeMessage(String message);

}
