import junit.framework.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amritachowdhury on 8/7/17.
 */
public class JobTests {
    @Test
    public void setPriority1_test() {
        List<Job> jobs = new ArrayList<>(3);
        Job j1 = new Job(1, 1, "j1");
        Job j2 = new Job(2, 1, "j2");
        Job j3 = new Job(3, 1, "j3");
        j1.children.add(j3);
        j2.children.add(j3);
        j3.parents.add(j1);
        j3.parents.add(j2);
        jobs.add(j1); jobs.add(j2); jobs.add(j3);
        for (Job j : jobs) {
            j.setPriority();
        }
        Assert.assertEquals(j1.priority, 1);
        Assert.assertEquals(j2.priority, 1);
        Assert.assertEquals(j3.priority, 3);
    }

    @Test
    public void setPriority1_reverse_test() {
        List<Job> jobs = new ArrayList<>(3);
        Job j1 = new Job(1, 1, "j1");
        Job j2 = new Job(2, 1, "j2");
        Job j3 = new Job(3, 1, "j3");
        j1.children.add(j3);
        j2.children.add(j3);
        j3.parents.add(j1);
        j3.parents.add(j2);
        jobs.add(j3); jobs.add(j1); jobs.add(j2);
        for (Job j : jobs) {
            j.setPriority();
        }
        Assert.assertEquals(j1.priority, 1);
        Assert.assertEquals(j2.priority, 1);
        Assert.assertEquals(j3.priority, 3);
    }

    @Test
    public void setPriority2_test() {
        List<Job> jobs = new ArrayList<>(3);
        Job j1 = new Job(1, 1, "j1");
        Job j2 = new Job(2, 1, "j2");
        Job j3 = new Job(3, 1, "j3");
        Job j4 = new Job(4, 1, "j4");
        j1.children.add(j2);
        j2.children.add(j3);
        j2.parents.add(j1);
        j3.children.add(j4);
        j3.parents.add(j2);
        j4.parents.add(j3);
        jobs.add(j1); jobs.add(j2); jobs.add(j3); jobs.add(j4);
        for (Job j : jobs) {
            j.setPriority();
        }
        Assert.assertEquals(j1.priority, 1);
        Assert.assertEquals(j2.priority, 2);
        Assert.assertEquals(j3.priority, 3);
        Assert.assertEquals(j4.priority, 4);
    }

    @Test
    public void setPriority2_reverse_test() {
        List<Job> jobs = new ArrayList<>(3);
        Job j1 = new Job(1, 1, "j1");
        Job j2 = new Job(2, 1, "j2");
        Job j3 = new Job(3, 1, "j3");
        Job j4 = new Job(4, 1, "j4");
        j1.children.add(j2);
        j2.children.add(j3);
        j2.parents.add(j1);
        j3.children.add(j4);
        j3.parents.add(j2);
        j4.parents.add(j3);
        jobs.add(j3); jobs.add(j4); jobs.add(j1); jobs.add(j2);
        for (Job j : jobs) {
            j.setPriority();
        }
        Assert.assertEquals(j1.priority, 1);
        Assert.assertEquals(j2.priority, 2);
        Assert.assertEquals(j3.priority, 3);
        Assert.assertEquals(j4.priority, 4);
    }
}
