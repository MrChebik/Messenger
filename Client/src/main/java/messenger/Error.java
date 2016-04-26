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

package messenger;

import messenger.lang.Parser;

import javax.swing.*;
import java.awt.*;

/**
 * @version 0.04
 * @author mrchebik
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

    public Error() {
        aboutError.setBounds(13, 15, 220, 26);
        aboutError.setFont(new Font(null, Font.BOLD, 16));
        aboutError.setHorizontalAlignment(SwingConstants.CENTER);
        aboutError.setVerticalAlignment(SwingConstants.CENTER);
        add(aboutError);

        aboutCauses.setBounds(12, 50, 220, 55);
        add(aboutCauses);

        setTitle(Parser.getError());
        setSize(250, 135);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
    }

    private static void writeAboutError(String errorMessage) {
        if (errorMessage.equals("В соединении отказано")) {
            aboutError.setText(Parser.getConnect0());
        } else {
            aboutError.setText(errorMessage);
        }
    }

    private static void writeAboutCauses(String errorMessage) {
        if (errorMessage.equals("В соединении отказано")) {
            aboutCauses.setText("<html>" + Parser.getReason() + "<br>" + Parser.getReason1() + "<br>" + Parser.getReason2());
        }
    }

}
