/**
 * Copyright 2015, 2016 Alexander Beschasny
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

/**
 * Created by alex on 10.01.16.
 */

import org.apache.log4j.Logger;
import messenger.server.lang.Parser;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

public class Main {

    private static Socket socket0;
    private static Socket socket1;
    private static ServerSocket serverSocket0;
    private static ServerSocket serverSocket1;
    private static ArrayList<PrintWriter> streams = new ArrayList();
    private static ArrayList<Integer> id_streams = new ArrayList();
    private static Connection connection;
    private static Statement statement;
    private static int errorInStage_createField = 0;
    private static int id;

    private static String host;
    private static String login;
    private static String password;
    private static String port_login;
    private static String port_messages;

    static Logger logger = Logger.getLogger(Main.class.getName());

    Main() {
        new Parser("en");
        logger.info(Parser.getLoadProperty() + "...");
        loadProperty(Main.class.getResourceAsStream("/config_server.properties"));
        logger.info(Parser.getConnectionToDatabasese() + "...");
        connectionToDatabase(host, login, password);
        logger.info(Parser.getCreatePorts() + "...");
        createPort_login(port_login);
        createPort_messages(port_messages);
        logger.info(Parser.getStart() + "...");
        while (true) {
            logger.info("> " + Parser.getWaitUser() + "...");
            waitUser();
            logger.info("> " + Parser.getCreate_searchFieldInDatabase() + "...");
            while (errorInStage_createField != 2 && errorInStage_createField != 5) {
                create_searchFieldInDatabase();
                if (errorInStage_createField != 2 && errorInStage_createField != 5) {
                    errorInStage_createField = 0;
                }
            }
            if (errorInStage_createField != 5) {
                logger.info("> " + Parser.getAddOutputStream() + "...");
                addOutputStream();
                logger.info("> " + Parser.getAddThreadToTheUser() + "...");
                addThreadForTheUser();
                logger.info("> -------- " + Parser.getNext() + " -------->>");
            }
            errorInStage_createField = 0;
        }
    }

    public static void main(String[] args) {
        new Main();
    }

    private static class checkMessageFromClient implements Runnable {
        BufferedReader reader;

        checkMessageFromClient() {
            try {
                reader = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            String newMessage;
            try {
                while ((newMessage = reader.readLine()) != null) {
                    if (newMessage.equals("$exit$")) {
                        String fast_login = reader.readLine();
                        ResultSet rs = statement.executeQuery("SELECT * FROM `Users` WHERE `login` LIKE \"" + fast_login + "\"");
                        if (rs.next()) {
                            for (int i = 0; i < id_streams.size(); i++) {
                                if (id_streams.get(i) == rs.getInt(1)) {
                                    id_streams.remove(i);
                                    streams.remove(i);
                                    break;
                                }
                            }
                        }
                    } else {
                        Iterator<PrintWriter> it = streams.iterator();
                        while (it.hasNext()) {
                            PrintWriter writer = it.next();
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

    private static void connectionToDatabase(String url, String user, String password) {
        try {
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void create_searchFieldInDatabase() {
        PrintWriter fast_writer = null;
        try {
            fast_writer = new PrintWriter(socket0.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket0.getInputStream()));
            String fast_message = reader.readLine();
            if (fast_message.equals("$exit$")) {
                errorInStage_createField = 5;
            } else if (fast_message.equals("signup")) {
                String fast_login = reader.readLine();
                statement.execute("INSERT INTO `Users` (`login`, `password`) VALUES ('" + fast_login + "', '" + reader.readLine() + "');");
                statement.execute("CREATE UNIQUE INDEX " + fast_login + " ON Users(login);");
                logger.info("> Sign Up");
            } else {
                String fast_login = reader.readLine();
                ResultSet rs = statement.executeQuery("SELECT * FROM `Users` WHERE `login` LIKE \"" + fast_login + "\"");
                if (rs.first() == false) {
                    errorInStage_createField = 4;
                } else {
                    id = rs.getInt(1);
                    if (!rs.getString(3).equals(reader.readLine())) {
                        errorInStage_createField = 3;
                    }
                    logger.info("> Sign In");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            errorInStage_createField = 1;
            if (e.getMessage().charAt(0) == 'D') {
                try {
                    statement.execute("DELETE FROM `Users` WHERE id=LAST_INSERT_ID();");
                } catch (SQLException e1) {
                    e.printStackTrace();
                }
            } else {
                e.printStackTrace();
            }
        } finally {
            if (errorInStage_createField == 0) {
                errorInStage_createField = 2;
            }
            if (errorInStage_createField != 5) {
                fast_writer.println(errorInStage_createField);
            }
        }
    }

    private static void createPort_login(String port) {
        try {
            serverSocket0 = new ServerSocket(Integer.parseInt(port));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createPort_messages(String port) {
        try {
            serverSocket1 = new ServerSocket(Integer.parseInt(port));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void waitUser() {
        try {
            socket0 = serverSocket0.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addOutputStream() {
        try {
            socket1 = serverSocket1.accept();
            PrintWriter writer = new PrintWriter(socket1.getOutputStream());
            streams.add(writer);
            id_streams.add(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addThreadForTheUser() {
        Thread t = new Thread(new checkMessageFromClient());
        t.start();
    }

    private static void loadProperty(InputStream inputStream) {
        try {
            Properties properties = new Properties();
            properties.load(inputStream);

            host = properties.getProperty("db.host");
            login = properties.getProperty("db.login");
            password = properties.getProperty("db.password");
            port_login = properties.getProperty("p.login");
            port_messages = properties.getProperty("p.messages");
        } catch (IOException e) {
            logger.error(Parser.getLoadProperty_err());
        }
    }

}