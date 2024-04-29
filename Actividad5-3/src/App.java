import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.util.HexFormat;

public class App {
    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("Introduce dos argumentos");
            System.exit(0);
        }

        String algoritmo = args[0].toString();
        File file = new File(args[1].toString());

        FileInputStream fis = new FileInputStream(file);

        if (!((algoritmo.equals("SHA-256")) || (algoritmo.equals("MD5")) || (algoritmo.equals("SHA-1")))) {
            System.out.println("Introduce un algoritmo valido");
            System.exit(0);
        }

        if(!file.exists()){
            System.out.println("El fichero no existe");
            System.exit(0);
        }

        MessageDigest md = MessageDigest.getInstance(algoritmo);

        byte[] demoArray = new byte[ (int) file.length() ];

        fis.read(demoArray);
        fis.close();

        byte[] hash = md.digest(demoArray);

        System.out.println(HexFormat.of().formatHex(hash));
    } 
}
