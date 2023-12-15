import java.util.ArrayList;
import java.util.Random;

public class Tienda {
    public static void main(String[] args) throws InterruptedException {
        int nCoches = Integer.parseInt(args[0]);
        int nPlazas = Integer.parseInt(args[1]);

        Parking parking = new Parking(nPlazas);
        parking.printPlazas();

        Coches[] coches = new Coches[nCoches];

        for(int i = 0; i < coches.length; i++){
            coches[i] = new Coches(parking);
            coches[i].start();
        }

        for(int i = 0; i < coches.length; i++){
            coches[i].join();
        }
    }
}

class Parking {

    private ArrayList<Coches> plazas;

    public Parking(int nPlazas){
        plazas = new ArrayList<>();
        for(int i = 0; i < nPlazas; i++ ){
            plazas.add(null);
        }
    }
    
    public synchronized void entrar(Coches coche) throws InterruptedException{
        while(!plazas.contains(null)){
            wait();
        }
        //entrar
        int plazaLibres = plazas.indexOf(null);
        plazas.set(plazaLibres, coche);

        printPlazas();
    }

    public synchronized void salir(Coches coche){
        //salimos
        int plazaLibres = plazas.indexOf(coche);
        plazas.set(plazaLibres, null);
        
        notifyAll();
        printPlazas();
    }

    public void printPlazas(){
        System.out.print("[");
        for(int i = 0; i < plazas.size(); i++){
            if(plazas.get(i) == null){
                System.out.print("------");
            }else{
                System.out.print(plazas.get(i).getName());
            }
            if(i != plazas.size() -1){
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
}

class Coches extends Thread{

    Parking parking;

    public Coches(Parking parking) {
        this.parking = parking;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                Random r = new Random();
            //paseamos
            System.out.println(getName() + ": Estoy paseando");
            Thread.sleep(r.nextInt(2000, 4000));
            //entramos
            System.out.println(getName() + ": Intento entrar");
            parking.entrar(this);
            //compramos
            System.out.println(getName() + ": Estoy dentro. Voy a comprar");
            Thread.sleep(r.nextInt(2000, 4000));
            //salimos
            System.out.println(getName() + ": Salgo...");
            parking.salir(this);
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
