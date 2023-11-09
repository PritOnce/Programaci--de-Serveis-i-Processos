import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;

/*
 * @PritOnce
 * Docs: https://es.stackoverflow.com/questions/404225/ficheros-y-procesos-en-java
 */

public class Padre {
    public static void main(String[] args) throws Exception {
        //cramos el array de chars y la variable que guardará la cantidad de vocales que hay en el archivo
        char arrayChar[] = { 'a', 'e', 'i', 'o', 'u' };
        int suma=0;

        //inicializamos todos los procesos y les pasamos el comando
        Process[] listaProcesos = new Process[arrayChar.length];
        for (int i = 0; i < arrayChar.length; i++) {
            String[] comando = ("java ../../Actividad1-3/src/Hijo.java ../../Actividad1-3/src/files/contadorLetras.txt "
                    + String.valueOf(arrayChar[i])).split(" ");
            ProcessBuilder pb = new ProcessBuilder(comando);
            listaProcesos[i] = pb.start();
        }

        //en este método por cada proceso que se esté realizando leerá un salida por linea de comando
        try {
            for (Process proceso : listaProcesos) {

                //lo agenciamos al proceso 
                InputStream is = proceso.getInputStream();
                //leemos el proceso
                BufferedReader bf = new BufferedReader(new InputStreamReader(is));

                String linea;
                //abrimos un while para que lea las lineas del sout y lo guardo en un fichero(opcional)
                while ((linea = bf.readLine()) != null) {
                    FileWriter fw = new FileWriter("./files/letras.txt", true);
                    fw.write(linea+"\n");
                    fw.close();

                    //separamos lo que obtenemos por el string linea por un ": "
                    //así dandonos una array con dos lugares
                    String[] numerosString = linea.split(": ");
                    try {
                        //obtenemos el numero de la posición 1 del array que es la que contiene el
                        //numero de vocales y lo sumo a la variable suma que declaramos al principio
                        int numero = Integer.parseInt(numerosString[1]);
                        suma += numero;
                    } catch (NumberFormatException nfe) {
                        nfe.getMessage();
                    }catch(Exception e){
                        e.getMessage();
                    }
                }
                //cerramos la lectura
                bf.close();

                int exitCode = proceso.waitFor();
                if (exitCode != 0) {
                    System.out.println("Error");
                }
            }

        } catch (InterruptedException ie) {
            System.out.println("Error: se ha interrumpido el proceso");
        } catch (Exception e) {
            System.out.println("Error general");
        }
        //hacemos el sout de la cantidad de vocales que hay
        System.out.println("Hi ha un total de " +suma+ " vocals");

    }
}
