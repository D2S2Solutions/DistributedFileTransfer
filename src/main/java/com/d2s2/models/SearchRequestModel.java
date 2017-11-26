package com.d2s2.models;

import com.d2s2.Handler.Handler;
import com.d2s2.Handler.HandlerImpl;
import com.d2s2.constants.ApplicationConstants;
import com.d2s2.files.FileHandlerImpl;
import com.d2s2.overlay.route.StatTableImpl;
import com.d2s2.ui.GUIController;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
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
    private ArrayList<Node> lastHops;


    public SearchRequestModel(String ip, int port, String fileName, int hops, ArrayList<Node> lastHops) throws RemoteException {
        super(ip, port);
        this.fileName = fileName;
        this.hops = hops;
        this.lastHops = lastHops;
    }

    public ArrayList<Node> getLastHops() {
        return lastHops;
    }

    public void setLastHops(ArrayList<Node> lastHops) {
        this.lastHops = lastHops;
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
    public void handle() throws RemoteException, NotBoundException {
        //search from stat table    List<String,NOde>
        //create searchRequestModels

        GUIController guiController = GUIController.getInstance();

        FileHandlerImpl instance = FileHandlerImpl.getInstance();
        List<String> fileList = instance.searchLocalFileList(this.fileName);

        int hops=this.hops;

        if (fileList.size() > 0) {

            new Thread(() -> {
                SearchResponseModel searchResponseModel = null;
                try {
                    searchResponseModel = new SearchResponseModel(this.ip, this.port, hops, fileList.size(), new HashSet<>(fileList));
                    try {
                        handler.sendLocalSearchToSource(searchResponseModel, fileList);
                    } catch (IOException | NotBoundException e) {
//                        e.printStackTrace();
                    }
                } catch (RemoteException e) {
//                    e.printStackTrace();
                }

            }).start();


        }

        --this.hops;
        if (this.hops > 0) {

            ConcurrentLinkedQueue statTablePeers = StatTableImpl.getInstance().search(this.fileName);

            Node node = new Node(ApplicationConstants.IP, ApplicationConstants.PORT);
            if (!this.getLastHops().contains(node)) {
                this.getLastHops().add(node);
            }

            try {
                handler.sendSearchRequest(this, statTablePeers);
            } catch (NotBoundException | IOException e) {
                e.printStackTrace();
            }


        }
    }


}
