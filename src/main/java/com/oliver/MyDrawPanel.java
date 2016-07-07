package com.oliver;

import javax.swing.*;
import java.awt.*;

/**
 * Created by yanli_000 on 16/7/6.
 */
public class MyDrawPanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(getRandomColor());
        g.fillOval(10, 10, 100, 100);
    }

    private static Color getRandomColor() {
        int r = (int) (Math.random() * 255);
        int g = (int) (Math.random() * 255);
        int b = (int) (Math.random() * 255);
        return new Color(r, g, b);
    }
}
