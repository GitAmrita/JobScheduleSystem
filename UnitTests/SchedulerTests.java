import junit.framework.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amritachowdhury on 8/7/17.
 */
public class SchedulerTests {
    @Test
    public void populatePriorityRange_onlyOnePriority_test() {
        Scheduler testObj = new Scheduler();
        testObj.allJobs = new ArrayList<>(4);
        Job j1 = new Job(1, 3);
        Job j2 = new Job(2, 3);
        Job j3 = new Job(3, 3);
        testObj.allJobs.add(j1); testObj.allJobs.add(j2); testObj.allJobs.add(j3);
        int [] g = testObj.populatePriorityRange();
        Assert.assertTrue(g.length == 2);
        Assert.assertTrue(g[0] == 3 && g[1] == 3);
    }

    @Test
    public void populatePriorityRange_test() {
        Scheduler testObj = new Scheduler();
        testObj.allJobs = new ArrayList<>(4);
        Job j1 = new Job(1, 5);
        Job j2 = new Job(2, 9);
        testObj.allJobs.add(j1); testObj.allJobs.add(j2);
        int g [] = testObj.populatePriorityRange();
        Assert.assertTrue(g.length == 2);
        Assert.assertTrue(g[0] == 5 && g[1] == 9);
    }

    @Test
    public void createBuckets_onlyOnePriority_test() {
        Scheduler testObj = new Scheduler();
        testObj.allJobs = new ArrayList<>(4);
        Job j1 = new Job(1, 1);
        Job j2 = new Job(2, 1);
        testObj.allJobs.add(j1); testObj.allJobs.add(j2);
        List<Bucket> buckets = testObj.createBuckets();
        Assert.assertTrue(true);
        Assert.assertTrue(buckets.size() ==  1);
        Assert.assertTrue(buckets.get(0).minPriority == 1 && buckets.get(0).maxPriority == 1);
    }

    @Test
    public void createBuckets_test() {
        Scheduler testObj = new Scheduler();
        testObj.allJobs = new ArrayList<>(4);
        Job j1 = new Job(1, 1);
        Job j2 = new Job(2, 2);
        Job j3 = new Job(3, 3);
        Job j4 = new Job(4, 4);
        Job j5 = new Job(5, 5);
        testObj.allJobs.add(j1); testObj.allJobs.add(j2); testObj.allJobs.add(j3); testObj.allJobs.add(j4);
        testObj.allJobs.add(j5);
        List<Bucket> buckets = testObj.createBuckets();
        Assert.assertTrue(true);
        Assert.assertTrue(buckets.size() ==  Config.NO_OF_BUCKETS);
        Assert.assertTrue(buckets.get(0).minPriority == 1 && buckets.get(0).maxPriority == 2);
        Assert.assertTrue(buckets.get(1).minPriority == 3 && buckets.get(1).maxPriority == 5);
    }

    @Test
    public void getBucket_test() {
        Scheduler testObj = new Scheduler();
        Bucket b1 = new Bucket(1, 1, 4);
        Bucket b2 = new Bucket(2, 5, 8);
        Bucket b3 = new Bucket(3, 9, 14);
        testObj.buckets.add(b1); testObj.buckets.add(b2); testObj.buckets.add(b3);
        Bucket b = testObj.getBucket(7);
        Assert.assertEquals(b.id, 2);

    }

    @Test
    public void getBucket_null_test() {
        Scheduler testObj = new Scheduler();
        Bucket b1 = new Bucket(1, 1, 4);
        Bucket b2 = new Bucket(2, 5, 8);
        Bucket b3 = new Bucket(3, 9, 14);
        testObj.buckets.add(b1); testObj.buckets.add(b2); testObj.buckets.add(b3);
        Bucket b = testObj.getBucket(78);
        Assert.assertTrue(b == null);
    }
}
