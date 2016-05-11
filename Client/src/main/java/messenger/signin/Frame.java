/**
 * Messenger it's a program, who help you to communicate with others people who are in this program.
 * <p>
 * Copyright (C) 2016 MrChebik
 * <p>
 * Messenger is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * Messenger is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with Messenger.  If not, see <http://www.gnu.org/licenses/>.
 * <p>
 * Alexander Beschasny mrchebik@yandex.ru
 */

package messenger.signin;

import messenger.Main;
import messenger.lang.Parser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author MrChebik
 * @version 0.06
 */
public class Frame extends JFrame {

    private static int err_login, err_password;

    static JTextField login;
    static JPasswordField password;
    static JButton signUp, signIn;
    static JLabel inputName, inputPassword;

    Timer time = new Timer(50, e -> {
        correctUserAndPassword();
    });

    private Frame() {
        Main.logger.info(Parser.getFrame() + Parser.getCreateComponents() + "...");

        JButton en_ru = new JButton("en/ru");
        en_ru.setMargin(new Insets(-40, -41, -38, -40));
        en_ru.setSize(37, 16);
        en_ru.addActionListener(e -> {
            Parser.changeLang();
            renameComp();
        });
        add(en_ru);

        inputName = new JLabel(Parser.getLogin());
        inputName.setHorizontalAlignment(SwingConstants.CENTER);
        inputName.setVerticalAlignment(SwingConstants.CENTER);
        inputName.setBounds(0, 0, 170, 24);
        add(inputName);

        login = new JTextField();
        login.setFont(new Font(null, Font.PLAIN, 13));
        login.setBounds(16, 25, 135, 20);
        login.setBorder(BorderFactory.createLineBorder(Color.decode("#808080"), 1));
        add(login);

        inputPassword = new JLabel(Parser.getPassword0());
        inputPassword.setHorizontalAlignment(SwingConstants.CENTER);
        inputPassword.setVerticalAlignment(SwingConstants.CENTER);
        inputPassword.setBounds(0, 45, 170, 24);
        add(inputPassword);

        password = new JPasswordField();
        password.setFont(new Font(null, Font.PLAIN, 13));
        password.setBounds(16, 69, 135, 20);
        password.setBorder(BorderFactory.createLineBorder(Color.decode("#808080"), 1));
        add(password);

        signIn = new JButton(Parser.getSignin());
        signIn.setBounds(16, 98, 135, 24);
        signIn.addActionListener(e -> {
            SignIn.writer.println("signin");
            sendInfoAboutUser();
        });
        add(signIn);

        signUp = new JButton(Parser.getSignup());
        signUp.setBounds(16, 131, 135, 24);
        signUp.addActionListener(e -> {
            SignIn.writer.println("signup");
            sendInfoAboutUser();
        });
        add(signUp);

        Main.logger.info(Parser.getSetting() + "...");

        setTitle("Sign in");
        setSize(171, 195);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                Main.logger.info(Parser.getExit() + "...");
                System.exit(0);
            }
        });
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
        time.start();
    }

    static void correctUserAndPassword() {
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
    }

    private static void renameComp() {
        Main.logger.info(Parser.getParser() + Parser.getRename() + "...");

        inputName.setText(Parser.getLogin());
        inputPassword.setText(Parser.getPassword0());
        signIn.setText(Parser.getSignin());
        signUp.setText(Parser.getSignup());
    }

    public static void sendInfoAboutUser() {
        Main.logger.info(Parser.getServer() + Parser.getSendInfo() + "...");

        SignIn.writer.println(Frame.getLogin());
        SignIn.writer.println(Frame.getPassword());
    }

    private static class SingletonHolder {
        private static final Frame INSTANCE = new Frame();
    }

    public static Frame getInstance() {
        return SingletonHolder.INSTANCE;
    }

    // ------- Getters and setters -------------------->
    public static String getLogin() {
        return login.getText();
    }

    public static String getPassword() {
        return password.getText();
    }

}
