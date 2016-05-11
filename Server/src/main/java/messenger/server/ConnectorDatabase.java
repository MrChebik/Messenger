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

import java.sql.*;

/**
 * @version 0.07
 * @author MrChebik
 */
public class ConnectorDatabase {

    private static Statement statement;

    public static String createInfo = "\n" +
            "--->\n" +
            "Database: MySQL\n" +
            "Commands:\n" +
            "\tCREATE DATABASE Messenger;\n" +
            "\tUSE Messenger;\n" +
            "\tCREATE TABLE Users(id MEDIUMINT NOT NULL AUTO_INCREMENT, login VARCHAR(12) UNIQUE, password VARCHAR(16), PRIMARY KEY(id));\n" +
            "--->\n" +
            "Good luck :)";

    public static void connect(String url, String user, String password) throws SQLException {
        Main.logger.info(Parser.getDatabase() + Parser.getConnect() + "...");

        Connection connection = DriverManager.getConnection(url, user, password);
        statement = connection.createStatement();
    }

    public static void createDatabase(String user, String password) throws SQLException {
        Main.logger.info(Parser.getDatabase() + Parser.getCreateDB() + "...");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/?user=" + user + "&password=" + password);
        statement = connection.createStatement();
        statement.executeUpdate("CREATE DATABASE Messenger;");
        statement.executeUpdate("USE Messenger;");
        statement.executeUpdate("CREATE TABLE Users(id MEDIUMINT NOT NULL AUTO_INCREMENT, login VARCHAR(12) UNIQUE, password VARCHAR(16), PRIMARY KEY(id));");
    }

    public static void createNewUser(String login, String password) throws SQLException {
        Main.logger.info(Parser.getDatabase() + Parser.getCreateNewUser() + "...");

        ConnectorDatabase.statement.execute("INSERT INTO Users (login, password) VALUES ('" + login + "', '" + password + "');");
        ConnectorDatabase.statement.execute("CREATE UNIQUE INDEX " + login + " ON Users(login);");
    }

    public static void deleteLastID() throws SQLException {
        Main.logger.info(Parser.getDatabase() + Parser.getDeleteLastID() + "...");

        ConnectorDatabase.statement.execute("DELETE FROM Users WHERE id=LAST_INSERT_ID();");
    }

    public static ResultSet findUser(String login) throws SQLException {
        Main.logger.info(Parser.getDatabase() + Parser.getFindUser() + "...");

        return ConnectorDatabase.statement.executeQuery("SELECT * FROM Users WHERE login LIKE \"" + login + "\"");
    }

    public static ResultSet showAllUsers() throws SQLException {
        Main.logger.info(Parser.getDatabase() + Parser.getShowAllUsers() + "...");

        return ConnectorDatabase.statement.executeQuery("SELECT * FROM Users;");
    }

    public static ResultSet showAllUsersLogin() throws SQLException {
        Main.logger.info(Parser.getDatabase() + Parser.getShowAllUsersLogin() + "...");

        return ConnectorDatabase.statement.executeQuery("SELECT login FROM Users;");
    }

}
