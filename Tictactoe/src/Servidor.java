import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class Servidor implements ServidorInterfaz{
    
    private Map<String, ClienteInterfaz> clientes;
    private Tablero tablero;
    private boolean juegoIniciado;

    public Servidor() throws RemoteException {
        clientes = new HashMap<>();
        tablero = new Tablero();
        juegoIniciado = false;
    }

    @Override
    public synchronized boolean conectar(int fila, int columna, Fichas ficha, String clienteID) throws RemoteException {
        if (!clientes.containsKey(clienteID)) {
            return false; // El cliente no está registrado
        }

        if (!juegoIniciado) {
            return false; // El juego no ha comenzado, no se pueden hacer movimientos aún
        }

        if (!tablero.validarMovimiento(fila, columna)) {
            return false; // Movimiento inválido
        }

        tablero.setFicha(fila, columna, ficha);
        mostrarTablero(clienteID);
        return true;
    }

    @Override
    public synchronized void mostrarTablero(String clienteID) throws RemoteException {
        if (!clientes.containsKey(clienteID)) {
            return; // El cliente no está registrado
        }

        ClienteInterfaz cliente = clientes.get(clienteID);
        cliente.mostrarMensaje(tablero.toString());
    }

    public static void main(String[] args) throws RemoteException {
        Servidor servidorObject = new Servidor();

        Registry reg = null;
        try{
            reg = LocateRegistry.createRegistry(7777);
        }catch(Exception e){
            System.out.println("ERROR: No se ha podido crear el registro");
            e.printStackTrace();
        }

        try {
            reg.bind("Tictatoe", (ServidorInterfaz) UnicastRemoteObject.exportObject(servidorObject, 0));
        } catch (Exception e) {
            System.out.println("ERROR: No se ha podido inscribir el objeto servidor.");
            e.printStackTrace();
        }
    }
}
