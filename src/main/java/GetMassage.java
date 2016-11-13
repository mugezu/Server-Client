import javax.swing.*;
import java.io.*;
import java.net.Socket;

/**
 * Created by user on 26.09.2016.
 */
public class GetMassage implements Runnable {
    Socket socket;
    DataInputStream in;
    DataOutputStream out;
    JTextArea editor;
    private String address;
    private int serverPort;

    GetMassage(Socket socket, JTextArea editor, String address,int serverPort) throws IOException {
        this.editor = editor;
        this.socket = socket;
        InputStream sin = socket.getInputStream();
        OutputStream sout = socket.getOutputStream();
        in = new DataInputStream(sin);
        out = new DataOutputStream(sout);
        this.address = address;
        this.serverPort=serverPort;
    }

    @Override
    public void run() {
        try {
            while (true) {
                editor.append("\n" + in.readUTF());
                editor.setCaretPosition(editor.getText().length());
            }
        } catch (IOException e) {
            editor.append("\nСервер оффлайн");
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
