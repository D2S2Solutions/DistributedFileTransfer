package com.d2s2.message.tokenize;

import com.d2s2.message.MessageConstants;
import com.d2s2.models.*;

import java.util.HashSet;
import java.util.StringTokenizer;

/**
 * Created by Heshan Sandamal on 10/6/2017.
 */
public class MessageTokenizerImpl implements MessageTokenizer {
    @Override
    public AbstractRequestResponseModel tokenizeMessage(String message) {
        message = message.substring(0, Integer.parseInt(message.substring(0, 4)));
        System.out.println(message);
        StringTokenizer stringTokenizer = new StringTokenizer(message, " ");
        int length = Integer.parseInt(stringTokenizer.nextToken());
        String response = stringTokenizer.nextToken();

        switch (response) {
            case MessageConstants.REGOK_MESSAGE:
                return this.getRegisterResponseMessageOb(stringTokenizer);

            case MessageConstants.UNREG_MESSAGE:
                return this.getUnregisterMessageOb(stringTokenizer);

            case MessageConstants.SER_MESSAGE:
                return this.getSearchMessageOb(stringTokenizer);


        }
        return null;


    }

    private AbstractRequestResponseModel getSearchMessageOb(StringTokenizer stringTokenizer) {
        String ip = stringTokenizer.nextToken();
        int port = Integer.parseInt(stringTokenizer.nextToken());
        String fileName=stringTokenizer.nextToken();
        int hops= Integer.parseInt(stringTokenizer.nextToken());
        return new SearchRequestModel(ip,port,fileName,hops);
    }

    private AbstractRequestResponseModel getUnregisterMessageOb(StringTokenizer stringTokenizer) {
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
                    int port = Integer.parseInt(stringTokenizer.nextToken());
                    nodeset.add(new Node(ip, port));

                }

                return new RegistrationResponseModel(nodeCount, nodeset);
        }

    }
}
