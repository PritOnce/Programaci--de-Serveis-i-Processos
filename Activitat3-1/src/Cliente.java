import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) {
        try {
            //creamos la conexion
            System.out.println("Intentamos conectarnos a localhost 7777");
        
            Socket conexion = new Socket("localhost", 7777);
            System.out.println("Conectado con exito");

            //nos conectamos para la lectura
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conexion.getInputStream()));

            //Creamos el fichero donde guardaremos lo que hemos recibido
            System.out.println("Creando el fichero recibido.txt...");
            File file = new File("recibido.txt");
            if (file.exists()) {
                System.out.println("El fichero ya existe, intenta eliminarlo.");
                file.delete();
                System.out.println("Fichero eliminado");
            }

            PrintWriter fichero = new PrintWriter(new FileWriter("recibido.txt"));
            System.out.println("Fichero creado");

            //leemos la conexion
            System.out.println("Empezamos a leer del socket y guardar en el fichero");
            String linea = bufferedReader.readLine();
            while (linea != null) {
                fichero.println(linea);
                linea = bufferedReader.readLine();
            }

            fichero.flush();
            System.out.println("Lectura concluida y fichero guardado");

            fichero.close();
            conexion.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
