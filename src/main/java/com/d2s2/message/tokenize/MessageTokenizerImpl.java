package com.d2s2.message.tokenize;

import com.d2s2.models.Node;
import com.d2s2.overlay.route.PeerTableImpl;

import java.util.StringTokenizer;

/**
 * Created by Heshan Sandamal on 10/6/2017.
 */
public class MessageTokenizerImpl implements MessageTokenizer {
    @Override
    public void tokenizeMessage(String message) {
        message = message.substring(0, Integer.parseInt(message.substring(0, 4)));
        System.out.println(message);
        StringTokenizer stringTokenizer = new StringTokenizer(message, " ");
        int length = Integer.parseInt(stringTokenizer.nextToken());
        String response = stringTokenizer.nextToken();
        if ("REGOK".equals(response)) {
            int nodeCount = Integer.parseInt(stringTokenizer.nextToken());
            switch (nodeCount) {
                case 0:
                    System.out.println("request is successful, no nodes in the system");
                    break;
                case 9996:
                    System.out.println(" failed, can’t register. BS full.");
                    break;
                case 9997:
                    System.out.println("failed, registered to another user, try a different IP and port");
                    break;
                case 9998:
                    System.out.println("failed, already registered to you, unregister first");
                    break;
                case 9999:
                    System.out.println(" failed, there is some error in the command");
                    break;
                case 1:
                    String ip = stringTokenizer.nextToken();
                    String port = stringTokenizer.nextToken();
                    System.out.println(ip);
                    System.out.println(port);
                    Node node = new Node(ip,port);
                    PeerTableImpl.getInstance().insert(node);
                    break;
                case 2:
                    String ip1 = stringTokenizer.nextToken();
                    String port1 = stringTokenizer.nextToken();
                    String ip2 = stringTokenizer.nextToken();
                    String port2 = stringTokenizer.nextToken();
                    System.out.println(ip1);
                    System.out.println(port1);
                    System.out.println(ip2);
                    System.out.println(port2);
                    Node node1 = new Node(ip1,port1);
                    Node node2 = new Node(ip2,port2);
                    PeerTableImpl.getInstance().insert(node1);
                PeerTableImpl.getInstance().insert(node2);
            }
        }

    }
}
