package com.d2s2.heartbeater;

import com.d2s2.constants.ApplicationConstants;
import com.d2s2.models.Node;
import com.d2s2.overlay.route.PeerTableImpl;
import com.d2s2.overlay.route.StatTableImpl;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Tharindu Diluksha on 10/24/2017.
 */
public class HeartBeaterImpl {

    private static HeartBeaterImpl heartBeater;
    private static volatile Set<Node> beatedNodes;
    private static volatile int count;
    //private static Set<Node> receivedNodes;

    static {
        count = 0;
        beatedNodes = ConcurrentHashMap.newKeySet();
    }

    private HeartBeaterImpl() {
        // ConcurrentHashMap<String, ConcurrentLinkedQueue<Node>> statTable = StatTableImpl.getInstance().
    }

    public static HeartBeaterImpl getInstance() {
        if (heartBeater == null) {
            synchronized (HeartBeaterImpl.class) {
                if (heartBeater == null) {
                    heartBeater = new HeartBeaterImpl();
                }
            }
        }
        return heartBeater;
    }

    public void saveBeatedNodes(Node beatedNode) {
        beatedNodes.add(beatedNode);
        System.out.println("HBEAT Received from " + beatedNode.getNodeIp() + " " + beatedNode.getPort());
    }

    public void clearBeatedNodes() {
        beatedNodes.clear();
        System.out.println("Clear Beated Nodes");
    }

    public void handleBeat() {
        //if there are no beats in HEART_BEAT_RECEIVE_THRESHOLD time
        if (!beatedNodes.isEmpty() && !PeerTableImpl.getInstance().getPeerNodeList().isEmpty()) {
            Iterator<Node> nodeIterator = PeerTableImpl.getInstance().getPeerNodeList().iterator();
            while (nodeIterator.hasNext()) {
                Node peerNode = nodeIterator.next();
                if (!beatedNodes.contains(peerNode)) {
                    System.out.println("Removing node in HBeat failure " + peerNode.getNodeIp() + " " + peerNode.getPort());
                    //remove node from peer(up) list
                    Boolean isPeerRemoved = PeerTableImpl.getInstance().remove(peerNode);
                    System.out.println("Removing peer " + isPeerRemoved);
                    //remove from stat table
                    Boolean isStatRemoved = StatTableImpl.getInstance().remove(peerNode);
                    System.out.println("Removing stat " + isStatRemoved);
                }
            }
        } else if (beatedNodes.isEmpty() && !PeerTableImpl.getInstance().getPeerNodeList().isEmpty()) {
            System.out.println("Hbeat handling empty BeatedNodes");
            Iterator<Node> nodeIterator = PeerTableImpl.getInstance().getPeerNodeList().iterator();
            while (nodeIterator.hasNext()) {
                Node peerNode = nodeIterator.next();
                System.out.println("Removing node in HBeat failure " + peerNode.getNodeIp() + " " + peerNode.getPort());
                //remove node from peer(up) list
                Boolean isPeerRemoved = PeerTableImpl.getInstance().remove(peerNode);
                System.out.println("Removing peer " + isPeerRemoved);
                //remove from stat table
                Boolean isStatRemoved = StatTableImpl.getInstance().remove(peerNode);
                System.out.println("Removing stat " + isStatRemoved);
            }
        } else if (!beatedNodes.isEmpty() && PeerTableImpl.getInstance().getPeerNodeList().isEmpty()) {
            System.out.println("Hbeat handling empty Peer Nodes");
        } else {
            System.out.println("Both empty");
        }
        if ("Linux".equals(System.getProperty("os.name"))) {
            System.out.println(ApplicationConstants.ANSI_CYAN + "Peer Nodes at live heart-beating " + ApplicationConstants.ANSI_RESET);
            PeerTableImpl.getInstance().getPeerNodeList().forEach(node -> System.out.println(ApplicationConstants.ANSI_YELLOW +
                    node.getNodeIp() + "\t\t" + node.getPort() + ApplicationConstants.ANSI_RESET
            ));
        } else {
            System.out.println("Peer Nodes at live heart-beating ");
            PeerTableImpl.getInstance().getPeerNodeList().forEach(node -> System.out.println(node.getNodeIp() + "\t\t" + node.getPort()
            ));
        }

    }
}
