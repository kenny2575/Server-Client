package ServerClass;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class MonoThreadClientHandler implements Runnable{

    private static Socket client;
    public MonoThreadClientHandler(Socket client){
        MonoThreadClientHandler.client = client;
    }
    @Override
    public void run() {
        try{
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            System.out.println("DataOutputStream created");
            DataInputStream in = new DataInputStream(client.getInputStream());
            System.out.println("DataInputStream created");
            while (!client.isClosed()) {
                out.writeUTF("What is your name?  ");
                String name = in.readUTF();
                out.writeUTF("Pleased to meet you, " + name);
                out.writeUTF(
                        String.format(" You connected to this server on port %d ",
                                client.getPort())
                );
                out.writeUTF("How old are you?");
                String clientAge = in.readUTF();
                if (clientAge.chars().allMatch(Character::isDigit)) {
                    int age = Integer.parseInt(clientAge);
                    if (age < 18) {
                        out.writeUTF(String.format("%s, so, you are %d years old " +
                                "take the tastiest candy", name, age));
                    } else {
                        out.writeUTF(String.format("Welcome to the adult zone, %s!, Have a good rest, or you can just work)", name));
                    }
                } else {
                    out.writeUTF("Wrong format! We expected only digits");
                }
                out.flush();
            }
            System.out.println("Client disconnected");
            System.out.println("Closing connections & channels.");

            // закрываем сначала каналы сокета !
            in.close();
            out.close();
            client.close();
            System.out.println("Closing connections & channels - DONE.");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
