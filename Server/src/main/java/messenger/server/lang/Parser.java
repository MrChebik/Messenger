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

package messenger.server.lang;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import messenger.server.Main;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * @version 0.05
 * @author MrChebik
 */
public class Parser {

    private static String loadProperty, loadProperty_err, connectionToDatabasese, createPorts, start, waitUser, create_searchFieldInDatabase, addOutputStream, addThreadToTheUser, next, nextUser, console, nullPointer, threadStop, threadStart;

    public Parser(String lang) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(String.valueOf(Main.class.getResource("/" + lang + ".xml")));
            doc.getDocumentElement().normalize();
            NodeList listInfo = doc.getElementsByTagName("info");
            Node nodeListInfo = listInfo.item(0);
            Element eElementListInfo = (Element) nodeListInfo;

            setLoadProperty(eElementListInfo.getElementsByTagName("loadProperty").item(0).getTextContent());
            setConnectionToDatabasese(eElementListInfo.getElementsByTagName("connectionToDatabase").item(0).getTextContent());
            setCreatePorts(eElementListInfo.getElementsByTagName("createPorts").item(0).getTextContent());
            setStart(eElementListInfo.getElementsByTagName("start").item(0).getTextContent());
            setWaitUser(eElementListInfo.getElementsByTagName("waitUser").item(0).getTextContent());
            setCreate_searchFieldInDatabase(eElementListInfo.getElementsByTagName("create_searchFieldInDatabase").item(0).getTextContent());
            setAddOutputStream(eElementListInfo.getElementsByTagName("addOutputStream").item(0).getTextContent());
            setAddThreadToTheUser(eElementListInfo.getElementsByTagName("addThreadToTheUser").item(0).getTextContent());
            setNext(eElementListInfo.getElementsByTagName("next").item(0).getTextContent());
            setNextUser(eElementListInfo.getElementsByTagName("waitNext").item(0).getTextContent());
            setConsole(eElementListInfo.getElementsByTagName("console").item(0).getTextContent());
            setThreadStop(eElementListInfo.getElementsByTagName("threadStop").item(0).getTextContent());
            setThreadStart(eElementListInfo.getElementsByTagName("threadStart").item(0).getTextContent());

            NodeList listError = doc.getElementsByTagName("error");
            Node nodeListError = listError.item(0);
            Element eElementListError = (Element) nodeListError;
            setLoadProperty_err(eElementListError.getElementsByTagName("loadProperty").item(0).getTextContent());
            setNullPointer(eElementListError.getElementsByTagName("nullPointer").item(0).getTextContent());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getLoadProperty() {
        return loadProperty;
    }

    public static void setLoadProperty(String loadProperty) {
        Parser.loadProperty = loadProperty;
    }

    public static String getConnectionToDatabasese() {
        return connectionToDatabasese;
    }

    public static void setConnectionToDatabasese(String connectionToDatabasese) {
        Parser.connectionToDatabasese = connectionToDatabasese;
    }

    public static String getCreatePorts() {
        return createPorts;
    }

    public static void setCreatePorts(String createPorts) {
        Parser.createPorts = createPorts;
    }

    public static String getStart() {
        return start;
    }

    public static void setStart(String start) {
        Parser.start = start;
    }

    public static String getWaitUser() {
        return waitUser;
    }

    public static void setWaitUser(String waitUser) {
        Parser.waitUser = waitUser;
    }

    public static String getCreate_searchFieldInDatabase() {
        return create_searchFieldInDatabase;
    }

    public static void setCreate_searchFieldInDatabase(String create_searchFieldInDatabase) {
        Parser.create_searchFieldInDatabase = create_searchFieldInDatabase;
    }

    public static String getAddOutputStream() {
        return addOutputStream;
    }

    public static void setAddOutputStream(String addOutputStream) {
        Parser.addOutputStream = addOutputStream;
    }

    public static String getAddThreadToTheUser() {
        return addThreadToTheUser;
    }

    public static void setAddThreadToTheUser(String addThreadToTheUser) {
        Parser.addThreadToTheUser = addThreadToTheUser;
    }

    public static String getNext() {
        return next;
    }

    public static void setNext(String next) {
        Parser.next = next;
    }

    public static String getLoadProperty_err() {
        return loadProperty_err;
    }

    public static void setLoadProperty_err(String loadProperty_err) {
        Parser.loadProperty_err = loadProperty_err;
    }

    public static String getNextUser() {
        return nextUser;
    }

    public static void setNextUser(String nextUser) {
        Parser.nextUser = nextUser;
    }

    public static String getConsole() {
        return console;
    }

    public static void setConsole(String console) {
        Parser.console = console;
    }

    public static String getNullPointer() {
        return nullPointer;
    }

    public static void setNullPointer(String nullPointer) {
        Parser.nullPointer = nullPointer;
    }

    public static String getThreadStop() {
        return threadStop;
    }

    public static void setThreadStop(String threadStop) {
        Parser.threadStop = threadStop;
    }

    public static String getThreadStart() {
        return threadStart;
    }

    public static void setThreadStart(String threadStart) {
        Parser.threadStart = threadStart;
    }
}