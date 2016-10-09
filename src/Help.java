import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by user on 26.09.2016.
 */
public class Help implements Runnable {
    DataInputStream in;

    Help(DataInputStream in) {
        this.in = in;
    }

    @Override
    public void run() {
        String line = null;
        while (true) {
            try {
                // line = null;
                line = in.readUTF();
                System.out.println(line);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
