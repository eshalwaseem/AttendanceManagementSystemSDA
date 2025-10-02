package org.example.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class loginForm {
    public JPanel LoginPanel;
    private JPanel LoginRight;
    private JPanel LoginLeft;
    private JLabel LoginLabel;
    private JTextField UsernameText;
    private JButton LoginButton;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel AMSlabel;
    private JLabel managementLabel;
    private JPasswordField passwordField1;

    public loginForm() {
        LoginPanel = new JPanel();
        LoginPanel.setLayout(null);
        LoginPanel.setPreferredSize(new Dimension(800, 400));

        LoginLeft = new JPanel();
        LoginLeft.setBounds(0, 0, 400, 400);
        LoginLeft.setBackground(Color.LIGHT_GRAY);
        LoginLeft.setLayout(new GridBagLayout());
        LoginPanel.add(LoginLeft);

        AMSlabel = new JLabel("ATTENDANCE");
        AMSlabel.setFont(new Font("Arial", Font.BOLD, 28));
        AMSlabel.setForeground(Color.BLACK);

        managementLabel = new JLabel("MANAGEMENT SYSTEM");
        managementLabel.setFont(new Font("Arial", Font.BOLD, 28));
        managementLabel.setForeground(Color.BLACK);

        Box box = Box.createVerticalBox();
        box.add(AMSlabel);
        box.add(Box.createVerticalStrut(10));
        box.add(managementLabel);

        LoginLeft.add(box);

        LoginRight = new JPanel();
        LoginRight.setBounds(400, 0, 400, 400);
        LoginRight.setBackground(new Color(52, 73, 94));
        LoginRight.setLayout(null);
        LoginPanel.add(LoginRight);

        LoginLabel = new JLabel("LOGIN");
        LoginLabel.setBounds(150, 20, 200, 40);
        LoginLabel.setFont(new Font("Arial", Font.BOLD, 28));
        LoginLabel.setForeground(Color.white);
        LoginRight.add(LoginLabel);

        usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(50, 80, 300, 25);
        usernameLabel.setForeground(Color.WHITE);
        LoginRight.add(usernameLabel);

        UsernameText = new JTextField();
        UsernameText.setBounds(50, 110, 300, 30);
        LoginRight.add(UsernameText);

        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(50, 150, 300, 25);
        passwordLabel.setForeground(Color.WHITE);
        LoginRight.add(passwordLabel);

        passwordField1 = new JPasswordField();
        passwordField1.setBounds(50, 180, 300, 30);
        LoginRight.add(passwordField1);

        LoginButton = new JButton("LOGIN");
        LoginButton.setBounds(130, 230, 120, 35);
        LoginButton.setBackground(Color.WHITE);
        LoginButton.setForeground(Color.BLACK);
        LoginRight.add(LoginButton);

        LoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = UsernameText.getText();
                String password = new String(passwordField1.getPassword());
                if (username.equals("admin") && password.equals("123")) {
                    JOptionPane.showMessageDialog(null, "Login Successfull");
                    JFrame dashboardFrame = new JFrame("Dashboard");
                    dashboardFrame.setContentPane(new dashboardForm().mainPanel);
                    dashboardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    dashboardFrame.setSize(800, 400);
                    dashboardFrame.setResizable(false);
                    dashboardFrame.setLocationRelativeTo(null);
                    dashboardFrame.setVisible(true);
                    SwingUtilities.getWindowAncestor(LoginButton).dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Login", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

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
