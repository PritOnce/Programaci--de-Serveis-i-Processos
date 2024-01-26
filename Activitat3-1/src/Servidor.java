import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        //Creo el servidor
        try {
            ServerSocket serverSocket = new ServerSocket(7777);

            //Esperamos una conexion
            System.out.println("Esperando conexion en el puerto 7777...");
            Socket conexion = serverSocket.accept();

            System.out.println("Cliente Conectado");
            //Cargamos el outputStream de la conexion para enviar el fichero
            PrintWriter printWriter = new PrintWriter(conexion.getOutputStream());

            //abrimos el fichero
            File file = new File("texto.txt");
            if(!file.exists()){
                System.out.println("El fichero no se encontro: " +file);
                System.exit(0);
            }

            BufferedReader fichero = new BufferedReader(new FileReader(file));

            //leemos 
            String linea = fichero.readLine();
            System.out.println("Empezaoms a enviar el fichero...");
            while (linea != null) {
                printWriter.println(linea);
                linea = fichero.readLine();
            }

            printWriter.flush();
            System.out.println("Fichero enviado con exito.");

            fichero.close();
            printWriter.close();
            conexion.close();
            serverSocket.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
