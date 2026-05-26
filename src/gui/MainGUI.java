package gui;

import engine.PlannerEngine;
import engine.Scheduler;
import model.Topic;
import model.StudySession;
import storage.FileManager;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MainGUI extends JFrame {

    private JTextField topicField;
    private JTextField confidenceField;
    private JTextField examDaysField;
    private JTextField studyHoursField;

    private JTextArea outputArea;

    private List<Topic> topics;

    private FileManager fileManager = new FileManager();

    public MainGUI() {

        // Load saved topics
        topics = fileManager.loadTopics();

        // ================= FRAME =================

        setTitle("Smart Adaptive Study Planner");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout(10, 10));

        // ================= TITLE =================

        JLabel titleLabel = new JLabel(
                "Smart Adaptive Study Planner",
                JLabel.CENTER
        );

        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        add(titleLabel, BorderLayout.NORTH);

        // ================= INPUT PANEL =================

        JPanel inputPanel = new JPanel();

        inputPanel.setLayout(new GridLayout(5, 2, 10, 10));

        inputPanel.setBorder(
                BorderFactory.createTitledBorder("Enter Topic Details")
        );

        // Topic Name
        inputPanel.add(new JLabel("Topic Name:"));

        topicField = new JTextField();

        inputPanel.add(topicField);

        // Confidence
        inputPanel.add(new JLabel("Confidence (1-10):"));

        confidenceField = new JTextField();

        inputPanel.add(confidenceField);

        // Exam Days
        inputPanel.add(new JLabel("Exam in Days:"));

        examDaysField = new JTextField();

        inputPanel.add(examDaysField);

        // Study Hours
        inputPanel.add(new JLabel("Study Hours:"));

        studyHoursField = new JTextField();

        inputPanel.add(studyHoursField);

        // ================= BUTTON PANEL =================

        JPanel buttonPanel = new JPanel();

        buttonPanel.setLayout(new FlowLayout());

        JButton addButton = new JButton("Add Topic");

        JButton generateButton =
                new JButton("Generate Plan");

        JButton newPlanButton =
                new JButton("New Plan");

        buttonPanel.add(addButton);

        buttonPanel.add(generateButton);

        buttonPanel.add(newPlanButton);

        // ================= CENTER PANEL =================

        JPanel centerPanel = new JPanel();

        centerPanel.setLayout(new BorderLayout());

        centerPanel.add(inputPanel, BorderLayout.NORTH);

        centerPanel.add(buttonPanel, BorderLayout.CENTER);

        outputArea = new JTextArea();

outputArea.setEditable(false);

outputArea.setFont(new Font("Monospaced", Font.PLAIN, 16));

JScrollPane scrollPane =
        new JScrollPane(outputArea);

scrollPane.setBorder(
        BorderFactory.createTitledBorder("Generated Study Plan")
);

// Split screen vertically
JSplitPane splitPane = new JSplitPane(
        JSplitPane.VERTICAL_SPLIT,
        centerPanel,
        scrollPane
);

splitPane.setDividerLocation(250);

add(splitPane, BorderLayout.CENTER);

        // ================= BUTTON ACTIONS =================

        addButton.addActionListener(e -> addTopic());

        generateButton.addActionListener(e -> generatePlan());

        newPlanButton.addActionListener(e -> startNewPlan());

        setVisible(true);
    }

    // ================= ADD TOPIC =================

    private void addTopic() {

        try {

            String topicName = topicField.getText();

            int confidence =
                    Integer.parseInt(confidenceField.getText());

            int examDays =
                    Integer.parseInt(examDaysField.getText());

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

            // Clear fields
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
                    Integer.parseInt(studyHoursField.getText());

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

            outputArea.setText("");

            outputArea.append("Today's Study Plan\n");

            outputArea.append(
                    "============================\n\n"
            );

            for (StudySession s : plan) {

                outputArea.append(
                        s.toString() + "\n"
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

        outputArea.setText("");

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