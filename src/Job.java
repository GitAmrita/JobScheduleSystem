import java.util.*;

/**
 * Created by amritachowdhury on 8/5/17.
 */

public class Job {
    public static int jobCount = 1; // for generating job ids
    public int jobId; public Set<Job> parents; public Set<Job> children; public JobStatus status; public boolean visited;
    public long expectedTimeToComplete; public long timeElapsed; public int priority; public int deadline;
    public String name;
    public Scheduler schedulerObj;

    // for unit testing
    public Job(int jobId, int priority, String name) {
        this.jobId = jobId;
        this.priority = priority;
        this.name = name;
        this.children = new HashSet<>();
        this.parents = new HashSet<>();
    }

    public Job(TestInput input, Scheduler scheduler) throws Exception  {
        this.schedulerObj = scheduler;
        this.visited = false;
        this.jobId = getJobId();
        this.name = input.name;
        this.status = JobStatus.TO_BE_STARTED;
        this.expectedTimeToComplete = input.expectedCompletion;
        this.deadline = input.deadline;
        this.timeElapsed = 0;
        this.priority = 1;
        this.children = new HashSet<>();
        this.parents = new HashSet<>();
        // store temporarily all the dependencies for this job
        schedulerObj.children.put(this, input.dependencies);
        // mapping and reverse mapping between job and input
        schedulerObj.job.put(input, this);
    }

    public void setParentsAndChildren() {
        // Store all the children (dependencies) of this job and store the reverse as well
        List<TestInput> list = schedulerObj.children.get(this);
        for (TestInput t : list) {
            Job j = schedulerObj.job.get(t);
            this.children.add(j);
            j.parents.add(this);
            String message = String.format("Parent: %s children : %s", this.name,
                    j.name);
            Logging.log(message, this.getClass().getName(), LogLevel.DEBUG);
        }
    }

    // reset priority to 1. For all the parents of job x add x.priority += parent.priority
    // for all children of x recalculate priority. Old priority is an optimization, if priority didn't
    // change from previous value then no need to recalculate children
    public void setPriority() {
        int oldPriority = this.priority;
        this.priority = 1;
       for (Job p : this.parents) {
           this.priority += p.priority;
       }
       if (oldPriority != this.priority) {
           for (Job c : this.children) {
               c.setPriority();
           }
       }
        String message = String.format("Priority of: %s is set to : %d", this.name, this.priority);
        Logging.log(message, this.getClass().getName(), LogLevel.DEBUG);
    }

    private int getJobId() throws Exception {
        if (jobCount == Config.MAX_NO_OF_JOBS) {
            String message = String.format("Number of jobs exceeded system capacity of %d", Config.MAX_NO_OF_JOBS);
            throw new Exception (message);
        }
        int id = jobCount;
        jobCount += 1;
        return id;
    }

}
