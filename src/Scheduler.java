import java.util.*;

/**
 * Created by amritachowdhury on 8/5/17.
 */
public class Scheduler {
    List<Bucket> buckets = new ArrayList<>();
    Map<Node, Bucket> nodeBucketLookUp = new HashMap<>();
    Map<Job, Node> jobNodeLookUp = new HashMap<>();
    List<Job> scheduled = new LinkedList<>();

    public Scheduler() {
        try {
            createBuckets();
            populateBuckets();
            Collections.sort(buckets);
            run();
            System.out.println("end");
            printScheduleRoutine();
        } catch (Exception ex) {

        }
    }

    private void printScheduleRoutine() {
        for (Job j : scheduled) {
            System.out.println(j.jobId);
        }
    }

    private int populatePriorityRange() {
        int minPriority = Integer.MAX_VALUE; int maxPriority = Integer.MIN_VALUE;
        for (Job j : Helper.allJobs) {
            if (j.priority < minPriority) {
                minPriority = j.priority;
            } if (j.priority > maxPriority) {
                maxPriority = j.priority;
            }
        }
        return (maxPriority - minPriority);
    }

    private void populateBuckets() throws Exception{
        for (Job j :Helper.allJobs) {
            Bucket b = getBucket(j.priority);
            if ( b == null ) {
                throw new Exception ("couldn't find bucket");
            }
            Node n = b.addJob(j);
            nodeBucketLookUp.put(n, b);
            jobNodeLookUp.put(j, n);
        }
    }

    public void finishJob(Job job) {
        Node nodeToRemove = jobNodeLookUp.get(job);
        Bucket b = nodeBucketLookUp.get(nodeToRemove);
        Job removedJob = b.removeJob(nodeToRemove).data;
        removedJob.status = JobStatus.FINISHED;
        for (Job j : removedJob.parents) {
            j.children.remove(removedJob);
        }
        scheduled.add(removedJob);
    }

    public void pickBucketToProcess(Processor p) {
        Iterator<Bucket> iterator = buckets.iterator();
        while (iterator.hasNext()) {
            if (!pickJobToProcess(iterator.next(), p)) {
                iterator.remove();
            }
        }
    }

    private boolean pickJobToProcess(Bucket bucket, Processor processor) {
        DoubleLinkedList nodes = bucket.jobs;
        if (nodes == null || nodes.isEmpty()) {
            return false;
        }
        boolean timeOver = false;
        long currentTime = System.currentTimeMillis();
        long endTime = currentTime + Config.BUCKET_TIME_SLICE;
        while (!nodes.isEmpty() && !timeOver) {
            Node currentNode = nodes.getNext(null);
            while (currentNode != null) {
                if (currentTime >= endTime) {
                    timeOver = true;
                    break;
                }
                if (currentNode.data.status != JobStatus.FINISHED && currentNode.data.children.isEmpty()) {
                    boolean isJobFinished = processJob(currentNode.data);
                    if (isJobFinished) {
                        finishJob(currentNode.data);
                        nodes = bucket.jobs;
                    }
                }
                currentNode = nodes.getNext(currentNode);
            }
        }
        return nodes == null || nodes.isEmpty() ? false : true;
    }

    private boolean processJob(Job job) {
        boolean finished = false;
        long startTime = System.currentTimeMillis();
        long endTime = startTime + Config.JOB_TIME_SLICE;
        long currentTime = startTime;
        while (currentTime <= endTime) {
            if (job.timeElapsed >= job.expectedTimeToComplete) {
                finished = true;
                break;
            }
            currentTime = System.currentTimeMillis();
        }
        job.timeElapsed += endTime - startTime;
        return finished;
    }

    private Bucket getBucket(int priority) {
        for (Bucket b : buckets) {
            if (priority <= b.maxPriority && priority >= b.minPriority) {
                return b;
            }
        }
        return null;
    }

    private void createBuckets() {
        int maxVal;
        int size = populatePriorityRange() / Config.NO_OF_BUCKETS;
        int minVal = 0;
        for (int i = 0; i < Config.NO_OF_BUCKETS; i++) {
            maxVal = i == Config.NO_OF_BUCKETS - 1 ? Integer.MAX_VALUE : minVal + size - 1;
            Bucket b = new Bucket(i, minVal, maxVal);
            buckets.add(b);
            minVal += size;
        }
    }

    public void run() {
        System.out.println("run");
        Processor p = new Processor();
        pickBucketToProcess(p);
    }

    private void finishJobTest() {
        for (int i = 0; i < 2; i++) {
        Bucket test = buckets.get(i);
        int pre = test.jobs.size();
        Job finishedTest = test.jobs.iterateTill(2).data;
        finishJob(finishedTest);
        int post = test.jobs.size();
        System.out.println("pre " + pre + " post " + post + " job " + finishedTest.jobId);}
    }
}
