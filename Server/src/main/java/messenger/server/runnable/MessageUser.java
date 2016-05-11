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

package messenger.server.runnable;

import messenger.server.ConnectorDatabase;
import messenger.server.Main;
import messenger.server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @version 0.07
 * @author MrChebik
 */
public class MessageUser implements Runnable {

    private static BufferedReader reader;
    private static Socket socket = Server.socketM;

    public MessageUser() {
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            Main.logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String newMessage;
        try {
            while ((newMessage = reader.readLine()) != null) {
                Set set = Server.streams.entrySet();
                Iterator iter = set.iterator();
                if (newMessage.charAt(0) == '$') {
                    if (newMessage.equals("$exit$")) {
                        ResultSet rs = ConnectorDatabase.findUser(reader.readLine());
                        if (rs.next()) {
                            while (iter.hasNext()) {
                                Map.Entry me = (Map.Entry) iter.next();
                                if ((int) me.getKey() == rs.getInt(1)) {
                                    iter.remove();
                                    break;
                                }
                            }
                        }
                    } else if (newMessage.equals("$search$")) {
                        PrintWriter writer = new PrintWriter(socket.getOutputStream());
                        String users = "$";
                        ResultSet rs = ConnectorDatabase.showAllUsersLogin();
                        while (rs.next()) {
                            users += rs.getString(1) + " ";
                        }
                        writer.println(users);
                        writer.flush();
                    }
                } else {
                    while (iter.hasNext()) {
                        Map.Entry me = (Map.Entry) iter.next();
                        PrintWriter writer = (PrintWriter) me.getValue();
                        writer.println(newMessage);
                        writer.flush();
                    }
                }
            }
        } catch (Exception ex) {
            Main.logger.error(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
