package com.d2s2.files;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Heshan Sandamal on 10/6/2017.
 */
public class FileHandlerImpl implements FileHandler {
    private List<String> localFileList;
    private volatile static FileHandlerImpl fileHandler;

    private FileHandlerImpl() {
        localFileList = new ArrayList<>();

    }

    public void initializeFileStorage(String documentName) {
        localFileList.add(documentName);
//        System.out.println("Added " + documentName + " "+localFileList.size());
    }

    public List<String> searchLocalFileList(String searchText) {
        return localFileList.stream().filter(s -> s.contains(searchText)).collect(Collectors.toList());
    }

    public static FileHandlerImpl getInstance() {
        if (fileHandler == null) {
            synchronized (FileHandlerImpl.class) {
                if (fileHandler == null) {
                    fileHandler = new FileHandlerImpl();
                }
            }
        }
        return fileHandler;

    }

}
