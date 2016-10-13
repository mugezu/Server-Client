import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by user on 26.09.2016.
 */
public class SendMassage implements Runnable {
    BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
    DataOutputStream out;
    String name;

    SendMassage(DataOutputStream out, String name) throws IOException {
        this.out = out;
        this.name = name;
    }

    @Override
    public void run() {
        while (true) {
            String line = null;
            try {
                line = keyboard.readLine();
                out.writeUTF(name + ": " + line);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
