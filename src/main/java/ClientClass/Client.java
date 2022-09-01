package ClientClass;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {

    public static void main(String[] args){

        ExecutorService exec = Executors.newFixedThreadPool(10);
        exec.execute(new RunnableClient());
        exec.shutdown();
    }
}
