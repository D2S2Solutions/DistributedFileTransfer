package com.d2s2.message.tokenize;

import com.d2s2.message.MessageConstants;
import com.d2s2.models.*;
import com.d2s2.overlay.route.NeighbourTableImpl;

import java.util.HashSet;
import java.util.StringTokenizer;

/**
 * Created by Heshan Sandamal on 10/6/2017.
 */
public class MessageTokenizerImpl implements MessageTokenizer {
    @Override
    public AbstractRequestResponseModel tokenizeMessage(String message) {
        message = message.substring(0, Integer.parseInt(message.substring(0, 4)));

        StringTokenizer stringTokenizer = new StringTokenizer(message, " ");
        int length = Integer.parseInt(stringTokenizer.nextToken());
        String response = stringTokenizer.nextToken();

        switch (response) {
            case MessageConstants.REGOK_MESSAGE:
                return this.getRegisterResponseMessageOb(stringTokenizer);

            case MessageConstants.UNROK_MESSAGE:
                return this.getUnregisterResponseMessageOb(stringTokenizer);

            case MessageConstants.SER_MESSAGE:
                return this.getSearchMessageOb(stringTokenizer);

            case MessageConstants.SEROK_MESSAGE:
                return this.getSearchResponseOb(stringTokenizer);

            case MessageConstants.HEARTBEAT_MESSAGE:
                return this.getHeartBeatSignalOb(stringTokenizer);
            case MessageConstants.NEIGHBOUR_MESSAGE:

                return this.getNeighbourResponseMessageOb(stringTokenizer);

            case MessageConstants.LEAVE_MESSAGE:
                System.out.println("LEAVE MESSAGE RECEIVED");
                return null;
                //return this.getNeighbourResponseMessageOb(stringTokenizer);


        }
        return null;


    }

    private AbstractRequestResponseModel getHeartBeatSignalOb(StringTokenizer stringTokenizer){
        String ip = stringTokenizer.nextToken();
        int port = Integer.parseInt(stringTokenizer.nextToken());
        String username = stringTokenizer.nextToken();
        return new HeartBeatSignalModel(ip,port,username);
    }

    private AbstractRequestResponseModel getSearchResponseOb(StringTokenizer stringTokenizer) {

        int noOfFiles = Integer.valueOf(stringTokenizer.nextToken());
        String ip = stringTokenizer.nextToken();
        int port = Integer.parseInt(stringTokenizer.nextToken());
        int hops = Integer.parseInt(stringTokenizer.nextToken());

        HashSet<String> fileSet = new HashSet<>();

        for (int i = 0; i < noOfFiles; i++) {
            fileSet.add(stringTokenizer.nextToken());
        }

        return new SearchResponseModel(ip, port, hops, noOfFiles, fileSet);

    }

    private AbstractRequestResponseModel getSearchMessageOb(StringTokenizer stringTokenizer) {
        String ip = stringTokenizer.nextToken();
        int port = Integer.parseInt(stringTokenizer.nextToken());
        String fileName = stringTokenizer.nextToken();
        int hops = Integer.parseInt(stringTokenizer.nextToken());
        System.out.println("HOPS COUNT " + hops);
        return new SearchRequestModel(ip, port, fileName, hops);
    }

    private AbstractRequestResponseModel getUnregisterResponseMessageOb(StringTokenizer stringTokenizer) {
        String token = stringTokenizer.nextToken();
        if (token != null) {
            return new UnregistrationResponseModel(Integer.parseInt(token));
        } else {
            return null;
        }

    }

    private AbstractRequestResponseModel getRegisterResponseMessageOb(StringTokenizer stringTokenizer) {
        int nodeCount = Integer.parseInt(stringTokenizer.nextToken());
        switch (nodeCount) {
            case 0:
                System.out.println("request is successful, no nodes in the system");
                return null;
            case 9996:
                System.out.println("failed, can’t register. BS full.");
                return null;
            case 9997:
                System.out.println("failed, registered to another user, try a different IP and port");
                return null;
            case 9998:
                System.out.println("failed, already registered to you, unregister first");
                return null;
            case 9999:
                System.out.println(" failed, there is some error in the command");
                return null;
            default:

                HashSet<Node> nodeset = new HashSet<>();

                for (int i = 0; i < nodeCount; i++) {
                    String ip = stringTokenizer.nextToken();
                    String port = stringTokenizer.nextToken();
                    nodeset.add(new Node(ip, Integer.parseInt(port)));

                }
                return new RegistrationResponseModel(nodeCount, nodeset);
        }

    }

    private AbstractRequestResponseModel getNeighbourResponseMessageOb(StringTokenizer stringTokenizer) {

        String ip =stringTokenizer.nextToken();
        String portST = stringTokenizer.nextToken();
        int port=Integer.parseInt(portST.substring(0,portST.length()-1));




        return new NotifyNeighbourRequestModel(ip,port);



    }
}
