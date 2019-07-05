package br.com.satheler.client.app;

import java.util.List;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.util.ArrayList;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import br.com.satheler.client.servers.SocketConnection;
import br.com.satheler.client.servers.ZooKeeperManager;
import br.com.satheler.client.utils.HostPort;

/**
 * Application
 */
public class Application {

    public static final int HOST_SERVER = 1;
    public static final int PORT_SERVER = 2;

    public static String USER_INPUT;

    public static void main(String args[]) throws IOException {
        if (args.length < 1) {
            System.err.println("USAGE: <zookeeper_host>:<zookeeper_port>");
            System.exit(2);
        }

        String zookeeperHost = args[0];
        ZooKeeperManager zooKeeperManager = new ZooKeeperManager(zookeeperHost);

        Application.USER_INPUT = null;

        while (true) {

            try {
                zooKeeperManager.searchServers();
                String[] selectZnode = zooKeeperManager.selectServer();
                HostPort hostPort = new HostPort(selectZnode[HOST_SERVER], Integer.parseInt(selectZnode[PORT_SERVER]));
                SocketConnection server = new SocketConnection(hostPort, zooKeeperManager.getServerName());
                server.run();

            } catch (IOException e) {
            } catch (KeeperException e) {
            } catch (InterruptedException e) {
            }
        }
    }

    public static void setTimeout(int delay) {
        try {
            Thread.sleep(delay);
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
