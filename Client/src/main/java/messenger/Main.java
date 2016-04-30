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

package messenger;

import messenger.lang.Parser;
import messenger.signin.SignIn;
import org.apache.log4j.Logger;

/**
 * @author MrChebik
 * @version 0.06
 */
public class Main {

    public static Logger logger = Logger.getLogger(SignIn.class.getName());

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
        new SignIn();
    }

}
