package br.com.satheler.client.servers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.List;

import br.com.satheler.client.app.Application;
import br.com.satheler.client.utils.HostPort;

/**
 * SocketServer
 */
public class SocketConnection {

    private Socket CONNECTION;
    private String serverName;
    private String host;
    private int port;

    public SocketConnection(HostPort hostPort, String serverName) {
        this.host = hostPort.host;
        this.port = hostPort.port;
        this.serverName = serverName;
    }

    public void run() throws IOException {
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

        CONNECTION = new Socket(this.host, this.port);
        PrintStream socketOutput = new PrintStream(CONNECTION.getOutputStream());

        BufferedReader server = new BufferedReader(new InputStreamReader(CONNECTION.getInputStream()));
        String response;
        while (true) {
            if (Application.USER_INPUT == null) {
                System.out.print("COMANDO > ");
                Application.USER_INPUT = keyboard.readLine();
            }

            socketOutput.println(Application.USER_INPUT);

            do {
                response = server.readLine();
                if (!response.equals("")) {
                    System.out.println("[" + this.serverName + "]" + " BOT > " + response);
                }
            } while (server.ready());

            Application.USER_INPUT = null;
        }
    }
}
