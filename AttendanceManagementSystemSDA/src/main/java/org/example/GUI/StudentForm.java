package org.example.GUI;

import org.example.DATA.Student;
import org.example.SERVICE.StudentService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class StudentForm {
    public JPanel mainPanel;
    private JPanel headerPanel;
    private JTable table1;
    private DefaultTableModel model;
    private JPanel bottomPanel;
    private JPanel formPanel;
    private JPanel buttonPanel;
    private JButton addButton;
    private JButton deleteButton;
    private JButton backButton;
    private JTextField studentNameField;
    private JComboBox<String> CoursecomboBox;
    private JLabel studentsLabel;
    private JLabel nameLabel;
    private JLabel courseLabel;
    private JButton loadButton;

    private final StudentService studentService;

    public StudentForm() {
        this.studentService = new StudentService();

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(52, 73, 94));

        headerPanel = new JPanel();
        headerPanel.setBackground(new Color(52, 73, 94));
        studentsLabel = new JLabel("Students");
        studentsLabel.setFont(new Font("Arial", Font.BOLD, 24));
        studentsLabel.setForeground(Color.WHITE);
        headerPanel.add(studentsLabel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        model = new DefaultTableModel(new String[]{"ID", "Name", "Course"}, 0);
        table1 = new JTable(model);
        table1.setRowHeight(25);
        mainPanel.add(new JScrollPane(table1), BorderLayout.CENTER);

        formPanel = new JPanel(new FlowLayout());
        formPanel.setBackground(new Color(52, 73, 94));
        studentNameField = new JTextField(25);
        CoursecomboBox = new JComboBox<>();
        loadCourses();
        loadButton = new JButton("Load");
        loadButton.setBackground(Color.WHITE);
        loadButton.setForeground(Color.BLACK);
        loadButton.setFont(new Font("Arial", Font.BOLD, 12));
        loadButton.setFocusPainted(false);

        nameLabel = new JLabel("Name:");
        nameLabel.setForeground(Color.WHITE);
        formPanel.add(nameLabel);
        formPanel.add(studentNameField);
        courseLabel = new JLabel("Select Course");
        courseLabel.setForeground(Color.WHITE);
        formPanel.add(courseLabel);
        formPanel.add(CoursecomboBox);
        formPanel.add(loadButton);

        bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBackground(new Color(52, 73, 94));
        bottomPanel.add(formPanel);

        buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(52, 73, 94));

        JButton viewHistoryButton = new JButton("View Attendance");
        viewHistoryButton.setBackground(Color.WHITE);
        viewHistoryButton.setForeground(Color.BLACK);
        viewHistoryButton.setFont(new Font("Arial", Font.BOLD, 12));
        viewHistoryButton.setFocusPainted(false);

        addButton = new JButton("ADD");
        addButton.setBackground(Color.WHITE);
        addButton.setForeground(Color.BLACK);
        addButton.setFont(new Font("Arial", Font.BOLD, 12));
        addButton.setFocusPainted(false);

        deleteButton = new JButton("DELETE");
        deleteButton.setBackground(Color.WHITE);
        deleteButton.setForeground(Color.BLACK);
        deleteButton.setFont(new Font("Arial", Font.BOLD, 12));
        deleteButton.setFocusPainted(false);

        backButton = new JButton("BACK");
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(Color.BLACK);
        backButton.setFont(new Font("Arial", Font.BOLD, 12));
        backButton.setFocusPainted(false);

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewHistoryButton);
        buttonPanel.add(backButton);
        bottomPanel.add(buttonPanel);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        viewHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table1.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a student.");
                    return;
                }
                int studentId = (int) model.getValueAt(row, 0);
                try {
                    String historyText = studentService.getAttendanceHistory(studentId);
                    JTextArea textArea = new JTextArea(historyText);
                    textArea.setEditable(false);
                    textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    scrollPane.setPreferredSize(new Dimension(400, 300));
                    JOptionPane.showMessageDialog(null, scrollPane, "Attendance History", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error loading history: " + ex.getMessage());
                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = studentNameField.getText();
                String course = (String) CoursecomboBox.getSelectedItem();
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter a name.");
                    return;
                }
                try {
                    studentService.addStudent(name, course);
                    loadStudents();
                    studentNameField.setText("");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error adding student.");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table1.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "Select a student to delete.");
                    return;
                }
                int id = (int) model.getValueAt(row, 0);
                try {
                    studentService.deleteStudent(id);
                    loadStudents();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Failed to delete student.");
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame dashboardFrame = new JFrame("Dashboard");
                dashboardFrame.setContentPane(new dashboardForm().mainPanel);
                dashboardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                dashboardFrame.setSize(800, 400);
                dashboardFrame.setResizable(false);
                dashboardFrame.setLocationRelativeTo(null);
                dashboardFrame.setVisible(true);
                SwingUtilities.getWindowAncestor(backButton).dispose();
            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadStudents();
            }
        });
    }

    private void loadStudents() {
        model.setRowCount(0);
        String selectedCourse = (String) CoursecomboBox.getSelectedItem();
        try {
            List<Student> students = studentService.getStudentsByCourse(selectedCourse);
            for (Student student : students) {
                model.addRow(new Object[]{
                        student.getId(),
                        student.getName(),
                        student.getLatestStatus()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed to load students: " + e.getMessage());
        }
    }

    private void loadCourses() {
        CoursecomboBox.removeAllItems();
        try {
            List<String> courseNames = studentService.getAllCourseNames();
            for (String name : courseNames) {
                CoursecomboBox.addItem(name);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed to load courses: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("ATTENDANCE MANAGEMENT SYSTEM");
        frame.setContentPane(new StudentForm().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
