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

package messenger.server;

import messenger.server.lang.Parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @version 0.07
 * @author MrChebik
 */
public class Property {

    private static Properties properties = new Properties();
    private static String host, user, password, portSign, portMessage;

    public static void loadFile(InputStream inputStream) throws IOException {
        Main.logger.info(Parser.getProperty() + Parser.getLoadFile() + "...");

        properties.load(inputStream);
    }

    public static void settingValues() {
        Main.logger.info(Parser.getProperty() + Parser.getSettingValues() + "...");

        setHost(properties.getProperty("db.host"));
        setUser(properties.getProperty("db.user"));
        setPassword(properties.getProperty("db.password"));
        setPortSign(properties.getProperty("p.sign"));
        setPortMessage(properties.getProperty("p.message"));
    }

    // ------- Getters and setters -------------------->
    public static String getHost() {
        return host;
    }

    public static void setHost(String host) {
        Property.host = host;
    }

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        Property.user = user;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        Property.password = password;
    }

    public static String getPortSign() {
        return portSign;
    }

    public static void setPortSign(String portSign) {
        Property.portSign = portSign;
    }

    public static String getPortMessage() {
        return portMessage;
    }

    public static void setPortMessage(String portMessage) {
        Property.portMessage = portMessage;
    }

}
