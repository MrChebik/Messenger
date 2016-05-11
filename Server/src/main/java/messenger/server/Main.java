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
import org.apache.log4j.Logger;

/**
 * @version 0.07
 * @author MrChebik
 */
public class Main {

    public static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        System.out.print("\tMessenger  Copyright (C) 2016  MrChebik\n" +
                "\n" +
                "\tThis program comes with ABSOLUTELY NO WARRANTY; for details type `show w'.\n" +
                "\tThis is free software, and you are welcome to redistribute it\n" +
                "\tunder certain conditions; type `show c' for details.\n");
        logger.info("-------- New Instance -------->>");
        logger.info("Parsing language file...");
        new Parser("en");
        new Server();
    }

}