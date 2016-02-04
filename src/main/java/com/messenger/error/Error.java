package com.messenger.error;

import javax.swing.*;
import java.awt.*;

/**
 * Created by alex on 15.01.16.
 */
public class Error extends JFrame {

    private static String errorMessage = "";
    private static JLabel aboutError = new JLabel();
    private static JLabel aboutCauses = new JLabel();

    public static void setErrorMessage(String errorMessage) {
        Error.errorMessage = errorMessage;
        writeAboutError(errorMessage);
        writeAboutCauses(errorMessage);
    }

    private Error() {
        aboutError.setBounds(13, 15, 220, 26);
        aboutError.setFont(new Font(null, Font.BOLD, 16));
        aboutError.setHorizontalAlignment(SwingConstants.CENTER);
        aboutError.setVerticalAlignment(SwingConstants.CENTER);
        add(aboutError);

        aboutCauses.setBounds(12, 50, 220, 55);
        add(aboutCauses);

        setTitle("Error");
        setSize(250, 135);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
    }

    private static void writeAboutError(String errorMessage) {
        aboutError.setText(errorMessage);
    }

    private static void writeAboutCauses(String errorMessage) {
        if (errorMessage.equals("В соединении отказано"))
            aboutCauses.setText("<html>Причины:<br>" +
                    " - нет доступа к Интернету<br>" +
                    " - сервер отключен/недоступен");
    }

    private static class SingletonHolder {
        private static final Error INSTANCE = new Error();
    }

    public static Error getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
