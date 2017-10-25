package com.d2s2.message.build;

import com.d2s2.models.NotifyNeighbourRequestModel;
import com.d2s2.models.HeartBeatSignalModel;
import com.d2s2.models.RegistrationRequestModel;
import com.d2s2.models.SearchRequestModel;
import com.d2s2.models.SearchResponseModel;

/**
 * Created by Heshan Sandamal on 10/6/2017.
 */
public interface MessageBuilder {

    String buildRegisterRequestMessage(RegistrationRequestModel model);

    String buildUnregisterRequestMessage();

    String buildJoinMessage();

    String buildLeaveMessage();

    String buildSearchRequestMessage(SearchRequestModel model);

    String buildNeighbourJoinMessage(NotifyNeighbourRequestModel model);

    String buildSearchResponseToSourceMessage(SearchResponseModel model);

    String buildHeartBeatSignalMessage(HeartBeatSignalModel model);

}
