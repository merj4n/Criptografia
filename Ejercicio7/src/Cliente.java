import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        String cadena;
        try {
            System.setProperty("javax.net.ssl.trustStore", "C:/Program Files/Java/jdk1.8.0_181/bin/KeyStore.jks");
            System.setProperty("javax.net.ssl.trustStorePassword", "farrukito");

            SocketFactory socketFactory = SSLSocketFactory.getDefault();


            // Obtenemos el input stream para leer datos
            while (true) {
                Socket socketToConnect = socketFactory.createSocket(InetAddress.getLocalHost(), 4000);
                InputStream is = socketToConnect.getInputStream();
                DataInputStream dis = new DataInputStream(is);
                OutputStream os = socketToConnect.getOutputStream();
                DataOutputStream dos = new DataOutputStream(os);

                System.out.println("Introduce frase a convertir: ");
                Scanner lee = new Scanner(System.in);
                cadena = lee.nextLine();
                dos.writeUTF(cadena);
                String dataFromServer = new String(dis.readUTF());
                System.out.println(dataFromServer);

                // Luego cerramos los stream y el socket
                dis.close();
                is.close();
                if (cadena.equals("bye")){
                    break;
                }
            }
            //socketToConnect.close();
        } catch (Exception e) {
            // Log and Handle exception
            e.printStackTrace();
        }
    }
}
