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

    public Job(int jobId, int priority) {
        this.jobId = jobId;
        this.priority = priority;
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
        this.priority = 0;
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

    // if there are n jobs dependant on this job and all the n jobs have equal priority say p then priority of this job = p + 1 * n
    // else priority of this job = max of priorities for n jobs + 1 * frequency of max priority
    public void setPriority() {
        if (this.name.equals("T1") || this.name.equals("T2") || this.name.equals("T3")) {this.priority = 0;}
        else if (this.name.equals("T5") || this.name.equals("T6")) {this.priority = 4;}
        else {this.priority = 3;}
        String message = String.format("Priority of: %s is set to : %d", this.name, this.priority);
        Logging.log(message, this.getClass().getName(), LogLevel.DEBUG);
    }
    /*public void  setPriority() {
        if(this.parents.size() == 0) {
            this.priority = 1;
        } else {
            List<Job> list = sortHighestPriorityFirst(new ArrayList<>(this.parents));
            int highestPriority = list.get(0).priority;
            int i = 0;
            for (Job j : list) {
               if (j.priority == highestPriority) {
                   i++;
               } else {
                   break;
               }
            }
            this.priority = highestPriority + 1 * i;
        }
        setChildrenPriority();
        String message = String.format("Priority of: %s is set to : %d", this.name, this.priority);
        Logging.log(message, this.getClass().getName(), LogLevel.DEBUG);

    }*/

    /*private void setChildrenPriority() {
        for (Job child : this.children) {
            if (child.priority == 0) {
                continue;
            }
            if (this.priority <= child.priority) {
                child.priority += 1;
            } else {
                child.priority = this.priority + 1;
            }
            String message = String.format("Priority of: %s is set to : %d", child.name, child.priority);
            Logging.log(message, this.getClass().getName(), LogLevel.DEBUG);
        }
    }*/

    private int getJobId() throws Exception {
        if (jobCount == Config.MAX_NO_OF_JOBS) {
            String message = String.format("Number of jobs exceeded system capacity of %d", Config.MAX_NO_OF_JOBS);
            throw new Exception (message);
        }
        int id = jobCount;
        jobCount += 1;
        return id;
    }

    // sort jobs based on highest priority
    public List<Job> sortHighestPriorityFirst(List<Job> jobs) {
        Collections.sort(jobs, new Comparator<Job>() {
            public int compare(Job j1, Job j2) {
                if (j1.priority < j2.priority) {
                    return 1;
                } else if (j1.priority == j2.priority) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });
        return jobs;
    }
}
