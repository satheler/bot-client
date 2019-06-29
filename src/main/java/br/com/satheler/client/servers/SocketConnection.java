package br.com.satheler.client.servers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * SocketServer
 */
public class SocketConnection {

    private Socket CONNECTION;
    private String host;
    private int port;

    public SocketConnection(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() {
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

        try {
            CONNECTION = new Socket(this.host, this.port);
            PrintStream socketOutput = new PrintStream(CONNECTION.getOutputStream());

            System.out.println("===== CONECTADO COM O SERVIDOR =====");
            String userInput;

            BufferedReader server = new BufferedReader(new InputStreamReader(CONNECTION.getInputStream()));
            String response;
            while (true) {
                System.out.print("COMANDO > ");
                userInput = keyboard.readLine();
                socketOutput.println(userInput);

                do {
                    response = server.readLine();
                    if (!response.equals("")) {
                        System.out.println("BOT > " + response);
                    }
                } while (server.ready());
            }

        } catch (IOException e) {
            System.err.println("NAO FOI POSSIVEL CONECTAR AO SERVIDOR");
            System.exit(1);
        }
    }
}
