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

package messenger.client.frame.panels;

import messenger.lang.Parser;

import javax.swing.*;
import java.awt.*;

/**
 * @author MrChebik
 * @version 0.06
 */
public class LeftPanel extends JPanel {

    public static JButton chat, search;

    public LeftPanel() {
        chat = new JButton(Parser.getChat());
        search = new JButton(Parser.getSearch());

        search.addActionListener(e -> {
            ChatPanel.getInstance().setVisible(false);
            SearchPanel.getInstance().setVisible(true);
            chat.setEnabled(true);
            search.setEnabled(false);

            SearchPanel.getUsers();
        });

        chat.addActionListener(e -> {
            ChatPanel.getInstance().setVisible(true);
            SearchPanel.getInstance().setVisible(false);
            chat.setEnabled(false);
            search.setEnabled(true);
        });
        chat.setEnabled(false);

        add(chat);
        add(search);

        setLayout(new GridLayout(2, 1));
    }

}
