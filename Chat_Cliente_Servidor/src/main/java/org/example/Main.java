package org.example;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost",4224);
            if(socket.isConnected()){
                Leer leer = new Leer(socket);
                leer.start();
                Escribir escribir = new Escribir(socket);
                escribir.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

class Leer extends Thread{
    Socket socket;

    public Leer(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while(true){
                String line = br.readLine();
                System.out.println(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

class Escribir extends Thread{
    Socket socket;
    Scanner sc;

    public Escribir(Socket socket) {
        this.socket = socket;
        sc = new Scanner(System.in);
    }

    @Override
    public void run() {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            while(true) {
                if (sc.hasNextLine()) {
                    bw.write(sc.nextLine()+ "\n");
                    bw.flush();
                }
            }
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}