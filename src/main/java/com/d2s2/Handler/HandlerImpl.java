package com.d2s2.Handler;

import com.d2s2.constants.ApplicationConstants;
import com.d2s2.message.build.MessageBuilder;
import com.d2s2.message.build.MessageBuilderImpl;
import com.d2s2.message.tokenize.MessageTokenizer;
import com.d2s2.message.tokenize.MessageTokenizerImpl;
import com.d2s2.models.*;
import com.d2s2.overlay.route.NeighbourTableImpl;
import com.d2s2.overlay.route.PeerTableImpl;
import com.d2s2.socket.UDPConnectorImpl;
import com.d2s2.socket.UdpConnector;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.*;
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
    public void sendHeartBeatSignal() {
        Set<Node> peerNodes = PeerTableImpl.getInstance().getPeerNodeList();

        if(!peerNodes.isEmpty()){
            HeartBeatSignalModel heartBeatSignalModel = new HeartBeatSignalModel(ApplicationConstants.IP, ApplicationConstants.PORT, ApplicationConstants.USER_NAME);
            for (Node peer : peerNodes) {
                String heartBeatMessage = messageBuilder.buildHeartBeatSignalMessage(heartBeatSignalModel);
                try {
                    System.out.println("Sending HBEAT by"+ApplicationConstants.IP +" "+ String.valueOf(ApplicationConstants.PORT)+" " + ApplicationConstants.USER_NAME);
                    udpConnector.send(heartBeatMessage, InetAddress.getByName(peer.getNodeIp()), peer.getPort());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void gracefulLeaveRequest() {
        NeighbourTableImpl neighbourTable = NeighbourTableImpl.getInstance();
        Set<Node> neighbourNodeList=neighbourTable.getNeighbourNodeList();

        GracefulLeaveRequestModel gracefulLeaveRequestModel = new GracefulLeaveRequestModel(ApplicationConstants.IP,ApplicationConstants.PORT,"username");
        String message = gracefulLeaveRequestModel.toString();
        for (Node node : neighbourNodeList) {

            try {
                udpConnector.send(message, null, gracefulLeaveRequestModel.getPort());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void searchFile(String file) {
        SearchRequestModel searchRequestModel = new SearchRequestModel(ApplicationConstants.IP, ApplicationConstants.PORT, file, ApplicationConstants.HOPS);
        searchRequestModel.handle();
        //forward to two picked nodes
    }

    @Override
    public void sendSearchRequest(SearchRequestModel model, ConcurrentLinkedQueue<Node> statTablePeers) throws IOException {
        String searchRequestMessage = messageBuilder.buildSearchRequestMessage(model);
        Iterator<Node> iterator = statTablePeers.iterator();
        while (iterator.hasNext()) {
            Node next = iterator.next();
            if (!isRequestingNode(model, next)) {
                udpConnector.send(searchRequestMessage, null, next.getPort());
            }
        }

        final Set<Node> peerNodeList = PeerTableImpl.getInstance().getPeerNodeList();

        final ArrayList<Node> peerNodeListToSend = new ArrayList<>();

        peerNodeList.forEach((node) -> {
            if ((!statTablePeers.contains(node)) && (!isRequestingNode(model, node))) {
                peerNodeListToSend.add(node);
            }
        });

        Random random = new Random();
        final int size = peerNodeListToSend.size();
        if (size > 0) {
            final int item1 = random.nextInt(size);
            final int item2 = random.nextInt(size);
            udpConnector.send(searchRequestMessage, null, peerNodeListToSend.get(item1).getPort());
            udpConnector.send(searchRequestMessage, null, peerNodeListToSend.get(item2).getPort());
        }
    }

    @Override
    public void sendLocalSearchToSource(SearchResponseModel searchResponseModel, List<String> list) throws IOException {
        String searchResponseToSourceMessage = messageBuilder.buildSearchResponseToSourceMessage(searchResponseModel);
        System.out.println(" LOCAL SEARCH RESPONSE " + searchResponseToSourceMessage);
        udpConnector.send(searchResponseToSourceMessage, null, searchResponseModel.getPort());
    }

    //check whether the stat table entry equals to the node which request the file
    private boolean isRequestingNode(SearchRequestModel searchRequestModel, Node node) {
        return searchRequestModel.getFileName().equals(node.getNodeIp()) && searchRequestModel.getPort() == node.getPort();
    }

    public void notifyNeighbours(String ip, int port) throws IOException{
        NotifyNeighbourRequestModel notifyNeighbourRequestModel = new NotifyNeighbourRequestModel(ApplicationConstants.IP,ApplicationConstants.PORT);
        String message = messageBuilder.buildNeighbourJoinMessage(notifyNeighbourRequestModel);
        //System.out.println(message);
        udpConnector.send(message, null, port);

    }




}
