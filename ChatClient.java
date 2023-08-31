import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    private Socket clientSocket;
    private BufferedReader receiver;
    private PrintWriter sender;
    
    public ChatClient() {
        try {
            clientSocket = new Socket("127.0.0.1", 1234);
            receiver = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            sender = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        sender.println(message);
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

    public static void main(String[] args) {
        ChatClient app = new ChatClient();    
        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.print("SEND MESSAGE>");
            String msg = sc.nextLine();
            app.sendMessage(msg);
            System.out.println(app.receiveMessage());
            if(msg.equals("eixit")) {
                System.out.println("終了します。");
                break;
            }
        }
    }
}
