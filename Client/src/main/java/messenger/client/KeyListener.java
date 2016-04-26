/**
 * Copyright 2016 Alexander Beschasny
 *
 * Messenger is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Messenger is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Messenger.  If not, see <http://www.gnu.org/licenses/gpl-3.0.txt>.
 *
 * Alexander Beschasny mrchebik@yandex.ru
 */

package messenger.client;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @version 0.01
 * @author mrchebik
 */
public class KeyListener extends KeyAdapter {
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            Client.sendMessage();
        }
    }
}
