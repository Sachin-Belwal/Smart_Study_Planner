package engine;

import model.Topic;
import model.StudySession;

import java.util.*;

public class Scheduler {

    public List<StudySession> generatePlan(List<Topic> topics, int hours) {

        List<StudySession> plan = new ArrayList<>();

        int totalMinutes = hours * 60;
        int sessionTime = 45;

        for (Topic t : topics) {
            if (totalMinutes <= 0) break;

            int duration = Math.min(sessionTime, totalMinutes);
            plan.add(new StudySession(t.getName(), duration));

            totalMinutes -= duration;
        }

        return plan;
    }
}