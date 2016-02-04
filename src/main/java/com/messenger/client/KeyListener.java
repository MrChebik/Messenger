package com.messenger.client;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by alex on 14.01.16.
 */
public class KeyListener extends KeyAdapter {
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            Client.sendMessage();
    }
}
