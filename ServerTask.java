import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerTask implements Runnable {
    private ChatServer      parent;
    private Socket          client;
    private BufferedReader  receiver;
    private PrintWriter     sender;

    public ServerTask(ChatServer server, Socket cs) {
        parent = server;
        client = cs;
        try {
            receiver = new BufferedReader(new InputStreamReader(cs.getInputStream()));
            sender = new PrintWriter(cs.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getIPAddress() {
        return client.getInetAddress().getHostAddress();
    }

    public String receiveMessage() {
        String message = null;
        try {
            message = receiver.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }

    public void sendMessage(String message) {
        sender.println(message);
    }

    public void disconnect() {
        try {
            if(sender != null) {
                sender.close();
            }
            if(receiver != null) {
                receiver.close();
            }
            if(client != null) {
                    client.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(true) {
            String msg = receiveMessage();
            if(msg.equals("exit")) {
                sendMessage("Bye!");
                disconnect();
                break;
            } else {
                parent.sendMessageAll(msg);
            }
        }
    }
    
}
