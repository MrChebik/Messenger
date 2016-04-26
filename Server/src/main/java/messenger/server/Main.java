/**
 * Copyright 2016 Alexander Beschasny
 *
 * Messenger is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Messenger is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Messenger.  If not, see <http://www.gnu.org/licenses/gpl-3.0.txt>.
 *
 * Alexander Beschasny mrchebik@yandex.ru
 */

package messenger.server;

import messenger.server.lang.Parser;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.*;

/**
 * @author mrchebik
 * @version 0.05
 */
public class Main {

    private static ServerSocket serverSocketS;
    private static ServerSocket serverSocketM;
    private static Connection connection;
    private static int id;

    private static Statement statement;

    static Logger logger = Logger.getLogger(Main.class.getName());
    static HashMap<Integer, PrintWriter> streams = new HashMap();
    static Socket socketM;

    Main() {
        logger.info(Parser.getLoadProperty() + "...");
        new Property(Main.class.getResourceAsStream("/config_server.properties"));
        logger.info(Parser.getConnectionToDatabasese() + "...");
        connectToDB(Property.getHost(), Property.getLogin(), Property.getPassword());
        logger.info(Parser.getCreatePorts() + "...");
        createPorts(Property.getPortLogin(), Property.getPortMessage());
        logger.info(Parser.getThreadStart() + "...");
        newThreadTU();
        logger.info(Parser.getConsole() + "...");
        Command.run();
    }

    public static void main(String[] args) {
        logger.info("-------- New Instance -------->>");
        logger.info("Parsing language file...");
        if (args.length > 0) {
            if (args[0].equals("ru")) {
                new Parser("ru");
            } else if (args[0].equals("en")) {
                new Parser("en");
            }
        } else {
            new Parser("en");
        }
        new Main();
    }

    private static void connectToDB(String url, String user, String password) {
        try {
            connection = DriverManager.getConnection(url, user, password);
            setStatement(connection.createStatement());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static int signInUp(Socket socket, int error, PrintWriter printWriter) throws NullPointerException {
        error = 0;
        try {
            printWriter = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String fast_message = reader.readLine();
            if (fast_message.equals("$exit$")) {
                error = 5;
            } else if (fast_message.equals("signUp")) {
                error = signUp(reader, error);
            } else {
                error = signIn(reader, error);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (error == 0) {
                error = 2;
            }
            if (error != 5) {
                printWriter.println(error);
            }
        }
        return error;
    }

    private static void createPorts(String portLogin, String portMessage) {
        try {
            serverSocketS = new ServerSocket(Integer.parseInt(portLogin));
            serverSocketM = new ServerSocket(Integer.parseInt(portMessage));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static Socket waitUser(Socket socket) {
        try {
            socket = serverSocketS.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return socket;
    }

    static void addOutputStream() {
        try {
            socketM = serverSocketM.accept();
            PrintWriter writer = new PrintWriter(socketM.getOutputStream());
            streams.put(id, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void newThreadMU() {
        Thread t = new Thread(new CheckMessage());
        t.start();
    }

    static void newThreadTU() {
        Thread takingThread = new TakeUser();
        takingThread.start();
    }

    static int signUp(BufferedReader reader, int error) {
        String fast_login;
        try {
            fast_login = reader.readLine();
            getStatement().execute("INSERT INTO `Users` (`login`, `password`) VALUES ('" + fast_login + "', '" + reader.readLine() + "');");
            getStatement().execute("CREATE UNIQUE INDEX " + fast_login + " ON Users(login);");
        } catch (SQLException sqle) {
            error = 1;
            if (sqle.getMessage().charAt(0) == 'D') {
                try {
                    getStatement().execute("DELETE FROM `Users` WHERE id=LAST_INSERT_ID();");
                } catch (SQLException e1) {
                    sqle.printStackTrace();
                }
            } else {
                sqle.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("> Sign Up");

        return error;
    }

    static int signIn(BufferedReader reader, int error) {
        try {
            String fast_login = reader.readLine();
            ResultSet rs = getStatement().executeQuery("SELECT * FROM `Users` WHERE `login` LIKE \"" + fast_login + "\"");
            if (rs.first() == false) {
                error = 4;
            } else {
                id = rs.getInt(1);
                if (!rs.getString(3).equals(reader.readLine())) {
                    error = 3;
                }
                logger.info("> Sign In");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return error;
    }

    static int getStreamSize() {
        return streams.size();
    }

    public static Statement getStatement() {
        return statement;
    }

    public static void setStatement(Statement statement) {
        Main.statement = statement;
    }
}