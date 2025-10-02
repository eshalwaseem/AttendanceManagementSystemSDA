package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import org.example.GUI.loginForm;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

            JFrame frame = new JFrame("ATTENDANCE MANAGEMENT SYSTEM");
            frame.setContentPane(new loginForm().LoginPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 400);
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }

}