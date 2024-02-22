import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.UUID;

public class Cliente extends UnicastRemoteObject implements ClienteInterfaz {
    public Cliente() throws RemoteException {}

    @Override
    public void mostrarMensaje(String mensaje) throws RemoteException {
        System.out.println("Tablero recibido del servidor:");
        System.out.println(mensaje);
    }

    public static void main(String[] args) {
        ServidorInterfaz servidor = null;
        Cliente cliente = null;
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 5555);
            servidor = (ServidorInterfaz) registry.lookup("Tictactoe");

            cliente = new Cliente();
            servidor.registrarCliente("cliente", cliente);
            System.out.println("Conexión establecida con el servidor.");

            Scanner scanner = new Scanner(System.in);
            System.out.println("Esperando a otro jugador...");
            // Esperar a que otro jugador se conecte (lógica adicional aquí)

            while (true) {
                System.out.println("Ingrese la fila (0-2):");
                int fila = scanner.nextInt();
                System.out.println("Ingrese la columna (0-2):");
                int columna = scanner.nextInt();

                // Envía el movimiento al servidor
                boolean movimientoValido = servidor.conectar(fila, columna, Fichas.X, "cliente");

                if (!movimientoValido) {
                    System.out.println("Movimiento inválido. Intente nuevamente.");
                }
            }
        } catch (Exception e) {
            System.err.println("Error en el cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
