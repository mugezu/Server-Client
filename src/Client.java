/**
 * Created by user on 08.09.2016.
 */
public class Client {
    public static void main(String[] ar) {
        int serverPort = 6666;
        String address ="192.168.0.1";/*"192.168.43.60";*/
        Runnable r = new ClientThread(serverPort,address);
        Thread t = new Thread(r);
        t.start();
    }
}