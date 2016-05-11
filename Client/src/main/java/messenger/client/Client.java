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

package messenger.client;

import messenger.Error;
import messenger.Main;
import messenger.client.frame.Frame;
import messenger.client.frame.panels.ChatPanel;
import messenger.client.frame.panels.SearchPanel;
import messenger.lang.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @version 0.06
 * @author MrChebik
 */
public class Client {

    public static String login = "";
    public static PrintWriter writer;
    public static BufferedReader reader;
    private static String text = "";
    private static DateTimeFormatter dateTimeFormatter;

    public Client() {
        try {
            Property.loadFile(Client.class.getResourceAsStream("/config_client.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            Main.logger.error(Parser.getLoadFile_err());
        }

        Property.settingValues();

        if (Property.getWork().equals("0")) {
            dateTimeFormatter = DateTimeFormatter.ofPattern(Property.getFormat());
        }

        Main.logger.info(Parser.getCreateComponents() + "...");
        Frame.getInstance();

        Main.logger.info(Parser.getConnect() + "...");
        try {
            Socket socket = new Socket(Property.getHost(), Integer.parseInt(Property.getPort()));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
            new Error(e.getMessage());
            Frame.getInstance().dispose();
        }

        Main.logger.info(Parser.getThread() + "...");
        Thread t = new Thread(new checkMessageFromServer());
        t.start();
    }


    public static class checkMessageFromServer implements Runnable {
        public void run() {
            try {
                String messageFromServer = "";
                while ((messageFromServer = reader.readLine()) != null) {
                    if (messageFromServer.charAt(0) != '$') {
                        text += (Property.getWork().equals("0") ? "[" + dateTimeFormatter.format(LocalTime.now()) + "] " : "") + messageFromServer + (Property.getEditorPane().equals("text/html") ? "<br>" : "\n");
                        ChatPanel.dialogs.setText(text);
                    } else {
                        String message = messageFromServer.substring(1);
                        String[] arr = message.split(" ");
                        SearchPanel.getInstance().listModel.removeAllElements();
                        SearchPanel.getInstance().listModelCopy.removeAllElements();
                        for (int i = 0; i < arr.length; i++) {
                            SearchPanel.getInstance().listModel.addElement(arr[i]);
                            SearchPanel.getInstance().listModelCopy.addElement(arr[i]);
                        }
                        SearchPanel.getInstance().list.setListData(SearchPanel.getInstance().listModel.toArray());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void sendMessage(String message) {
        if (message.equals("$search$")) {
            writer.println(message);
        } else if (!message.equals("")) {
            String messageFromClient = "<b>" + login + "</b>: " + ChatPanel.message.getText();
            writer.println(messageFromClient);
            ChatPanel.message.setText("");
        }
    }

}
