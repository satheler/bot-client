package br.com.satheler.client.servers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class ZooKeeperManager implements Watcher {

    private ZooKeeper zookeeperServer;
    private String serverName;
    private List<String> availableServers;

    public ZooKeeperManager(String host) throws IOException {
        this.zookeeperServer = new ZooKeeper(host, 3000, this);
    }

    public void searchServers() throws KeeperException, InterruptedException, IOException {
        List<String> zNodeList = this.zookeeperServer.getChildren("/", true);
        List<String> availableServers = new ArrayList<String>();

        if(zNodeList.size() == 1) {
            throw new IOException("Nenhum servidor disponivel");
        }

        for (String zNode : zNodeList) {
            if (zNode.equals("zookeeper"))
                continue;
            availableServers.add(zNode);
        }

        this.availableServers = availableServers;
    }

    public String[] selectServer() throws KeeperException, InterruptedException {
        final int CONNECTIONS_ON_SERVER = 0;

        String[] zNodeSelected = null;
        for (String zNode : this.availableServers) {
            byte[] zNodeDataInBytes = this.zookeeperServer.getData("/" + zNode, null, null);
            String zNodeDataInString = new String(zNodeDataInBytes);

            String[] zNodeData = zNodeDataInString.split(":");
            int connectionsOnServer = Integer.parseInt(zNodeData[CONNECTIONS_ON_SERVER]);

            if(zNodeSelected == null || connectionsOnServer == 0 || connectionsOnServer < Integer.parseInt(zNodeSelected[CONNECTIONS_ON_SERVER])) {
                zNodeSelected = zNodeData;
                this.serverName = zNode;
            }
        }

        return zNodeSelected;
    }

    public String getServerName() {
        return this.serverName;
    }

    @Override
    public void process(WatchedEvent event) {

    }
}
