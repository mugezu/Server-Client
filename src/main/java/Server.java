/**
 * Created by user on 08.09.2016.
 */

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Server {
    public static void main(String[] ar) throws IOException {
        int port = 6666;
        String ip;
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                if (iface.isLoopback() || !iface.isUp())
                    continue;
                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    ip = addr.getHostAddress();
                    System.out.println(iface.getDisplayName() + " " + ip + " ");
                }
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        ExecutorService threadPool = new ThreadPoolExecutor(4, 64, 60l,
                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(256));
        ServerSocket serverSocket = new ServerSocket(port);
        while (true) {
            Socket socket = serverSocket.accept();
            threadPool.submit(new ServerThred(socket));
        }
    }
}