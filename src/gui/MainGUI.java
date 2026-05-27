package gui;

import engine.PlannerEngine;
import engine.Scheduler;
import model.Topic;
import model.StudySession;
import storage.FileManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class MainGUI extends JFrame {

    private JTextField topicField;
    private JTextField confidenceField;
    private JTextField examDaysField;
    private JTextField studyHoursField;

    private JTable planTable;
    private DefaultTableModel tableModel;

    private List<Topic> topics;

    private FileManager fileManager = new FileManager();

    public MainGUI() {

        // Load saved topics
        topics = fileManager.loadTopics();

        // ================= FRAME =================

        setTitle("Smart Adaptive Study Planner");

        setSize(900, 650);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // ================= HEADER =================

        JPanel headerPanel = new JPanel();

        headerPanel.setBackground(new Color(25, 45, 85));

        JLabel title = new JLabel(
                "Smart Adaptive Study Planner"
        );

        title.setForeground(Color.WHITE);

        title.setFont(new Font("Arial", Font.BOLD, 28));

        headerPanel.add(title);

        add(headerPanel, BorderLayout.NORTH);

        // ================= LEFT INPUT PANEL =================

        JPanel inputPanel = new JPanel();

        inputPanel.setLayout(new GridLayout(10, 1, 10, 10));

        inputPanel.setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );

        inputPanel.setBackground(Color.WHITE);

        // Topic
        inputPanel.add(new JLabel("Topic Name"));

        topicField = new JTextField();

        inputPanel.add(topicField);

        // Confidence
        inputPanel.add(new JLabel("Confidence (1-10)"));

        confidenceField = new JTextField();

        inputPanel.add(confidenceField);

        // Exam Days
        inputPanel.add(new JLabel("Exam in Days"));

        examDaysField = new JTextField();

        inputPanel.add(examDaysField);

        // Study Hours
        inputPanel.add(new JLabel("Study Hours"));

        studyHoursField = new JTextField();

        inputPanel.add(studyHoursField);

        // ================= BUTTONS =================

        JButton addButton =
                createStyledButton("Add Topic");

        JButton generateButton =
                createStyledButton("Generate Plan");

        JButton newPlanButton =
                createStyledButton("New Plan");

        inputPanel.add(addButton);

        inputPanel.add(generateButton);

        inputPanel.add(newPlanButton);

        add(inputPanel, BorderLayout.WEST);

        // ================= TABLE =================

        String[] columns = {
                "Topic",
                "Study Duration"
        };

        tableModel = new DefaultTableModel(columns, 0);

        planTable = new JTable(tableModel);

        planTable.setRowHeight(30);

        planTable.setFont(
                new Font("Arial", Font.PLAIN, 16)
        );

        planTable.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 16)
        );

        JScrollPane scrollPane =
                new JScrollPane(planTable);

        scrollPane.setBorder(
                BorderFactory.createTitledBorder(
                        "Today's Study Plan"
                )
        );

        add(scrollPane, BorderLayout.CENTER);

        // ================= BUTTON ACTIONS =================

        addButton.addActionListener(e -> addTopic());

        generateButton.addActionListener(e -> generatePlan());

        newPlanButton.addActionListener(e -> startNewPlan());

        setVisible(true);
    }

    // ================= STYLED BUTTON =================

    private JButton createStyledButton(String text) {

        JButton button = new JButton(text);

        button.setBackground(new Color(45, 95, 190));

        button.setForeground(Color.WHITE);

        button.setFocusPainted(false);

        button.setFont(new Font("Arial", Font.BOLD, 15));

        return button;
    }

    // ================= ADD TOPIC =================

    private void addTopic() {

        try {

            String topicName =
                    topicField.getText();

            int confidence =
                    Integer.parseInt(
                            confidenceField.getText()
                    );

            int examDays =
                    Integer.parseInt(
                            examDaysField.getText()
                    );

            Topic topic = new Topic(
                    topicName,
                    confidence,
                    LocalDate.now(),
                    LocalDate.now().plusDays(examDays)
            );

            topics.add(topic);

            // Save automatically
            fileManager.saveTopics(topics);

            JOptionPane.showMessageDialog(
                    this,
                    "Topic Added Successfully!"
            );

            topicField.setText("");

            confidenceField.setText("");

            examDaysField.setText("");

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter valid input!"
            );
        }
    }

    // ================= GENERATE PLAN =================

    private void generatePlan() {

        try {

            int studyHours =
                    Integer.parseInt(
                            studyHoursField.getText()
                    );

            PlannerEngine planner =
                    new PlannerEngine();

            Scheduler scheduler =
                    new Scheduler();

            List<Topic> sortedTopics =
                    planner.sortByWeakness(topics);

            List<StudySession> plan =
                    scheduler.generatePlan(
                            sortedTopics,
                            studyHours
                    );

            // Clear old rows
            tableModel.setRowCount(0);

            // Add new rows
            for (StudySession s : plan) {

                tableModel.addRow(
                        new Object[]{
                                s.getTopicName(),
                                s.getDuration() + " mins"
                        }
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error generating plan!"
            );
        }
    }

    // ================= NEW PLAN =================

    private void startNewPlan() {

        topics.clear();

        fileManager.saveTopics(topics);

        tableModel.setRowCount(0);

        topicField.setText("");

        confidenceField.setText("");

        examDaysField.setText("");

        studyHoursField.setText("");

        JOptionPane.showMessageDialog(
                this,
                "Previous Plan Deleted!\nStart New Plan."
        );
    }
}