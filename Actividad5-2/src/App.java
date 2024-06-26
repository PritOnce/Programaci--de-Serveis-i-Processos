import java.security.MessageDigest;
import java.util.HexFormat;

public class App {
    public static void main(String[] args) throws Exception {
        //Pedimos los argumentos por argumentos
        String algoritmo = args[0];
        String message = args[1];

        MessageDigest md = MessageDigest.getInstance(algoritmo);

        byte[] hash = md.digest(message.getBytes());

        System.out.println(HexFormat.of().formatHex(hash));
    }
}
