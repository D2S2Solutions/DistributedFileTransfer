package com.d2s2.overlay.route;

import com.d2s2.models.Node;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Heshan Sandamal on 10/6/2017.
 */
public class StatTableImpl {

    private static ConcurrentHashMap<String, ConcurrentLinkedQueue<Node>> statTable = new ConcurrentHashMap();
    private volatile static StatTableImpl statTableImpl;

    private StatTableImpl() {
    }

    public static ConcurrentHashMap<String, ConcurrentLinkedQueue<Node>> getStatTable() {
        return statTable;
    }

    public static StatTableImpl getInstance() {
        if (statTableImpl == null) {
            synchronized (StatTableImpl.class) {
                if (statTableImpl == null) {
                    statTableImpl = new StatTableImpl();
                }
            }
        }
        return statTableImpl;
    }

    public void insert() {
        ConcurrentLinkedQueue<Node> objects = new ConcurrentLinkedQueue<>();
        objects.add(new Node("1", 5000));
        objects.add(new Node("2", 5005));
        ConcurrentLinkedQueue<Node> nodes = new ConcurrentLinkedQueue<>();
        objects.add(new Node("5", 5000));
        objects.add(new Node("7", 5005));
        statTable.put("FIRST", objects);
        statTable.put("SECOND", nodes);
    }

    public void insert(String fileName, ConcurrentLinkedQueue<Node> queue) {
        this.statTable.put(fileName, queue);

    }

    public Boolean remove(Node node) {
        Boolean isElementRemoved = false;
        Collection<ConcurrentLinkedQueue<Node>> values = statTable.values();
        for (Iterator<ConcurrentLinkedQueue<Node>> iterator = values.iterator(); iterator.hasNext();) {
            ConcurrentLinkedQueue<Node> next = iterator.next();
            boolean nowRemoved = next.remove(node);
            if (!isElementRemoved && nowRemoved) {
                isElementRemoved = true;
            }
            if (next.size() == 0){
                iterator.remove();
            }
        }

        return isElementRemoved;
    }

    public ConcurrentLinkedQueue<Node> get(String fileName) {
        return this.statTable.get(fileName);
    }


    public ConcurrentLinkedQueue<Node> search(String query) {
        ConcurrentLinkedQueue<Node> concurrentLinkedQueues = new ConcurrentLinkedQueue();
        statTable.keySet().stream().filter(s -> s.contains(query)).forEach(s -> {
            statTable.get(s).stream().filter(z -> !concurrentLinkedQueues.contains(z)).forEach(node -> concurrentLinkedQueues.add(node));
        });
        return concurrentLinkedQueues;
    }


    public ConcurrentHashMap<String, ConcurrentLinkedQueue<Node>> get() {
        return statTable;
    }
}
