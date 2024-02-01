package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private final static ArrayList<Socket> connections = new ArrayList<>();
    private final static ArrayList<String> mensajes = new ArrayList<>();

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(4224);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            // Metrics metrics = new Metrics();
            // metrics.start();
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Conectado con exito");
                connections.add(socket);
                ConnectionServer conection = new ConnectionServer(socket);
                conection.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // static class Metrics extends Thread{
    //     @Override
    //     public void run() {
    //         while(true) {
    //             if(!mensajes.isEmpty()){
    //                 System.out.println("Mensajes:");
    //                 for (String mensaje : mensajes){
    //                     System.out.println(mensaje);
    //                 }
    //             }
    //             try {
    //                 Thread.sleep(5000);
    //             } catch (InterruptedException e) {
    //                 throw new RuntimeException(e);
    //             }
    //         }
    //     }
    // }

    static class ConnectionServer extends Thread {

        Socket socket;

        public ConnectionServer(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            BufferedWriter bw;
            BufferedReader br;
            try {
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String line;
                while ((line = br.readLine()) != null) {
                    mensajes.add(line);
                    for (Socket sock : connections) {
                        if(sock!=socket){
                            bw = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
                        bw.write(line + "\n");
                        bw.flush();
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
}


