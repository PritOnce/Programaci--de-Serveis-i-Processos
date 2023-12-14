import java.util.concurrent.Semaphore;

public class Rent {
    public static void main(String[] args) throws Exception {
        /*
        Creo un semaforo con 3 espacios que sera el maximo espacio que tendrá mi parking
        Creo 10 coches, pero no todos acabarán entrando o saliendo
        Y una caja por cada coche que hay
        */
        Semaphore semaphore = new Semaphore(3);
        Coches [] coches = new Coches[10];
        Caja[] caja = new Caja[coches.length];

        //Creo todas las cajas por coche
        for(int e = 0; e < caja.length; e++){
            caja[e] = new Caja();
        }

        //Inicio el for para generar los coches(Hilos) y le paso el semaforo
        //el i para darle un id y una caja a cada uno
        for(int i = 0; i < coches.length ; i++){
            Thread t = new Thread(new Coches(semaphore, i, caja[i]));
            t.start();
        }
    }
}


class Coches implements Runnable{

    /*
    Creo las variables y ademas coches salidos para controlar el while de mas adelante
    y no dejar en bucle el programa
    */
    private Semaphore semaphore;
    private int id;
    private Caja caja;
    private static int cochesSalidos = 0;

    public Coches(Semaphore semaphore, int id, Caja caja){
        this.semaphore = semaphore;
        this.id = id;
        this.caja = caja;
    }

    //Inicio los hilos
    @Override
    public void run() {
    try {
        
        //Habro un while para continuar lo que yo quiera y controlarlo despues con la variabel cochesSalidos
        while (true) {
            //Genero un numero random para darle un tiempo aleatorio a lo que esta el coche en el parking
            //y para que espere para volver a entrar si "quiere"
            long tiempo = (long) ((Math.random() * 5000) + 1000);
            System.out.println("El coche: " + id + " esta en la fila");
            System.out.println("--------");
            semaphore.acquire();
            System.out.println("El coche: " + id + " ha entrado");
            System.out.println("--------");
            Thread.sleep( tiempo );
            System.out.println("El coche " + id + " ha salido a circular");
            System.out.println("--------");
            semaphore.release();
            //Sumo 5 al contador de cada coche que haya salido
            //Y sumo 1 para controlar cuantos coches han salido
            caja.setPrecio(5);
            cochesSalidos++;
            System.out.println("El coche: " + id + " acumula " + caja.getPrecio() + " Euros");
            System.out.println("--------");
            Thread.sleep(tiempo);//Controla el tiempo que el coche está "circulando"
            //Cuando cochesSalidos llega a 5 el while finaliza y acaba
            if(cochesSalidos >= 5){
                System.exit(0);
            }
        }

    } catch (InterruptedException ie) {
        System.out.println("Se ha interrumpido la ejecucion");
    } catch (Exception e) {
        System.out.println("Error general");
    }
}

}


class Caja{
    private int precio;

    //Mostrar lo recaudado por coche
    public int getPrecio() {
        return precio;
    }

    //Set que ira cambiando por cada coche que sale con synchronized para evitar la perdida de datos
    public synchronized void setPrecio(int coste) {
        precio = precio + coste;
    }

    
}
