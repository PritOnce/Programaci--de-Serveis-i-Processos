import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class UsuarioB {
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException, InvalidKeySpecException {
        String mensaje = "Hello, World!";

        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256);

        SecretKey secretKey = keyGen.generateKey();

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] criptogramaMsg = cipher.doFinal(mensaje.getBytes());

        FileOutputStream cipherFile = new FileOutputStream("msg.aes");
        cipherFile.write(criptogramaMsg);
        cipherFile.flush();
        cipherFile.close();

        FileInputStream fileInputStream = new FileInputStream("key.public");
        byte[] encodePublicKey = fileInputStream.readAllBytes();

        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(encodePublicKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

        Cipher cipher2 = Cipher.getInstance("RSA");
        cipher2.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] criptogramaAES = cipher2.doFinal(secretKey.getEncoded());

        cipherFile = new FileOutputStream("aes.rsa");
        cipherFile.write(criptogramaAES);
        cipherFile.flush();
        cipherFile.close();
    }
}
