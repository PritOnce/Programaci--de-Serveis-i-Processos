import java.io.IOException;

import javax.swing.JOptionPane;

public class App {
    public static void main(String[] args) {
        //Llamamos a la clase intrucciones
        instrucciones instrucciones = new instrucciones();
        //Creamos un if para que controle que el usuario ha introducido algún argumento
        if(args.length > 0){
            /*
             * En el caso que sea así abrimos un for para que se lean todos 
             * los argumentos que el usuario a pasado y los guarde en una variable para
             * despues pasarlos al metodo ejecutarMetodo
             */
            for(int i=0; i< args.length; i++){
                String comand = args[i];
            instrucciones.ejecutarMetodo(comand);
            }
        }else{
            //En el caso que no sea así saltará un mensaje de error
            JOptionPane.showMessageDialog(null, "Porfavor, introduce algun argumento");
            System.exit(0);
        }
    }
}

class instrucciones {
    public instrucciones(){

    }

    public void ejecutarMetodo(String command){

        try {
            //Iniciamos el proceso y pasamos por parametro el comando que queremos ejecutar y lo inicia
            Process process = new ProcessBuilder(command).start();
            //cargamos el proceso en un int para y esperamos a que acabe y obtengamos el código
            int comandOutput = process.waitFor();
            int value = process.exitValue();
            System.out.println(value);
            //si la variaable codigoSalida es igual a 0 el comando se ejecutará con éxito
            if (comandOutput == 0) {
                JOptionPane.showMessageDialog(null,"La ejecucion del comando se completo con exito.");
            } else {
                //en el caso que no sea así saltará un mensaje de error
               JOptionPane.showMessageDialog(null, "Error durante la ejecucion del comando: " + comandOutput, "ERROR", JOptionPane.WARNING_MESSAGE);
            }
            
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al ejecutar el comando: " +"\n" + e.getMessage(), "ERROR", JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        }catch(InterruptedException e){
            JOptionPane.showMessageDialog(null, "Error durante la espera de la ejecucion: " +"\n"+ e.getMessage(), "ERROR", JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        }

    }
}

