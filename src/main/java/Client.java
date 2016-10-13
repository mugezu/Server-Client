import javax.swing.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;


/**
 * Created by user on 26.09.2016.
 */
public class Client {
    private JTextArea editor;
    private int serverPort;
    private String address;
    private DataInputStream in;
    private DataOutputStream out;
    private String name;

    public Client(int serverPort, String address, JTextArea editor) {
        this.serverPort = serverPort;
        this.address = address;
        this.editor = editor;
    }

    public void init() {
        name = null;
        try {
           /* System.out.print("Enter your name: ");
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            name = keyboard.readLine();*/

            InetAddress ipAddress = InetAddress.getByName(address);
            Integer Port = realPort(ipAddress);
            System.out.println("Any of you heard of a socket with IP address " + address + " and port " + Port + "?");
            Socket socket = new Socket(ipAddress, Port);
            System.out.println("Yes! I just got hold of the program.");
            name = "Клон" + Port;
            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();

            in = new DataInputStream(sin);
            out = new DataOutputStream(sout);

            Runnable c = new GetMassage(in, editor);
            Thread d = new Thread(c);
            d.start();
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

    private Integer realPort(InetAddress ipAddress) {
        int pp = 0;
        try {
            Socket socket = new Socket(ipAddress, serverPort);
            InputStream sin = socket.getInputStream();
            ObjectInputStream in = new ObjectInputStream(new DataInputStream(sin));
            List<Integer> sub = (List<Integer>) in.readObject();
            for (Integer i = serverPort + 1; i < 6800; i++) {
                int count = 0;
                for (Integer j = 0; j < sub.size(); j++) {
                    if (sub.get(j) != i)
                        count++;
                    if (j == (sub.size() - 1) && count == j + 1) {
                        socket.close();
                        pp = i;
                        i = 6800;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return pp;
        }
    }

    public DataInputStream getIn() {
        return in;
    }

    public void setIn(DataInputStream in) {
        this.in = in;
    }

    public DataOutputStream getOut() {
        return out;
    }

    public void setOut(DataOutputStream out) {
        this.out = out;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

