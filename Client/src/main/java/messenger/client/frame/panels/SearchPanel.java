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

import messenger.Main;
import messenger.client.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author MrChebik
 * @version 0.06
 */
public class SearchPanel extends JPanel {

    public JList list = new JList();
    public DefaultListModel<String> listModel = new DefaultListModel();
    public DefaultListModel<String> listModelCopy = new DefaultListModel();

    private SearchPanel() {
        setLayout(new BorderLayout());

        JTextField search = new JTextField();
        search.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);

                listModel.removeAllElements();
                if (search.getText().equals("")) {
                    for (int i = 0; i < listModelCopy.getSize(); i++) {
                        listModel.addElement(listModelCopy.getElementAt(i));
                    }
                } else {
                    for (int i = 0; i < listModelCopy.getSize(); i++) {
                        if (listModelCopy.getElementAt(i).toLowerCase().contains(search.getText().toLowerCase())) {
                            listModel.addElement(listModelCopy.getElementAt(i));
                        }
                    }
                }
                list.setListData(listModel.toArray());
            }
        });
        add(search, BorderLayout.NORTH);

        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        list.setLayoutOrientation(JList.VERTICAL);
        add(list, BorderLayout.CENTER);

        setSize(396, 186);
        setLocation(74, 0);
        setVisible(false);
    }

    private static class SingletonHolder {
        private static final SearchPanel INSTANCE = new SearchPanel();
    }

    public static SearchPanel getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public static void getUsers() {
        Main.logger.info("sending message...");
        Client.sendMessage("$search$");
    }
}
