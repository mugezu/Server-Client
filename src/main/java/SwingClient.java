import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * Created by user on 13.10.2016.
 */
public class SwingClient extends JFrame {
    private static final long serialVersionUID = 1l;
    private static DataInputStream in;
    private static DataOutputStream out;
    public static String name;

    private JButton sendButton = new JButton("Отправить");
    private JTextArea sendText = new JTextArea();
    private static JTextArea text = new JTextArea();
    Client c;

    public static void main(String[] args) {
        int serverPort = 6666;
        String n=null;
        JTextArea textArea = new JTextArea();
        JLabel label = new JLabel();
        label.setText("Представтесь");
        JComponent[] inputs = {label, textArea};
        int replyi = JOptionPane.showConfirmDialog(null, inputs, "Заголовок", JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (replyi == JOptionPane.OK_OPTION) {
            System.out.println("OK");
            System.out.println(textArea.getText());
            n = textArea.getText().toString();
        }
        else {
            return;
        }
        StringBuilder address = new StringBuilder();
        FileReader r = null;
        try {
            r = new FileReader(new File("ServerIP.bin"));
            int c;
            while ((c = r.read()) != -1) {
                address.append((char) c);
            }
        } catch (IOException e) {
            int reply = JOptionPane.showConfirmDialog(null, "Файл ServerIP.bin не найден", "Ошибка", JOptionPane.CLOSED_OPTION);
        } finally {
            try {
                r.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(n);
        name=n;
        Client c = new Client(serverPort, address.toString(), text, name);
        c.init();
        in = c.getIn();
        out = c.getOut();
        try {
            new SwingClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        sendText.setCaretPosition(0);
        final JScrollPane sendTextScroll = new JScrollPane(sendText);
        panel.setLayout(new BorderLayout());
        panel.add(sendButton, BorderLayout.EAST);
        panel.add(sendTextScroll, BorderLayout.CENTER);
        panel.setPreferredSize(new Dimension(200, 50));

        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        text.setEditable(false);
        final JScrollPane jScrollPane = new JScrollPane(text);
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
