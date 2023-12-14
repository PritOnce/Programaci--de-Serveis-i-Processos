import java.util.Random;

public class Rescate {
    public static void main(String[] args) throws Exception {
        Cima cima = new Cima(57);
        Helicopteros h1 = new Helicopteros(cima, 5);
        Helicopteros h2 = new Helicopteros(cima, 3);
        Helicopteros h3 = new Helicopteros(cima, 1);

        System.out.println("Empieza el rescate");

        h1.start();
        h2.start();
        h3.start();

        h1.join();
        h2.join();
        h3.join();

        System.out.println("Rescate finalizado");
    }
}

class Cima {
    private int atrapados;

    public Cima(int atrapados) {
        this.atrapados = atrapados;
    }

    public int getAtrapados() {
        return atrapados;
    }

    public synchronized int rescatar(Helicopteros helicopteros) throws InterruptedException {
        System.out.println(helicopteros.getName() + ": estoy en la cima. Procedo al rescate");
        Thread.sleep(new Random().nextInt(2000, 4000));
        int rescatados = 0;
        if (helicopteros.capacidad > atrapados) {
            // solo a los atrapados
            rescatados = atrapados;
        } else {
            rescatados = helicopteros.capacidad;
        }

        // los rescatamos
        atrapados -= rescatados;

        System.out.println(helicopteros.getName() + ": estoy en la cima. He rescatado a " + rescatados + " atrapados. Quedan " + atrapados);

        return rescatados;
    }
}

class Helicopteros extends Thread {
    Cima cima;
    int capacidad;

    public Helicopteros(Cima cima, int capacidad) {
        this.cima = cima;
        this.capacidad = capacidad;
    }

    @Override
    public void run() {
        Random r = new Random();
        while (cima.getAtrapados() > 0) {
            try {
                // simular rescate
                // ida
                System.out.println(getName() + ": vamos a la cima");

                Thread.sleep(r.nextInt(2000, 4000));

                // carga
                int rescatados = cima.rescatar(this);
                // vuelta
                System.out.println(getName() + ": vamos a la base");
                Thread.sleep(r.nextInt(2000, 4000));
                // descarga
                System.out.println(getName() + ": vamos a la base. En este viaje, he rescatado a " + rescatados);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
