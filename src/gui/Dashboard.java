package gui;

import javax.swing.*;

public class Dashboard extends JFrame {

    public Dashboard() {

        setTitle("Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Welcome to Dashboard");

        add(label);

        setVisible(true);
    }
}