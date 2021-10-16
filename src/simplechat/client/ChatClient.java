// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package simplechat.client;

import ocsf.client.AbstractClient;
import simplechat.common.ChatIF;

import java.io.IOException;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the ocsf.client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
public class ChatClient extends AbstractClient {
    //Instance variables **********************************************

    /**
     * The interface type variable.  It allows the implementation of
     * the display method in the ocsf.client.
     */
    ChatIF clientUI;


    //Constructors ****************************************************

    /**
     * Constructs an instance of the chat ocsf.client.
     *
     * @param host     The server to connect to.
     * @param port     The port number to connect on.
     * @param clientUI The interface type variable.
     */

    public ChatClient(String host, int port, ChatIF clientUI)
            throws IOException {
        super(host, port); //Call the superclass constructor
        this.clientUI = clientUI;
        openConnection();
    }


    //Instance methods ************************************************

    /**
     * This method handles all data that comes in from the server.
     *
     * @param msg The message from the server.
     */
    public void handleMessageFromServer(Object msg) {
        clientUI.display(msg.toString());
    }

    /**
     * This method handles all data coming from the UI
     *
     * @param msg The message from the UI.
     */
    public void handleMessageFromClientUI(String msg) {

        //check if command qualifier '#' is used
        if (msg.substring(0, 1).equals("#")) {
            String[] command = msg.toLowerCase().replaceFirst("#", "").split(" ");

            switch (command[0]) {
                case "quit":
                    clientUI.display("Goodbye!");
                    quit();
                    break;
                case "logoff":
                    if (isConnected()) {
                        clientUI.display("Logging off...");
                        try {
                            closeConnection();
                            clientUI.display("Logged off.");
                        } catch (IOException e) {/**/}
                    } else {
                        clientUI.display("You're already logged off");
                    }
                    break;
                case "sethost":
                    if (isConnected()) {
                        clientUI.display("Log off before setting host.");
                    } else {
                        setHost(command[1]);
                    }
                    break;
                case "setport":
                    if (isConnected()) {
                        clientUI.display("Log off before setting port.");
                    } else try {
                        int port = Integer.parseInt(command[1]);
                        setPort(port);
                        clientUI.display("Port has been set to " + port);
                    } catch (NumberFormatException e) {
                        clientUI.display("Port must be an integer.");
                    } catch (ArrayIndexOutOfBoundsException e) {
                        clientUI.display("Port number required");
                    }
                    break;
                case "login":
                    if (!isConnected()) {
                        try {
                            openConnection();
                            clientUI.display("Connected!");
                        } catch (IOException e) {
                            clientUI.display("Couldn't connect. Are your host and port correct?");
                        }
                    } else clientUI.display("You must be logged off to do that");
                    break;
                case "gethost":
                    clientUI.display("Host: " + getHost());
                    break;
                case "getport":
                    clientUI.display("Port: " + getPort());
                    break;
                default:
                    clientUI.display(String.format(
                            "The command qualifier was used without a valid command.%n" +
                                    "Here's a list of commands:%n" +
                                    "#quit will disconnect and close the client.%n" +
                                    "#logoff will disconnect from the server.%n" +
                                    "#sethost sets the host. Not allowed when connected to a server.%n" +
                                    "#setport sets the port. Not allowed when connected to a server.%n" +
                                    "#login connects you to the server. Must not already be connected.%n" +
                                    "#getport displays the current port.%n" +
                                    "#gethost displays the current host name."));
            }
        } else {

            try {
                sendToServer(msg);
            } catch (IOException e) {
                clientUI.display
                        ("Could not send message to server.  Terminating ocsf.client.");
                quit();
            }
        }
    }

//  public void connectionClosed(){
//    clientUI.display("Connection to server has been closed. Terminating ocsf.client.");
//    quit();
//  }

    public void connectionException(Exception exception) {
        clientUI.display("Connection error occurred. Terminating ocsf.client.");
        quit();
    }

    /**
     * This method terminates the ocsf.client.
     */
    public void quit() {
        try {
            closeConnection();
        } catch (IOException e) {
        }
        System.exit(0);
    }


}
//End of ChatClient class
