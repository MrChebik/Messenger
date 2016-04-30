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

import messenger.client.Client;
import messenger.client.Property;
import messenger.lang.Parser;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author MrChebik
 * @version 0.06
 */
public class ChatPanel extends JPanel {

    public static JEditorPane dialogs = new JEditorPane();
    public static JTextField message = new JTextField("");
    public static JButton send;

    private ChatPanel() {
        setLayout(new BorderLayout());

        dialogs.setContentType(Property.getEditorPane());
        dialogs.setEditable(false);
        dialogs.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    Client.sendMessage(message.getText());
                }
            }
        });
        DefaultCaret caret = (DefaultCaret) dialogs.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        JScrollPane scrollPane = new JScrollPane(dialogs);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);

        add(new subPanel(), BorderLayout.SOUTH);

        setSize(396, 186);
        setLocation(74, 0);
    }

    private class subPanel extends JPanel {

        subPanel() {
            setLayout(new BorderLayout());

            message.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    super.keyPressed(e);
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        Client.sendMessage(message.getText());
                    }
                }
            });
            add(message, BorderLayout.CENTER);

            send = new JButton(Parser.getSend());
            send.addActionListener(e -> Client.sendMessage(message.getText()));
            add(send, BorderLayout.EAST);
        }

    }

    private static class SingletonHolder {
        private static final ChatPanel INSTANCE = new ChatPanel();
    }

    public static ChatPanel getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
