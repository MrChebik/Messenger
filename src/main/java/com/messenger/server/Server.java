package com.messenger.server;

/**
 * Created by alex on 10.01.16.
 */

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class Server {

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
    private static FileInputStream fis;
    private static Properties property = new Properties();

    private static String host;
    private static String login;
    private static String password;
    private static String port_login;
    private static String port_messages;

    private Server() {
        System.out.println("Loading property file...");
        loadProperty();
        System.out.println("Connection to database...");
        connectionToDatabase(host, login, password);
        System.out.println("Creating port for user login...");
        createPort_login(port_login);
        System.out.println("Creating port for messages...");
        createPort_messages(port_messages);
        System.out.println("The server started taking users...");
        while (true) {
            System.out.println("> Wait user...");
            waitUser();
            System.out.println("> Creating/Searching user fields in the database...");
            while (errorInStage_createField != 2 && errorInStage_createField != 5) {
                create_searchFieldInDatabase();
                if (errorInStage_createField != 2 && errorInStage_createField != 5) {
                    errorInStage_createField = 0;
                }
            }
            if (errorInStage_createField != 5) {
                System.out.println("> Add an output stream...");
                addOutputStream();
                System.out.println("> Creating thread input/output stream for message that the user...");
                addThreadForTheUser();
                System.out.println("> --------------------------");
            }
            errorInStage_createField = 0;
        }
    }

    public static void main(String[] args) {
        getInstance();
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
                        java.util.Iterator<PrintWriter> it = streams.iterator();
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
            System.out.println("-> " + e.getMessage());
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
            } else {
                String fast_login = reader.readLine();
                ResultSet rs = statement.executeQuery("SELECT * FROM `Users` WHERE `login` LIKE \"" + fast_login + "\"");
                if (rs.first() == false) {
                    errorInStage_createField = 4;
                } else {
                    id = rs.getInt(1);
                    //System.out.printf("id: %d, login: %s, password: %s %n", rs.getInt(1), rs.getString(2), rs.getString(3));
                    if (!rs.getString(3).equals(reader.readLine())) {
                        errorInStage_createField = 3;
                    }
                    System.out.println("> Sign In");
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
                    System.out.println("-> " + e.getMessage());
                }
            } else {
                System.out.println("-> " + e.getMessage());
            }
        } finally {
            if (errorInStage_createField == 0) {
                errorInStage_createField = 2;
            }
            if (errorInStage_createField != 5)
                fast_writer.println(errorInStage_createField);
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

    private static void loadProperty(){
        try {
            fis = new FileInputStream("src/main/resources/config_server.properties");
            property.load(fis);

            host = property.getProperty("db.host");
            login = property.getProperty("db.login");
            password = property.getProperty("db.password");
            port_login = property.getProperty("p.login");
            port_messages = property.getProperty("p.messages");
        } catch (IOException e) {
            System.err.println("ERROR: Property file not exist!");
        }
    }

    private static class SingletonHolder {
        private static final Server INSTANCE = new Server();
    }

    public static Server getInstance() {
        return SingletonHolder.INSTANCE;
    }
}