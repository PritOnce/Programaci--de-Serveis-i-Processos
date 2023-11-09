import java.util.concurrent.Semaphore;

public class Rent {
    public static void main(String[] args) throws Exception {
        Semaphore semaphore = new Semaphore(3);
        Coches [] coches = new Coches[10];
        Caja[] caja = new Caja[coches.length];

        for(int e = 0; e < caja.length; e++){
            caja[e] = new Caja();
        }

        for(int i = 0; i < coches.length ; i++){
            Thread t = new Thread(new Coches(semaphore, i, caja[i]));
            t.start();
        }
    }
}


class Coches implements Runnable{

    private Semaphore semaphore;
    private int id;
    private Caja caja;
    private static int cochesSalidos = 0;

    public Coches(Semaphore semaphore, int id, Caja caja){
        this.semaphore = semaphore;
        this.id = id;
        this.caja = caja;
    }

    @Override
    public void run() {
    try {
        
        while (true) {
            long tiempo = (long) ((Math.random() * 5000) + 1000);
            System.out.println("El coche: " + id + " esta en la fila");
            semaphore.acquire();
            System.out.println("El coche: " + id + " ha entrado");
            Thread.sleep( tiempo );
            System.out.println("El coche " + id + " ha salido a circular");
            semaphore.release();
            caja.setPrecio(5);
            cochesSalidos++;
            System.out.println("El coche: " + id + " acumula " + caja.getPrecio() + " Euros");
            Thread.sleep(tiempo);
            if(cochesSalidos >= 5){
                System.out.println(cochesSalidos);
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
    public int length;
    private int precio;

    public synchronized int getPrecio() {
        return precio;
    }

    public synchronized void setPrecio(int coste) {
        precio = precio + coste;
    }

    
}
