package org.example.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class dashboardForm {
    public JPanel mainPanel;
    private JLabel attendance_management_label;
    private JLabel welcomeLabel;
    private JPanel quickAccessPanel;
    private JLabel qaLabel;
    private JButton studentsButton;
    private JButton coursesButton;
    private JButton attendanceButton;
    private JButton logoutButton;
    private JPanel buttonPanel;
    private JPanel topPanel;

    public dashboardForm() {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.LIGHT_GRAY);

        topPanel = new JPanel(new GridLayout(2, 1));
        topPanel.setBackground(Color.LIGHT_GRAY);
        attendance_management_label = new JLabel("ATTENDANCE MANAGEMENT SYSTEM");
        attendance_management_label.setFont(new Font("Arial", Font.BOLD, 16));
        attendance_management_label.setForeground(Color.BLACK);
        welcomeLabel = new JLabel("Welcome Admin");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        welcomeLabel.setForeground(Color.BLACK);
        topPanel.add(attendance_management_label);
        topPanel.add(welcomeLabel);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        quickAccessPanel = new JPanel();
        quickAccessPanel.setLayout(new BoxLayout(quickAccessPanel, BoxLayout.Y_AXIS));
        quickAccessPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));
        quickAccessPanel.setBackground(new Color(52, 73, 94));
        qaLabel = new JLabel("QUICK ACCESS", SwingConstants.CENTER);
        qaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        qaLabel.setFont(new Font("Arial", Font.BOLD, 14));
        qaLabel.setForeground(Color.white);
        quickAccessPanel.add(qaLabel);
        quickAccessPanel.add(Box.createVerticalStrut(20));

        buttonPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        buttonPanel.setBackground(new Color(52, 73, 94));
        studentsButton = new JButton("Students");
        studentsButton.setFont(new Font("Arial", Font.BOLD, 16));
        coursesButton = new JButton("Courses");
        coursesButton.setFont(new Font("Arial", Font.BOLD, 16));
        attendanceButton = new JButton("Attendance");
        attendanceButton.setFont(new Font("Arial", Font.BOLD, 16));
        logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 16));
        buttonPanel.add(studentsButton);
        buttonPanel.add(coursesButton);
        buttonPanel.add(attendanceButton);
        buttonPanel.add(logoutButton);

        quickAccessPanel.add(buttonPanel);
        mainPanel.add(quickAccessPanel, BorderLayout.CENTER);

        attendanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame CourseWiseAttendance = new JFrame("Attendance");
                CourseWiseAttendance.setContentPane(new CourseWiseAttendanceForm().mainPanel);
                CourseWiseAttendance.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                CourseWiseAttendance.setSize(800, 400);
                CourseWiseAttendance.setResizable(false);
                CourseWiseAttendance.setLocationRelativeTo(null);
                CourseWiseAttendance.setVisible(true);
                SwingUtilities.getWindowAncestor(attendanceButton).dispose();
            }
        });

        studentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame Student = new JFrame("Students");
                Student.setContentPane(new StudentForm().mainPanel);
                Student.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                Student.setSize(800, 400);
                Student.setResizable(false);
                Student.setLocationRelativeTo(null);
                Student.setVisible(true);
                SwingUtilities.getWindowAncestor(studentsButton).dispose();
            }
        });

        coursesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame Course = new JFrame("Courses");
                Course.setContentPane(new CourseForm().mainPanel);
                Course.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                Course.setSize(800, 400);
                Course.setResizable(false);
                Course.setLocationRelativeTo(null);
                Course.setVisible(true);
                SwingUtilities.getWindowAncestor(coursesButton).dispose();
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame log = new JFrame("Logout");
                log.setContentPane(new loginForm().LoginPanel);
                log.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                log.setSize(800, 400);
                log.setResizable(false);
                log.setLocationRelativeTo(null);
                log.setVisible(true);
                SwingUtilities.getWindowAncestor(logoutButton).dispose();
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("ATTENDANCE MANAGEMENT SYSTEM");
        frame.setContentPane(new dashboardForm().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
