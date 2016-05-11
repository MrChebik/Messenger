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

import messenger.Error;
import messenger.Main;
import messenger.client.Client;
import messenger.lang.Parser;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @version 0.07
 * @author MrChebik
 */
public class SignIn {

    private static Thread t = new Thread(new checkMessageFromServer());
    public static PrintWriter writer;
    private static BufferedReader reader;

    public SignIn() {
        try {
            Property.loadFile(SignIn.class.getResourceAsStream("/config_client.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            Main.logger.error(Parser.getProperty() + Parser.getLoadFile_err());
        }

        Property.settingValues();

        try {
            connectionToTheServer();
        } catch (IOException e) {
            Main.logger.error(Parser.getProperty() + e.getMessage());
            e.printStackTrace();
            new Error(e.getMessage());
        }

        if (Error.getErrorMessage().equals("")) {
            Frame.getInstance();

            Main.logger.info(Parser.getThread() + "...");
            t.start();
        }
    }

    private static void connectionToTheServer() throws IOException {
        Main.logger.info(Parser.getConnect() + "...");

        Socket socket = new Socket(Property.getHost(), Property.getPort());
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream(), true);
    }

    public static class checkMessageFromServer implements Runnable {
        String messageFromServer;

        public void run() {
            try {
                while ((messageFromServer = reader.readLine()) != null) {
                    if (messageFromServer.equals("0")) {
                        Frame.getInstance().dispose();
                        new Client();
                        messenger.client.frame.Frame.setLogin(Frame.getLogin());
                        t.stop();
                    } else if (messageFromServer.equals("3")) {
                        JOptionPane.showMessageDialog(null, Parser.getPassword(), Parser.getError(), JOptionPane.ERROR_MESSAGE);
                        Frame.password.setText("");
                    } else {
                        if (messageFromServer.equals("4")) {
                            JOptionPane.showMessageDialog(null, Parser.getNotExist(), Parser.getError(), JOptionPane.ERROR_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, Parser.getExistLogin(), Parser.getError(), JOptionPane.ERROR_MESSAGE);
                        }
                        Frame.login.setText("");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}