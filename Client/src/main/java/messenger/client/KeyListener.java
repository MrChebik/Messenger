/**
 * Copyright 2015, 2016 Alexander Beschasny
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

package messenger.client;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by alex on 14.01.16.
 */
public class KeyListener extends KeyAdapter {
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            Client.sendMessage();
        }
    }
}
