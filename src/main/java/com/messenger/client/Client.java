package com.messenger.client;

import com.messenger.error.Error;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

/**
 * Created by alex on 12.01.16.
 */

public class Client {

    private static String messageFromClient = "";
    private static JEditorPane dialogs = new JEditorPane();
    private static String messageFromServer = "";
    private static JTextField message = new JTextField("");
    private static String login = "";
    private static JFrame frame = new JFrame();
    private static PrintWriter writer;
    private static BufferedReader reader;
    private static String text = "";
    private static DateTimeFormatter dateTimeFormatter;

    private static InputStream fis;
    private static Properties property = new Properties();
    private static String host, port, editorPane, work, format;

    public static void setLogin(String login) {
        Client.login = login;
    }

    public Client() {
        loadProperty();

        if (work.equals("0")) {
            dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        }

        dialogs.setContentType(editorPane);
        dialogs.setEditable(false);
        dialogs.addKeyListener(new KeyListener());
        dialogs.setBounds(15, 15, 340, 140);
        DefaultCaret caret = (DefaultCaret)dialogs.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        JScrollPane scrollPane = new JScrollPane(dialogs);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(15, 15, 340, 140);
        frame.add(scrollPane);

        message.addKeyListener(new KeyListener());
        message.setSize(250, 19);
        message.setLocation(15, 155);
        frame.add(message);

        JButton send = new JButton("Send");
        send.setSize(89, 18);
        send.setLocation(265, 155);
        send.addActionListener(e -> sendMessage());
        frame.add(send);

        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        menuBar.add(file);
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(e -> System.exit(0));
        file.add(exit);
        JMenu help = new JMenu("Help");
        menuBar.add(help);
        JMenuItem about = new JMenuItem("About");
        about.addActionListener(e -> JOptionPane.showMessageDialog(null, "Version:     0.02\nDeveloper: MrChebik", "About", JOptionPane.INFORMATION_MESSAGE));
        help.add(about);

        frame.setTitle("Messenger");
        frame.setSize(374, 235);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                writer.println("$exit$");
                writer.println(login);
                System.exit(0);
            }
        });
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setJMenuBar(menuBar);
        frame.setVisible(true);


        try {
            Socket socket = new Socket(host, Integer.parseInt(port));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
            Error.getInstance().setErrorMessage(e.getMessage());
            Client.frame.dispose();
        }

        Thread t = new Thread(new checkMessageFromServer());
        t.start();
    }


    public static class checkMessageFromServer implements Runnable {
        public void run() {
            try {
                while ((messageFromServer = reader.readLine()) != null) {
                    text += (work.equals("0") ? "[" + dateTimeFormatter.format(LocalTime.now()) + "] " : "") + messageFromServer + (editorPane.equals("text/html") ? "<br>" : "\n");
                    dialogs.setText(text);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void sendMessage() {
        if (!message.getText().equals("")) {
            messageFromClient = "<b>" + login + "</b>: " + message.getText();
            writer.println(messageFromClient);
            message.setText("");
        }
    }

    private static void loadProperty() {
        try {
            fis = Client.class.getResourceAsStream("/config_client.properties");
            property.load(fis);

            host = property.getProperty("s.host");
            port = property.getProperty("s1.port");
            editorPane = property.getProperty("d.JEditorPane");
            work = property.getProperty("t.work");
            format = property.getProperty("t.format");
        } catch (IOException e) {
            System.err.println("ERROR: Property file not exist!");
        }
    }

    private static class SingletonHolder {
        private static final Client INSTANCE = new Client();
    }

    public static Client getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
