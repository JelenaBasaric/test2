package server;
// Server Side
import java.net.*;
import java.io.*;
import client.*;

/**
 *
 * @author Jelena Basaric
 */


public class Server { 
  public void run() {
	try {
		int serverPort = 4000;
		ServerSocket serverSocket = new ServerSocket(serverPort);    
		System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "..."); 
		Socket server = serverSocket.accept();
		System.out.println("Just connected to " + server.getRemoteSocketAddress()); 
                BufferedWriter toClient = 
                        new BufferedWriter(
                                new OutputStreamWriter(server.getOutputStream()));
            	BufferedReader fromClient =
			new BufferedReader(
                        	new InputStreamReader(server.getInputStream()));
                WriteThreadDummy writeThread = new WriteThreadDummy(server, toClient, fromClient);
                WriteThreadCancel writeThreadCancel=new WriteThreadCancel(server, toClient, fromClient);
                ReaderThread readerThread = new ReaderThread(server, toClient, fromClient);
               // while(true){     
                     new Thread(writeThread).start();
                     new Thread(writeThreadCancel).start();
                    // new Thread(readerThread).start();
                     try{
                         new Thread( writeThread).join();
                         new Thread(writeThreadCancel).join();
                      //   new Thread(readerThread).join();
                     }catch(InterruptedException e){
                         System.out.println("doslo je do prekida");
                     }   
                     String line;
                     while((line=fromClient.readLine())!=null) {       
                          System.out.println("client: "+line);
                }
                
        }catch(UnknownHostException ex) {
		ex.printStackTrace();
	}catch(IOException e){
		e.printStackTrace();
	}
        
                
  }

}

    

