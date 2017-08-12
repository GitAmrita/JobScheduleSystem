import junit.framework.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by amritachowdhury on 8/7/17.
 */
public class SchedulerTests {
    @Test
    public void populateBuckets_onlyOnePriority_test() throws Exception {
        Scheduler testObj = new Scheduler();
        testObj.allJobs = new ArrayList<>(4);
        Job j1 = new Job(1, 4, "j1");
        Job j2 = new Job(2, 4, "j2");
        testObj.allJobs.add(j1); testObj.allJobs.add(j2);
        testObj.populateBuckets();
        Assert.assertTrue(true);
        Assert.assertTrue(testObj.buckets.size() ==  1);
        Assert.assertTrue(testObj.buckets.get(0).priority == 4);
    }

    @Test
    public void populateBuckets_test() throws Exception {
        Scheduler testObj = new Scheduler();
        testObj.allJobs = new ArrayList<>(4);
        Job j1 = new Job(1, 1, "j1");
        Job j2 = new Job(2, 2, "j2");
        Job j3 = new Job(3, 3, "j3");
        Job j4 = new Job(4, 4, "j4");
        Job j5 = new Job(5, 5, "j5");
        testObj.allJobs.add(j1); testObj.allJobs.add(j2); testObj.allJobs.add(j3); testObj.allJobs.add(j4);
        testObj.allJobs.add(j5);
        testObj.populateBuckets();
        Assert.assertTrue(true);
        Assert.assertTrue(testObj.buckets.size() ==  5);
        Collections.sort(testObj.buckets);
        Assert.assertTrue(testObj.buckets.get(0).priority == 5);
        Assert.assertTrue(testObj.buckets.get(1).priority == 4);
        Assert.assertTrue(testObj.buckets.get(2).priority == 3);
        Assert.assertTrue(testObj.buckets.get(3).priority == 2);
        Assert.assertTrue(testObj.buckets.get(4).priority == 1);
    }

    @Test
    public void getBucket_test() {
        Scheduler testObj = new Scheduler();
        Bucket b1 = new Bucket(1, 1);
        Bucket b2 = new Bucket(2, 5);
        Bucket b3 = new Bucket(3, 9);
        testObj.buckets.add(b1); testObj.buckets.add(b2); testObj.buckets.add(b3);
        Bucket b = testObj.getBucket(1);
        Assert.assertEquals(b.id, 0);
        b = testObj.getBucket(5);
        Assert.assertEquals(b.id, 1);
        b = testObj.getBucket(9);
        Assert.assertEquals(b.id, 2);
    }
}
