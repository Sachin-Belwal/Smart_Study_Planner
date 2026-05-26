package storage;

import model.Topic;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    private static final String FILE_NAME = "topics.txt";

    // Save topics to file
    public void saveTopics(List<Topic> topics) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {

            for (Topic t : topics) {

                writer.write(
                        t.getName() + "," +
                        t.getConfidence() + "," +
                        t.getLastStudiedDate() + "," +
                        t.getExamDate()
                );

                writer.newLine();
            }

            System.out.println("Topics saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving topics.");
        }
    }

    // Load topics from file
    public List<Topic> loadTopics() {

        List<Topic> topics = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",");

                String name = data[0];
                int confidence = Integer.parseInt(data[1]);
                LocalDate lastStudied = LocalDate.parse(data[2]);
                LocalDate examDate = LocalDate.parse(data[3]);

                topics.add(
                        new Topic(name, confidence, lastStudied, examDate)
                );
            }

        } catch (IOException e) {
            System.out.println("No previous data found.");
        }

        return topics;
    }
}