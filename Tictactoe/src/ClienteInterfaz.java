import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClienteInterfaz extends Remote{
    public void mostrarMensaje(String mensaje) throws RemoteException;
}
