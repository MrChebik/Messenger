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

import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * @author MrChebik
 * @version 0.07
 */
public class ParserTest {

    static File file = new File(System.getProperty("user.dir") + "/src/main/resources/en.xml");

    static Element eElementListProperty, eElementListDatabase0, eElementListServer, eElementListTakeUser, eElementListDatabase1, eElementListError;

    @Test
    public void file_isDirectory() {
        assertFalse("'file' is directory", file.isDirectory());
    }

    @Test
    public void file_isFile() {
        assertTrue("'file' does not file", file.isFile());
    }

    @Test
    public void file_canRead() {
        assertTrue("'file' can not be read", file.canRead());
    }

    @BeforeClass
    public static void file_Initialize() {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        Document doc = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(String.valueOf(messenger.Main.class.getResource("/en.xml")));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            fail(e.getMessage());
        }

        NodeList listProperty = doc.getElementsByTagName("property");
        Node nodeListProperty = listProperty.item(0);
        eElementListProperty = (Element) nodeListProperty;

        NodeList listDatabase = doc.getElementsByTagName("database");
        Node nodeListDatabase0 = listDatabase.item(0);
        eElementListDatabase0 = (Element) nodeListDatabase0;

        NodeList listServer = doc.getElementsByTagName("server");
        Node nodeListServer = listServer.item(0);
        eElementListServer = (Element) nodeListServer;

        NodeList listTakeUser = doc.getElementsByTagName("takeUser");
        Node nodeListTakeUser = listTakeUser.item(0);
        eElementListTakeUser = (Element) nodeListTakeUser;

        Node nodeListDatabase1 = listDatabase.item(1);
        eElementListDatabase1 = (Element) nodeListDatabase1;

        NodeList listError = doc.getElementsByTagName("error");
        Node nodeListError = listError.item(0);
        eElementListError = (Element) nodeListError;
    }

    @Test
    public void tag_NotNull_LoadFile() {
        String loadFile = eElementListProperty.getElementsByTagName("loadFile").item(0).getTextContent();

        assertNotNull(loadFile);
    }

    @Test
    public void tag_NotNull_SettingValues() {
        String settingValues = eElementListProperty.getElementsByTagName("settingValues").item(0).getTextContent();

        assertNotNull(settingValues);
    }

    @Test
    public void tag_NotNull_Connect() {
        String connect = eElementListDatabase0.getElementsByTagName("connect").item(0).getTextContent();

        assertNotNull(connect);
    }

    @Test
    public void tag_NotNull_CreateDB() {
        String createDB = eElementListDatabase0.getElementsByTagName("createDB").item(0).getTextContent();

        assertNotNull(createDB);
    }

    @Test
    public void tag_NotNull_CreateNewUser() {
        String createNewUser = eElementListDatabase0.getElementsByTagName("createNewUser").item(0).getTextContent();

        assertNotNull(createNewUser);
    }

    @Test
    public void tag_NotNull_DeleteLastID() {
        String deleteLastID = eElementListDatabase0.getElementsByTagName("deleteLastID").item(0).getTextContent();

        assertNotNull(deleteLastID);
    }

    @Test
    public void tag_NotNull_FindUser() {
        String findUser = eElementListDatabase0.getElementsByTagName("findUser").item(0).getTextContent();

        assertNotNull(findUser);
    }

    @Test
    public void tag_NotNull_ShowAllUsers() {
        String showAllUsers = eElementListDatabase0.getElementsByTagName("showAllUsers").item(0).getTextContent();

        assertNotNull(showAllUsers);
    }

    @Test
    public void tag_NotNull_ShowAllUsersLogin() {
        String showAllUsersLogin = eElementListDatabase0.getElementsByTagName("showAllUsersLogin").item(0).getTextContent();

        assertNotNull(showAllUsersLogin);
    }

    @Test
    public void tag_NotNull_Start() {
        String start = eElementListServer.getElementsByTagName("start").item(0).getTextContent();

        assertNotNull(start);
    }

    @Test
    public void tag_NotNull_CreatePorts() {
        String createPorts = eElementListServer.getElementsByTagName("createPorts").item(0).getTextContent();

        assertNotNull(createPorts);
    }

    @Test
    public void tag_NotNull_Console() {
        String console = eElementListServer.getElementsByTagName("console").item(0).getTextContent();

        assertNotNull(console);
    }

    @Test
    public void tag_NotNull_ThreadStart() {
        String threadStart = eElementListTakeUser.getElementsByTagName("threadStart").item(0).getTextContent();

        assertNotNull(threadStart);
    }

    @Test
    public void tag_NotNull_WaitUser() {
        String waitUser = eElementListTakeUser.getElementsByTagName("waitUser").item(0).getTextContent();

        assertNotNull(waitUser);
    }

    @Test
    public void tag_NotNull_SignInUp() {
        String signInUp = eElementListTakeUser.getElementsByTagName("signInUp").item(0).getTextContent();

        assertNotNull(signInUp);
    }

    @Test
    public void tag_NotNull_AddOutputStream() {
        String addOutputStream = eElementListTakeUser.getElementsByTagName("addOutputStream").item(0).getTextContent();

        assertNotNull(addOutputStream);
    }

    @Test
    public void tag_NotNull_AddThreadMessageUser() {
        String addThreadMessageUser = eElementListTakeUser.getElementsByTagName("addThreadMessageUser").item(0).getTextContent();

        assertNotNull(addThreadMessageUser);
    }

    @Test
    public void tag_NotNull_Success() {
        String success = eElementListTakeUser.getElementsByTagName("success").item(0).getTextContent();

        assertNotNull(success);
    }

    @Test
    public void tag_NotNull_InvalidAuthorizationConsole_err() {
        String invalidAuthorizationConsole_err = eElementListDatabase1.getElementsByTagName("invalidAuthorizationConsole").item(0).getTextContent();

        assertNotNull(invalidAuthorizationConsole_err);
    }

    @Test
    public void tag_NotNull_InvalidAuthorizationFile_err() {
        String invalidAuthorizationFile_err = eElementListDatabase1.getElementsByTagName("invalidAuthorizationFile").item(0).getTextContent();

        assertNotNull(invalidAuthorizationFile_err);
    }

    @Test
    public void tag_NotNull_LoadFile_err() {
        String loadFile_err = eElementListError.getElementsByTagName("loadFile").item(0).getTextContent();

        assertNotNull(loadFile_err);
    }

    @Test
    public void tag_NotNull_Property() {
        String property = eElementListProperty.getAttribute("value");

        assertNotNull(property);
    }

    @Test
    public void tag_NotNull_Database() {
        String database = eElementListDatabase0.getAttribute("value");

        assertNotNull(database);
    }

    @Test
    public void tag_NotNull_Server() {
        String server = eElementListServer.getAttribute("value");

        assertNotNull(server);
    }

    @Test
    public void tag_NotNull_TakeUser() {
        String takeUser = eElementListTakeUser.getAttribute("value");

        assertNotNull(takeUser);
    }

}