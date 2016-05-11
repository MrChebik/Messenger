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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * @version 0.06
 * @author MrChebik
 */
public class Command {

    public static void run() {
        Main.logger.info(Parser.getConsole() + "...");

        while (true) {
            System.out.print("[messenger.server]$ ");
            whatInput(new Scanner(System.in).nextLine());
        }
    }

    public static void main(String[] args) {
        whatInput("");
    }

    private static void whatInput(String command) {
        if (command.equals("--help")) {
            help();
        } else if (command.equals("--about")) {
            about();
        } else if (command.equals("--online") || command.equals("-o")) {
            online();
        } else if (command.equals("--allUsers") || command.equals("-au")) {
            allUsers();
        } else if (command.equals("--exit")) {
            System.exit(0);
        } else if (command.equals("show w")) {
            warranty();
        } else if (command.equals("show c")) {
            redistribute();
        } else {
            System.out.println("Command not found!");
        }
    }

    private static void allUsers() {
        try {
            ResultSet rs = ConnectorDatabase.showAllUsers();
            System.out.printf("%6s %12s %16s", "ID", "USER", "PASSWORD\n");
            while (rs.next()) {
                System.out.printf("%6d %12s %16s", rs.getInt(1), rs.getString(2), rs.getString(3) + "\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void online() {
        System.out.println("Online: " + Server.getStreamSize());
    }

    private static void about() {
        System.out.println("Messenger.Server:\n" +
                "Version: 0.07\n" +
                "Developer: MrChebik\n" +
                "License: GNU GPL v3");
    }

    private static void help() {
        System.out.println("Commands:");
        System.out.printf("%-4s %-15s %s", "-o,", "--online", "Shows how many users online;\n");
        System.out.printf("%-4s %-15s %s", "-au,", "--allUsers", "Shows all users from database (id, user, password);\n");
        System.out.printf("%-20s %s", "--about", "Read about this program (name, version, developer, license);\n");
        System.out.printf("%-20s %s", "--exit", "Exit from server.\n");
    }

    private static void warranty() {
        System.out.println("\tThis program is distributed in the hope that it will be useful,\n" +
                "\tbut WITHOUT ANY WARRANTY; without even the implied warranty of\n" +
                "\tMERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the\n" +
                "\tGNU General Public License for more details.");
    }

    private static void redistribute() {
        System.out.println("\tThis program is free software: you can redistribute it and/or modify\n" +
                "\tit under the terms of the GNU General Public License as published by\n" +
                "\tthe Free Software Foundation, either version 3 of the License, or\n" +
                "\t(at your option) any later version.");
    }
}
