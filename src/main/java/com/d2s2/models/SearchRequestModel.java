package com.d2s2.models;

import com.d2s2.Handler.Handler;
import com.d2s2.Handler.HandlerImpl;
import com.d2s2.constants.ApplicationConstants;
import com.d2s2.files.FileHandlerImpl;
import com.d2s2.overlay.route.StatTableImpl;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Heshan Sandamal on 10/24/2017.
 */
public class SearchRequestModel extends AbstractRequestModel {

    private static Handler handler = new HandlerImpl();
    private String fileName;
    private int hops;

    public SearchRequestModel(String ip, int port, String fileName, int hops) {
        super(ip, port);
        this.fileName = fileName;
        this.hops = hops;
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
        --this.hops;
        if (this.hops > 0) {
            FileHandlerImpl instance = FileHandlerImpl.getInstance();
            List<String> fileList = instance.searchLocalFileList(this.fileName);

            if (fileList.size() > 0) {
                SearchResponseModel searchResponseModel = new SearchResponseModel(ApplicationConstants.IP, ApplicationConstants.PORT, this.hops, fileList.size(), new HashSet<>(fileList));
                try {
                    handler.sendLocalSearchToSource(searchResponseModel, fileList);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            ConcurrentLinkedQueue statTablePeers = StatTableImpl.getInstance().search(this.fileName);
            System.out.println("Stat table "+statTablePeers);


            try {
                handler.sendSearchRequest(this, statTablePeers);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
