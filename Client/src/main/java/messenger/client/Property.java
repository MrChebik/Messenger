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

import messenger.Main;
import messenger.lang.Parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author MrChebik
 * @version 0.06
 */
public class Property {

    private static String host, port, editorPane, work, format;

    Property(InputStream inputStream) {
        Properties property = new Properties();
        try {
            property.load(inputStream);
        } catch (IOException e) {
            Main.logger.error(Parser.getLoadProperty_err());
        }

        setHost(property.getProperty("s.host"));
        setPort(property.getProperty("s1.port"));
        setEditorPane(property.getProperty("d.JEditorPane"));
        setWork(property.getProperty("t.work"));
        setFormat(property.getProperty("t.format"));
    }

    public static String getHost() {
        return host;
    }

    public static void setHost(String host) {
        Property.host = host;
    }

    public static String getPort() {
        return port;
    }

    public static void setPort(String port) {
        Property.port = port;
    }

    public static String getEditorPane() {
        return editorPane;
    }

    public static void setEditorPane(String editorPane) {
        Property.editorPane = editorPane;
    }

    public static String getWork() {
        return work;
    }

    public static void setWork(String work) {
        Property.work = work;
    }

    public static String getFormat() {
        return format;
    }

    public static void setFormat(String format) {
        Property.format = format;
    }
}
