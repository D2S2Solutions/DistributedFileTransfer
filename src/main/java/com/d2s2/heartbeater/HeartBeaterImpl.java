package com.d2s2.heartbeater;

import com.d2s2.Handler.Handler;
import com.d2s2.message.build.MessageBuilder;
import com.d2s2.models.HeartBeatSignalModel;
import com.d2s2.models.Node;
import com.d2s2.overlay.route.NeighbourTableImpl;
import com.d2s2.overlay.route.PeerTableImpl;
import com.d2s2.overlay.route.StatTableImpl;
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
    StatTableImpl statTable;
    HeartBeatSignalModel heartBeatSignalModel;
    /**
     * todo Implement tables from table interface and change reference type here
    * */
    UDPConnectorImpl udpConnector;
    private Set<Node> peerNodes;
    private MessageBuilder messageBuilder;

    public HeartBeaterImpl(PeerTableImpl peerTable) {
        this.peerTable = peerTable;
        this.neighbourTable = neighbourTable;

    }

    @Override
    public void handleBeat() {
        //todo if there are no consecutive two beats with in the time period (2s) remove node from neighbour(down) list
    }
}
