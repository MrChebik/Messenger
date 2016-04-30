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

import messenger.lang.Parser;

import javax.swing.*;

/**
 * @author MrChebik
 * @version 0.06
 */
public class Menu extends JMenuBar {

    public static JMenu file, settings, languages, help;
    public static JMenuItem exit, about;

    Menu() {
        file = new JMenu(Parser.getFile());
        add(file);
        settings = new JMenu("Settings");
        file.add(settings);
        file.addSeparator();
        languages = new JMenu("Languages");
        settings.add(languages);
        JMenuItem ru = new JMenuItem("RU");
        ru.addActionListener(e -> {
            Parser.ru();
            Frame.renameComponents();
        });
        languages.add(ru);
        JMenuItem en = new JMenuItem("EN");
        en.addActionListener(e -> {
            Parser.en();
            Frame.renameComponents();
        });
        languages.add(en);
        exit = new JMenuItem(Parser.getExit());
        exit.addActionListener(e -> System.exit(0));
        file.add(exit);
        help = new JMenu(Parser.getHelp());
        add(help);
        about = new JMenuItem(Parser.getAbout());
        about.addActionListener(e -> JOptionPane.showMessageDialog(null, Parser.getVersion() + "\n" + Parser.getDeveloper(), Parser.getAbout(), JOptionPane.INFORMATION_MESSAGE));
        help.add(about);
    }

}
