import java.io.IOException;

import javax.swing.JOptionPane;

public class App {
    public static void main(String[] args) {
        // Comprobamos que se haya proporcionado al menos un argumento
        if (args.length < 1) {
            System.out.println("Introduce al menos un argumento");
            System.exit(0);
        }

        // Guardamos el comando
        String command = args[0];

        // Concatenar los argumentos para formar la instrucción completa
        StringBuilder arguments = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            arguments.append(args[i]+" ");
        }

        try {
            int exitCode = ejecutarMetodo(command, arguments.toString());
            // Comprobamos el código de salida
            if (exitCode == 0) {
                System.out.println("Correcta ejecucion");
            } else {
                System.err.println("Error en la salida de la orden: " + exitCode);
            }
        } catch (IOException io) {
            JOptionPane.showMessageDialog(null, "Error en la ejecución del comando: " + io.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }catch(InterruptedException interruptedException){
            JOptionPane.showMessageDialog(null, "Error, se interrumpio la ejecucion.\n"+interruptedException.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static int ejecutarMetodo(String command, String arguments) throws IOException, InterruptedException {
        // Obtenemos el objeto Runtime
        Runtime runtime = Runtime.getRuntime();

        // Ejecutamos el comando con la instrucción completa
        Process process = runtime.exec(command + " " + arguments);

        // Esperamos a que el proceso hijo termine
        int exitCode = process.waitFor();

        return exitCode;
    }
}
