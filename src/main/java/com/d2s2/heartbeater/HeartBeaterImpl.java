package com.d2s2.heartbeater;

import com.d2s2.message.build.MessageBuilder;
import com.d2s2.models.HeartBeatSignalModel;
import com.d2s2.models.Node;
import com.d2s2.overlay.route.NeighbourTableImpl;
import com.d2s2.overlay.route.PeerTableImpl;
import com.d2s2.socket.UDPConnectorImpl;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Set;
/**
 * Created by Tharindu Diluksha on 10/24/2017.
 */
public class HeartBeaterImpl implements HeartBeater {


    PeerTableImpl peerTable; //    PeerTableImpl peerTable = PeerTableImpl.getInstance();
    NeighbourTableImpl neighbourTable;
    HeartBeatSignalModel heartBeatSignalModel;
    /**
     * todo Implement tables from table interface and change reference type here
    * */
    UDPConnectorImpl udpConnector;
    private Set<Node> peerNodes;
    private MessageBuilder messageBuilder;

    public HeartBeaterImpl(UDPConnectorImpl udpConnector,PeerTableImpl peerTable,NeighbourTableImpl neighbourTable,HeartBeatSignalModel heartBeatSignalModel) {
        this.peerTable = peerTable;
        this.neighbourTable = neighbourTable;
        this.heartBeatSignalModel = heartBeatSignalModel;
        this.udpConnector = udpConnector;
    }

    @Override
    public void makeHeartBeat() {
        //todo search through peer(up) table and send message to every node in every 0.5 seconds

        peerNodes = peerTable.getPeerNodeList();

        if(!peerNodes.isEmpty()){
            for (Node peer : peerNodes) {
                String heartBeatMessage = messageBuilder.buildHeartBeatSignalMessage(heartBeatSignalModel);
                try {
                    udpConnector.send(heartBeatMessage, InetAddress.getByName(peer.getNodeIp()), peer.getPort());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void handleBeat() {
        //todo if there are no consecutive two beats with in the time period (2s) remove node from neighbour(down) list
    }
}
