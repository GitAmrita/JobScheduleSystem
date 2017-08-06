import java.util.*;

/**
 * Created by amritachowdhury on 8/5/17.
 */

public class Job {
    public static int jobCount = 1;
    public int jobId; private Set<Job> parents; private Set<Job> children; public JobStatus status;
    private int expectedTimeToComplete; private int timeElapsed; public int priority;

    public Job(TestInput input) throws Exception {
        this.jobId = input.deadline; //getJobId()
        this.status = JobStatus.TO_BE_STARTED;
        this.expectedTimeToComplete = input.expectedCompletion;
        this.timeElapsed = 0;
        this.priority = 0;
        this.children = new HashSet<>();
        this.parents = new HashSet<>();
        Helper.children.put(this, input.dependencies);
        Helper.job.put(input, this);
    }

    public void setParentsAndChildren() {
        List<TestInput> list = Helper.children.get(this);
        for (TestInput t : list) {
            Job j = Helper.job.get(t);
            this.children.add(j);
            j.parents.add(this);
        }
    }

    public void  setPriority(){
        if(this.parents.size() == 0) {
            this.priority = 0;
        } else {
            List<Job> list = Helper.sortHighestPriorityFirst(new ArrayList<>(this.parents));
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
    }

    private int getJobId() throws Exception{
        if (jobCount == Integer.MAX_VALUE) {
            throw new Exception ("System cant take any more jobs");
        }
        int id = jobCount;
        jobCount += 1;
        return id;
    }
}
