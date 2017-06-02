/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.IOException;
import java.net.ServerSocket;
import javax.swing.SwingWorker;

/**
 *
 * @author haibk
 */
public class Server {

    public Server() {

    }

    public void startServer(int port) {
        final SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

            @Override
            protected Void doInBackground() throws IOException {
                try {
                    ServerSocket serverSocket = new ServerSocket(port);
                    System.out.println("Server started!");
                    while (true) {
                        Runnable t = new ThreadServer(serverSocket.accept());
                        new Thread(t).start();
                        System.out.println("Client accept");
                    }

                } catch (IOException e) {
                    System.out.println("Client stopped sending request!");

                }
                return null;
            }

            @Override
            protected void done() {
            }
        };
        worker.execute();
    }

}
