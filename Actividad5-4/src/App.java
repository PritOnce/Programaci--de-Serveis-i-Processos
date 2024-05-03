import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class App {
    public static void main(String[] args) {
        File ficheroLectura = new File("fichero.txt");
        File ficheroClave = new File("clave.key");
        File ficheroEncriptado = new File("encriptado.txt");
        File ficheroDesencriptado = new File("desencriptado.txt");

        encriptar(ficheroLectura, ficheroClave, ficheroEncriptado, "AES");
        desencriptar(ficheroEncriptado, ficheroClave, ficheroDesencriptado, "AES");

    }

    private static void encriptar(File ficheroLectura, File ficheroClave, File ficheroEncriptado, String algoritmo) {
        SecretKey sKey = null;
        KeyGenerator kgen  = null;
        
        try {
            //Generamos las claves
            kgen  = KeyGenerator.getInstance(algoritmo);
            kgen.init(256);
            sKey = kgen.generateKey();

            FileOutputStream fos = new FileOutputStream(ficheroClave);
            fos.write(sKey.getEncoded());
            fos.flush();
            fos.close();

            // Encriptamos el fichero
            FileInputStream fis = new FileInputStream(ficheroLectura);
            byte[] dataFichero = fis.readAllBytes();

            Cipher cipher = Cipher.getInstance(algoritmo);
            cipher.init(Cipher.ENCRYPT_MODE, sKey);
            byte[] dataEncrypted = cipher.doFinal(dataFichero);

            FileOutputStream fos2 = new FileOutputStream(ficheroEncriptado);
            fos2.write(dataEncrypted);
            fos2.flush();
            fos2.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void desencriptar(File ficheroLectura, File ficheroClave, File ficheroDesencriptado, String algoritmo) {
     //Leemos la clave
        try {
            FileInputStream fis = new FileInputStream(ficheroClave);
            byte[] dataClave = fis.readAllBytes();
            fis.close();

            SecretKey sKey = new SecretKeySpec(dataClave, algoritmo);

            FileInputStream fis2 = new FileInputStream(ficheroLectura);
            byte[] dataFichero = fis2.readAllBytes();
            fis2.close();

            Cipher cipher = Cipher.getInstance(algoritmo);
            cipher.init(Cipher.DECRYPT_MODE, sKey);
            byte[] dataDesencriptado = cipher.doFinal(dataFichero);

            FileOutputStream fos = new FileOutputStream(ficheroDesencriptado);
            fos.write(dataDesencriptado );
            fos.flush();
            fos.close();
            
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
