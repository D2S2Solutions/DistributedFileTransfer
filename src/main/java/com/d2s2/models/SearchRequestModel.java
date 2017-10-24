package com.d2s2.models;

import com.d2s2.Handler.Handler;
import com.d2s2.socket.UdpConnector;

import java.util.ArrayList;

/**
 * Created by Heshan Sandamal on 10/24/2017.
 */
public class SearchRequestModel extends AbstractRequestModel{

    private  Handler handler;
    private String fileName;
    private int hops;

    public SearchRequestModel(Handler handler, String ip, String port, String fileName, int hops) {
        super(ip, port);
        this.handler=handler;
    }


    @Override
    public void handle() {
        //search from local file table   List<String>
        //search from stat table    List<String,NOde>
        //create searchRequestModels

        ArrayList<SearchRequestModel> searchRequestModels=new ArrayList<>();

        handler.sendSearchRequest(searchRequestModels);

    }
}
