package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.net.Socket;
import dummy.*;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;

/**
 *
 * @author Jelena Basaric
 */
public class WriteThreadDummy implements Runnable {

    private Socket socket;
    private boolean transfer;
    BufferedWriter toClient;
    BufferedReader fromClient;
    int id=2;

    public WriteThreadDummy(Socket socket, BufferedWriter toClient, BufferedReader fromClient) {
        this.socket = socket;
        this.toClient = toClient;
        this.fromClient = fromClient;
    }

    public WriteThreadDummy(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        Dummy d = new Dummy();
        id+=2;
        try {
             toClient = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
           // int id=Thread.activeCount();
           // d.setId(id++);
            System.out.println("id dummy"+id);
            //System.out.println("Insert delay:");
            //int delay=fromClient.read();
            d.setDelay(30);
            d.setId(id);
          //  System.out.println("delay"+30);
            String packet = "";
            ByteBuffer bb = ByteBuffer.allocate(d.getLen());
            bb.putInt(d.getIdPaket());
            bb.putInt(d.getLen());
            bb.putInt(d.getDelay());
            bb.putInt(d.getId());
           
            bb.flip();
            if (!bb.hasRemaining()) {
                System.out.println("prazan je");
                return;
            }

            byte[] bytes = new byte[bb.remaining()];
            bb.get(bytes);
            String header = "";
            for (int i = 4; i < 8; i++) {
                header += bytes[i];
            }
            toClient.write(header + "\n");
            System.out.println("poslat header " + header + "\n");
            toClient.flush();
            for (int i = 0; i < bytes.length; i++) {
                packet += bytes[i];
            }
            toClient.write(packet + "\n");
            toClient.flush();
            System.out.println("poslata poruka " + packet + "\n");
            toClient.flush();
           // toClient.wait();
        } catch (Exception e) {
            System.out.println("greska u header cancel\n");
            Thread.currentThread().interrupt();
            e.printStackTrace();

        }
    }
   
       
}

