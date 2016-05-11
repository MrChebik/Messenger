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

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;
import messenger.server.lang.Parser;
import messenger.server.runnable.MessageUser;
import messenger.server.runnable.TakeUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLInvalidAuthorizationSpecException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author MrChebik
 * @version 0.07
 */
public class Server {

    private static ServerSocket serverSocketS;
    private static ServerSocket serverSocketM;
    private static int id;

    public static HashMap streams = new HashMap();
    public static Socket socketM;

    Server() {
        try {
            Property.loadFile(Main.class.getResourceAsStream("/config_server.properties"));
        } catch (IOException e) {
            Main.logger.error(Parser.getProperty() + e.getMessage());
            e.printStackTrace();
        }
        Property.settingValues();
        try {
            ConnectorDatabase.connect(Property.getHost(), Property.getUser(), Property.getPassword());
        } catch (MySQLSyntaxErrorException see) {
            Main.logger.error(Parser.getDatabase() + see.getMessage());
            if (see.getMessage().contains("Unknown database")) {
                System.out.print("Database 'Messenger' is not exist.\n" +
                        "Do you want to create this database? (Y/n): ");
                String choice = new Scanner(System.in).next();
                while (!choice.equalsIgnoreCase("y") || !choice.equalsIgnoreCase("n")) {
                    if (choice.equalsIgnoreCase("y")) {
                        System.out.println("Choose way to create (1/2):\n" +
                                "-Automatically\n" +
                                "-Setting");
                        String way = new Scanner(System.in).next();
                        String user = Property.getUser();
                        String password = Property.getPassword();
                        while (!way.equals("1") || !way.equals("2")) {
                            if (way.equals("2")) {
                                System.out.print("Username: ");
                                user = new Scanner(System.in).next();
                                System.out.println("Password: ");
                                password = new Scanner(System.in).next();
                            }
                        }
                        try {
                            ConnectorDatabase.createDatabase(user, password);
                        } catch (SQLInvalidAuthorizationSpecException iase) {
                            Main.logger.error(Parser.getDatabase() + iase.getMessage() + " -> " + Parser.getInvalidAuthorizationConsole_err());
                            iase.printStackTrace();
                        } catch (SQLException e) {
                            Main.logger.error(Parser.getDatabase() + e.getMessage());
                            e.printStackTrace();
                        }
                    } else if (choice.equalsIgnoreCase("n")) {
                        System.out.println("Ok, you can create this database independently:" + ConnectorDatabase.createInfo);
                        System.exit(0);
                    }
                }
            } else {
                Main.logger.error(Parser.getDatabase() + see.getMessage());
                see.printStackTrace();
            }
        } catch (SQLInvalidAuthorizationSpecException iase) {
            Main.logger.error(Parser.getDatabase() + iase.getMessage() + " -> " + Parser.getInvalidAuthorizationFile_err());
            iase.printStackTrace();
        } catch (SQLException e) {
            Main.logger.error(Parser.getDatabase() + e.getMessage());
            e.printStackTrace();
        }
        try {
            createPorts(Property.getPortSign(), Property.getPortMessage());
        } catch (IOException e) {
            Main.logger.error(e.getMessage());
            e.printStackTrace();
        }
        Main.logger.info(Parser.getServer() + Parser.getStart() + "...");
        newThreadTakeUser();
        Command.run();
    }

    public static int signInUp(Socket socket) {
        Main.logger.info(Parser.getTakeUser() + Parser.getSignInUp() + "...");

        int error = 0;
        PrintWriter printWriter = null;

        try {
            printWriter = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String message = reader.readLine();
            if (message == null) {
                error = 5;
            } else if (message.equals("signup")) {
                error = signUp(reader);
            } else {
                error = signIn(reader);
            }
        } catch (IOException | SQLException e) {
            Main.logger.error(Parser.getDatabase() + e.getMessage());
            e.printStackTrace();
        } finally {
            if (error != 5) {
                printWriter.println(error);
            }
        }
        return error;
    }

    private static void createPorts(String portSign, String portMessage) throws IOException {
        Main.logger.info(Parser.getServer() + Parser.getCreatePorts() + "...");

        serverSocketS = new ServerSocket(Integer.parseInt(portSign));
        serverSocketM = new ServerSocket(Integer.parseInt(portMessage));
    }

    public static Socket waitUser() throws IOException {
        Main.logger.info(Parser.getTakeUser() + Parser.getWaitUser() + "...");

        return serverSocketS.accept();
    }

    public static void addOutputStream() throws IOException {
        Main.logger.info(Parser.getTakeUser() + Parser.getAddOutputStream() + "...");

        socketM = serverSocketM.accept();
        PrintWriter writer = new PrintWriter(socketM.getOutputStream());
        streams.put(id, writer);
    }

    public static void newThreadMessageUser() {
        Main.logger.info(Parser.getTakeUser() + Parser.getAddThreadMessageUser() + "...");

        new Thread(new MessageUser()).start();
    }

    public static void newThreadTakeUser() {
        Main.logger.info(Parser.getTakeUser() + Parser.getThreadStart() + "...");

        new Thread(new TakeUser()).start();
    }

    private static synchronized int signUp(BufferedReader reader) throws SQLException, IOException {
        try {
            ConnectorDatabase.createNewUser(reader.readLine(), reader.readLine());
        } catch (SQLException e) {
            if (e.getMessage().charAt(0) == 'D') {
                ConnectorDatabase.deleteLastID();
                return 1;
            } else {
                Main.logger.error(Parser.getDatabase() + e.getMessage());
                e.printStackTrace();
            }
        }

        return 0;
    }

    private static synchronized int signIn(BufferedReader reader) throws SQLException, IOException {
        ResultSet rs = ConnectorDatabase.findUser(reader.readLine());
        if (!rs.first()) {
            return 4;
        } else if (!rs.getString(3).equals(reader.readLine())) {
            return 3;
        } else {
            id = rs.getInt(1);
        }

        return 0;
    }

    // ------- Getters and setters -------------------->
    public static int getStreamSize() {
        return streams.size();
    }
}