import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

public interface InterfazServidor extends Remote{

    public void sendMessage(String message, InterfazCliente cliente) throws RemoteException;
    public void addUsuario(InterfazCliente cliente) throws RemoteException;
    public void removeUser(InterfazCliente cliente) throws RemoteException;

     
}