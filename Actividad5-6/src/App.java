
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Scanner;

public class App {

    public static void main(String[] args) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {

        try {
            //Creamos las claves, publica y privada
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);

            KeyPair keyPair = keyGen.generateKeyPair();

            saveKeys(keyPair.getPrivate(), keyPair.getPublic());

            //Pido el mensaje para firmar   
            Scanner sc = new Scanner(System.in);
            String message = sc.nextLine();

            //Con la clave privada firmo el mensaje
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(keyPair.getPrivate());
            signature.update(message.getBytes());
            byte[] signatureBytes = signature.sign();

            boolean verified = verifySignature(message, signatureBytes, keyPair.getPublic());

            if(verified){
                System.out.println("La firma es correcta");
            }else{
                System.out.println("La firma es incorrecta");
            }

            
            boolean signatureIsValid = verifySignature2(message, signatureBytes, keyPair.getPublic());
    
            System.out.println("La signatura proporcionada es va lida: " + signatureIsValid);

        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

    private static void saveKeys(PrivateKey aPrivate, PublicKey aPublic) {

        FileOutputStream keys = null;

        try {
            keys = new FileOutputStream("key.private");
            keys.write(aPrivate.getEncoded());
            keys.flush();
            keys.close();

            keys = new FileOutputStream("key.public");
            keys.write(aPublic.getEncoded());
            keys.flush();
            keys.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static boolean verifySignature(String message, byte[] signatureBytes, PublicKey publicKey) throws SignatureException, NoSuchAlgorithmException, InvalidKeyException {
        Signature sign = Signature.getInstance("SHA256withRSA");
        sign.initVerify(publicKey);
        sign.update(message.getBytes());

        return sign.verify(signatureBytes);

    }

    // Segon programa per a la verificació de la signatura
    public static boolean verifySignature2(String message, byte[] signature, PublicKey publicKey) throws Exception {
        Signature sign = Signature.getInstance("SHA256withRSA");
        sign.initVerify(publicKey);
        sign.update(message.getBytes());
        return sign.verify(signature);
    }
}
