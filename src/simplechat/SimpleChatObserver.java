package simplechat;

import simplechat.client.ChatClient;

import java.util.Observable;
import java.util.Observer;

public class SimpleChatObserver implements Observer {
    @Override
    public void update(Observable observable, Object message) {
        if (observable.getClass().equals(EchoServer.class)) {
            System.out.println("OBSERVER: EchoServer has been observed (\"" + message + "\")");
            if (message instanceof String) {
                switch (message.toString()) {
                    case EchoServer.SERVER_CLOSED:
                        //todo
                        break;
                    case EchoServer.SERVER_STARTED:
                        //todo
                        break;
                    case EchoServer.CLIENT_CONNECTED:
                        //todo
                        break;
                    case EchoServer.SERVER_STOPPED:
                        //todo
                        break;
                    case EchoServer.CLIENT_DISCONNECTED:
                        //todo
                        break;
                    case EchoServer.CLIENT_EXCEPTION:
                        //todo
                        break;
                    case EchoServer.LISTENING_EXCEPTION:
                        //todo
                        break;
                    default:
                        System.out.println("An invalid message was received by SimpleChatObserver");
                }
            } else System.out.println("An invalid message was received by SimpleChatObserver");
        }
        if (observable.getClass().equals(ChatClient.class)) {
            System.out.println("OBSERVER: ChatClient has been observed (\"" + message + "\")");
            if (message instanceof String) {
                switch (message.toString()) {
                    case ChatClient.CONNECTION_CLOSED:
                        //todo
                        break;
                    case ChatClient.CONNECTION_ESTABLISHED:
                        //todo
                        break;
                    case ChatClient.CONNECTION_EXCEPTION:
                        //todo
                        break;
                    default:
                        System.out.println("An invalid message was received by SimpleChatObserver");
                }
            } else System.out.println("An invalid message was received by SimpleChatObserver");
        }
    }
}
