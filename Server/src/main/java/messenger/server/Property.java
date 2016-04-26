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

import messenger.server.lang.Parser;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @version 0.05
 * @author mrchebik
 */
public class Property {

    private static String host, login, password, portLogin, portMessage;

    static Logger logger = Logger.getLogger(Property.class.getName());

    public Property(InputStream inputStream) {
        Properties properties = new Properties();

        try {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error(Parser.getLoadProperty_err());
        }

        setHost(properties.getProperty("db.host"));
        setLogin(properties.getProperty("db.login"));
        setPassword(properties.getProperty("db.password"));
        setPortLogin(properties.getProperty("p.login"));
        setPortMessage(properties.getProperty("p.messages"));
    }

    public static String getHost() {
        return host;
    }

    public static void setHost(String host) {
        Property.host = host;
    }

    public static String getLogin() {
        return login;
    }

    public static void setLogin(String login) {
        Property.login = login;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        Property.password = password;
    }

    public static String getPortLogin() {
        return portLogin;
    }

    public static void setPortLogin(String portLogin) {
        Property.portLogin = portLogin;
    }

    public static String getPortMessage() {
        return portMessage;
    }

    public static void setPortMessage(String portMessage) {
        Property.portMessage = portMessage;
    }
}
