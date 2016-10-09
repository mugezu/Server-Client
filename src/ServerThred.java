import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 26.09.2016.
 */
public class ServerThred implements Runnable {
    ServerSocket SS;
    OutputStream sout;
    static List<DataOutputStream> ListDataOutputStream = new ArrayList<>();
    static String ListPort = "6666,";
    int port;

    public ServerThred(ServerSocket SS, int port) {
        this.SS = SS;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            Socket socket;
            if (port == 6666)
                while (true) {
                    socket = SS.accept();
                    InputStream sin = socket.getInputStream();
                    sout = socket.getOutputStream();
                    DataOutputStream out = new DataOutputStream(sout);
                    out.writeUTF(ListPort);
                    socket.close();

                }

            socket = SS.accept();
            if (port != 6666)
                ListPort += (port + ",");
            System.out.println("Got a client :) ... Finally, someone saw me through all the cover!");
            System.out.println();

            InputStream sin = socket.getInputStream();
            sout = socket.getOutputStream();

            DataInputStream in = new DataInputStream(sin);
            DataOutputStream out = new DataOutputStream(sout);
            ListDataOutputStream.add(out);

            String line = null;

            while (true) {
                line = in.readUTF();
                System.out.println("The dumb client just sent me this line : " + line);
                System.out.println("I'm sending it back...");
                for (DataOutputStream outStream : ListDataOutputStream) {
                    outStream.writeUTF(line);
                    out.flush();
                }
                System.out.println("Waiting for the next line...");
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
