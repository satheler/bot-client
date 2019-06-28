package br.com.satheler.client.app;

/**
 * Hello world!
 *
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import org.apache.zookeeper.Zookeeper;

/**
 * Application
 */
public class Application {

    private static Socket CONNECTION;
    public static void main(String args[]) {
        if(args.length < 1) {
            System.err.println("USAGE: <zookeeper_host>:<zookeeper_port>");
            System.exit(2);
        }

        String zookeeperHost = args[0];
        Zookeeper zookeeper = new Zookeeper(zookeeperHost, 3000);

        findServers(zookeeper);

        String[] hostPort = args[0].split(":");
        String host = hostPort[0];
        int port = Integer.parseInt(hostPort[1]);

        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

        try {
            CONNECTION = new Socket(host, port);
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
                    if(!response.equals("")) {
                        System.out.println("BOT > " + response);
                    }
                } while (server.ready());
            }

        } catch (IOException e) {
            System.err.println("NAO FOI POSSIVEL CONECTAR AO SERVIDOR");
            System.exit(1);
        }
    }

    public static void setTimeout(Runnable runnable, int delay) {
        try {
            Thread.sleep(delay);
            runnable.run();
        }
        catch (Exception e){
            System.err.println(e);
        }
    }
}
