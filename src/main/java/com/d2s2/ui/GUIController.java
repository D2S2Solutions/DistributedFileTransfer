package com.d2s2.ui;

import com.d2s2.Handler.Handler;
import com.d2s2.Handler.HandlerImpl;
import com.d2s2.models.SearchResponseModel;

import javax.swing.*;
import java.io.IOException;
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

    public void searchFile(String fileName) {
        handler.searchFile(fileName);
    }

    public void registerInBS() throws IOException {
        String BsServerIp = fileSearchInterface.showInputDialog("Error in IP address format");
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

    public void unRegister() {
        handler.gracefulLeaveRequest();
    }

    public void displaySearchResults(SearchResponseModel searchResponseModel) {
        fileSearchInterface.addToTable(searchResponseModel.getIp(), searchResponseModel.getPort(), searchResponseModel.getNoOfFiles()
                , searchResponseModel.getFileList(), searchResponseModel.getHops());
    }
    public void displayMessage(String message){
        fileSearchInterface.showMessage(message);
    }


}
