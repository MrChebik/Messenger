/**
 * Copyright 2016 Alexander Beschasny
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
 * along with Messenger.  If not, see <http://www.gnu.org/licenses/gpl-3.0.txt>.
 * <p>
 * Alexander Beschasny mrchebik@yandex.ru
 */

package messenger.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @version 0.05
 * @author mrchebik
 */
public class CheckMessage implements Runnable {
    BufferedReader reader;

    CheckMessage() {
        try {
            reader = new BufferedReader(new InputStreamReader(Main.socketM.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String newMessage;
        try {
            while ((newMessage = reader.readLine()) != null) {
                Set set = Main.streams.entrySet();
                Iterator iter = set.iterator();
                if (newMessage.equals("$exit$")) {
                    String fast_login = reader.readLine();
                    ResultSet rs = Main.getStatement().executeQuery("SELECT * FROM `Users` WHERE `login` LIKE \"" + fast_login + "\"");
                    if (rs.next()) {
                        while (iter.hasNext()) {
                            Map.Entry me = (Map.Entry) iter.next();
                            if ((int) me.getKey() == rs.getInt(1)) {
                                iter.remove();
                                break;
                            }
                        }
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
            ex.printStackTrace();
        }
    }
}
