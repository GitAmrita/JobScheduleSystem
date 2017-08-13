package SchedulerJob;

import java.util.*;

/**
 * Created by amritachowdhury on 8/12/17.
 */
public class RequestValidity {

    public PriorityQueue<Job> queue;

    public RequestValidity(List<Job> jobs) {
        this.queue = new PriorityQueue<>(jobs.size(), new Comparator<Job>() {
            @Override
            public int compare(Job o1, Job o2) {
                if (o1.priority < o2.priority) {return 1;}
                else if (o1.priority > o2.priority) {return -1;}
                else {
                    if (o1.expectedTimeToComplete < o2.expectedTimeToComplete) {return -1;}
                    else if (o1.expectedTimeToComplete > o2.expectedTimeToComplete) {return 1;}
                    else return 0;
                }
            }
        });
        queue.addAll(jobs);

    }

    public boolean canScheduleBeCompleted() {
        long timeToFinish = 0;
        while (!queue.isEmpty()) {
            Job j = queue.poll();
            timeToFinish += j.expectedTimeToComplete;
            if (timeToFinish > j.deadline) {
                String message = String.format("Schedule miss job : %s expectedTimeToComplete : %x deadline %x", j.name,
                        timeToFinish, j.deadline);
                Logging.log(message, this.getClass().getName(), LogLevel.ERROR);
                return false;
            }
            String message = String.format("Schedule hit job : %s expectedTimeToComplete : %x deadline %x", j.name,
                    timeToFinish, j.deadline);
            Logging.log(message, this.getClass().getName(), LogLevel.DEBUG);
        }
        return true;
    }

}
