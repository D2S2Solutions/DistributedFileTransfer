package com.d2s2.Handler;

import com.d2s2.constants.ApplicationConstants;
import com.d2s2.message.build.MessageBuilder;
import com.d2s2.message.build.MessageBuilderImpl;
import com.d2s2.message.tokenize.MessageTokenizer;
import com.d2s2.message.tokenize.MessageTokenizerImpl;
import com.d2s2.models.*;
import com.d2s2.overlay.route.NeighbourTableImpl;
import com.d2s2.overlay.route.PeerTableImpl;
import com.d2s2.overlay.route.StatTableImpl;
import com.d2s2.rmi.client.ServerConnector;
import com.d2s2.socket.UDPConnectorImpl;
import com.d2s2.socket.UdpConnector;
import com.d2s2.ui.FileSearchInterface;
import com.d2s2.ui.GUIController;


import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Future;


/**
 * Created by Heshan Sandamal on 10/24/2017.
 */
public class HandlerImpl implements Handler {

    private MessageTokenizer messageTokenizer;
    private MessageBuilder messageBuilder;
    private UdpConnector udpConnector;

    public HandlerImpl() {
        this.udpConnector = new UDPConnectorImpl();
        this.messageTokenizer = new MessageTokenizerImpl();
        this.messageBuilder = new MessageBuilderImpl();
    }

    @Override
    public void handleResponse(String message) throws RemoteException, NotBoundException {
        AbstractRequestResponseModel abstractRequestResponseModel = messageTokenizer.tokenizeMessage(message);
        if (abstractRequestResponseModel != null) {
            abstractRequestResponseModel.handle();
        }

    }

    @Override
    public void registerInBS(String bsServerIp) throws IOException {
        RegistrationRequestModel registrationRequestModel = new RegistrationRequestModel(ApplicationConstants.IP, ApplicationConstants.PORT, ApplicationConstants.USER_NAME);
        String message = messageBuilder.buildRegisterRequestMessage(registrationRequestModel);
        udpConnector.send(message, InetAddress.getByName(bsServerIp), 55555);
        ApplicationConstants.BootstrapServerIp = bsServerIp;
    }

    @Override
    public void sendHeartBeatSignal() throws NotBoundException {
        Set<Node> neighbourNodes = NeighbourTableImpl.getInstance().getNeighbourNodeList();
        if (!neighbourNodes.isEmpty()) {

            for (Node neighbour : neighbourNodes) {
                try {
                    System.out.println("Sending HBEAT to " + neighbour.getNodeIp() + " " + neighbour.getPort() + "by" + ApplicationConstants.IP + " " + String.valueOf(ApplicationConstants.PORT) + " " + ApplicationConstants.USER_NAME);
                    final ServerConnector serverConnector = ServerConnector.getServerConnector(neighbour.getNodeIp(), neighbour.getPort());
                    serverConnector.callRemoteHeartbeatSignalHandle(ApplicationConstants.IP, ApplicationConstants.PORT, ApplicationConstants.USER_NAME);
                } catch (IOException e) {
                    //e.printStackTrace();
                    System.out.println("Connection refusing when Heart Beating");
                }
            }
        }
    }

    @Override
    public void gracefulLeaveRequest() throws RemoteException {
        NeighbourTableImpl neighbourTable = NeighbourTableImpl.getInstance();

        //First Unreg from the Bootstrap server
        GracefulLeaveBootstrapServerRequestModel gracefulLeaveBootstrapServerRequestModel = new GracefulLeaveBootstrapServerRequestModel(ApplicationConstants.IP, ApplicationConstants.PORT, ApplicationConstants.USER_NAME);
        String message = messageBuilder.buildUnregisterRequestMessage(gracefulLeaveBootstrapServerRequestModel);
        try {
            udpConnector.send(message, InetAddress.getByName(ApplicationConstants.BootstrapServerIp), ApplicationConstants.BS_SERVER_PORT);
        } catch (IOException e) {
//            e.printStackTrace();
        }

        new Thread(() -> {
            System.out.println(">>>>>>>>>> WAITING FOR Leave Response <<<<<<<<<<<<\n");
            Future<String> stringFuture = null;
            try {
                stringFuture = udpConnector.receive();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ).start();

        //Next, Notify Neighbours of our departur

    }

    @Override
    public void sendLeaveOkToSource(GracefulLeaveResponseModel gracefulLeaveResponseModel) throws IOException, NotBoundException {
        final ServerConnector serverConnector = ServerConnector.getServerConnector(gracefulLeaveResponseModel.getIp(), gracefulLeaveResponseModel.getPort());
        if (serverConnector != null) {
            serverConnector.callRemoteGracefulLeaveResponseHandle(ApplicationConstants.IP, ApplicationConstants.PORT, gracefulLeaveResponseModel.getStatus());
        }

    }

    @Override
    public void searchFile(String file) throws RemoteException, NotBoundException {
        ArrayList<Node> nodes = new ArrayList<>();
        nodes.add(new Node(ApplicationConstants.IP, ApplicationConstants.PORT));
        SearchRequestModel searchRequestModel = new SearchRequestModel(ApplicationConstants.IP, ApplicationConstants.PORT, file, ApplicationConstants.HOPS, nodes);
        searchRequestModel.handle();
        //forward to two picked nodes
    }

    @Override
    public void sendSearchRequest(SearchRequestModel model, ConcurrentLinkedQueue<Node> statTablePeers) throws IOException, NotBoundException {

        System.out.println("Found stat table entries");
        System.out.println(statTablePeers);
        GUIController guiController = GUIController.getInstance();


        Iterator<Node> nodeIterator = statTablePeers.iterator();
        while (nodeIterator.hasNext()) {
            Node node = nodeIterator.next();
            if (!model.getLastHops().contains(node)) {
                ServerConnector.getServerConnector(node.getNodeIp(), node.getPort())
                        .callRemoteSearchRequestHadle(model.getIp(), model.getPort(), model.getFileName(), model.getHops(), model.getLastHops());

                guiController.updateQueryMessageForwarded();
            }

            System.out.println("send to stat table entries " + node.getPort());
        }

        final Set<Node> peerNodeList = PeerTableImpl.getInstance().getPeerNodeList();

        final ArrayList<Node> peerNodeListToSend = new ArrayList<>();

        peerNodeList.forEach((node) -> {
            if (!model.getLastHops().contains(node) && !statTablePeers.contains(node)) {
                peerNodeListToSend.add(node);
            }
        });

        System.out.println("peer nodes to send list " + peerNodeListToSend);
        Random random = new Random();
        int size = peerNodeListToSend.size();
        if (size > 0) {
            final int item1 = random.nextInt(size);
            ServerConnector serverConnector = ServerConnector.getServerConnector(peerNodeListToSend.get(item1).getNodeIp(), peerNodeListToSend.get(item1).getPort());

            if (serverConnector != null) {
                serverConnector.callRemoteSearchRequestHadle(model.getIp(), model.getPort(), model.getFileName(), model.getHops(), model.getLastHops());
                System.out.println("Sending to peer node " + peerNodeListToSend.get(item1).getPort());
                peerNodeListToSend.remove(item1);
                guiController.updateQueryMessageForwarded();
            } else {
                System.out.println("server connector is null");
            }

        }
        size = peerNodeListToSend.size();
        if (size > 0) {
            final int item2 = random.nextInt(size);
            ServerConnector serverConnector = ServerConnector.getServerConnector(peerNodeListToSend.get(item2).getNodeIp(), peerNodeListToSend.get(item2).getPort());
            if (serverConnector != null) {
                serverConnector.callRemoteSearchRequestHadle(model.getIp(), model.getPort(), model.getFileName(), model.getHops(), model.getLastHops());
                System.out.println("Sending to peer node " + peerNodeListToSend.get(item2).getPort());
                guiController.updateQueryMessageForwarded();
            } else {
                System.out.println("server connector is null");
            }
        }
    }

    @Override
    public void sendLocalSearchToSource(SearchResponseModel searchResponseModel, List<String> list) throws IOException, NotBoundException {
        final ServerConnector serverConnector = ServerConnector.getServerConnector(searchResponseModel.getIp(), searchResponseModel.getPort());
        if (serverConnector != null) {
            serverConnector.callRemoteSearchResponseHadle(
                    ApplicationConstants.IP, ApplicationConstants.PORT,
                    searchResponseModel.getHops(), searchResponseModel.getNoOfFiles(), new HashSet<>(list));
        } else {
            System.out.println("server connector is null");
        }
    }

    //check whether the stat table entry equals to the node which request the file
    private boolean isRequestingNode(SearchRequestModel searchRequestModel, Node node) {
        return searchRequestModel.getFileName().equals(node.getNodeIp()) && searchRequestModel.getPort() == node.getPort();
    }

    public void notifyNeighbours(String ip, int port) throws IOException, NotBoundException {
        final ServerConnector serverConnector = ServerConnector.getServerConnector(ip, port);
        if (serverConnector != null) {
            serverConnector.callRemoteNotifyNeighbourRequestHandle(ApplicationConstants.IP, ApplicationConstants.PORT);
        } else {
            System.out.println("server connector is null");
        }
    }

    public void notifyNeighbourLeave(Set<Node> nodes) throws IOException, NotBoundException {
        System.out.println(nodes);
        Iterator<Node> nodeIterator = nodes.iterator();

        while (nodeIterator.hasNext()) {
            Node node = nodeIterator.next();
            System.out.println("sending unregister to " + node.getPort());
            final ServerConnector serverConnector = ServerConnector.getServerConnector(node.getNodeIp(), node.getPort());
            serverConnector.callRemoteGracefulLeaveRequestHandle(ApplicationConstants.IP, ApplicationConstants.PORT);
        }
    }


}
