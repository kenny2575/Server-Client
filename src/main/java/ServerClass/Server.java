package ServerClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public Server(){}
    private final int port = 8082;
    static ExecutorService executeIt = Executors.newFixedThreadPool(2);

    public void run(){
        try(ServerSocket serverSocket = new ServerSocket(port);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
            System.out.println("Server started!");
            while (!serverSocket.isClosed()){
                if (!br.ready()){
                    String command = br.readLine();
                    if (command.equalsIgnoreCase("exit")||command.equalsIgnoreCase("quit")){
                        System.out.println("Server found some messages on it");
                        System.out.println("Server has been turning off");
                        serverSocket.close();
                        break;
                    }
                }
                Socket client = serverSocket.accept();
                executeIt.execute(new MonoThreadClientHandler(client));
                System.out.println("Connection accepted");
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            System.out.println("Server stopped!");
            executeIt.shutdown();
        }
    }
}
