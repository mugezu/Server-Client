import javax.swing.*;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by user on 26.09.2016.
 */
public class GetMassage implements Runnable {
    DataInputStream in;
    JTextArea editor;
    GetMassage(DataInputStream in, JTextArea editor) {
        this.editor=editor;
        this.in = in;
    }

    @Override
    public void run() {
        try {
        while (true) {
                editor.append("\n"+in.readUTF());
            editor.setCaretPosition(editor.getText().length());
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
