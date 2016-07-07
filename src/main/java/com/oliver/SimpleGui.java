package com.oliver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by yanli_000 on 16/7/6.
 */
public class SimpleGui {
    JFrame frame;
    JButton button;
    Label label;

    public static void main(String[] args) {
        SimpleGui simpleGui = new SimpleGui();
        simpleGui.go();
    }

    public void go() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        MyDrawPanel panel = new MyDrawPanel();
        button = new JButton("change colors");
        button.addActionListener(new ColorChangingButtonListener());

        label = new Label("my label");
        JButton buttonLabel = new JButton("change label");
        buttonLabel.addActionListener(new LabelChangingButtonListener());

        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.getContentPane().add(BorderLayout.SOUTH, this.button);
        frame.getContentPane().add(BorderLayout.WEST, label);
        frame.getContentPane().add(BorderLayout.EAST, buttonLabel);

        frame.setSize(300, 300);
        frame.setVisible(true);
    }

    public class ColorChangingButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            frame.repaint();
        }
    }

    public class LabelChangingButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            label.setText("text changed!");
        }
    }
}
