import java.util.*;

/**
 * Created by amritachowdhury on 8/5/17.
 */
public class Helper {
    public static Map<Job, List<TestInput>> children = new HashMap<>();
    public static Map<TestInput, Job> job = new HashMap<>();
    public static List<Job> allJobs = new ArrayList<>();
    public static List<Job> sortHighestPriorityFirst(List<Job> jobs) {
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
