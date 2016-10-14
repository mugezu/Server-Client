/**
 * Created by user on 08.09.2016.
 */

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Server {
    public static void main(String[] ar) throws UnknownHostException {
        int port = 6666;
        InetAddress addr = InetAddress.getLocalHost();
        String myLANIP = addr.getHostAddress();
        System.out.println("Local IP-address: "+myLANIP);
        try {
            List<ServerSocket> a = new Vector<>();
            List<Runnable> r = new ArrayList<>();
            List<Thread> t = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                a.add(new ServerSocket(port));
                r.add(new ServerThred(a.get(i),port++));
                t.add(new Thread(r.get(i)));
                t.get(i).start();
            }
        } catch (Exception x) {
            x.printStackTrace();
        }

    }
}