import java.util.Random;

public class Carrera {
    public static void main(String[] args) throws Exception {
        Robots [] robots = new Robots[5];

        for(int i = 0; i<robots.length; i++){
            robots[i] = new Robots(0);
            robots[i].start();
        }

        try{
            for(int i = 0; i<robots.length; i++){
            robots[i].join();
            }
        }catch(InterruptedException ie){
            ie.getMessage();
        }
        
        
    }
}

class Robots extends Thread{


    private int distancia;

    public Robots(int distancia) {
        this.distancia = 0;
    }

    @Override
    public void run(){
        super.run();

        while(distancia<=100){
            int metros = new Random().nextInt(13) - 3;
            distancia = distancia + metros;
            System.out.println("El robot " + getName() + " lleva " + distancia + "metros recorridos");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ie) {
                ie.getMessage();
            }
        }
        System.out.println("El robot " +getName()+ " ha llegado a la cima");
    }

}
