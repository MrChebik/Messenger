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

    private static String loadProperty, createComponents, settingFrame, connect, thread, exit, password, notExist, existLogin, loadProperty_err, error, connect0, login, password0, signin, signup, send, file, help, about, version, developer, program, reason, reason1, reason2, settings, languages, chat, search;

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
        NodeList listInfo = doc.getElementsByTagName("info");
        Node nodeListInfo = listInfo.item(0);
        Element eElementListInfo = (Element) nodeListInfo;

        setLoadProperty(eElementListInfo.getElementsByTagName("loadProperty").item(0).getTextContent());
        setCreateComponents(eElementListInfo.getElementsByTagName("createComponents").item(0).getTextContent());
        setSettingFrame(eElementListInfo.getElementsByTagName("settingFrame").item(0).getTextContent());
        setConnect(eElementListInfo.getElementsByTagName("connect").item(0).getTextContent());
        setThread(eElementListInfo.getElementsByTagName("thread").item(0).getTextContent());
        setExit(eElementListInfo.getElementsByTagName("exit").item(0).getTextContent());

        NodeList listError = doc.getElementsByTagName("error");
        Node nodeListError = listError.item(0);
        Element eElementListError = (Element) nodeListError;
        setPassword(eElementListError.getElementsByTagName("password").item(0).getTextContent());
        setNotExist(eElementListError.getElementsByTagName("notExist").item(0).getTextContent());
        setExistLogin(eElementListError.getElementsByTagName("existLogin").item(0).getTextContent());
        setLoadProperty_err(eElementListError.getElementsByTagName("loadProperty").item(0).getTextContent());
        setError(eElementListError.getElementsByTagName("error").item(0).getTextContent());
        setConnect0(eElementListError.getElementsByTagName("connect").item(0).getTextContent());
        setReason(eElementListError.getElementsByTagName("reason").item(0).getTextContent());
        setReason1(eElementListError.getElementsByTagName("reason1").item(0).getTextContent());
        setReason2(eElementListError.getElementsByTagName("reason2").item(0).getTextContent());

        NodeList listComponents = doc.getElementsByTagName("components");
        Node nodeListComponents = listComponents.item(0);
        Element eElementListComponents = (Element) nodeListComponents;
        setLogin(eElementListComponents.getElementsByTagName("login").item(0).getTextContent());
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

    public static String getLoadProperty() {
        return loadProperty;
    }

    public static void setLoadProperty(String loadProperty) {
        Parser.loadProperty = loadProperty;
    }

    public static String getCreateComponents() {
        return createComponents;
    }

    public static void setCreateComponents(String createComponents) {
        Parser.createComponents = createComponents;
    }

    public static String getSettingFrame() {
        return settingFrame;
    }

    public static void setSettingFrame(String settingFrame) {
        Parser.settingFrame = settingFrame;
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

    public static String getLoadProperty_err() {
        return loadProperty_err;
    }

    public static void setLoadProperty_err(String loadProperty_err) {
        Parser.loadProperty_err = loadProperty_err;
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
}