import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JOptionPane;

public class Hijo {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            JOptionPane.showMessageDialog(null, "Por favor, introduce dos argumentos:\nEl path del archivo y la vocal a buscar");
            System.exit(1);
        }

        String comando = args[0];
        char letraBuscada = args[1].charAt(0);

        contadorDeLetras cLetras = new contadorDeLetras(comando, letraBuscada);
        int contador = cLetras.contarLetras();
        System.out.println(letraBuscada+": "+ contador);
        
    }
}

class contadorDeLetras {

    private char letraCargada;
    private String comando;

    public contadorDeLetras(String comando, char letraCargada) {
        this.comando = comando;
        this.letraCargada = letraCargada;
    }

    public int contarLetras() throws IOException {
        int contador = 0;
        try (BufferedReader bf = new BufferedReader(new FileReader(comando))) {
            String linia;
            while ((linia = bf.readLine()) != null) {
                for (char letra : linia.toCharArray()) {
                    if (Character.toUpperCase(letra) == Character.toUpperCase(letraCargada)) {
                        contador++;
                    }
                }
            }
        }catch(IOException e){
            JOptionPane.showMessageDialog(null, "Error al leer el archivo: " + e.getMessage());
        }catch(Exception e){
            System.out.println("Error General");
        }
        return contador;
    }
}
