import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

public class ClavesUsuarioA {
    public static void main(String[] args) throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        KeyPair keyPair = generator.generateKeyPair();

        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        FileOutputStream keyPrivate = new FileOutputStream("key.private");
        keyPrivate.write(privateKey.getEncoded());
        keyPrivate.flush();
        keyPrivate.close();

        FileOutputStream keyPublic = new FileOutputStream("key.public");
        keyPublic.write(publicKey.getEncoded());
        keyPublic.flush();
        keyPublic.close();
    }
}
