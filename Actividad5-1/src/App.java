import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Inserta un mensaje: ");
        
        Scanner sc = new Scanner(System.in);
        String message = sc.nextLine();

        //Genero la llave
        SecretKey skey = keygenKeyGeneration(128);
        byte[] mensajeCifrado = cifrarMensaje(skey, message);

        System.out.println("Mensaje cifrado:\n"+new String(mensajeCifrado));

        String mensajeDescifrado = descrifrarMensaje(skey, mensajeCifrado);

        System.out.println("Mensaje descifrado:\n"+mensajeDescifrado);
    }


    //Generamos la clave
    public static SecretKey keygenKeyGeneration(int keySize) throws NoSuchAlgorithmException {
    SecretKey sKey = null;
    if ((keySize == 128)||(keySize == 192)||(keySize == 256)) {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(keySize);
        sKey = kgen.generateKey();
      }     
    return sKey;       
  }


    public static byte[] cifrarMensaje(SecretKey sKey, String message) {
        byte[] mensajeCifrado = null;
        
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, sKey);
            mensajeCifrado = cipher.doFinal(message.getBytes());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return mensajeCifrado;
    }

    //Desciframos el mensaje
    public static String descrifrarMensaje(SecretKey sKey, byte[] message) {
        String messageDescifrado = null;
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, sKey);
            messageDescifrado = new String(cipher.doFinal(message));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return messageDescifrado;
    }
}
