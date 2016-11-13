import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;


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

    public Client(int serverPort, String address, JTextArea editor,String name) {
        this.serverPort = serverPort;
        this.address = address;
        this.editor = editor;
        this.name=name;
    }

    public void init() {
        try {
            InetAddress ipAddress = InetAddress.getByName(address);
            System.out.println("Подключение по адресу " + address + " порт " + serverPort );
            Socket socket = new Socket(ipAddress, serverPort);
            System.out.println("Подключение удачно");
            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();

            in = new DataInputStream(sin);
            out = new DataOutputStream(sout);
            out.writeUTF("\nПодключен новый пользователь "+ name+"\n");
            Runnable c = new GetMassage(in, editor);
            Thread d = new Thread(c);
            d.start();
        } catch (Exception x) {
            x.printStackTrace();
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

