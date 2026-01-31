package org.example.server;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.example.service.RezervareService;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ApplicationScoped
public class SocketServer {

    @Inject
    RezervareService service;

    private final ExecutorService threadPool = Executors.newCachedThreadPool();

    void onStart(@Observes StartupEvent ev) {
        service.initDatabase();

        new Thread(this::startServer).start();
    }

    private void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(8888)) {
            System.out.println("=== SERVERUL A PORNIT PE PORTUL 8888 ===");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client nou conectat!");

                ClientHandler handler = new ClientHandler(clientSocket, service);
                threadPool.submit(handler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {
        private final Socket socket;
        private final RezervareService service;
        private final String clientToken;

        public ClientHandler(Socket socket, RezervareService service) {
            this.socket = socket;
            this.service = service;
            this.clientToken = "Client-" + UUID.randomUUID().toString().substring(0, 5);
        }

        @Override
        public void run() {
            try (
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
            ) {
                out.println("BINE AI VENIT! ID-ul tau este: " + clientToken);
                out.println("Comenzi disponibile: LIST, RESERVE <ora>, CANCEL <ora>, MY, EXIT");

                String line;
                while ((line = in.readLine()) != null) {
                    String[] parts = line.split(" ");
                    String command = parts[0].toUpperCase();
                    String raspuns = "";

                    try {
                        switch (command) {
                            case "LIST":
                                raspuns = service.getSloturiDisponibile();
                                break;
                            case "RESERVE":
                                if (parts.length < 2) raspuns = "Folosire: RESERVE <ora>";
                                else raspuns = service.rezervaLoc(Integer.parseInt(parts[1]), clientToken);
                                break;
                            case "CANCEL":
                                if (parts.length < 2) raspuns = "Folosire: CANCEL <ora>";
                                else raspuns = service.anuleazaRezervare(Integer.parseInt(parts[1]), clientToken);
                                break;
                            case "MY":
                                raspuns = service.getRezervarileMele(clientToken);
                                break;
                            case "EXIT":
                                out.println("La revedere!");
                                socket.close();
                                return; // Opreste thread-ul
                            default:
                                raspuns = "Comanda necunoscuta.";
                        }
                    } catch (NumberFormatException e) {
                        raspuns = "Eroare: Ora trebuie sa fie numar.";
                    } catch (Exception e) {
                        raspuns = "Eroare server: " + e.getMessage();
                    }

                    out.println(raspuns.replace("\n", " | "));
                }
            } catch (IOException e) {
                System.out.println("Client deconectat: " + clientToken);
            }
        }
    }
}