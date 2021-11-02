package simplechat;// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import com.sun.javaws.exceptions.InvalidArgumentException;
import simplechat.client.ChatClient;
import simplechat.common.ChatIF;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This class constructs the UI for a chat ocsf.client.  It implements the
 * chat interface in order to activate the display() method.
 * Warning: Some of the code here is cloned in ServerConsole
 *
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @version July 2000
 */
public class ClientConsole implements ChatIF {
    //Class variables *************************************************

    /**
     * The default port to connect on.
     */
    final public static int DEFAULT_PORT = 5555;

    //Instance variables **********************************************

    /**
     * The instance of the ocsf.client that created this ConsoleChat.
     */
    ChatClient client;


    //Constructors ****************************************************

    /**
     * Constructs an instance of the ClientConsole UI.
     *
     * @param host The host to connect to.
     * @param port The port to connect on.
     */
    public ClientConsole(String id, String host, int port) {
        try {
            client = new ChatClient(id, host, port, this);
        } catch (IOException exception) {
            System.out.println("Error: Can't setup connection!"
                    + " Terminating ocsf.client.");
            System.exit(1);
        }
    }


    //Instance methods ************************************************

    /**
     * This method is responsible for the creation of the Client UI.
     *
     * @param args [0] The host to connect to.
     * @param args [1] The port to connect to.
     */
    public static void main(String[] args) {
        String id = ""; //the login ID
        String host = ""; //the hostname
        int port = 0;     //The port number

            try{
                id = args[0].trim();
                if(id.equals("")){
                    throw new InvalidArgumentException(new String[]{"Login ID is mandatory"});
                }
            } catch (Exception e){
                System.out.println("Login ID is mandatory");
                System.exit(0);
            }

        try {
            host = args[1];

        } catch (ArrayIndexOutOfBoundsException e) {
            host = "localhost";
        }
        try {
            port = Integer.parseInt(args[2]);
        } catch (Exception ex) {
            port = DEFAULT_PORT;
        }
        ClientConsole chat = new ClientConsole(id, host, port);
        chat.accept();  //Wait for console data
    }

    /**
     * This method waits for input from the console.  Once it is
     * received, it sends it to the ocsf.client's message handler.
     */
    public void accept() {
        try {
            BufferedReader fromConsole =
                    new BufferedReader(new InputStreamReader(System.in));
            String message;

            while (true) {
                message = fromConsole.readLine();
                client.handleMessageFromClientUI(message);
            }
        } catch (Exception ex) {
            System.out.println
                    ("Unexpected error while reading from console!");
        }
    }


    //Class methods ***************************************************

    /**
     * This method overrides the method in the ChatIF interface.  It
     * displays a message onto the screen.
     *
     * @param message The string to be displayed.
     */
    public void display(String message) {
        System.out.println("> " + message);
    }
}
//End of ConsoleChat class
