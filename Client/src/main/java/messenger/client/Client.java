/**
 * Copyright 2016 Alexander Beschasny
 *
 * Messenger is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Messenger is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Messenger.  If not, see <http://www.gnu.org/licenses/gpl-3.0.txt>.
 *
 * Alexander Beschasny mrchebik@yandex.ru
 */

package messenger.client;

import messenger.Error;
import messenger.lang.Parser;
import messenger.SignIn;
import org.apache.log4j.Logger;

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
 * @version 0.04
 * @author mrchebik
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

    static Logger logger = Logger.getLogger(SignIn.class.getName());

    public Client() {
        logger.info(Parser.getLoadProperty() + "...");
        loadProperty();

        if (work.equals("0")) {
            dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        }
        logger.info(Parser.getCreateComponents() + "...");
        dialogs.setContentType(editorPane);
        dialogs.setEditable(false);
        dialogs.addKeyListener(new KeyListener());
        dialogs.setBounds(15, 15, 370, 140);
        DefaultCaret caret = (DefaultCaret) dialogs.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        JScrollPane scrollPane = new JScrollPane(dialogs);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(15, 15, 370, 140);
        frame.add(scrollPane);

        message.addKeyListener(new KeyListener());
        message.setSize(250, 19);
        message.setLocation(15, 155);
        frame.add(message);

        JButton send = new JButton(Parser.getSend());
        send.setSize(119, 18);
        send.setLocation(265, 155);
        send.addActionListener(e -> sendMessage());
        frame.add(send);

        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu(Parser.getFile());
        menuBar.add(file);
        JMenuItem exit = new JMenuItem(Parser.getExit());
        exit.addActionListener(e -> System.exit(0));
        file.add(exit);
        JMenu help = new JMenu(Parser.getHelp());
        menuBar.add(help);
        JMenuItem about = new JMenuItem(Parser.getAbout());
        about.addActionListener(e -> JOptionPane.showMessageDialog(null, Parser.getVersion() + "\n" + Parser.getDeveloper(), Parser.getAbout(), JOptionPane.INFORMATION_MESSAGE));
        help.add(about);

        logger.info(Parser.getSettingFrame() + "...");
        frame.setTitle(Parser.getProgram());
        frame.setSize(404, 235);
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

        logger.info(Parser.getConnect() + "...");
        try {
            Socket socket = new Socket(host, Integer.parseInt(port));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
            new Error().setErrorMessage(e.getMessage());
            Client.frame.dispose();
        }

        logger.info(Parser.getThread() + "...");
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
            logger.error(Parser.getLoadProperty_err());
        }
    }

}
