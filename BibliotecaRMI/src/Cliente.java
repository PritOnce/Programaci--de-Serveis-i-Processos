import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        InterfaceBiblio biblio = null;
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 5000);
            biblio = (InterfaceBiblio) registry.lookup("Biblioteca");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (biblio != null) {
            String orden;

            try {
                boolean salir = true;
                while (salir) {
                    Scanner sc = new Scanner(System.in);
                    System.out.println("Consultar libro: C " + "\n" +
                            "Eliminar un libro: E " + "\n" +
                            "Añadir un libro : A " + "\n" +
                            "Salir: S");

                    orden = sc.nextLine();

                    switch (orden.toUpperCase()) {
                        case "C":
                            System.out.println("Introduce la posicion que quieres buscar: ");
                            int posicion = sc.nextInt();
                            Libro libro = biblio.mostrarLibro(posicion);
                            System.out.println(libro.getNomLibro());
                            System.out.println(libro.getAutorLibro());
                            System.out.println(libro.getPrecioLibro());
                            System.out.println(libro.getValoracionLibro());
                            break;
                        case "S":
                            salir = false;
                            break;
                        default:
                            System.out.println("Orden no reconocida. Inténtalo de nuevo.");
                    }
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
