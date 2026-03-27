package model;

public class StudySession {

    private String topicName;
    private int duration;

    public StudySession(String topicName, int duration) {
        this.topicName = topicName;
        this.duration = duration;
    }

    public String getTopicName() {
        return topicName;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return topicName + " - " + duration + " mins";
    }
}