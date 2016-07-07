package com.oliver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yanli_000 on 16/7/7.
 */
public class SimpleChatClientA {
    private final static Logger logger = LoggerFactory.getLogger(SimpleChatClientA.class);

    JTextArea incoming;
    JTextField outgoing;
    BufferedReader reader;
    PrintWriter writer;
    Socket socket;

    public static void main(String[] args) {
        SimpleChatClientA simpleChatClientA = new SimpleChatClientA();
        simpleChatClientA.go();
    }

    public void go() {
        logger.info("client Started!");
        JFrame frame = new JFrame("Simple Chat Client");
        JPanel mainPanel = new JPanel();

        incoming = new JTextArea(15, 50);
        incoming.setLineWrap(true);
        incoming.setWrapStyleWord(true);
        incoming.setEditable(false);
        incoming.setBackground(Color.ORANGE);
        JScrollPane scroller = new JScrollPane(incoming);
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        outgoing = new JTextField(20);
        JButton sendButton = new JButton("send");
        sendButton.addActionListener(new SendButtonListener());

        mainPanel.add(scroller);
        mainPanel.add(outgoing);
        mainPanel.add(sendButton);
        setUpSocket();

        Thread readerThread = new Thread(new IncomingReader());
        readerThread.start();

        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setSize(600, 700);
        frame.setVisible(true);
    }

    public void setUpSocket() {
        try {
            final String ip = "127.0.0.1";
            socket = new Socket(ip, 5000);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());
            logger.info("socket establised on {}!", ip);
        } catch (IOException e) {
            logger.error(Throwables.getStackTraceAsString(e));
        }
    }

    public class SendButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                String sendText = outgoing.getText();
                writer.println(sendText);
                writer.flush();
                logger.info("sent text = {}", sendText);
            } catch (Exception ex) {
                logger.error(Throwables.getStackTraceAsString(ex));
            }
            outgoing.setText("");
            outgoing.requestFocus();
        }
    }

    public class IncomingReader implements Runnable {
        public void run() {
            String message;
            try {
                while (true) {
                    message = reader.readLine();
                    logger.info("got message = {}", message);
                    incoming.append(message + "\n");
                }
            } catch (Exception ex) {
                logger.error(Throwables.getStackTraceAsString(ex));
            }
        }
    }
}






















