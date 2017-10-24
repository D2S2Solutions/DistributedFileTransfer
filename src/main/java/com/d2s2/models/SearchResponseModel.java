package com.d2s2.models;

import com.d2s2.overlay.route.PeerTableImpl;
import com.d2s2.overlay.route.StatTableImpl;

import java.util.HashSet;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Heshan Sandamal on 10/24/2017.
 */
public class SearchResponseModel extends AbstractRequestResponseModel {

    private String ip;
    private int port;
    private int hops;
    private int noOfFiles;
    private HashSet<String> fileList;

    public SearchResponseModel(String ip, int port, int hops, int noOfFiles, HashSet<String> fileList) {
        this.ip = ip;
        this.port = port;
        this.hops = hops;
        this.noOfFiles = noOfFiles;
        this.fileList = fileList;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public int getHops() {
        return hops;
    }

    public int getNoOfFiles() {
        return noOfFiles;
    }

    public HashSet<String> getFileList() {
        return fileList;
    }

    @Override
    public void handle() {
        final StatTableImpl statTable = StatTableImpl.getInstance();
        final PeerTableImpl peerTable = PeerTableImpl.getInstance();

        fileList.forEach((fileName) -> {

            ConcurrentLinkedQueue<Node> concurrentLinkedQueue = statTable.get(fileName);
            Node node = new Node(this.ip, this.port);
            concurrentLinkedQueue.add(node);
            peerTable.insert(node);


        });


    }
}
