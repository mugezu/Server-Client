import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by user on 26.09.2016.
 */
public class ServerThred implements Runnable {
    Socket SS;
    DataInputStream in;
    DataOutputStream out;
    String name;
    static List<DataOutputStream> ListDataOutputStream = new LinkedList<>();
    static List<String> ListName= new LinkedList<>();

    public ServerThred(Socket SS) {
        this.SS = SS;
    }

    @Override
    public void run() {
        try {
            System.out.println("Новый пользователь");

            InputStream sin;
            OutputStream sout;

            sin = SS.getInputStream();
            sout = SS.getOutputStream();

            in = new DataInputStream(sin);
            out = new DataOutputStream(sout);
            ListDataOutputStream.add(out);
            miniThread();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void miniThread() {
        String line = null;
        try {
            name = in.readUTF();
            ListName.add(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            while (true) {
                line = in.readUTF();
                System.out.println("The dumb client just sent me this line : " + line);
                System.out.println("I'm sending it back...");
                for (DataOutputStream outStream : ListDataOutputStream) {
                    outStream.writeUTF(line);
                    outStream.flush();
                }
                System.out.println("Waiting for the next line...");
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ListDataOutputStream.size() == 1)
                System.out.println("Пользователь "+ name +" отключился"+"\nСервер пуст");
            else
                System.out.println("Пользователь "+ name +" отключился");
            try {
                ListDataOutputStream.remove(out);
                ListName.remove(name);
                for (DataOutputStream outStream : ListDataOutputStream) {
                    outStream.writeUTF("Пользователь "+ name +" отключился");
                    outStream.flush();
                }
                in.close();
                out.close();
                SS.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

