import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Servidor implements InterfazServidor{

    static ArrayList<InterfazCliente> usuarios = new ArrayList<>();

    public void sendMessage(String message, InterfazCliente cliente) throws RemoteException {
        System.out.println(message);
        for(InterfazCliente usuario: usuarios){
            if(usuario.getUUID() != cliente.getUUID()){
                usuario.reSendMessage(message);
            }
        }
    }

    public void addUsuario(InterfazCliente cliente) throws RemoteException{
        usuarios.add(cliente);
    }

    public void removeUser(InterfazCliente cliente) throws RemoteException{
        usuarios.remove(cliente);
    }

    public static void main(String[] args) {
        Servidor serverObject = new Servidor();

        Registry reg = null;
        try {
            reg = LocateRegistry.createRegistry(5555);
        } catch (Exception e) {
            System.out.println("ERROR: No se ha podido crear el registro");
            e.printStackTrace();
        }

        try {
            reg.bind("Chat", (InterfazServidor) UnicastRemoteObject.exportObject(serverObject, 0));
        } catch (Exception e) {
            System.out.println("ERROR: No se ha podido inscribir el objeto servidor.");
            e.printStackTrace();
        }
    }

}
