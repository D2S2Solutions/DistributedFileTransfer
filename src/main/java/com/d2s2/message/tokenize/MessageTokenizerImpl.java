package com.d2s2.message.tokenize;

import com.d2s2.message.MessageConstants;
import com.d2s2.models.*;
import com.d2s2.overlay.route.PeerTableImpl;

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
            case MessageConstants.REG_MESSAGE:
                return this.getRegisterMessageOb(stringTokenizer);

            case MessageConstants.UNREG_MESSAGE:
                return this.getUnregisterMessageOb(stringTokenizer);


        }
        return null;


    }

    private AbstractRequestResponseModel getUnregisterMessageOb(StringTokenizer stringTokenizer) {
        String token = stringTokenizer.nextToken();
        if (token != null) {
            return new UnregistrationResponseModel(Integer.parseInt(token));
        } else {
            return null;
        }

    }

    private AbstractRequestResponseModel getRegisterMessageOb(StringTokenizer stringTokenizer) {
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

                    nodeset.add(new Node(ip, port));

                }

                return new RegistrationResponseModel(nodeCount, nodeset);
        }

    }
}
