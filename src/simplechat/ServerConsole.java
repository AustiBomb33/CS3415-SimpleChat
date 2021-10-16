package simplechat;

import simplechat.common.ChatIF;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ServerConsole implements ChatIF {
    EchoServer server;

    //constructor
    public ServerConsole(int port) {
        try {
            server = new EchoServer(port, this);
        } catch (Exception e) {
            display("An error has occurred and EchoServer could not be started. Terminating");
            System.exit(1);
        }
    }

    //class methods
    @Override
    public void display(String message) {
        System.out.println("> " + message);
    }

    public void accept() {
        try {
            BufferedReader fromConsole =
                    new BufferedReader(new InputStreamReader(System.in));
            String message;

            while (true) {
                message = fromConsole.readLine();
                server.handleMessageFromServerUI(message);
            }
        } catch (Exception ex) {
            System.out.println
                    ("Unexpected error while reading from console!");
        }
    }

    //instance methods
    public static void main(String[] args) {
        int port = 0;
        try {
            port = Integer.parseInt(args[0]);
        } catch (Exception e) {
            port = EchoServer.DEFAULT_PORT;
        }

        ServerConsole svrCon = new ServerConsole(port);

        try {
            svrCon.server.listen();
        } catch (IOException e) {
            svrCon.display("Couldn't listen for clients");
        }

        svrCon.accept();


    }
}
