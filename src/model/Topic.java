package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Topic {

    private String name;
    private int confidence;
    private LocalDate lastStudiedDate;
    private LocalDate examDate;

    public Topic(String name, int confidence, LocalDate lastStudiedDate, LocalDate examDate) {
        this.name = name;
        this.confidence = confidence;
        this.lastStudiedDate = lastStudiedDate;
        this.examDate = examDate;
    }

    public String getName() {
        return name;
    }

    public int getConfidence() {
        return confidence;
    }

    public LocalDate getLastStudiedDate() {
        return lastStudiedDate;
    }

    public LocalDate getExamDate() {
        return examDate;
    }

    public int getDaysSinceLastStudy() {
        return (int) ChronoUnit.DAYS.between(lastStudiedDate, LocalDate.now());
    }

    public int getExamUrgency() {
        int daysLeft = (int) ChronoUnit.DAYS.between(LocalDate.now(), examDate);
        if (daysLeft <= 0) return 10;
        return Math.max(1, 10 - daysLeft);
    }
}