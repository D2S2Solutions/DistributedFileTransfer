package com.d2s2.heartbeater;

/**
 * Created by Tharindu Diluksha on 10/24/2017.
 */
public class HeartBeaterImpl implements HeartBeater {
    @Override
    public void makeHeartBeat() {
        //todo search through peer(up) table and send message to every node in every 0.5 seconds
    }

    @Override
    public void handleBeat() {
        //todo if there are no consecutive two beats with in the time period remove node from neighbour(down) list
    }
}
