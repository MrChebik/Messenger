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
 * @version 0.07
 * @author MrChebik
 */
public class Parser {

    private static String property, loadFile, settingValues;
    private static String database, connect, createDB, createNewUser, deleteLastID, findUser, showAllUsers, showAllUsersLogin;
    private static String server, start, createPorts, console;
    private static String takeUser, threadStart, waitUser, signInUp, addOutputStream, addThreadMessageUser, success;

    private static String invalidAuthorizationConsole_err, invalidAuthorizationFile_err;
    private static String loadFile_err;

    public Parser(String lang) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(String.valueOf(Main.class.getResource("/" + lang + ".xml")));
            doc.getDocumentElement().normalize();

            NodeList listProperty = doc.getElementsByTagName("property");
            Node nodeListProperty = listProperty.item(0);
            Element eElementlistProperty = (Element) nodeListProperty;
            setProperty(eElementlistProperty.getAttribute("value"));
            setLoadFile(eElementlistProperty.getElementsByTagName("loadFile").item(0).getTextContent());
            setSettingValues(eElementlistProperty.getElementsByTagName("settingValues").item(0).getTextContent());

            NodeList listDatabase = doc.getElementsByTagName("database");
            Node nodeListDatabase0 = listDatabase.item(0);
            Element eElementListDatabase0 = (Element) nodeListDatabase0;
            setDatabase(eElementListDatabase0.getAttribute("value"));
            setConnect(eElementListDatabase0.getElementsByTagName("connect").item(0).getTextContent());
            setCreateDB(eElementListDatabase0.getElementsByTagName("createDB").item(0).getTextContent());
            setCreateNewUser(eElementListDatabase0.getElementsByTagName("createNewUser").item(0).getTextContent());
            setDeleteLastID(eElementListDatabase0.getElementsByTagName("deleteLastID").item(0).getTextContent());
            setFindUser(eElementListDatabase0.getElementsByTagName("findUser").item(0).getTextContent());
            setShowAllUsers(eElementListDatabase0.getElementsByTagName("showAllUsers").item(0).getTextContent());
            setShowAllUsersLogin(eElementListDatabase0.getElementsByTagName("showAllUsersLogin").item(0).getTextContent());

            NodeList listServer = doc.getElementsByTagName("server");
            Node nodeListServer = listServer.item(0);
            Element eElementListServer = (Element) nodeListServer;
            setServer(eElementListServer.getAttribute("value"));
            setStart(eElementListServer.getElementsByTagName("start").item(0).getTextContent());
            setCreatePorts(eElementListServer.getElementsByTagName("createPorts").item(0).getTextContent());
            setConsole(eElementListServer.getElementsByTagName("console").item(0).getTextContent());

            NodeList listTakeUser = doc.getElementsByTagName("takeUser");
            Node nodeListTakeUser = listTakeUser.item(0);
            Element eElementListTakeUser = (Element) nodeListTakeUser;
            setTakeUser(eElementListTakeUser.getAttribute("value"));
            setThreadStart(eElementListTakeUser.getElementsByTagName("threadStart").item(0).getTextContent());
            setWaitUser(eElementListTakeUser.getElementsByTagName("waitUser").item(0).getTextContent());
            setSignInUp(eElementListTakeUser.getElementsByTagName("signInUp").item(0).getTextContent());
            setAddOutputStream(eElementListTakeUser.getElementsByTagName("addOutputStream").item(0).getTextContent());
            setAddThreadMessageUser(eElementListTakeUser.getElementsByTagName("addThreadMessageUser").item(0).getTextContent());
            setSuccess(eElementListTakeUser.getElementsByTagName("success").item(0).getTextContent());

            Node nodeListDatabase1 = listDatabase.item(1);
            Element eElementListDatabase1 = (Element) nodeListDatabase1;
            setInvalidAuthorizationConsole_err(eElementListDatabase1.getElementsByTagName("invalidAuthorizationConsole").item(0).getTextContent());
            setInvalidAuthorizationFile_err(eElementListDatabase1.getElementsByTagName("invalidAuthorizationFile").item(0).getTextContent());

            NodeList listError = doc.getElementsByTagName("error");
            Node nodeListError = listError.item(0);
            Element eElementListError = (Element) nodeListError;
            setLoadFile_err(eElementListError.getElementsByTagName("loadFile").item(0).getTextContent());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ------- Getters and setters -------------------->
    public static String getLoadFile() {
        return loadFile;
    }

    public static void setLoadFile(String loadFile) {
        Parser.loadFile = loadFile;
    }

    public static String getSettingValues() {
        return settingValues;
    }

    public static void setSettingValues(String settingValues) {
        Parser.settingValues = settingValues;
    }

    public static String getConnect() {
        return connect;
    }

    public static void setConnect(String connect) {
        Parser.connect = connect;
    }

    public static String getCreateDB() {
        return createDB;
    }

    public static void setCreateDB(String createDB) {
        Parser.createDB = createDB;
    }

    public static String getCreateNewUser() {
        return createNewUser;
    }

    public static void setCreateNewUser(String createNewUser) {
        Parser.createNewUser = createNewUser;
    }

    public static String getDeleteLastID() {
        return deleteLastID;
    }

    public static void setDeleteLastID(String deleteLastID) {
        Parser.deleteLastID = deleteLastID;
    }

    public static String getFindUser() {
        return findUser;
    }

    public static void setFindUser(String findUser) {
        Parser.findUser = findUser;
    }

    public static String getShowAllUsers() {
        return showAllUsers;
    }

    public static void setShowAllUsers(String showAllUsers) {
        Parser.showAllUsers = showAllUsers;
    }

    public static String getShowAllUsersLogin() {
        return showAllUsersLogin;
    }

    public static void setShowAllUsersLogin(String showAllUsersLogin) {
        Parser.showAllUsersLogin = showAllUsersLogin;
    }

    public static String getStart() {
        return start;
    }

    public static void setStart(String start) {
        Parser.start = start;
    }

    public static String getCreatePorts() {
        return createPorts;
    }

    public static void setCreatePorts(String createPorts) {
        Parser.createPorts = createPorts;
    }

    public static String getConsole() {
        return console;
    }

    public static void setConsole(String console) {
        Parser.console = console;
    }

    public static String getThreadStart() {
        return threadStart;
    }

    public static void setThreadStart(String threadStart) {
        Parser.threadStart = threadStart;
    }

    public static String getWaitUser() {
        return waitUser;
    }

    public static void setWaitUser(String waitUser) {
        Parser.waitUser = waitUser;
    }

    public static String getSignInUp() {
        return signInUp;
    }

    public static void setSignInUp(String signInUp) {
        Parser.signInUp = signInUp;
    }

    public static String getAddOutputStream() {
        return addOutputStream;
    }

    public static void setAddOutputStream(String addOutputStream) {
        Parser.addOutputStream = addOutputStream;
    }

    public static String getAddThreadMessageUser() {
        return addThreadMessageUser;
    }

    public static void setAddThreadMessageUser(String addThreadMessageUser) {
        Parser.addThreadMessageUser = addThreadMessageUser;
    }

    public static String getSuccess() {
        return success;
    }

    public static void setSuccess(String success) {
        Parser.success = success;
    }

    public static String getInvalidAuthorizationConsole_err() {
        return invalidAuthorizationConsole_err;
    }

    public static void setInvalidAuthorizationConsole_err(String invalidAuthorizationConsole_err) {
        Parser.invalidAuthorizationConsole_err = invalidAuthorizationConsole_err;
    }

    public static String getInvalidAuthorizationFile_err() {
        return invalidAuthorizationFile_err;
    }

    public static void setInvalidAuthorizationFile_err(String invalidAuthorizationFile_err) {
        Parser.invalidAuthorizationFile_err = invalidAuthorizationFile_err;
    }

    public static String getLoadFile_err() {
        return loadFile_err;
    }

    public static void setLoadFile_err(String loadFile_err) {
        Parser.loadFile_err = loadFile_err;
    }

    public static String getProperty() {
        return property;
    }

    public static void setProperty(String property) {
        Parser.property = property;
    }

    public static String getDatabase() {
        return database;
    }

    public static void setDatabase(String database) {
        Parser.database = database;
    }

    public static String getServer() {
        return server;
    }

    public static void setServer(String server) {
        Parser.server = server;
    }

    public static String getTakeUser() {
        return takeUser;
    }

    public static void setTakeUser(String takeUser) {
        Parser.takeUser = takeUser;
    }

}