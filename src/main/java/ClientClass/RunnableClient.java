package ClientClass;

import java.io.*;
import java.net.Socket;

public class RunnableClient implements Runnable {

    static Socket socket;
    private static String url = "netology.homework";
    private static int port = 8082;
    public RunnableClient() {
        try {
            socket = new Socket(url, port);
            System.out.println("Client connected to socket");
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        try (
            DataOutputStream outputStreamSocket = new DataOutputStream(socket.getOutputStream());
            DataInputStream inputStreamSocket = new DataInputStream(socket.getInputStream());
            BufferedReader inputStreamSystem = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Client oos & ois initialized");

            String requestString;
            String responseString;

            responseString = inputStreamSocket.readUTF();
            System.out.println(responseString);
            requestString = inputStreamSystem.readLine();
            outputStreamSocket.writeUTF(requestString);

            requestString = inputStreamSocket.readUTF();
            requestString += inputStreamSocket.readUTF();
            requestString += inputStreamSocket.readUTF();
            System.out.println(requestString);

            responseString = inputStreamSystem.readLine();
            outputStreamSocket.writeUTF(responseString);

            requestString = inputStreamSocket.readUTF();
            System.out.println(requestString);
            outputStreamSocket.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}