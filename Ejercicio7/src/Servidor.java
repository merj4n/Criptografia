import javax.net.ServerSocketFactory;
import javax.net.ssl.*;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyStore;

public class Servidor {
    public static void main(String[] args) {
        String cadena;
        try {

            // Aquí agregamos la properties desde el código lo ideal es pasarla como parámetros o leerlas de una locación externa
            System.setProperty("javax.net.ssl.keyStore", "C:/Program Files/Java/jdk1.8.0_181/bin/KeyStore.jks");
            System.setProperty("javax.net.ssl.keyStorePassword", "farrukito");
            // Podemos activar esta property en caso de solicitar información para el debugging
            // System.setProperty("javax.net.debug", "ssl");

            // Utilizamos un SocketFactory de SSL el cual maneja la creacion por nosotros
            ServerSocketFactory serverSocketFactory = SSLServerSocketFactory.getDefault();

            try (ServerSocket serverSocket = serverSocketFactory.createServerSocket(4000, 50, InetAddress.getLocalHost())) {

                /*
                 * El método accept() es bloqueante por lo cual genera un
                 * bloqueo hasta que llega una conexión
                 */
                while (true) {
                    Socket clientSocket = serverSocket.accept();

                    InputStream is = clientSocket.getInputStream();
                    DataInputStream dis = new DataInputStream(is);
                    // Pedimos el output stream para enviar mensajes al cliente
                    OutputStream os = clientSocket.getOutputStream();
                    DataOutputStream dos = new DataOutputStream(os);
                    // Usamos un wrapper el cual nos permite escribir valores
                    // primitivos de forma simple

                    cadena = dis.readUTF();
                    if (cadena.equals("bye")) {
                        dos.writeUTF("Adios");
                        break;
                    } else {
                        dos.writeUTF(cadena.toUpperCase());
                    }

                    // Close output stream and client
                    dos.close();
                    os.close();
                    clientSocket.close();
                }
            }
        } catch (Exception e) {
            // Log and Handle exception
            e.printStackTrace();
        }
    }
}
