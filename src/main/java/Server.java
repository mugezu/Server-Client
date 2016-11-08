/**
 * Created by user on 08.09.2016.
 */

import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

public class Server {
    public static void main(String[] ar) throws UnknownHostException {
        int port = 6666;
        String ip;
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                // filters out 127.0.0.1 and inactive interfaces
                if (iface.isLoopback() || !iface.isUp())
                    continue;
                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while(addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    ip = addr.getHostAddress();
                    System.out.println(iface.getDisplayName() + " " + ip);
                }
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
       // InetAddress addr = InetAddress;
       // String myLANIP = addr.getHostAddress();
      //  System.out.println("Local IP-address: "+myLANIP);
        try {
            List<ServerSocket> a = new Vector<>();
            List<Runnable> r = new ArrayList<>();
            List<Thread> t = new ArrayList<>();
            for (int i = 0; i < 200; i++) {
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