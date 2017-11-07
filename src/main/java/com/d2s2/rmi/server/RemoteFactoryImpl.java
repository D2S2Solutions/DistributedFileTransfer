package com.d2s2.rmi.server;

import com.d2s2.models.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Heshan Sandamal on 11/4/2017.
 */
public class RemoteFactoryImpl extends UnicastRemoteObject implements RemoteFactory {

    public RemoteFactoryImpl() throws RemoteException {
    }

    @Override
    public AbstractRequestResponseModel getSearchRequestModel(String ip, int port, String fileName, int hops, ArrayList<Node> lastHops) throws RemoteException {
        return new SearchRequestModel(ip,port,fileName,hops,lastHops);
    }

    @Override
    public AbstractRequestResponseModel getNotifyNeighbourRequestModel(String ip, int port) throws RemoteException {
        return new NotifyNeighbourRequestModel(ip,port);
    }

    @Override
    public AbstractRequestResponseModel getGracefulLeaveRequestModel(String ip, int port) throws RemoteException {
        return new GracefulLeaveRequestModel(ip,port);
    }

    @Override
    public AbstractRequestResponseModel getGracefulLeaveResponseModel(String ip,int port,int status) throws RemoteException {
        return new GracefulLeaveResponseModel(ip,port,status);
    }

    @Override
    public AbstractRequestResponseModel getGracefulLeaveBootstrapServerRequestModel(String ip, int port, String userName) throws RemoteException {
        return new GracefulLeaveBootstrapServerRequestModel(ip,port,userName);
    }

    @Override
    public AbstractRequestResponseModel getGracefulLeaveBootstrapServerResponseModel(int value) throws RemoteException {
        return new GracefulLeaveBootstrapServerResponseModel(value);
    }

    @Override
    public AbstractRequestResponseModel getHeartBeatSignalModel(String ip, int port, String userName) throws RemoteException {
        return new HeartBeatSignalModel(ip,port,userName);
    }

    @Override
    public AbstractRequestResponseModel getSearchResponseModel(String ip, int port, int hops, int noOfFiles, HashSet<String> fileList) throws RemoteException {
        return new SearchResponseModel(ip,port,hops,noOfFiles,fileList);
    }

}
