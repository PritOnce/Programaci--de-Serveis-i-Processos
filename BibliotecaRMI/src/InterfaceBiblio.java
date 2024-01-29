import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceBiblio extends Remote{
    
    public Libro mostrarLibro(int posicion) throws RemoteException;
    public void addLibro(String nomLibro, String autorLibro, int precioLibro, int valoracionLibro) throws RemoteException;
    public void removeLibro(int posicion) throws RemoteException; 
}
