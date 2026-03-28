import engine.PlannerEngine;
import engine.Scheduler;
import model.Topic;
import model.StudySession;

import java.time.LocalDate;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        List<Topic> topics = new ArrayList<>();

        topics.add(new Topic("Recursion", 4, LocalDate.now().minusDays(5), LocalDate.now().plusDays(3)));
        topics.add(new Topic("Integration", 6, LocalDate.now().minusDays(2), LocalDate.now().plusDays(10)));
        topics.add(new Topic("OOP", 8, LocalDate.now().minusDays(1), LocalDate.now().plusDays(15)));

        PlannerEngine planner = new PlannerEngine();
        Scheduler scheduler = new Scheduler();

        List<Topic> sortedTopics = planner.sortByWeakness(topics);
        List<StudySession> plan = scheduler.generatePlan(sortedTopics, 3);

        System.out.println("Today's Study Plan:");
        System.out.println("----------------------");

        for (StudySession s : plan) {
            System.out.println(s);
        }
    }
}