import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) throws Exception {

        InetAddress ip = InetAddress.getByName("224.0.0.1");
        InetSocketAddress grupo = new InetSocketAddress(ip, 6789);

        NetworkInterface interfaz = null;
        interfaz = NetworkInterface.getByName("172.16.26.27");
        MulticastSocket chat = null;
        chat = new MulticastSocket(6789);

        chat.joinGroup(grupo, interfaz);

        Enviar enviar = new Enviar(grupo, chat);
        Recibir recibir = new Recibir(chat);
        enviar.start();
        recibir.start();
    }
}

class Enviar extends Thread {

    private InetSocketAddress grupo;
    private MulticastSocket chat;

    public Enviar(InetSocketAddress grupo, MulticastSocket chat) {
        this.grupo = grupo;
        this.chat = chat;
    }

    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            try {
                String mensage = sc.nextLine();
                byte[] data = mensage.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(data, data.length, grupo);
                chat.send(sendPacket);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }
}

class Recibir extends Thread {

    private MulticastSocket chat;

    public Recibir(MulticastSocket chat) {
        this.chat = chat;
    }

    @Override
    public void run() {
        try {

            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            while (true) {
                chat.receive(packet);
                String mensageRecibido = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Mensaje recibido: " + mensageRecibido);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
