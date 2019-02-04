import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

public class Ejercicio2 {
    public static void main(String[] args) {
        //declarar funciones resumen
        try{
            MessageDigest messageDigest = MessageDigest.getInstance("MD5"); // Inicializa MD5
            MessageDigest messageDigest2 = MessageDigest.getInstance("MD5");

            //leer fichero byte a byte

            try{
                InputStream archivo1 = new FileInputStream( args[0] );
                InputStream archivo2 = new FileInputStream( args[1] );
                byte[] buffer = new byte[1];
                byte[] buffer2 = new byte[1];
                int fin_archivo = -1;
                int caracter1,caracter2;

                caracter1 = archivo1.read(buffer);
                caracter2 = archivo2.read(buffer2);

                while( caracter1 != fin_archivo ) {

                    messageDigest.update(buffer); // Pasa texto claro a la función resumen
                    caracter1 = archivo1.read(buffer);
                }

                while( caracter2 != fin_archivo ) {

                    messageDigest2.update(buffer2);
                    caracter2 = archivo2.read(buffer2);
                }

                archivo1.close();
                archivo2.close();
                byte[] resumen = messageDigest.digest(); // Genera el resumen MD5
                byte[] resumen2 = messageDigest2.digest(); // Genera el resumen SHA-1

                if (calculo(resumen).equals(calculo(resumen2))){
                    System.out.println("Resumen MD5: " + calculo(resumen));
                    System.out.println("El fichero mantiene su integridad.");
                }else{
                    System.out.println("Resumen MD5: " + calculo(resumen));
                    System.out.println("Resumen MD5: " + calculo(resumen2));
                    System.out.println("El fichero ha sido alterado.");
                }
            }
            //lectura de los datos del fichero
            catch(java.io.FileNotFoundException fnfe) {}
            catch(java.io.IOException ioe) {}

        }
        //declarar funciones resumen
        catch(java.security.NoSuchAlgorithmException nsae) {}
    }

    public static String calculo(byte[] resumen){

        String s = "";
        for (int i = 0; i < resumen.length; i++)
        {
            s += Integer.toHexString((resumen[i] >> 4) & 0xf);
            s += Integer.toHexString(resumen[i] & 0xf);
        }
        return s;
    }
}
