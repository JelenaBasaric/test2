package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import cancel.*;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;


/**
 *
 * @author Jelena Basaric
 */
public class WriteThreadCancel implements Runnable {

    private final Socket socket;
    BufferedWriter toClient;
    BufferedReader fromClient;
     Cancel c ;
     int id=1;

    public WriteThreadCancel(Socket socket, BufferedWriter toClient, BufferedReader fromClient) {
        this.socket = socket;
        this.toClient = toClient;
        this.fromClient = fromClient;
    }

    public WriteThreadCancel(Socket socket) {
        this.socket = socket;
    }

    public void run() {
       

        try {
             id+=2;
            System.out.println("id"+id);
            c.setId(id);
            String packet = "";
            ByteBuffer bb = ByteBuffer.allocate(c.getLen());
            bb.putInt(c.getIdpaketa());
            bb.putInt(c.getLen());
            bb.putInt(c.getId());
            toClient = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bb.flip();
            if ( !bb.hasRemaining()) {
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
        } catch (IOException e) {
            System.out.println("greska u writeThreadCancel\n");
            Thread.currentThread().interrupt();
            e.printStackTrace();

        }
    }
   
       
}

