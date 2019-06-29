package br.com.satheler.client.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import br.com.satheler.client.servers.SocketConnection;

/**
 * Application
 */
public class Application implements Watcher {

    public static void main(String args[]) throws IOException, KeeperException, InterruptedException {
        if (args.length < 1) {
            System.err.println("USAGE: <zookeeper_host>:<zookeeper_port>");
            System.exit(2);
        }

        String zookeeperHost = args[0];
        ZooKeeper zkServer = new ZooKeeper(zookeeperHost, 3000, new Application());

        String socketServer = findServers(zkServer);
        // LISTA DE SERVIDORES
        // SELECIOANR UM ZNODO
        // PEGAR O ENDEREÇO DO SERVIDOR ATRÁVES DO ZNODO
        // PROCURAR OUTRO SERVIDOR CASO UM CAIA

        String[] hostPort = socketServer.split(":");
        String host = hostPort[0];
        int port = Integer.parseInt(hostPort[1]);

        System.out.println(socketServer);

        SocketConnection server = new SocketConnection(host, port);
        server.run();
    }

    private static String findServers(ZooKeeper zookeeper) throws KeeperException, InterruptedException {
        List<String> zNodeList = zookeeper.getChildren("/", true);
        List<String> availableServers = new ArrayList<String>();


        for (String zNode : zNodeList) {
            if(!zNode.equals("zookeeper")) {
                availableServers.add("/" + zNode);
            }
        }

        byte[] zNodeData = zookeeper.getData(availableServers.get(0), null, null);
        String socketHost = new String(zNodeData);

        return socketHost;
    }

    public static void setTimeout(Runnable runnable, int delay) {
        try {
            Thread.sleep(delay);
            runnable.run();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Override
    public void process(WatchedEvent event) {
        // TODO
    }
}
