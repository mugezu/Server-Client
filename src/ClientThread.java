import java.io.*;
import java.net.InetAddress;
import java.net.Socket;


/**
 * Created by user on 26.09.2016.
 */
public class ClientThread implements Runnable {
    int serverPort;
    String address;

    public ClientThread(int serverPort, String address) {
        this.serverPort = serverPort;
        this.address = address;
    }

    @Override
    public void run() {
        String name = null;

        try {
            System.out.print("Enter your name: ");
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            name = keyboard.readLine();

            InetAddress ipAddress = InetAddress.getByName(address);
            Integer Port = realPort(ipAddress);
            System.out.println("Any of you heard of a socket with IP address " + address + " and port " + Port + "?");
            Socket socket = new Socket(ipAddress, Port);
            System.out.println("Yes! I just got hold of the program.");

            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();

            DataInputStream in = new DataInputStream(sin);
            DataOutputStream out = new DataOutputStream(sout);

            System.out.println("Type in something and press enter. Will send it to the server and tell ya what it thinks.");
            System.out.println();
            Runnable a = new HelpThread(out, name);
            Thread b = new Thread(a);
            Runnable c = new Help(in);
            Thread d = new Thread(c);
            d.start();
            b.start();
        } catch (Exception x) {
            x.printStackTrace();
        }
    }


    private Integer realPort(InetAddress ipAddress) throws IOException {
        Socket socket = new Socket(ipAddress, serverPort);

        InputStream sin = socket.getInputStream();
        OutputStream sout = socket.getOutputStream();

        DataInputStream in = new DataInputStream(sin);
        DataOutputStream out = new DataOutputStream(sout);
        int pp=0;
        String[] sub = in.readUTF().split(",");
        for (Integer i = serverPort + 1; i < 6800; i++) {
            int count = 0;
            for (Integer j = 0; j < sub.length; j++) {
                if (Integer.parseInt(sub[j]) != i)
                    count++;
                if (j == (sub.length - 1) && count == j+1) {
                    socket.close();
                    pp=i;
                    i=6800;
                }
            }
        }
        System.out.println(pp);
        return pp;
    }
}

