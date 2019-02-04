import javax.net.ServerSocketFactory;
import javax.net.ssl.*;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyStore;

// Para generear el pool de certificados RSA para la comunicacion SSL.
// keytool -genkey -keyalg RSA -alias serverKey -keystore KeyStore.jks -storepass servpass

//-keytool está en el directorio bin de donde tengamos instalado java.
//-Con la opción -genkey le estamos diciendo que genere un certificado.
//-keyalg RSA le indicamos que lo queremos encriptado con el algorítmo RSA
//-alias serverKey. El certificado se meterá en un fichero de almacén de certificados que podrá contener varios certificados.
// Este alias es el nombre con el que luego podremos identificar este certificado concreto dentro del almacén. Podemos poner cualquier nombre que nos de una pista de qué es ese certificado.
//-keystore serverKey.jks. Este es el fichero que hará de almacén de certificados. Si no existe se crea, si ya existe se añade el certificado con el alias que se haya indicado.
//-storepass servpass. El almacén está protegido con contraseña, para acceder a él necesitamos la contraseña.
// Si el almacén no existe, se crea usando esta contraseña, por lo que deberemos recordarla. Si ya existe, debemos proporcionar la contraseña que tuviera ese almacén.


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
