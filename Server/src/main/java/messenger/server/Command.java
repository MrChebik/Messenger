/**
 * Copyright 2016 Alexander Beschasny
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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * @version 0.05
 * @author mrchebik
 */
public class Command {

    static void run() {
        while (true) {
            System.out.print("[messenger.server]$ ");
            String command = new Scanner(System.in).next();
            whatInput(command);
        }
    }

    private static void whatInput(String command) {
        if (command.equals("-help")) {
            help();
        } else if (command.equals("-about")) {
            about();
        } else if (command.equals("-v") || command.equals("-version")) {
            version();
        } else if (command.equals("-online")) {
            online();
        } else if (command.equals("-allUsers")) {
            allUsers();
        } else if (command.equals("-exit")) {
            System.exit(0);
        } else {
            System.out.println("Команда не найдена!");
        }
    }

    private static void allUsers() {
        try {
            ResultSet rs = Main.getStatement().executeQuery("SELECT * FROM Users;");
            System.out.printf("%6s", "ID");
            System.out.printf("%12s", "USER");
            System.out.printf("%16s", "PASSWORD\n");
            while (rs.next()) {
                System.out.printf("%6d", rs.getInt(1));
                System.out.printf("%12s", rs.getString(2));
                System.out.printf("%16s", rs.getString(3) + "\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void online() {
        System.out.println("Online: " + Main.getStreamSize());
    }

    private static void version() {
        System.out.println("Version: 0.05");
    }

    private static void about() {
        System.out.println("Messenger.Server\n" +
                "Version: 0.05\n" +
                "Developer: MrChebik\n" +
                "License: GNU GPL\n" +
                "Copyright: 2016 Alexander Beschasny (LICENSE in project)");
    }

    private static void help() {
        System.out.println("-about -- Узнать о программе\n" +
                "-v / -version -- Версия\n" +
                "-online -- Показывает кол-во пользователей в сети\n" +
                "-allUsers -- Показывает всех пользователей\n" +
                "-exit -- Выходит из программы");
    }
}
