import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Scanner;

import javax.crypto.Cipher;

public class App {
    public static void main(String[] args) throws Exception {
        KeyPair keys = null;
        PrivateKey privateKey = null;
        PublicKey publicKey = null;
        FileOutputStream fos = null;

        try {
            //Creo las keys
            keys = createKeys(2048);

            privateKey = keys.getPrivate();
            publicKey = keys.getPublic();

            saveKeys(privateKey, publicKey);

            //Pido el mensaje
            Scanner sc = new Scanner(System.in);
            String mensaje = sc.nextLine();

            byte[] mensajeToBytes = mensaje.getBytes();

            //Guardo el mensaje original
            fos = new FileOutputStream("mensaje.txt");
            fos.write(mensajeToBytes);
            fos.flush();
            fos.close();

            //Cifro mensaje
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] dataCifrada = messageDigest.digest(mensajeToBytes);

            //Firmo el hash del mensaje con la clave privada
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] firma = cipher.doFinal(dataCifrada);

            fos = new FileOutputStream("firma.rsa");
            fos.write(firma);
            fos.flush();
            fos.close();

            //Descrifo el mensaje
            Cipher cipher2 = Cipher.getInstance("RSA");
            cipher2.init(Cipher.DECRYPT_MODE, publicKey);
            byte[] desencrytedData = cipher2.doFinal(firma);

            if(MessageDigest.isEqual(dataCifrada, desencrytedData)){
                System.out.println("VERIFICACION COMPLETADA");
            }else{
                System.out.println("FIRMA NO COMPLETADA");
            }

        } catch (Exception e) {
            System.out.println("ERROR EN EL MAIN: " + e.getMessage());
        }
    }

    // Creamos las claves, privada y pública
    private static KeyPair createKeys(int size) {

        KeyPair keyPair = null;
        KeyPairGenerator generator = null;

        try {
            generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(size);
            keyPair = generator.genKeyPair();

        } catch (Exception e) {
            System.out.println("Error en la creacion de llaves" + e.getMessage());
        }
        return keyPair;
    }

    private static void saveKeys(PrivateKey privateKey, PublicKey publicKey) {

        FileOutputStream fos = null;

        try {

            fos = new FileOutputStream("private.key");
            fos.write(privateKey.getEncoded());
            fos.flush();
            fos.close();

            fos = new FileOutputStream("public.key");
            fos.write(publicKey.getEncoded());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
