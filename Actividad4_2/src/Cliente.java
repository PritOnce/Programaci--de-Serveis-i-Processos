import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.UUID;

public class Cliente extends UnicastRemoteObject implements InterfazCliente {

    public UUID uuid;

    protected Cliente() throws RemoteException {
        super();
        uuid = UUID.randomUUID();
    }

    public void reSendMessage(String message) {
        System.out.println(message);
    }

    public UUID getUUID(){
        return uuid;
    }

    public static void main(String[] args) {
        InterfazServidor chat = null;
        Cliente cliente = null;
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 5555);
            chat = (InterfazServidor) registry.lookup("Chat");

            cliente = new Cliente();
            chat.addUsuario(cliente);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (chat != null) {
            String mensage;

            try {
                String comandoSalir = "S";
                System.out.println("Para salir del chat envia S");
                while (true) {
                    Scanner sc = new Scanner(System.in);

                    mensage = sc.nextLine();

                    if (mensage.equalsIgnoreCase(comandoSalir)) {
                        chat.removeUser(cliente);
                        System.exit(0);
                    } else {
                        chat.sendMessage(mensage, cliente);

                    }
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
