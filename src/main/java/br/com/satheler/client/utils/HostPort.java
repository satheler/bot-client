package br.com.satheler.client.utils;

public class HostPort {
	public String host;
    public int port;


    public HostPort(String hostPort) {
        String[] hostPortSplit = hostPort.split(":");
        this.host = hostPortSplit[0];
        this.port = Integer.parseInt(hostPortSplit[1]);
    }

    public HostPort(String host, int port) {
        this.host = host;
        this.port = port;
    }
}
