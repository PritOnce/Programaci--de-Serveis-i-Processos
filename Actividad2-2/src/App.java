import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws Exception {
        //creamos el array que contendrá las vocales a buscar
        char [] vocales = {'a', 'e', 'i', 'o', 'u'};
        //instanciamos la clase memorai y contador para que cada una haga una cosa
        Memoria memoria = new Memoria();
        Contador [] fils = new Contador[vocales.length];
        
        //creo un for donde crearé los robots a los cuanles les pasaré una memoria y la vocal que tenga 
        //que buscar. Esto ayuda a que todos compartan la misma memoria
        //e iniciamos los hilos
        for(int i = 0; i < vocales.length; i++){
            fils[i] = new Contador(memoria, vocales[i]);
            fils[i].start();
        }

        //finalizamos los hilos
        for(Contador fil : fils){
            fil.join();
        }

        System.out.println(memoria.getTotalVocales());
    }
}


class Contador extends Thread{

    //instaciamos un objeto del tipo memoria para recibir una memoria 
    //distinta por cada hilo
    Memoria memoria;
    char vocales;

    public Contador(Memoria memoria, char vocales) {
        this.memoria = memoria;
        this.vocales = vocales;
    }

    @Override
    public void run(){
        //creamos el método run para lanzar los hilos
        //abrimos un try y un catch para control de errores
        //y un BufferedReader para leer el archivo
        try (BufferedReader br = new BufferedReader(new FileReader("./files/text2.txt"))) {
            //creamos una variable del tipo int para almacenar las letras en ascii del archivo
            int vocal = 0;

            //y un while para que las obtenga mientras no se haya acabado el archivo
            while ((vocal = br.read()) != -1) {
                //las casteamos y pasamos a un char para despues comprobar que sean minusculas y que esté en el array de vocales
                char letra = (char) vocal;
                    if(Character.isLowerCase(letra) && letra == vocales){
                        //si es así le pasaré mas uno al método de la clase Memoria
                        memoria.setTotalVocales(1);
                    }
            }
            //cerramos la lectura
            br.close();
            

        } catch (IOException ioe) {
            System.out.println("Error en I/O");
        }catch(Exception e){
            System.out.println("Error general");
        }
    }
}


//creamos la clase memoria que tiene dos métodos uno para pasarle el total de vocales y otra para mostrarlo
class Memoria{
    private int totalVocales;

    public int getTotalVocales() {
        return totalVocales;
    }

    public void setTotalVocales(int vocal) {
        totalVocales = totalVocales + vocal;
    }

}