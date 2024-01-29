import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        InterfaceBiblio biblio = null;
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 5555);
            biblio = (InterfaceBiblio) registry.lookup("Biblioteca");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (biblio != null) {
            String orden;

            try {
                Scanner sc = new Scanner(System.in);
                System.out.println("Consultar libro: C " + "\n" +
                "Eliminar un libro: E " + "\n" +
                "Añadir un libro : A ");

                orden = sc.nextLine();

                if(orden.equalsIgnoreCase("C")){
                    System.out.println("Introduce la posicion que quieres buscar: ");
                    int posicion = sc.nextInt();
                    Libro libro = biblio.mostrarLibro(posicion);
                    System.out.println(libro.getNomLibro());
                    System.out.println(libro.getAutorLibro());
                    System.out.println(libro.getPrecioLibro());
                    System.out.println(libro.getValoracionLibro());
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

}
