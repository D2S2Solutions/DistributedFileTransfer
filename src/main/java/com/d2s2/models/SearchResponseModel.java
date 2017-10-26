package com.d2s2.models;

import com.d2s2.constants.ApplicationConstants;
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


        // Go inside iff ip and the port are equal.
        if (ApplicationConstants.IP.equals(this.ip) && ApplicationConstants.PORT == this.port) {
            System.out.println("Matching file are found at query source node, they are : ");
            fileList.stream().map(s -> s.replace("@", " ")).forEach(System.out::println);
        } else {
            System.out.println("Files found @ >>>>> " + this.getIp() + " : " + this.port + " and they are : ");
            fileList.forEach(s -> System.out.println("\t"+"* " +s));
            Node node = new Node(this.ip, this.port);
            fileList.forEach((fileName) -> {
                ConcurrentLinkedQueue<Node> concurrentLinkedQueue = statTable.get(fileName);
                if (concurrentLinkedQueue == null) {
                    concurrentLinkedQueue = new ConcurrentLinkedQueue<>();
                    statTable.insert(fileName, concurrentLinkedQueue);
                }
                if (!concurrentLinkedQueue.contains(node)) {
                    concurrentLinkedQueue.add(node);
                }
            });
            if (!peerTable.getPeerNodeList().contains(node)) {
                peerTable.insert(node);
            }
        }
    }
}
