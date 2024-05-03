import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.MessageDigest;

/**
 *
 * @author prito
 */
public class App {
    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("Introduce dos argumentos");
            System.exit(0);
        }

        String algoritmo = args[0];
        File file = new File(args[1]);

        FileInputStream fis = new FileInputStream(file);
        FileOutputStream fos = new FileOutputStream("data.hash");

        if (!((algoritmo.equals("SHA-256")) || (algoritmo.equals("MD5")) || (algoritmo.equals("SHA-1")))) {
            System.out.println("Introduce un algoritmo valido");
            System.exit(0);
        }

        if(!file.exists()){
            System.out.println("El fichero no existe");
            System.exit(0);
        }

        MessageDigest md = MessageDigest.getInstance(algoritmo);

        byte dataToHash[] = fis.readAllBytes();

        byte[] hash = md.digest(dataToHash);

        fos.write(hash);
        fos.flush();
        fos.close();
        
    } 
}
