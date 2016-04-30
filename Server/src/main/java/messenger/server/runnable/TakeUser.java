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

package messenger.server.runnable;

import messenger.server.lang.Parser;
import org.apache.log4j.Logger;

import java.io.PrintWriter;
import java.net.Socket;

import static messenger.server.Main.*;

/**
 * @version 0.05
 * @author MrChebik
 */
public class TakeUser implements Runnable {

    private static Socket socket;
    private static PrintWriter printWriter;

    static Logger logger = Logger.getLogger(TakeUser.class.getName());

    @Override
    public void run() {
        logger.info(Parser.getWaitUser() + "...");
        socket = waitUser();

        logger.info(Parser.getThreadStart() + "...");
        newThreadTU();

        int errors = 0;

        while (errors != 2 && errors != 5) {
            logger.info(Parser.getCreate_searchFieldInDatabase() + "...");
            errors = signInUp(socket, printWriter);
            if (errors != 2 && errors != 5) {
                errors = 0;
            }
        }
        if (errors != 5) {
            logger.info(Parser.getAddOutputStream() + "...");
            addOutputStream();
            logger.info(Parser.getAddThreadToTheUser() + "...");
            newThreadMU();
        }

        logger.info(Parser.getThreadStop() + "...");
    }
}
