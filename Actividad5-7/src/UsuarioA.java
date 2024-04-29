import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class UsuarioA {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        FileInputStream fileInputStream = new FileInputStream("key.private");

        byte[] keyPrivateBytes = fileInputStream.readAllBytes();

        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(keyPrivateBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

        fileInputStream = new FileInputStream("aes.rsa");
        byte[] aesEncodedEncryted = fileInputStream.readAllBytes();

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] aesEncoded = cipher.doFinal(aesEncodedEncryted);

        SecretKey secretKey = new SecretKeySpec(aesEncoded, "AES");

        fileInputStream = new FileInputStream("msg.aes");
        byte[] msgCifrado = fileInputStream.readAllBytes();

        cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] msgDescifrado = cipher.doFinal(msgCifrado);

        System.out.println(new String(msgDescifrado));
    }
}
