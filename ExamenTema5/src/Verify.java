import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class Verify {
    public static void main(String[] args) {
        FileInputStream fis = null;

        try {

            //leo la clave privada
            fis = new FileInputStream("public.key");
            byte[] keyPublic = fis.readAllBytes();

            //Refactorizo para convertirlo en Public Key
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyPublic);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

            //Leo el mensaje del fichero
            fis = new FileInputStream("mensaje.txt");
            byte[] mensaje = fis.readAllBytes();

            //Hasheo el mensaje
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashParaComparar = md.digest(mensaje);

            //Obtengo la firma
            fis = new FileInputStream("firma.rsa");
            byte[] firma = fis.readAllBytes();

            //Desencripto la firma
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            //Obtengo el hash de la firma
            byte[] hashAComparar = cipher.doFinal(firma);

            //Comparo los Hash
            if (MessageDigest.isEqual(hashParaComparar, hashAComparar)) {
                System.out.println("Mensaje Verificado");
            }else{
                System.out.println("Mensaje no Verificado");
            }

        } catch (Exception e) {
            System.out.println("Error en Verify: " + e.getMessage());
        }
    }
}
