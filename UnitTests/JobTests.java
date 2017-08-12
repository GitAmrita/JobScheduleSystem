import SchedulerJob.Job;
import SchedulerJob.Scheduler;
import junit.framework.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by amritachowdhury on 8/7/17.
 */
public class JobTests {
    @Test
    public void setPriority1_test() {
        Scheduler scheduler = new Scheduler();
        scheduler.childParentMapping.put("j3", Arrays.asList("j1", "j2"));
        List<Job> jobs = new ArrayList<>(3);
        Job j1 = new Job( 1, "j1");
        Job j2 = new Job(1, "j2");
        Job j3 = new Job( 1, "j3");
        scheduler.jobNameObjectMapping.put("j1", j1);
        scheduler.jobNameObjectMapping.put("j2", j2);
        scheduler.jobNameObjectMapping.put("j3", j3);
        j1.children.add(j3.name);
        j2.children.add(j3.name);
        jobs.add(j1); jobs.add(j2); jobs.add(j3);
        for (Job j : jobs) {
            j.setPriority(scheduler);
        }
        Assert.assertEquals(j1.priority, 1);
        Assert.assertEquals(j2.priority, 1);
        Assert.assertEquals(j3.priority, 3);
    }

    @Test
    public void setPriority1_reverse_test() {
        Scheduler scheduler = new Scheduler();
        scheduler.childParentMapping.put("j3", Arrays.asList("j1", "j2"));
        List<Job> jobs = new ArrayList<>(3);
        Job j1 = new Job( 1, "j1");
        Job j2 = new Job( 1, "j2");
        Job j3 = new Job( 1, "j3");
        scheduler.jobNameObjectMapping.put("j1", j1);
        scheduler.jobNameObjectMapping.put("j2", j2);
        scheduler.jobNameObjectMapping.put("j3", j3);
        j1.children.add(j3.name);
        j2.children.add(j3.name);
        jobs.add(j3); jobs.add(j1); jobs.add(j2);
        for (Job j : jobs) {
            j.setPriority(scheduler);
        }
        Assert.assertEquals(j1.priority, 1);
        Assert.assertEquals(j2.priority, 1);
        Assert.assertEquals(j3.priority, 3);
    }

    @Test
    public void setPriority2_test() {
        Scheduler scheduler = new Scheduler();
        scheduler.childParentMapping.put("j2", Arrays.asList("j1"));
        scheduler.childParentMapping.put("j3", Arrays.asList("j2"));
        scheduler.childParentMapping.put("j4", Arrays.asList("j3"));
        List<Job> jobs = new ArrayList<>(3);
        Job j1 = new Job( 1, "j1");
        Job j2 = new Job( 1, "j2");
        Job j3 = new Job(1, "j3");
        Job j4 = new Job(1, "j4");
        scheduler.jobNameObjectMapping.put("j1", j1);
        scheduler.jobNameObjectMapping.put("j2", j2);
        scheduler.jobNameObjectMapping.put("j3", j3);
        scheduler.jobNameObjectMapping.put("j4", j4);
        j1.children.add(j2.name);
        j2.children.add(j3.name);
        j3.children.add(j4.name);
        jobs.add(j1); jobs.add(j2); jobs.add(j3); jobs.add(j4);
        for (Job j : jobs) {
            j.setPriority(scheduler);
        }
        Assert.assertEquals(j1.priority, 1);
        Assert.assertEquals(j2.priority, 2);
        Assert.assertEquals(j3.priority, 3);
        Assert.assertEquals(j4.priority, 4);
    }

    @Test
    public void setPriority2_reverse_test() {
        Scheduler scheduler = new Scheduler();
        scheduler.childParentMapping.put("j2", Arrays.asList("j1"));
        scheduler.childParentMapping.put("j3", Arrays.asList("j2"));
        scheduler.childParentMapping.put("j4", Arrays.asList("j3"));
        List<Job> jobs = new ArrayList<>(3);
        Job j1 = new Job( 1, "j1");
        Job j2 = new Job( 1, "j2");
        Job j3 = new Job( 1, "j3");
        Job j4 = new Job( 1, "j4");
        scheduler.jobNameObjectMapping.put("j1", j1);
        scheduler.jobNameObjectMapping.put("j2", j2);
        scheduler.jobNameObjectMapping.put("j3", j3);
        scheduler.jobNameObjectMapping.put("j4", j4);
        j1.children.add(j2.name);
        j2.children.add(j3.name);
        j3.children.add(j4.name);
        jobs.add(j3); jobs.add(j4); jobs.add(j1); jobs.add(j2);
        for (Job j : jobs) {
            j.setPriority(scheduler);
        }
        Assert.assertEquals(j1.priority, 1);
        Assert.assertEquals(j2.priority, 2);
        Assert.assertEquals(j3.priority, 3);
        Assert.assertEquals(j4.priority, 4);
    }
}
