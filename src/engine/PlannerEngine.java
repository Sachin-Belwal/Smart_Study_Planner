package engine;

import model.Topic;
import java.util.*;

public class PlannerEngine {

    public int calculateWeakness(Topic t) {
        int confidenceFactor = 10 - t.getConfidence();
        int revisionGap = t.getDaysSinceLastStudy();
        int urgency = t.getExamUrgency();

        return confidenceFactor + revisionGap + urgency;
    }

    public List<Topic> sortByWeakness(List<Topic> topics) {
        topics.sort((a, b) -> calculateWeakness(b) - calculateWeakness(a));
        return topics;
    }
}