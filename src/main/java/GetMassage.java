import javax.swing.*;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by user on 26.09.2016.
 */
public class GetMassage implements Runnable {
    DataInputStream in;
    JEditorPane editor;
    GetMassage(DataInputStream in, JEditorPane editor) {
        this.editor=editor;
        this.in = in;
    }

    @Override
    public void run() {
        String line = null;
        try {
        while (true) {
                line =  editor.getText()+"\n"+in.readUTF();
                System.out.println(line);
                editor.setText(line);
        }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
