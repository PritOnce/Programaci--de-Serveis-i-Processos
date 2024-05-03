
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.MessageDigest;


public class CompareHash {

    public static void main(String[] args) {

        String algoritmo = args[0];

        FileOutputStream fos = null;
        FileInputStream fis = null;

        try {

            MessageDigest md = MessageDigest.getInstance(algoritmo);

            fis = new FileInputStream("data.txt");
            byte[] data = fis.readAllBytes();

            byte[] hashParaComparar = md.digest(data);

            fis = new FileInputStream("data.hash");

            byte[] hashComparador = fis.readAllBytes();

            if (MessageDigest.isEqual(hashParaComparar, hashComparador)) {
                System.out.println("Los ficheros son iguales");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
