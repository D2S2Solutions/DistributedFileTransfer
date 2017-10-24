package com.d2s2.models;

import com.d2s2.overlay.route.PeerTableImpl;

import java.util.HashSet;

/**
 * Created by Heshan Sandamal on 10/24/2017.
 */
public class RegistrationResponseModel extends AbstractRequestResponseModel {

    HashSet<Node> nodeset;
    int nodeCount;

    public RegistrationResponseModel(int nodeCount, HashSet<Node> nodeset) {
        this.nodeCount = nodeCount;
        this.nodeset = nodeset;
    }

    @Override
    public void handle() {

        nodeset.forEach((node) -> {
            PeerTableImpl.getInstance().insert(node);
        });

        PeerTableImpl.getInstance().getPeerNodeList().forEach((node) -> {
            System.out.println(node.getId());
        });

    }
}
