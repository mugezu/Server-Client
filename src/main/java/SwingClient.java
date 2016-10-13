import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by user on 13.10.2016.
 */
public class SwingClient extends JFrame {
    private static final long serialVersionUID = 1l;
    private static DataInputStream in;
    private static DataOutputStream out;
    private JButton sendButton = new JButton("Отправить");
    private JEditorPane sendText = new JEditorPane();
    private static JEditorPane text = new JEditorPane();
    ClientThread c;

    public static void main(String[] args) throws IOException {
        int serverPort = 6666;
        String address = "192.168.0.103";/*"192.168.43.60";*/
        /*InetAddress addr = InetAddress.getLocalHost();
        String myLANIP = addr.getHostAddress();
        System.out.println(myLANIP);*/
        ClientThread c = new ClientThread(serverPort, address,text);
        c.init();
        in = c.getIn();
        out = c.getOut();

        new SwingClient();
    }

    public SwingClient() throws HeadlessException, IOException {
        super();
        setTitle("Клиент");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addComponents(getContentPane());
        setPreferredSize(new Dimension(500, 600));
        pack();
        setVisible(true);
    }

    private void addComponents(Container contentPane) {
        Panel panel = new Panel();
        sendButton.addActionListener(new actionButton());

        panel.setLayout(new BorderLayout());
        panel.add(sendButton, BorderLayout.EAST);

        panel.add(sendText, BorderLayout.CENTER);
        contentPane.setLayout(new BorderLayout());
        contentPane.add(text, BorderLayout.CENTER);
        contentPane.add(panel, BorderLayout.SOUTH);
    }

    public class actionButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
              text.setText(text.getText()+ sendText.getText()+"\n");
            try {
                out.writeUTF(sendText.getText());
                out.flush();
                sendText.setText("");
            } catch (IOException e1) {
                e1.printStackTrace();
                try {
                    out.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }
}
