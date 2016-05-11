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

package messenger.lang;

import messenger.signin.SignIn;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * @version 0.06
 * @author MrChebik
 */

public class Parser {

    private static String property, loadFile, settingValues;
    private static String frame, createComponents, setting, exit;
    private static String server, connect, thread, sendInfo;
    private static String parser, rename;

    private static String error, error0;
    private static String password, notExist, existLogin, loadFile_err;
    private static String connect0, reason, reason1, reason2;

    private static String login, password0, signin, signup, send, file, help, about, version, developer, program, settings, languages, chat, search;

    public static String language;

    public Parser(String lang) {
        doSomething(lang);
    }

    private static void doSomething(String lang) {
        language = lang;

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document doc = null;
        try {
            doc = dBuilder.parse(String.valueOf(SignIn.class.getResource("/" + lang + ".xml")));
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        doc.getDocumentElement().normalize();

        NodeList listProperty = doc.getElementsByTagName("property");
        Node nodeListProperty = listProperty.item(0);
        Element eElementlistProperty = (Element) nodeListProperty;
        setProperty(eElementlistProperty.getAttribute("value"));
        setLoadFile(eElementlistProperty.getElementsByTagName("loadFile").item(0).getTextContent());
        setSettingValues(eElementlistProperty.getElementsByTagName("settingValues").item(0).getTextContent());

        NodeList listFrame = doc.getElementsByTagName("frame");
        Node nodeListFrame = listFrame.item(0);
        Element eElementListFrame = (Element) nodeListFrame;
        setFrame(eElementListFrame.getAttribute("value"));
        setCreateComponents(eElementListFrame.getElementsByTagName("createComponents").item(0).getTextContent());
        setSetting(eElementListFrame.getElementsByTagName("setting").item(0).getTextContent());
        setExit(eElementListFrame.getElementsByTagName("exit").item(0).getTextContent());

        NodeList listServer = doc.getElementsByTagName("server");
        Node nodeListServer = listServer.item(0);
        Element eElementListServer = (Element) nodeListServer;
        setServer(eElementListServer.getAttribute("value"));
        setConnect(eElementListServer.getElementsByTagName("connect").item(0).getTextContent());
        setThread(eElementListServer.getElementsByTagName("thread").item(0).getTextContent());
        setSendInfo(eElementListServer.getElementsByTagName("sendInfo").item(0).getTextContent());

        NodeList listParser = doc.getElementsByTagName("parser");
        Node nodeListParser = listParser.item(0);
        Element eElementListParser = (Element) nodeListParser;
        setParser(eElementListParser.getAttribute("value"));
        setRename(eElementListParser.getElementsByTagName("rename").item(0).getTextContent());

        Node nodeListServer0 = listServer.item(1);
        Element eElementListServer0 = (Element) nodeListServer0;
        setPassword(eElementListServer0.getElementsByTagName("password").item(0).getTextContent());
        setNotExist(eElementListServer0.getElementsByTagName("notExist").item(0).getTextContent());
        setExistLogin(eElementListServer0.getElementsByTagName("existLogin").item(0).getTextContent());

        NodeList listError = doc.getElementsByTagName("error");
        Node nodeListError = listError.item(0);
        Element eElementListError = (Element) nodeListError;
        setError(eElementListError.getAttribute("value"));
        setError0(eElementListError.getElementsByTagName("error").item(0).getTextContent());
        setLoadFile_err(eElementListError.getElementsByTagName("loadFile").item(0).getTextContent());

        Node nodeListFrame0 = listError.item(1);
        Element eElementListFrame0 = (Element) nodeListFrame0;
        setConnect0(eElementListFrame0.getElementsByTagName("connect").item(0).getTextContent());
        setReason(eElementListFrame0.getElementsByTagName("reason").item(0).getTextContent());
        setReason1(eElementListFrame0.getElementsByTagName("reason1").item(0).getTextContent());
        setReason2(eElementListFrame0.getElementsByTagName("reason2").item(0).getTextContent());

        NodeList listComponents = doc.getElementsByTagName("components");
        Node nodeListComponents = listComponents.item(0);
        Element eElementListComponents = (Element) nodeListComponents;
        setLogin(eElementListComponents.getElementsByTagName("user").item(0).getTextContent());
        setPassword0(eElementListComponents.getElementsByTagName("password").item(0).getTextContent());
        setSignin(eElementListComponents.getElementsByTagName("signin").item(0).getTextContent());
        setSignup(eElementListComponents.getElementsByTagName("signup").item(0).getTextContent());
        setSend(eElementListComponents.getElementsByTagName("send").item(0).getTextContent());
        setFile(eElementListComponents.getElementsByTagName("file").item(0).getTextContent());
        setHelp(eElementListComponents.getElementsByTagName("help").item(0).getTextContent());
        setAbout(eElementListComponents.getElementsByTagName("about").item(0).getTextContent());
        setVersion(eElementListComponents.getElementsByTagName("version").item(0).getTextContent());
        setDeveloper(eElementListComponents.getElementsByTagName("developer").item(0).getTextContent());
        setProgram(eElementListComponents.getElementsByTagName("program").item(0).getTextContent());
        setSettings(eElementListComponents.getElementsByTagName("settings").item(0).getTextContent());
        setLanguages(eElementListComponents.getElementsByTagName("languages").item(0).getTextContent());
        setChat(eElementListComponents.getElementsByTagName("chat").item(0).getTextContent());
        setSearch(eElementListComponents.getElementsByTagName("search").item(0).getTextContent());
    }

    public static void changeLang() {
        if (language.equals("en")) {
            doSomething("ru");
        } else {
            doSomething("en");
        }
    }

    public static void ru() {
        doSomething("ru");
    }

    public static void en() {
        doSomething("en");
    }

    // ------- Getters and setters -------------------->
    public static String getLoadFile() {
        return loadFile;
    }

    public static void setLoadFile(String loadFile) {
        Parser.loadFile = loadFile;
    }

    public static String getCreateComponents() {
        return createComponents;
    }

    public static void setCreateComponents(String createComponents) {
        Parser.createComponents = createComponents;
    }

    public static String getSetting() {
        return setting;
    }

    public static void setSetting(String setting) {
        Parser.setting = setting;
    }

    public static String getConnect() {
        return connect;
    }

    public static void setConnect(String connect) {
        Parser.connect = connect;
    }

    public static String getThread() {
        return thread;
    }

    public static void setThread(String thread) {
        Parser.thread = thread;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        Parser.password = password;
    }

    public static String getNotExist() {
        return notExist;
    }

    public static void setNotExist(String notExist) {
        Parser.notExist = notExist;
    }

    public static String getExistLogin() {
        return existLogin;
    }

    public static void setExistLogin(String existLogin) {
        Parser.existLogin = existLogin;
    }

    public static String getError() {
        return error;
    }

    public static void setError(String error) {
        Parser.error = error;
    }

    public static String getLoadFile_err() {
        return loadFile_err;
    }

    public static void setLoadFile_err(String loadFile_err) {
        Parser.loadFile_err = loadFile_err;
    }

    public static String getLogin() {
        return login;
    }

    public static void setLogin(String login) {
        Parser.login = login;
    }

    public static String getPassword0() {
        return password0;
    }

    public static void setPassword0(String password0) {
        Parser.password0 = password0;
    }

    public static String getSend() {
        return send;
    }

    public static void setSend(String send) {
        Parser.send = send;
    }

    public static String getSignup() {
        return signup;
    }

    public static void setSignup(String signup) {
        Parser.signup = signup;
    }

    public static String getSignin() {
        return signin;
    }

    public static void setSignin(String signin) {
        Parser.signin = signin;
    }

    public static String getExit() {
        return exit;
    }

    public static void setExit(String exit) {
        Parser.exit = exit;
    }

    public static String getProgram() {
        return program;
    }

    public static void setProgram(String program) {
        Parser.program = program;
    }

    public static String getFile() {
        return file;
    }

    public static void setFile(String file) {
        Parser.file = file;
    }

    public static String getHelp() {
        return help;
    }

    public static void setHelp(String help) {
        Parser.help = help;
    }

    public static String getAbout() {
        return about;
    }

    public static void setAbout(String about) {
        Parser.about = about;
    }

    public static String getVersion() {
        return version;
    }

    public static void setVersion(String version) {
        Parser.version = version;
    }

    public static String getDeveloper() {
        return developer;
    }

    public static void setDeveloper(String developer) {
        Parser.developer = developer;
    }

    public static String getConnect0() {
        return connect0;
    }

    public static void setConnect0(String connect0) {
        Parser.connect0 = connect0;
    }

    public static String getReason() {
        return reason;
    }

    public static void setReason(String reason) {
        Parser.reason = reason;
    }

    public static String getReason1() {
        return reason1;
    }

    public static void setReason1(String reason1) {
        Parser.reason1 = reason1;
    }

    public static String getReason2() {
        return reason2;
    }

    public static void setReason2(String reason2) {
        Parser.reason2 = reason2;
    }

    public static String getSettings() {
        return settings;
    }

    public static void setSettings(String settings) {
        Parser.settings = settings;
    }

    public static String getLanguages() {
        return languages;
    }

    public static void setLanguages(String languages) {
        Parser.languages = languages;
    }

    public static String getSearch() {
        return search;
    }

    public static void setSearch(String search) {
        Parser.search = search;
    }

    public static String getChat() {
        return chat;
    }

    public static void setChat(String chat) {
        Parser.chat = chat;
    }

    public static String getProperty() {
        return property;
    }

    public static void setProperty(String property) {
        Parser.property = property;
    }

    public static String getFrame() {
        return frame;
    }

    public static void setFrame(String frame) {
        Parser.frame = frame;
    }

    public static String getServer() {
        return server;
    }

    public static void setServer(String server) {
        Parser.server = server;
    }

    public static String getSettingValues() {
        return settingValues;
    }

    public static void setSettingValues(String settingValues) {
        Parser.settingValues = settingValues;
    }

    public static String getError0() {
        return error0;
    }

    public static void setError0(String error0) {
        Parser.error0 = error0;
    }

    public static String getRename() {
        return rename;
    }

    public static void setRename(String rename) {
        Parser.rename = rename;
    }

    public static String getParser() {
        return parser;
    }

    public static void setParser(String parser) {
        Parser.parser = parser;
    }

    public static String getSendInfo() {
        return sendInfo;
    }

    public static void setSendInfo(String sendInfo) {
        Parser.sendInfo = sendInfo;
    }
}