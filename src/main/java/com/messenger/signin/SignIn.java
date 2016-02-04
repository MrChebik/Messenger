package com.messenger.signin;

import com.messenger.client.Client;
import com.messenger.error.Error;

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
    private static PrintWriter writer;
    private static BufferedReader reader;
    private static int err_login, err_password;

    private static InputStream fis;
    private static Properties property = new Properties();
    private static String host, port;

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

    private SignIn() {
        loadProperty();

        JLabel inputName = new JLabel("Login:");
        inputName.setHorizontalAlignment(SwingConstants.CENTER);
        inputName.setVerticalAlignment(SwingConstants.CENTER);
        inputName.setBounds(0, 0, 170, 24);
        frame.add(inputName);

        login = new JTextField();
        login.setFont(new Font(null, Font.PLAIN, 13));
        login.setBounds(16, 25, 135, 20);
        login.setBorder(BorderFactory.createLineBorder(Color.decode("#808080"), 1));
        frame.add(login);

        JLabel inputPassword = new JLabel("Password:");
        inputPassword.setHorizontalAlignment(SwingConstants.CENTER);
        inputPassword.setVerticalAlignment(SwingConstants.CENTER);
        inputPassword.setBounds(0, 45, 170, 24);
        frame.add(inputPassword);

        password = new JPasswordField();
        password.setFont(new Font(null, Font.PLAIN, 13));
        password.setBounds(16, 69, 135, 20);
        password.setBorder(BorderFactory.createLineBorder(Color.decode("#808080"), 1));
        frame.add(password);

        signIn = new JButton("Sign in");
        signIn.setBounds(16, 98, 135, 24);
        signIn.addActionListener(e -> {
            writer.println("signin");
            sendInfoAboutUser();
        });
        frame.add(signIn);

        signUp = new JButton("Sign up");
        signUp.setBounds(16, 131, 135, 24);
        signUp.addActionListener(e -> {
            writer.println("signup");
            sendInfoAboutUser();
        });
        frame.add(signUp);

        frame.setTitle("Sign in");
        frame.setSize(171, 195);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                writer.println("$exit$");
                System.exit(0);
            }
        });
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setVisible(true);

        time.start();

        try {
            Socket socket = new Socket(host, Integer.parseInt(port));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
            Error.getInstance().setErrorMessage(e.getMessage());
            SignIn.frame.dispose();
        }
        t.start();
    }

    public static void main(String[] args) {
        getInstance();
    }

    public static class checkMessageFromServer implements Runnable {
        String messageFromServer;

        public void run() {
            try {
                while ((messageFromServer = reader.readLine()) != null) {
                    if (messageFromServer.equals("2")) {
                        frame.dispose();
                        Client.getInstance().setLogin(getLogin());
                        t.stop();
                    } else if (messageFromServer.equals("3")) {
                        JOptionPane.showMessageDialog(null, "Неправильно введен пароль!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                        password.setText("");
                    } else if (messageFromServer.equals("4")) {
                        JOptionPane.showMessageDialog(null, "Пользователь с данным ником не зарегистрирован!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                        login.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "Введенный логин уже используется!", "Ошибка", JOptionPane.ERROR_MESSAGE);
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

    private static void loadProperty() {
        try {
            fis = SignIn.class.getResourceAsStream("/config_client.properties");
            property.load(fis);

            host = property.getProperty("s.host");
            port = property.getProperty("s0.port");
        } catch (IOException e) {
            System.err.println("ERROR: Property file not exist!");
        }
    }

    private static class SingletonHolder {
        private static final SignIn INSTANCE = new SignIn();
    }

    public static SignIn getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
