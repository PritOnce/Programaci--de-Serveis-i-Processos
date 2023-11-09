import java.util.concurrent.Semaphore;

public class Rent {
    public static void main(String[] args) throws Exception {
        //creao el semaforo con la cantidad de coches que pueden haber
        //en el parking al mismo tiempo
        Semaphore semaphore = new Semaphore(3);
        Coches [] coches = new Coches[5];
        Caja[] caja = new Caja[coches.length];

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

    public Coches(Semaphore semaphore, int id, Caja caja){
        this.semaphore = semaphore;
        this.id = id;
        this.caja = caja;
    }

    @Override
    public void run() {
        try {
            
            System.out.println("El coche: " +id+ " esta en la fila");
            semaphore.acquire();
            System.out.println("El coche: " +id+ " ha entrado");
            Thread.sleep((long) ((Math.random()*9000)+1000));
            System.out.println("El coche " +id+ " ha salido a circular");
            semaphore.release();
            System.out.println("El coche: " +id+ " acumula " +caja.getPrecio()+ " Euros");
            caja.setPrecio(5);
        } catch (InterruptedException ie) {
            System.out.println("Se ha interrumpido la ejecucion");
        }catch(Exception e){
            System.out.println("Error general");
        }
    }

}

class Caja{
    private int precio;

    public synchronized int getPrecio() {
        return precio;
    }

    public synchronized void setPrecio(int coste) {
        precio = precio + coste;
    }

    
}