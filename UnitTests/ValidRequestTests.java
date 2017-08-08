import junit.framework.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amritachowdhury on 8/7/17.
 */
public class ValidRequestTests {
    @Test
    public void canBeScheduled_true_test() {
        Job j1 = new Job(1, 1, "j1"); Job j2 = new Job(2, 2, "j2");
        Job j3 = new Job(3, 3, "j3"); Job j4 = new Job(4, 4, "j4");
        j1.expectedTimeToComplete = 2; j1.deadline = 20;
        j2.expectedTimeToComplete = 3; j2.deadline = 20;
        j3.expectedTimeToComplete = 6; j3.deadline = 20;
        j4.expectedTimeToComplete = 4; j4.deadline = 20;
        List<Job> jobs = new ArrayList<>(4); jobs.add(j1); jobs.add(j2); jobs.add(j3); jobs.add(j4);
        ValidRequest testObj = new ValidRequest(jobs);
        Assert.assertEquals(testObj.queue.size(), 4);
        boolean canBeScheduled = testObj.canScheduleBeCompleted();
        Assert.assertTrue(canBeScheduled);
    }

    @Test
    public void canBeScheduled_false1_test() {
        Job j1 = new Job(1, 1, "j1"); Job j2 = new Job(2, 2, "j2");
        Job j3 = new Job(3, 3, "j3"); Job j4 = new Job(4, 4, "j4");
        j1.expectedTimeToComplete = 2; j1.deadline = 20;
        j2.expectedTimeToComplete = 3; j2.deadline = 1;
        j3.expectedTimeToComplete = 6; j3.deadline = 20;
        j4.expectedTimeToComplete = 4; j4.deadline = 20;
        List<Job> jobs = new ArrayList<>(4); jobs.add(j1); jobs.add(j2); jobs.add(j3); jobs.add(j4);
        ValidRequest testObj = new ValidRequest(jobs);
        boolean canBeScheduled = testObj.canScheduleBeCompleted();
        Assert.assertFalse(canBeScheduled);
    }

    @Test
    public void canBeScheduled_false2_test() {
        Job j1 = new Job(1, 1, "j1"); Job j2 = new Job(2, 2, "j2");
        Job j3 = new Job(3, 3, "j3"); Job j4 = new Job(4, 4, "j4");
        j1.expectedTimeToComplete = 2; j1.deadline = 20;
        j2.expectedTimeToComplete = 3; j2.deadline = 1;
        j3.expectedTimeToComplete = 6; j3.deadline = 20;
        j4.expectedTimeToComplete = 4; j4.deadline = 1;
        List<Job> jobs = new ArrayList<>(4); jobs.add(j1); jobs.add(j2); jobs.add(j3); jobs.add(j4);
        ValidRequest testObj = new ValidRequest(jobs);
        boolean canBeScheduled = testObj.canScheduleBeCompleted();
        Assert.assertFalse(canBeScheduled);
    }
}
