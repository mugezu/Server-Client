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
    private static String name;

    private JButton sendButton = new JButton("Отправить");
    private JEditorPane sendText = new JEditorPane();
    private static JTextArea text = new JTextArea();
    Client c;

    public static void main(String[] args) throws IOException {
        int serverPort = 6666;
        String address = "192.168.0.103";/*"192.168.43.60";*/
        /*InetAddress addr = InetAddress.getLocalHost();
        String myLANIP = addr.getHostAddress();
        System.out.println(myLANIP);*/
        Client c = new Client(serverPort, address, text);
        c.init();
        in = c.getIn();
        out = c.getOut();
        name = c.getName();

        new SwingClient();
    }

    public SwingClient() throws HeadlessException, IOException {
        super();
        setTitle("Клиент");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addComponents(getContentPane());
        setPreferredSize(new Dimension(500, 600));
        pack();
        setVisible(true);
    }

    private void addComponents(Container contentPane) {
        Panel panel = new Panel();
        sendButton.addActionListener(new actionButton());
        text.setCaretPosition(0);
        final JScrollPane jScrollPane = new JScrollPane(text);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.setLayout(new BorderLayout());
        panel.add(sendButton, BorderLayout.EAST);
        panel.add(sendText, BorderLayout.CENTER);
        contentPane.setLayout(new BorderLayout());
        contentPane.add(jScrollPane, BorderLayout.CENTER);
        contentPane.add(panel, BorderLayout.SOUTH);
    }

    public class actionButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                out.writeUTF(name + ": " + sendText.getText());
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
