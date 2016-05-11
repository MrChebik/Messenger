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

package messenger.server;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import static org.junit.Assert.*;

/**
 * @author MrChebik
 * @version 0.07
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PropertyTest {

    static File file = new File(System.getProperty("user.dir") + "/src/main/resources/config_server.properties");

    static Properties property = new Properties();

    @Test
    public void file_isDirectory() {
        assertFalse("'file' is directory", file.isDirectory());
    }

    @Test
    public void file_isFile() {
        assertTrue("'file' does not file", file.isFile());
    }

    @Test
    public void file_canRead() {
        assertTrue("'file' can not be read", file.canRead());
    }

    @Test
    public void file_toInitialize() {
        try {
            property.load(PropertyTest.class.getResourceAsStream("/config_server.properties"));
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void property_host_notNull() {
        String host = property.getProperty("db.host");

        assertNotNull(host);
    }

    @Test
    public void property_user_notNull() {
        String user = property.getProperty("db.user");

        assertNotNull(user);
    }

    @Test
    public void property_password_notNull() {
        String password = property.getProperty("db.password");

        assertNotNull(password);
    }

    @Test
    public void property_sign_notNull() {
        String sign = property.getProperty("p.sign");

        assertNotNull(sign);
    }

    @Test
    public void property_message_notNull() {
        String message = property.getProperty("p.message");

        assertNotNull(message);
    }

}