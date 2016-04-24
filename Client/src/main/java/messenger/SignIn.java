/**
 * Copyright 2015, 2016 Alexander Beschasny
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

package messenger;

import messenger.client.Client;
import messenger.lang.Parser;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;
import java.util.Properties;

/**
 * Created by alex on 12.01.16.
 */

public class SignIn {

    private static JFrame frame = new JFrame();
    private static JTextField login;
    private static JPasswordField password;
    private static JButton signUp, signIn;
    private static Thread t = new Thread(new checkMessageFromServer());
    static PrintWriter writer;
    private static BufferedReader reader;
    private static int err_login, err_password;

    private static String host, port;

    static Logger logger = Logger.getLogger(SignIn.class.getName());

    public static String getLogin() {
        return login.getText();
    }

    public static String getPassword() {
        return password.getText();
    }

    Timer time = new Timer(50, e -> {
        err_login = 0;
        err_password = 0;
        if (login.getText().equals("")) {
            err_login = 2;
        } else if (login.getText().length() < 3 || login.getText().length() > 12) {
            err_login = 1;
        } else {
            for (int i = 0; i < login.getText().length(); i++) {
                if ((int) login.getText().charAt(i) < 65 || ((int) login.getText().charAt(i) > 90) && ((int) login.getText().charAt(i) < 97) || (int) login.getText().charAt(i) > 122) {
                    err_login = 1;
                    break;
                }
            }
        }
        if (err_login == 1) {
            login.setBorder(BorderFactory.createLineBorder(Color.decode("#FF0000"), 1));
        } else {
            login.setBorder(BorderFactory.createLineBorder(Color.decode("#808080"), 1));
        }

        if (password.getText().equals("")) {
            err_password = 2;
        } else if (password.getText().length() < 4 || password.getText().length() > 16) {
            err_password = 1;
        } else {
            for (int i = 0; i < password.getText().length(); i++) {
                if ((int) password.getText().charAt(i) < 48 || ((int) password.getText().charAt(i) > 57 && (int) password.getText().charAt(i) < 65) || ((int) password.getText().charAt(i) > 90) && ((int) password.getText().charAt(i) < 97) || (int) password.getText().charAt(i) > 122) {
                    err_password = 1;
                    break;
                }
            }
        }
        if (err_password == 1) {
            password.setBorder(BorderFactory.createLineBorder(Color.decode("#FF0000"), 1));
        } else {
            password.setBorder(BorderFactory.createLineBorder(Color.decode("#808080"), 1));
        }

        if (err_login >= 1 || err_password >= 1) {
            signIn.setEnabled(false);
            signUp.setEnabled(false);
        } else {
            signIn.setEnabled(true);
            signUp.setEnabled(true);
        }
    });

    public static void main(String[] args) {
        if (args.length > 0) {
            if (args[0].equals("ru")) {
                new Parser("ru");
            } else if (args[0].equals("en")) {
                new Parser("en");
            }
        } else {
            new Parser("en");
        }
        new SignIn();
    }

    public SignIn() {
        logger.info(Parser.getLoadProperty() + "...");
        loadProperty(SignIn.class.getResourceAsStream("/config_client.properties"));
        logger.info(Parser.getCreateComponents() + "...");
        JLabel inputName = new JLabel(Parser.getLogin() + ":");
        inputName.setHorizontalAlignment(SwingConstants.CENTER);
        inputName.setVerticalAlignment(SwingConstants.CENTER);
        inputName.setBounds(0, 0, 170, 24);
        frame.add(inputName);

        login = new JTextField();
        login.setFont(new Font(null, Font.PLAIN, 13));
        login.setBounds(16, 25, 135, 20);
        login.setBorder(BorderFactory.createLineBorder(Color.decode("#808080"), 1));
        frame.add(login);

        JLabel inputPassword = new JLabel(Parser.getPassword0() + ":");
        inputPassword.setHorizontalAlignment(SwingConstants.CENTER);
        inputPassword.setVerticalAlignment(SwingConstants.CENTER);
        inputPassword.setBounds(0, 45, 170, 24);
        frame.add(inputPassword);

        password = new JPasswordField();
        password.setFont(new Font(null, Font.PLAIN, 13));
        password.setBounds(16, 69, 135, 20);
        password.setBorder(BorderFactory.createLineBorder(Color.decode("#808080"), 1));
        frame.add(password);

        signIn = new JButton(Parser.getSignin());
        signIn.setBounds(16, 98, 135, 24);
        signIn.addActionListener(e -> {
            writer.println("signin");
            sendInfoAboutUser();
        });
        frame.add(signIn);

        signUp = new JButton(Parser.getSignup());
        signUp.setBounds(16, 131, 135, 24);
        signUp.addActionListener(e -> {
            writer.println("signup");
            sendInfoAboutUser();
        });
        frame.add(signUp);

        logger.info(Parser.getSettingFrame() + "...");
        frame.setTitle(Parser.getSignin());
        frame.setSize(171, 195);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                writer.println("$exit$");
                logger.info(Parser.getExit() + "...");
                System.exit(0);
            }
        });
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setVisible(true);

        time.start();
        logger.info(Parser.getConnect() + "...");
        try {
            Socket socket = new Socket(host, Integer.parseInt(port));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
            new Error().setErrorMessage(e.getMessage());
            SignIn.frame.dispose();
        }
        logger.info(Parser.getThread() + "...");
        t.start();
    }

    public static class checkMessageFromServer implements Runnable {
        String messageFromServer;

        public void run() {
            try {
                while ((messageFromServer = reader.readLine()) != null) {
                    if (messageFromServer.equals("2")) {
                        frame.dispose();
                        new Client().setLogin(getLogin());
                        t.stop();
                    } else if (messageFromServer.equals("3")) {
                        JOptionPane.showMessageDialog(null, Parser.getPassword(), Parser.getError(), JOptionPane.ERROR_MESSAGE);
                        password.setText("");
                    } else if (messageFromServer.equals("4")) {
                        JOptionPane.showMessageDialog(null, Parser.getNotExist(), Parser.getError(), JOptionPane.ERROR_MESSAGE);
                        login.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, Parser.getExistLogin(), Parser.getError(), JOptionPane.ERROR_MESSAGE);
                        login.setText("");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static void sendInfoAboutUser() {
        writer.println(getLogin());
        writer.println(getPassword());
    }

    private static void loadProperty(InputStream inputStream) {
        try {
            Properties properties = new Properties();
            properties.load(inputStream);

            host = properties.getProperty("s.host");
            port = properties.getProperty("s0.port");
        } catch (IOException e) {
            logger.error(Parser.getLoadProperty_err());
        }
    }

}