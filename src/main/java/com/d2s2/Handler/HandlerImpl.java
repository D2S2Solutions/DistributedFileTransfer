package com.d2s2.Handler;

import com.d2s2.constants.ApplicationConstants;
import com.d2s2.message.build.MessageBuilder;
import com.d2s2.message.build.MessageBuilderImpl;
import com.d2s2.message.tokenize.MessageTokenizer;
import com.d2s2.message.tokenize.MessageTokenizerImpl;
import com.d2s2.models.*;
import com.d2s2.overlay.route.PeerTableImpl;
import com.d2s2.socket.UDPConnectorImpl;
import com.d2s2.socket.UdpConnector;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Heshan Sandamal on 10/24/2017.
 */
public class HandlerImpl implements Handler {

    MessageTokenizer messageTokenizer;
    MessageBuilder messageBuilder;
    UdpConnector udpConnector;

    public HandlerImpl() {
        this.udpConnector = new UDPConnectorImpl();
        this.messageTokenizer = new MessageTokenizerImpl();
        this.messageBuilder = new MessageBuilderImpl();
    }

    @Override
    public void handleResponse(String message) {
        AbstractRequestResponseModel abstractRequestResponseModel = messageTokenizer.tokenizeMessage(message);
        if (abstractRequestResponseModel != null) {
            abstractRequestResponseModel.handle();
        }
    }

    @Override
    public void registerInBS() throws IOException {
        RegistrationRequestModel registrationRequestModel = new RegistrationRequestModel(ApplicationConstants.IP, ApplicationConstants.PORT, ApplicationConstants.USER_NAME);
        String message = messageBuilder.buildRegisterRequestMessage(registrationRequestModel);
        udpConnector.send(message, null, 55555);
    }

    @Override
    public void searchFile(String file) {
        SearchRequestModel searchRequestModel = new SearchRequestModel(ApplicationConstants.IP, ApplicationConstants.PORT, file, ApplicationConstants.HOPS);
        searchRequestModel.handle();
        //forward to two picked nodes
    }

    @Override
    public void sendSearchRequest(SearchRequestModel model, ConcurrentLinkedQueue<Node> concurrentLinkedQueue) throws IOException {
        String searchRequestMessage = messageBuilder.buildSearchRequestMessage(model);
        Iterator<Node> iterator = concurrentLinkedQueue.iterator();
        while (iterator.hasNext()) {
            Node next = iterator.next();
            udpConnector.send(searchRequestMessage, null, next.getPort());
        }

        final Set<Node> peerNodeList = PeerTableImpl.getInstance().getPeerNodeList();
        Random random = new Random();
        final int size = peerNodeList.size();
        if (size > 0) {
            final int item1 = random.nextInt(size);
            final int item2 = random.nextInt(size);

            int i = 0;
            Iterator<Node> nodeIterator = peerNodeList.iterator();
            while (nodeIterator.hasNext()) {

                if ((item1 == i || item2 == i) && !concurrentLinkedQueue.contains(item1)) {
                    udpConnector.send(searchRequestMessage, null, nodeIterator.next().getPort());
                }
                i++;
            }
        }


    }

    @Override
    public void sendLocalSearchToSource(SearchResponseModel searchResponseModel, List<String> list) throws IOException {
        String searchResponseToSourceMessage = messageBuilder.buildSearchResponseToSourceMessage(searchResponseModel);
        System.out.println(" Local port is " + searchResponseModel.getPort());
        udpConnector.send(searchResponseToSourceMessage, null, searchResponseModel.getPort());
    }


}
