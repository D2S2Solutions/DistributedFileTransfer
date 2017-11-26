package com.d2s2.ui;

import com.d2s2.Handler.Handler;
import com.d2s2.Handler.HandlerImpl;
import com.d2s2.models.Node;
import com.d2s2.models.SearchResponseModel;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.d2s2.constants.ApplicationConstants.IP_ADDRESS_PATTERN;

/**
 * Created by Heshan Sandamal on 11/4/2017.
 */
public class GUIController {

    private static GUIController guiController;
    private static FileSearchInterface fileSearchInterface;
    private static Handler handler = new HandlerImpl();

    public static GUIController getInstance() {
        if (guiController == null) {
            synchronized (GUIController.class) {
                if (guiController == null) {
                    guiController = new GUIController();
                }
            }
        }
        return guiController;
    }

    public void setUIinstance(FileSearchInterface fileSearchInterface) {
        GUIController.fileSearchInterface = fileSearchInterface;
    }

    public void searchFile(String fileName) throws RemoteException, NotBoundException {
        handler.searchFile(fileName);
    }

    public void registerInBS() throws IOException {
        String BsServerIp = fileSearchInterface.showInputDialog("Enter Bootstrap server IP");
        if (BsServerIp == null) {
            return;
        }
        Pattern pattern = Pattern.compile(IP_ADDRESS_PATTERN);
        Matcher matcher = pattern.matcher(BsServerIp);
        if (matcher.matches()) {
            handler.registerInBS(BsServerIp);
        } else {
            fileSearchInterface.showMessage("Error in IP address format");
            registerInBS();
        }
    }

    public void unRegister() throws RemoteException {
        handler.gracefulLeaveRequest();
    }

    public void displaySearchResults(SearchResponseModel searchResponseModel) {
        fileSearchInterface.addToTable(searchResponseModel.getIp(), searchResponseModel.getPort(), searchResponseModel.getNoOfFiles()
                , searchResponseModel.getFileList(), searchResponseModel.getHops());
    }

    public void displayMessage(String message) {
        fileSearchInterface.showMessage(message);
    }

    public void populatePeerTable(Set<Node> nodeArrayList){
        fileSearchInterface.populatePeerTable(nodeArrayList);
    }

    public void populateStatTable(ConcurrentHashMap<String, ConcurrentLinkedQueue<Node>> statTable){
        fileSearchInterface.populateStatTable(statTable);
    }
    public void handleRegisteration(){
        fileSearchInterface.handleRegistration();
    }
    public void handleUnRegistration(){
        fileSearchInterface.handleUnRegistration();
    }

}
