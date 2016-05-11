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

import messenger.server.Main;
import messenger.server.Server;
import messenger.server.lang.Parser;

import java.io.IOException;
import java.net.Socket;

/**
 * @version 0.07
 * @author MrChebik
 */
public class TakeUser implements Runnable {

    @Override
    public void run() {
        Socket socket = null;
        try {
            socket = Server.waitUser();
        } catch (IOException e) {
            Main.logger.error(e.getMessage());

            e.printStackTrace();
        }
        Server.newThreadTakeUser();
        int error = 2;
        while (error != 0 && error != 5) {
            error = Server.signInUp(socket);
        }
        if (error != 5) {
            try {
                Server.addOutputStream();
            } catch (IOException e) {
                Main.logger.error(e.getMessage());

                e.printStackTrace();
            }
            Server.newThreadMessageUser();
        }

        Main.logger.info(Parser.getTakeUser() + Parser.getSuccess() + "!");
    }

}
