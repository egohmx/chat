import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer {
    private ServerSocket ss;
    private ArrayList<ServerTask> tasks = new ArrayList<>();

    public ChatServer(String ipAddress, int portNumber) {
        try {
            ss = new ServerSocket();
            ss.bind(new InetSocketAddress(ipAddress, portNumber));
            System.out.println("ChatSereverを起動しました。");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket nextConnect() {
        Socket cs = null;

        try {
            cs = ss.accept();
            ServerTask st = new ServerTask(this, cs);
            tasks.add(st);
            Thread t = new Thread(st);
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cs;
    }

    public void sendMessageAll(String message) {
        for(ServerTask t : tasks) {
            t.sendMessage("to " + t.getIPAddress() + ":" + message);
        }
    }

    public void removeTask(ServerTask task) {
        tasks.remove(task);
    }

    public static void main(String[] args) {
        ChatServer server = new ChatServer("127.0.0.1", 1234);
        while(true) {
            server.nextConnect();
        }
    }
    
}
