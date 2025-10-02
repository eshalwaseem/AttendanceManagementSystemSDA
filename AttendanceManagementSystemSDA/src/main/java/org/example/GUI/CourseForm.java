package org.example.GUI;

import org.example.DATA.Course;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
import java.util.List;
import org.example.SERVICE.CourseService;
//import org.example.DATA.Course;
//import java.util.ArrayList;


public class CourseForm {
    public JPanel mainPanel;
    private JPanel headerPanel;
    private JLabel courseLabel;
    private JTable table1;
    private DefaultTableModel model;
    private JPanel bottomPanel;
    private JButton backButton;
    private JButton refreshButton;
    private JPanel buttonPanel;
    private JButton addCourseButton;
    private JButton removeCourseButton;
    private JTextField addCourseTextField;
    private JButton statsButton;
    private JTextField countTextField;
    private JLabel countLabel;
    private JTextField searchTextField;
    private JButton searchButton;
    private JButton renameCourseButton;

    private final CourseService courseService;

    public CourseForm() {
        this.courseService = new CourseService();

        initializeUI();

        loadCourses();

        setupEventListeners();
    }

    private void initializeUI() {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(52, 73, 94));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Header Panel
        headerPanel = new JPanel();
        headerPanel.setBackground(new Color(52, 73, 94));
        courseLabel = new JLabel("Course");
        courseLabel.setFont(new Font("Arial", Font.BOLD, 24));
        courseLabel.setForeground(Color.WHITE);
        headerPanel.add(courseLabel);

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(new Color(52, 73, 94));
        searchTextField = new JTextField(15);
        searchButton = new JButton("Search");
        searchButton.setBackground(Color.WHITE);
        searchButton.setForeground(Color.BLACK);
        searchButton.setFont(new Font("Arial", Font.BOLD, 12));
        searchButton.setFocusPainted(false);
        searchButton.setPreferredSize(new Dimension(120, 30));
        searchPanel.add(searchTextField);
        searchPanel.add(searchButton);

        // Top Panel (contains header and search)
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(new Color(52, 73, 94));
        topPanel.add(headerPanel);
        topPanel.add(Box.createVerticalStrut(10));
        topPanel.add(searchPanel);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Table setup
        model = new DefaultTableModel(new String[]{"Course ID", "Course Name", "Number of Students"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table1 = new JTable(model);
        table1.setRowHeight(25);
        table1.getColumnModel().getColumn(2).setPreferredWidth(150);
        table1.setFont(new Font("Arial", Font.PLAIN, 13));
        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table1);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Button Panel
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        addCourseTextField = new JTextField(15);

        addCourseButton = new JButton("Add Course");
        addCourseButton.setBackground(Color.WHITE);
        addCourseButton.setForeground(Color.BLACK);
        addCourseButton.setFont(new Font("Arial", Font.BOLD, 12));
        addCourseButton.setFocusPainted(false);
        addCourseButton.setPreferredSize(new Dimension(120, 30));

        removeCourseButton = new JButton("Remove");
        removeCourseButton.setBackground(Color.WHITE);
        removeCourseButton.setForeground(Color.BLACK);
        removeCourseButton.setFont(new Font("Arial", Font.BOLD, 12));
        removeCourseButton.setFocusPainted(false);
        removeCourseButton.setPreferredSize(new Dimension(120, 30));

        renameCourseButton = new JButton("Rename");
        renameCourseButton.setBackground(Color.WHITE);
        renameCourseButton.setForeground(Color.BLACK);
        renameCourseButton.setFont(new Font("Arial", Font.BOLD, 12));
        renameCourseButton.setFocusPainted(false);
        renameCourseButton.setPreferredSize(new Dimension(120, 30));

        statsButton = new JButton("Statistics");
        statsButton.setBackground(Color.WHITE);
        statsButton.setForeground(Color.BLACK);
        statsButton.setFont(new Font("Arial", Font.BOLD, 12));
        statsButton.setFocusPainted(false);
        statsButton.setPreferredSize(new Dimension(120, 30));

        // Bottom Panel
        bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(new Color(52, 73, 94));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        countTextField = new JTextField();
        countTextField.setEditable(false);
        countTextField.setHorizontalAlignment(JTextField.CENTER);
        countTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        countTextField.setPreferredSize(new Dimension(40, 25));
        countLabel = new JLabel("Count");
        countLabel.setForeground(Color.WHITE);
        countLabel.setFont(new Font("Arial", Font.BOLD, 14));

        refreshButton = new JButton("Refresh");
        refreshButton.setBackground(Color.WHITE);
        refreshButton.setForeground(Color.BLACK);
        refreshButton.setFont(new Font("Arial", Font.BOLD, 12));
        refreshButton.setFocusPainted(false);
        refreshButton.setPreferredSize(new Dimension(120, 30));

        backButton = new JButton("Back");
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(Color.BLACK);
        backButton.setFont(new Font("Arial", Font.BOLD, 12));
        backButton.setFocusPainted(false);
        backButton.setPreferredSize(new Dimension(120, 30));

        buttonPanel.add(addCourseTextField);
        buttonPanel.add(addCourseButton);
        buttonPanel.add(removeCourseButton);
        buttonPanel.add(renameCourseButton);
        buttonPanel.add(statsButton);

        bottomPanel.add(countLabel);
        bottomPanel.add(countTextField);
        bottomPanel.add(Box.createHorizontalStrut(20));
        bottomPanel.add(refreshButton);
        bottomPanel.add(Box.createHorizontalStrut(20));
        bottomPanel.add(backButton);

        JPanel lowerPanel = new JPanel();
        lowerPanel.setLayout(new BoxLayout(lowerPanel, BoxLayout.Y_AXIS));
        lowerPanel.setBackground(new Color(52, 73, 94));

        lowerPanel.add(buttonPanel);
        lowerPanel.add(bottomPanel);
        mainPanel.add(lowerPanel, BorderLayout.SOUTH);
    }

    private void setupEventListeners() {
        refreshButton.addActionListener(_ -> loadCourses());

        backButton.addActionListener(_ -> {
            JFrame dashboardFrame = new JFrame("Dashboard");
            dashboardFrame.setContentPane(new dashboardForm().mainPanel);
            dashboardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            dashboardFrame.setSize(800, 400);
            dashboardFrame.setResizable(false);
            dashboardFrame.setLocationRelativeTo(null);
            dashboardFrame.setVisible(true);
            SwingUtilities.getWindowAncestor(mainPanel).dispose();
        });

        addCourseButton.addActionListener(_ -> {
            String courseName = addCourseTextField.getText();
            if (courseName.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a name");
                return;
            }
            try {
                courseService.addCourse(courseName);
                loadCourses();
                addCourseTextField.setText("");
                JOptionPane.showMessageDialog(null, "Course added Successfully");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error adding course: " + ex.getMessage());
            }
        });

        removeCourseButton.addActionListener(_ -> {
            int row = table1.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Please select a course to delete");
                return;
            }
            int courseId = (int) model.getValueAt(row, 0);
            try {
                courseService.deleteCourse(courseId);
                loadCourses();
                JOptionPane.showMessageDialog(null, "Course deleted");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error" + ex.getMessage());
            }
        });

        renameCourseButton.addActionListener(_ -> {
            int row = table1.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Please select a course to rename.");
                return;
            }
            String currentName = (String) model.getValueAt(row, 1);
            String newName = JOptionPane.showInputDialog(null, "Enter new course name:", currentName);
            if (newName != null && !newName.trim().isEmpty()) {
                int courseId = (int) model.getValueAt(row, 0);
                try {
                    courseService.renameCourse(courseId, newName.trim());
                    JOptionPane.showMessageDialog(null, "Course renamed successfully.");
                    loadCourses();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error renaming course: " + ex.getMessage());
                }
            } else if (newName != null) {
                JOptionPane.showMessageDialog(null, "Course name cannot be empty.");
            }
        });

        searchButton.addActionListener(_ -> {
            String searchTerm = searchTextField.getText();
            if (!searchTerm.isEmpty()) {
                try {
                    model.setRowCount(0);
                    List<Course> courses = courseService.searchCourses(searchTerm);
                    for (Course course : courses) {
                        model.addRow(new Object[]{
                                course.getId(),
                                course.getName(),
                                course.getStudentCount()
                        });
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Search error: " + ex.getMessage());
                }
            } else {
                loadCourses();
            }
        });

        statsButton.addActionListener(_ -> {
            int row = table1.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Please select a course first.");
                return;
            }
            int courseId = (int) model.getValueAt(row, 0);
            String courseName = (String) model.getValueAt(row, 1);
            try {
                String stats = courseService.getCourseStatistics(courseId, courseName);
                JOptionPane.showMessageDialog(null, stats, "Course Statistics", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error loading statistics: " + ex.getMessage());
            }
        });
    }

    private void loadCourses() {
        model.setRowCount(0);
        int courseCount = 0;
        try {
            List<Course> courses = courseService.getAllCourses();
            for (Course course : courses) {
                model.addRow(new Object[]{
                        course.getId(),
                        course.getName(),
                        course.getStudentCount()
                });
                courseCount++;
            }
            countTextField.setText(String.valueOf(courseCount));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed to load courses: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("ATTENDANCE MANAGEMENT SYSTEM");
            frame.setContentPane(new CourseForm().mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 400);
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
