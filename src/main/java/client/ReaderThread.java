package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.logging.Logger;
import server.WriteThreadCancel;
import server.WriteThreadDummy;

/**
 *
 * @author Jelena Basaric
 */
public class ReaderThread extends Thread {

    private final Socket client;
    BufferedWriter toServer;
    BufferedReader fromServer;

    public ReaderThread(Socket client, BufferedWriter toServer, BufferedReader fromServer) {
        this.client = client;
        this.toServer = toServer;
        this.fromServer = fromServer;
    }

    @Override
    public void run() {

        try {
            BufferedWriter toServer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            fromServer = new BufferedReader(new InputStreamReader(client.getInputStream()));

            while (!isInterrupted()) {
                // while(true){
                String receivedMessage = fromServer.readLine();
                String reciveString = "";
                if (receivedMessage.length() == 5) {
                    int len = Integer.parseInt(receivedMessage);
                    System.out.println("duzina " + len);
                    if (len == 16) {
                        reciveString = fromServer.readLine();
                        System.out.println("packet:" + reciveString);
                        //byte[] arr = reciveString.getBytes(StandardCharsets.UTF_8);
                          byte[] arr = reciveString.getBytes();
                        ByteBuffer bb = ByteBuffer.wrap(arr);
                        int delay = bb.getInt(3);
                        System.out.println("Delay" + delay);
                        WriteThreadCancel writeThreadCancel = new WriteThreadCancel(client, toServer, fromServer);
                        WriteThreadDummy writeThread = new WriteThreadDummy(client, toServer, fromServer);
                        synchronized (writeThread) {
                            try {

                                System.out.println("Nit ceka");
                                writeThread.wait(delay);
                                System.out.println("Paket poslat nazad");
                                new Thread(writeThread).start();
                            } catch (InterruptedException e) {
                                System.out.println("Nit je prekinuta");
                                toServer.write("nit je pekinuta");
                            }
                        }
                    } else if (len == 12) {
                        WriteThreadCancel writeThreadCancel = new WriteThreadCancel(client, toServer, fromServer);
                        new Thread(writeThreadCancel).start();

                    }
                }

            }
        } catch (IOException ex) {
            System.out.println("greska u readerThread");
            Thread.currentThread().interrupt();
            ex.printStackTrace();
            } finally {
            try {
                toServer.close();
                fromServer.close();
                client.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    

}
