package org.example.GUI;

import org.example.DATA.Attendance;
import org.example.SERVICE.AttendanceService;
import org.example.SERVICE.StudentService;
import org.example.DATA.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class CourseWiseAttendanceForm {
    public JPanel mainPanel;
    private JPanel headerPanel;
    private JPanel topPanel;
    private JComboBox<String> courseComboBox;
    private JButton loadButton;
    private JLabel courseLabel;
    private JLabel attendanceLabel;
    private JTable table1;
    private DefaultTableModel model;
    private JPanel bottomPanel;
    private JButton markAttendanceButton;
    private JButton backButton;
    private JTextField countTextField;
    private JLabel countLabel;
    private JButton summaryButton;

    private final StudentService studentService;
    private final AttendanceService attendanceService;

    public CourseWiseAttendanceForm() {
        this.studentService = new StudentService();
        this.attendanceService = new AttendanceService();

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(52, 3, 94));

        headerPanel = new JPanel();
        headerPanel.setBackground(new Color(52, 3, 94));
        attendanceLabel = new JLabel("Attendance");
        attendanceLabel.setFont(new Font("Arial", Font.BOLD, 24));
        attendanceLabel.setForeground(Color.WHITE);
        headerPanel.add(attendanceLabel);

        topPanel = new JPanel();
        topPanel.setBackground(new Color(52, 73, 94));
        courseComboBox = new JComboBox<>();
        loadButton = new JButton("Load Students");
        loadButton.setBackground(Color.WHITE);
        loadButton.setForeground(Color.BLACK);
        loadButton.setFont(new Font("Arial", Font.BOLD, 12));
        loadButton.setFocusPainted(false);

        courseLabel = new JLabel("Select Course");
        courseLabel.setForeground(Color.WHITE);
        courseLabel.setFont(new Font("Arial", Font.BOLD, 14));
        topPanel.add(courseLabel);
        topPanel.add(courseComboBox);
        topPanel.add(loadButton);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(topPanel, BorderLayout.BEFORE_FIRST_LINE);

        model = new DefaultTableModel(new String[]{"Student ID", "Name", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }
        };

        table1 = new JTable(model) {
            @Override
            public TableCellEditor getCellEditor(int row, int column) {
                if (column == 2) {
                    return new DefaultCellEditor(new JComboBox<>(new String[]{"Present", "Absent"}));
                }
                return super.getCellEditor(row, column);
            }
        };
        table1.setRowHeight(25);
        table1.setFont(new Font("Arial", Font.PLAIN, 13));
        mainPanel.add(new JScrollPane(table1), BorderLayout.CENTER);

        markAttendanceButton = new JButton("Mark Attendance");
        markAttendanceButton.setBackground(Color.WHITE);
        markAttendanceButton.setForeground(Color.BLACK);
        markAttendanceButton.setFont(new Font("Arial", Font.BOLD, 12));
        markAttendanceButton.setFocusPainted(false);

        backButton = new JButton("Back");
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(Color.BLACK);
        backButton.setFont(new Font("Arial", Font.BOLD, 12));
        backButton.setFocusPainted(false);

        countTextField = new JTextField(2);
        countLabel = new JLabel("Count");
        countLabel.setForeground(Color.WHITE);
        countLabel.setFont(new Font("Arial", Font.BOLD, 14));

        summaryButton = new JButton("Attendance Summary");
        summaryButton.setBackground(Color.WHITE);
        summaryButton.setForeground(Color.BLACK);
        summaryButton.setFont(new Font("Arial", Font.BOLD, 12));
        summaryButton.setFocusPainted(false);

        bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(52, 73, 94));
        bottomPanel.add(countLabel);
        bottomPanel.add(countTextField);
        bottomPanel.add(markAttendanceButton);
        bottomPanel.add(backButton);
        bottomPanel.add(summaryButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        markAttendanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Attendance> attendances = new ArrayList<>();
                for (int i = 0; i < model.getRowCount(); i++) {
                    int studentId = (int) model.getValueAt(i, 0);
                    String status = (String) model.getValueAt(i, 2);
                    attendances.add(new Attendance(studentId, status));
                }
                try {
                    attendanceService.markAttendance(attendances);
                    JOptionPane.showMessageDialog(null, "Attendance saved!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error saving attendance: " + ex.getMessage());
                }
            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadStudents();
            }
        });

        loadCourses();

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

        summaryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String report = attendanceService.getAttendanceSummary();
                    if (report.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No attendance records found.");
                    } else {
                        JOptionPane.showMessageDialog(null, report, "Attendance Summary", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error loading attendance summary: " + ex.getMessage());
                }
            }
        });
    }

    private void loadStudents() {
        model.setRowCount(0);
        String selectedCourse = (String) courseComboBox.getSelectedItem();
        try {
            List<Student> students = studentService.getStudentsByCourse(selectedCourse);
            int studentCount = 0;
            for (Student student : students) {
                model.addRow(new Object[]{
                        student.getId(),
                        student.getName(),
                        "Present"
                });
                studentCount++;
            }
            countTextField.setText(String.valueOf(studentCount));
            countTextField.setEditable(false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed to load students: " + e.getMessage());
            countTextField.setText("0");
        }
    }

    private void loadCourses() {
        courseComboBox.removeAllItems();
        try {
            List<String> courseNames = studentService.getAllCourseNames();
            for (String name : courseNames) {
                courseComboBox.addItem(name);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed to load courses: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("ATTENDANCE MANAGEMENT SYSTEM");
        frame.setContentPane(new CourseWiseAttendanceForm().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}