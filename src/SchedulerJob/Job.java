package SchedulerJob;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amritachowdhury on 8/12/17.
 */
public class Job {
    public static int jobCount = 1; // for generating job ids
    public List<String> children; public JobStatus status; public boolean visited;
    public long expectedTimeToComplete; public long timeElapsed; public int priority; public int deadline;
    public String name;

    // for unit testing
    public Job(int priority, String name) {
        this.priority = priority;
        this.name = name;
        this.children = new ArrayList<>();
    }

    public Job (Input input) throws Exception  {
        checkJobWithinLimit();
        this.visited = false;
        this.name = input.name;
        this.status = JobStatus.TO_BE_STARTED;
        this.expectedTimeToComplete = input.expectedCompletion;
        this.deadline = input.deadline;
        this.timeElapsed = 0;
        this.priority = 1;
        this.children = input.dependencies;
    }

    // reset priority to 1. For all the parents of job x add x.priority += parent.priority
    // for all children of x recalculate priority. Old priority is an optimization, if priority didn't
    // change from previous value then no need to recalculate children
    public void setPriority(Scheduler scheduler) {
        int oldPriority = this.priority;
        this.priority = 1;
        List<String> parentNames = scheduler.childParentMapping.get(this.name);
        if (parentNames != null) {
            for (String parentName : parentNames) {
                Job parent = scheduler.jobNameObjectMapping.get(parentName);
                this.priority += parent.priority;
            }
        }
        if (oldPriority != this.priority) {
            for (String childName : this.children) {
                Job child = scheduler.jobNameObjectMapping.get(childName);
                child.setPriority(scheduler);
            }
        }
        String message = String.format("Priority of: %s is set to : %d", this.name, this.priority);
        Logging.log(message, this.getClass().getName(), LogLevel.DEBUG);
    }

    private void checkJobWithinLimit() throws Exception {
        if (jobCount == Config.MAX_NO_OF_JOBS) {
            String message = String.format("Number of jobs exceeded system capacity of %d", Config.MAX_NO_OF_JOBS);
            throw new Exception (message);
        }
        int id = jobCount;
        jobCount += 1;
    }
}
