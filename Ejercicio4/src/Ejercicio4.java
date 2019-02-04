import sun.misc.BASE64Encoder;

import java.io.*;
import java.security.*;

public class Ejercicio4 {
    public static void main(String[] args) throws NoSuchAlgorithmException, SignatureException, IOException, InvalidKeyException {
        firmar(args);
    }

    private static void firmar(String[] args) throws IOException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {

        byte[] data = "test".getBytes("UTF-8");

        Signature sig = Signature.getInstance("SHA1withRSA");
        KeyPair claves =parDeClaves();
        sig.initSign(claves.getPrivate());
        sig.update(data);

        byte[] signatureBytes = sig.sign();
        System.out.println("Signature: " + new BASE64Encoder().encode(signatureBytes));

        sig.initVerify(claves.getPublic());
        sig.update(data);
        System.out.println(sig.verify(signatureBytes));
    }

    public static KeyPair parDeClaves() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(1024);
        return keyGen.generateKeyPair();
    }
}