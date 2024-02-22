import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServidorInterfaz extends Remote{
    
    public boolean conectar(int fila, int columna, Fichas ficha, String clienteID) throws RemoteException;
    public void mostrarTablero(String clienteID) throws RemoteException;

}
