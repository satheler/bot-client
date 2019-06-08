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

/**
 * Application
 */
public class Application {

    private static Socket CONNECTION;

    public Application(Socket socket) {
        CONNECTION = socket;
    }

    public static void main(String args[]) {

        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

        try {
            CONNECTION = new Socket("localhost", 1234);
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
            System.out.println("NAO FOI POSSIVEL CONECTAR AO SERVIDOR");
            System.exit(0);
        }
    }
}
