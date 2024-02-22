import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Servidor implements InterfaceBiblio {

    ArrayList<Libro> libros = new ArrayList<>();

    public Libro mostrarLibro(int posicion) throws RemoteException {
        return libros.get(posicion);
    }

    public void addLibro(String nomLibro, String autorLibro, int precioLibro, int valoracionLibro)
            throws RemoteException {
        Libro newLibro = new Libro(nomLibro, autorLibro, precioLibro, valoracionLibro);
        libros.add(newLibro);
        System.out.println("Libro añadido");
    }

    public void removeLibro(int posicion) throws RemoteException {
        libros.remove(posicion);
        System.out.println("Libro eliminado");
    }

    public static void main(String[] args) throws RemoteException {
        Servidor serverObject = new Servidor();

        ArrayList<Libro> librosPorDefecto = new ArrayList<>();
        librosPorDefecto.add(new Libro("El señor de los anillos", "J.R.R. Tolkien", 25, 5));
        librosPorDefecto.add(new Libro("Cien años de soledad", "Gabriel García Márquez", 20, 4));
        librosPorDefecto.add(new Libro("1984", "George Orwell", 18, 4));
        librosPorDefecto.add(new Libro("To Kill a Mockingbird", "Harper Lee", 15, 4));
        librosPorDefecto.add(new Libro("Harry Potter y la piedra filosofal", "J.K. Rowling", 22, 5));
        librosPorDefecto.add(new Libro("Don Quijote de la Mancha", "Miguel de Cervantes", 30, 5));
        librosPorDefecto.add(new Libro("The Catcher in the Rye", "J.D. Salinger", 16, 4));
        librosPorDefecto.add(new Libro("The Great Gatsby", "F. Scott Fitzgerald", 17, 4));

        serverObject.libros.addAll(librosPorDefecto);
        
        Registry reg = null;
        try {
            reg = LocateRegistry.createRegistry(5000);
        } catch (Exception e) {
            System.out.println("ERROR: No se ha podido crear el registro");
            e.printStackTrace();
        }

        try {
            reg.bind("Biblioteca", (InterfaceBiblio) UnicastRemoteObject.exportObject(serverObject, 0));
        } catch (Exception e) {
            System.out.println("ERROR: No se ha podido inscribir el objeto servidor.");
            e.printStackTrace();
        }
    }

}
