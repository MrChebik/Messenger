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

package messenger.client.frame;

import messenger.Main;
import messenger.client.Client;
import messenger.client.frame.panels.*;
import messenger.lang.Parser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


/**
 * @author MrChebik
 * @version 0.06
 */
public class Frame extends JFrame {

    private Frame() {
        setLayout(new BorderLayout());

        add(new LeftPanel(), BorderLayout.WEST);

        JPanel centerPanel = new JPanel(null);
        add(ChatPanel.getInstance());
        add(SearchPanel.getInstance());
        add(centerPanel, BorderLayout.CENTER);

        ChatPanel.getInstance().setSize(396, 186);
        ChatPanel.getInstance().setLocation(74, 0);

        Main.logger.info(Parser.getSetting() + "...");
        setTitle(Parser.getProgram());
        setSize(474, 235);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                Client.writer.println("$exit$");
                Client.writer.println(Client.login);
                System.exit(0);
            }
        });
        setLocationRelativeTo(null);
        setResizable(false);
        setJMenuBar(new Menu());
        setVisible(true);
    }

    public static void setLogin(String login) {
        Client.login = login;
    }

    public static void renameComponents() {
        Main.logger.info(Parser.getFrame() + Parser.getRename());

        Menu.file.setText(Parser.getFile());
        Menu.settings.setText(Parser.getSettings());
        Menu.languages.setText(Parser.getLanguages());
        Menu.help.setText(Parser.getHelp());
        Menu.exit.setText(Parser.getExit());
        Menu.about.setText(Parser.getAbout());

        Frame.getInstance().setTitle(Parser.getProgram());

        ChatPanel.send.setText(Parser.getSend());

        LeftPanel.chat.setText(Parser.getChat());
        LeftPanel.search.setText(Parser.getSearch());
    }

    private static class SingletonHolder {
        private static final Frame INSTANCE = new Frame();
    }

    public static Frame getInstance() {
        return SingletonHolder.INSTANCE;
    }

}
