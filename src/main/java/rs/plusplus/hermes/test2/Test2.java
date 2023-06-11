

package rs.plusplus.hermes.test2;

import client.Client;
import server.Server;

/**
 *
 * @author Jelena Basaric
 */
public class Test2 {

    public static void main(String[] args) {
        Server server = new Server();
	server.run();
        Client client = new Client();
	client.run();
        
       
    }
}
