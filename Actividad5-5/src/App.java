
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class App {

    public static void main(String[] args) throws Exception {
        File file = new File("data.txt");
        File fileToSafeEncryptData = new File("data.encrypted");
        String algoritmo = "AES";
        String key = "manoloGonzalez";

        try {
            //Leo los datos del fichero
            FileInputStream fis = new FileInputStream(file);
            byte[] data = fis.readAllBytes();
            fis.close();

            //Encripto la "key" que me han pasado
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashKey = md.digest(key.getBytes());

            //Creo la key
            SecretKeySpec sKey = new SecretKeySpec(hashKey, algoritmo);

            //Encriptamos los datos
            Cipher cipher = Cipher.getInstance(algoritmo);
            cipher.init(Cipher.ENCRYPT_MODE, sKey);
            byte[] encryptedData = cipher.doFinal(data);

            //Escribo los datos encriptados
            FileOutputStream fos = new FileOutputStream(fileToSafeEncryptData);
            fos.write(encryptedData);
            fos.flush();
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
