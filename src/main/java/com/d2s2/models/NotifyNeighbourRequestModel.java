package com.d2s2.models;

import com.d2s2.Handler.Handler;
import com.d2s2.Handler.HandlerImpl;
import com.d2s2.constants.ApplicationConstants;
import com.d2s2.overlay.route.NeighbourTableImpl;
import com.d2s2.overlay.route.PeerTableImpl;
import com.d2s2.ui.GUIController;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Created by dimuth on 10/24/17.
 */
public class NotifyNeighbourRequestModel extends AbstractRequestModel {

    private static Handler handler = new HandlerImpl();

    public NotifyNeighbourRequestModel(String ip, int port) throws RemoteException {
        super(ip, port);

    }


    @Override
    public void handle() throws NotBoundException {
        NeighbourTableImpl neighbourTable = NeighbourTableImpl.getInstance();
        Node node = new Node(this.ip, this.port);
        neighbourTable.insert(node);
        PeerTableImpl peerTable = PeerTableImpl.getInstance();
        if (peerTable.getPeerNodeList().size() < 10) {
            if (!peerTable.getPeerNodeList().contains(node)) {
                peerTable.insert(node);
                try {
                    handler.notifyNeighbours(node.getNodeIp(), node.getPort());
                } catch (IOException io) {
                    io.printStackTrace();
                }
                GUIController guiController = GUIController.getInstance();
                guiController.populatePeerTable(peerTable.getPeerNodeList());
            }
        }

        if ("Linux".equals(System.getProperty("os.name"))) {
            System.out.println(ApplicationConstants.ANSI_CYAN + "Peer Nodes Table" + ApplicationConstants.ANSI_RESET);
            System.out.println(ApplicationConstants.ANSI_PURPLE + "===================" + ApplicationConstants.ANSI_RESET);
            peerTable.getPeerNodeList().forEach(n -> System.out.println(ApplicationConstants.ANSI_YELLOW +
                    n.getNodeIp() + "\t\t" + n.getPort() + ApplicationConstants.ANSI_RESET
            ));
        } else {
            System.out.println("Peer Nodes Table");
            System.out.println("===================");
            peerTable.getPeerNodeList().forEach(n -> System.out.println(n.getNodeIp() + "\t\t" + n.getPort()
            ));
        }

    }
}
