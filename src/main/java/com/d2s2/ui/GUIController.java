package com.d2s2.ui;

import com.d2s2.Handler.Handler;
import com.d2s2.Handler.HandlerImpl;
import com.d2s2.models.SearchResponseModel;

import java.io.IOException;

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
        handler.registerInBS();
    }

    public void unRegister() {
        handler.gracefulLeaveRequest();
    }

    public void displaySearchResults(SearchResponseModel searchResponseModel) {
        fileSearchInterface.addToTable(searchResponseModel.getIp(), searchResponseModel.getPort(), searchResponseModel.getNoOfFiles()
                , searchResponseModel.getFileList(), searchResponseModel.getHops());
    }


}
