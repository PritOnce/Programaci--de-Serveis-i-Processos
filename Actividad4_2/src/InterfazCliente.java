import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

public interface InterfazCliente extends Remote{

    public void reSendMessage(String message) throws RemoteException;
    public UUID getUUID();
    
}