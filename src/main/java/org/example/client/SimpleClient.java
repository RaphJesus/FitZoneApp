package org.example.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class SimpleClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 8888);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Conectat la server!");

            new Thread(() -> {
                try {
                    String serverResponse;
                    while ((serverResponse = in.readLine()) != null) {
                        System.out.println("\n[SERVER]: " + serverResponse.replace(" | ", "\n"));
                        System.out.print("> ");
                    }
                } catch (IOException e) {
                    System.out.println("Serverul s-a inchis.");
                    System.exit(0);
                }
            }).start();

            while (true) {
                String comanda = scanner.nextLine();
                out.println(comanda);
                if (comanda.equalsIgnoreCase("EXIT")) break;
            }

        } catch (IOException e) {
            System.out.println("Nu ma pot conecta la server. Verifica daca e pornit!");
        }
    }
}