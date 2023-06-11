package client;

/**
 *
 * @author Jelena Basaric
 *
 *
 * *
 */
import java.io.*;
import java.net.*;
import server.WriteThreadDummy;

public class Client {

    public void run() {
        try {
            int serverPort = 4000;
            InetAddress host = InetAddress.getByName("localhost");

            System.out.println("Connecting to server on port " + serverPort);

            Socket client = new Socket(host, serverPort);
            System.out.println("Just connected to " + client.getRemoteSocketAddress());
            BufferedWriter toServer
                    = new BufferedWriter(
                            new OutputStreamWriter(client.getOutputStream()));

            BufferedReader fromServer
                    = new BufferedReader(
                            new InputStreamReader(client.getInputStream()));

            WriteThreadDummy writeThread = new WriteThreadDummy(client, toServer, fromServer);
            ReaderThread readerThread = new ReaderThread(client, toServer, fromServer);

            new Thread(readerThread).start();
            try {
                new Thread(readerThread).join();
            } catch (InterruptedException e) {
                System.out.println("doslo je do prekida");
            }
          } catch (UnknownHostException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    

}
