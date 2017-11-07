package com.d2s2.rmi.server;

import com.d2s2.models.AbstractRequestResponseModel;
import com.d2s2.models.Node;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Heshan Sandamal on 11/4/2017.
 */
public interface RemoteFactory extends Remote{

    AbstractRequestResponseModel getSearchRequestModel(String ip, int port, String fileName, int hops, ArrayList<Node> lastHops) throws RemoteException;
    AbstractRequestResponseModel getSearchResponseModel(String ip, int port, int hops, int noOfFiles, HashSet<String> fileList) throws RemoteException;

    AbstractRequestResponseModel getNotifyNeighbourRequestModel(String ip, int port) throws RemoteException;

    AbstractRequestResponseModel getGracefulLeaveRequestModel(String ip, int port) throws RemoteException;
    AbstractRequestResponseModel getGracefulLeaveResponseModel(String ip,int port,int status)throws RemoteException;

    AbstractRequestResponseModel getGracefulLeaveBootstrapServerRequestModel(String ip, int port, String userName) throws RemoteException;
    AbstractRequestResponseModel getGracefulLeaveBootstrapServerResponseModel(int value) throws RemoteException;

    AbstractRequestResponseModel getHeartBeatSignalModel(String ip, int port, String userName) throws RemoteException;

}
