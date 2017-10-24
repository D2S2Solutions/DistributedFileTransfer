package com.d2s2.models;

import com.d2s2.Handler.Handler;
import com.d2s2.Handler.HandlerImpl;
import com.d2s2.files.FileHandlerImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Heshan Sandamal on 10/24/2017.
 */
public class SearchRequestModel extends AbstractRequestModel {

    private static Handler handler = new HandlerImpl();
    private String fileName;
    private int hops;

    public SearchRequestModel(String ip, int port, String fileName, int hops) {
        super(ip, port);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getHops() {
        return hops;
    }

    public void setHops(int hops) {
        this.hops = hops;
    }

    @Override
    public void handle() {
        //search from stat table    List<String,NOde>
        //create searchRequestModels

        FileHandlerImpl instance = FileHandlerImpl.getInstance();
        List<String> fileList = instance.searchLocalFileList(this.fileName);


        ArrayList<Node> searchRequestModels = new ArrayList<>();

        this.hops--;

        if (hops > 0) {
            try {
                handler.sendSearchRequest(this, searchRequestModels);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
