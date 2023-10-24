import java.io.IOException;

public class Padre {
    public static void main(String[] args) throws IOException {
        char arrayChar[] = { 'a', 'e', 'i', 'o', 'u' };

        Process[] listaProcesos = new Process[arrayChar.length];
        for (int i = 0; i < arrayChar.length; i++) {
            String[] comando = ("java Hijo.java files/contadorLetras.txt " + String.valueOf(arrayChar[i])).split(" ");
            ProcessBuilder pb = new ProcessBuilder(comando);
            pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            listaProcesos[i] = pb.start();
        }
        
        try {
            for (Process proceso : listaProcesos) {
                int exitCode = proceso.waitFor();
                if (exitCode == 0) {
                    System.out.println("Correcta ejecucion del proceso");
                } else {
                    System.out.println("Error en la salida de la orden");
                }
            }

        } catch (InterruptedException ie) {
            System.out.println("Error: se ha interrumpido el proceso");
        } catch (Exception e) {
            System.out.println("Error general");
        }

    }
}
