package com.shepherd.zk.client;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class zkClient {

    // 注意：逗号左右不能有空格
    // 连接集群 hadoop102:2181,hadoop103:2181,hadoop104:2181
    private String connectString = "10.10.0.18:2181";
    private int sessionTimeout = 10000;
    private ZooKeeper zkClient;

    @Before
    public void init() throws IOException {

        zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {

                // 持续监听
//                System.out.println("-------------------------------");
//                List<String> children = null;
//                try {
//                    children = zkClient.getChildren("/", true);
//
//                    for (String child : children) {
//                        System.out.println(child);
//                    }
//
//                    System.out.println("-------------------------------");
//                } catch (KeeperException e) {
//                    e.printStackTrace();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        });
    }

    @Test
    public void create() throws KeeperException, InterruptedException {
        // 参数1： 节点路径   参数2： 节点数据 参数3： 节点权限   参数4： 节点类型
        String nodeCreated = zkClient.create("/shepherd", "MeiYing".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    @Test
    public void getChildren() throws KeeperException, InterruptedException {
        List<String> children = zkClient.getChildren("/", true);

        for (String child : children) {
            System.out.println(child);
        }

        // 延时 →watch为true，代表监听， 在zkClient的process()方法持续监听后续的节点变化
        Thread.sleep(Long.MAX_VALUE);
    }

    @Test
    public void exist() throws KeeperException, InterruptedException {

        // 客户端不启动监听
        Stat stat = zkClient.exists("/shepherd", false);

        System.out.println(stat==null? "not exist " : "exist");
    }
}
