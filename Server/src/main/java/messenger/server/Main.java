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

package messenger.server;

import messenger.server.lang.Parser;
import messenger.server.runnable.MessageUser;
import messenger.server.runnable.TakeUser;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.HashMap;

/**
 * @version 0.06
 * @author MrChebik
 */
public class Main {

    private static ServerSocket serverSocketS;
    private static ServerSocket serverSocketM;
    private static int id;

    private static Logger logger = Logger.getLogger(Main.class.getName());
    public static HashMap streams = new HashMap();
    public static Socket socketM;

    private Main() {
        logger.info(Parser.getLoadProperty() + "...");
        new Property(Main.class.getResourceAsStream("/config_server.properties"));
        logger.info(Parser.getConnectionToDatabasese() + "...");
        new ConnectorDatabase(Property.getHost(), Property.getLogin(), Property.getPassword());
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

    public static synchronized int signInUp(Socket socket, PrintWriter printWriter) throws NullPointerException {
        int error = 0;
        try {
            printWriter = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String fast_message = reader.readLine();
            if (fast_message.equals("exit")) {
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

    public static Socket waitUser() {
        Socket socket = null;
        try {
            socket = serverSocketS.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return socket;
    }

    public static void addOutputStream() {
        try {
            socketM = serverSocketM.accept();
            PrintWriter writer = new PrintWriter(socketM.getOutputStream());
            streams.put(id, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void newThreadMU() {
        new Thread(new MessageUser()).start();
    }

    public static void newThreadTU() {
        new Thread(new TakeUser()).start();
    }

    private static synchronized int signUp(BufferedReader reader, int error) {
        try {
            String fast_login = reader.readLine();
            ConnectorDatabase.getStatement().execute("INSERT INTO `Users` (`login`, `password`) VALUES ('" + fast_login + "', '" + reader.readLine() + "');");
            ConnectorDatabase.getStatement().execute("CREATE UNIQUE INDEX " + fast_login + " ON Users(login);");
            logger.info("> Sign Up");
        } catch (SQLException sqle) {
            if (sqle.getMessage().charAt(0) == 'D') {
                error = 1;
                ConnectorDatabase.deleteLastID();
            } else {
                sqle.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return error;
    }

    private static synchronized int signIn(BufferedReader reader, int error) {
        try {
            String fast_login = reader.readLine();
            ResultSet rs = ConnectorDatabase.getStatement().executeQuery("SELECT * FROM `Users` WHERE `login` LIKE \"" + fast_login + "\"");
            if (!rs.first()) {
                error = 4;
            } else {
                if (!rs.getString(3).equals(reader.readLine())) {
                    error = 3;
                } else {
                    id = rs.getInt(1);
                    logger.info("> Sign In");
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return error;
    }

    public static int getStreamSize() {
        return streams.size();
    }
}