package messenger.lang;

import messenger.SignIn;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by alex on 24.04.16.
 */

public class Parser {

    private static String loadProperty, createComponents, settingFrame, connect, thread, exit, password, notExist, existLogin, loadProperty_err, error, connect0, login, password0, signin, signup, send, file, help, about, version, developer, program, reason, reason1, reason2;

    public Parser(String lang) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(String.valueOf(SignIn.class.getResource("/" + lang + ".xml")));
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

}