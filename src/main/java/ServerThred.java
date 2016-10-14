import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 26.09.2016.
 */
public class ServerThred implements Runnable {
    ServerSocket SS;
    DataInputStream in;
    DataOutputStream out;
    static List<DataOutputStream> ListDataOutputStream = new ArrayList<>();
    static List<Integer> ListPort = new ArrayList<>();
    static {
        ListPort.add(6666);
    }
    int port;

    public ServerThred(ServerSocket SS, int port) {
        this.SS = SS;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            Socket socket;
            InputStream sin;
            OutputStream sout;
            if (port == 6666)
                while (true) {
                    socket = SS.accept();
                    sin = socket.getInputStream();
                    sout = socket.getOutputStream();
                    ObjectOutputStream out = new ObjectOutputStream(new DataOutputStream(sout));
                    out.writeObject(ListPort);
                    socket.close();
                }

            socket = SS.accept();
            if (port != 6666)
                ListPort.add(port);
            System.out.println("Новый пользователь " +port);
            System.out.println();

            sin = socket.getInputStream();
            sout = socket.getOutputStream();

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
            try {
                for (int i=1;i<ListPort.size()+1;i++){
                    if (ListPort.get(i)==port){
                        ListPort.remove(i);
                        ListDataOutputStream.get(i-1).close();
                        ListDataOutputStream.remove(i-1);
                        break;
                    }
                }
                in.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            finally {
                if (ListDataOutputStream.size()==0)
                    System.out.println( "Пользовать " + port+" отключился"+"\nСервер пуст");
                else {
                    System.out.println("Пользовать " + port+" отключился");
                }
                run();
            }
        }
    }
}
