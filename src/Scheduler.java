import java.util.*;

/**
 * Created by amritachowdhury on 8/5/17.
 */
public class Scheduler {

    //list that holds all the jobs
    public static List<Job> allJobs;
    // buckets holds jobs based on priority. Each bucket has a priority range that it can hold.
    List<Bucket> buckets;
    // hash maps for ease of finding the job from double linked list node of the bucket while removing
    Map<Node, Bucket> nodeBucketLookUp;
    Map<Job, Node> jobNodeLookUp;
    // final result list
    List<Job> scheduled;

    // *******used to temporarily store the testInput and job relationship*******
    // input and job mapping, needed for pre processing steps
    public static Map<TestInput, Job> job;
    // once the job is created in the system, map it with input dependencies
    public static Map<Job, List<TestInput>> children;
    // ***************************************************************************

    public Scheduler() {
        this.allJobs = new ArrayList<>();
        this.buckets = new ArrayList<>();
        this.nodeBucketLookUp = new HashMap<>();
        this.jobNodeLookUp = new HashMap<>();
        this.scheduled = new LinkedList<>();
        this.job = new HashMap<>();
        this.children = new HashMap<>();
    }

    public boolean run() {
        try {
            String message = "Start the job scheduler";
            Logging.log(message, this.getClass().getName(), LogLevel.INFO);
            preProcess();
            pickBucketToProcess();
            return true;
        } catch (Exception e) {
            Logging.log(e.getMessage(), this.getClass().getName(), LogLevel.ERROR);
            return false;
        }

    }

    private void preProcess () throws Exception{
        createBuckets();
        populateBuckets();
        // sorting the buckets so that bucket with the highest priority range comes first
        Collections.sort(buckets);
        String message = "Scheduler pre process completed";
        Logging.log(message, this.getClass().getName(), LogLevel.INFO);
    }

    public List<Bucket> createBuckets() {
        // get the max and min priority (range) and modulo with no of buckets. That would give the range of priority
        // each bucket will hold. Create buckets.
        int maxVal;
        int[] range = populatePriorityRange();
        if (range[0] == range[1]) {
            Bucket b = new Bucket(0, range[0], range[0]);
            buckets.add(b);
        } else {
            int size = ((range[1] - range[0]) + 1 ) / Config.NO_OF_BUCKETS;
            int minVal = range[0];
            for (int i = 0; i < Config.NO_OF_BUCKETS; i++) {
                maxVal = i == Config.NO_OF_BUCKETS - 1 ? range[1] : minVal + size - 1;
                Bucket b = new Bucket(i, minVal, maxVal);
                buckets.add(b);
                minVal += size;
            }
        }
        return buckets;
    }

    public int[] populatePriorityRange() {
        // get range of priority = max - min
        int minPriority = Integer.MAX_VALUE; int maxPriority = Integer.MIN_VALUE;
        for (Job j : allJobs) {
            if (j.priority < minPriority) {
                minPriority = j.priority;
            } if (j.priority > maxPriority) {
                maxPriority = j.priority;
            }
        }
        return new int[] {minPriority, maxPriority};
    }

    private void populateBuckets() throws Exception {
        for (Job j :allJobs) {
            Bucket b = getBucket(j.priority);
            if ( b == null ) {
                String message = "Couldn't find bucket";
                Logging.log(message, this.getClass().getName(), LogLevel.ERROR);
                throw new Exception ("Couldn't find bucket");
            }
            Node n = b.addJob(j);
            nodeBucketLookUp.put(n, b);
            jobNodeLookUp.put(j, n);
        }
    }

    public Bucket getBucket(int priority) {
        // Based on the job priority select the bucket
        for (Bucket b : buckets) {
            if (priority <= b.maxPriority && priority >= b.minPriority) {
                return b;
            }
        }
        return null;
    }

    public void pickBucketToProcess() throws Exception{
        // Pick each bucket within the bucket time slice till all buckets are processed. Using Iterator since I'm
        // removing bucket when all jobs in the bucket are finished while iterating
        Iterator<Bucket> iterator = buckets.iterator();
        while (iterator.hasNext()) {
            if (!pickJobToProcess(iterator.next())) {
                iterator.remove();
            }
        }
    }

    private boolean pickJobToProcess (Bucket bucket) throws Exception {
        // time over is set to true when the bucket time slice elapses. Jobs that don't have dependency are scheduled
        // first. Go for the next bucket when bucket time slice elapses. Returns false when all the jobs in the bucket
        // is scheduled.
        DoubleLinkedList nodes = bucket.jobs;
        if (nodes == null || nodes.isEmpty()) {
            return false;
        }
        boolean timeOver = false;
        long currentTime = System.currentTimeMillis();
        long endTime = currentTime + Config.BUCKET_TIME_SLICE;
        while (!nodes.isEmpty() && !timeOver) {
            // start from the head node
            Node currentNode = nodes.getNext(null);
            while (currentNode != null) {
                if (currentTime >= endTime) { // bucket time slice is over, get the next bucket
                    timeOver = true;
                    break;
                }
                // get the jobs that are not finished and don't have unfinished dependencies
                if (currentNode.data.status == JobStatus.TO_BE_STARTED && currentNode.data.children.isEmpty()) {
                    currentNode.data.status = JobStatus.IN_PROGRESS;
                    if (processJob(currentNode.data)) {
                        finishJob(currentNode.data);
                        nodes = bucket.jobs;
                    } else {
                        currentNode.data.status = JobStatus.TO_BE_STARTED;
                    }
                }
                currentNode = nodes.getNext(currentNode);
            }
        }
        return nodes == null || nodes.isEmpty() ? false : true;
    }

    private boolean processJob(Job job) {
        long startTime = System.currentTimeMillis();
        long endTime = startTime + Config.JOB_TIME_SLICE;
        long currentTime = startTime;
        while (currentTime <= endTime) { // keep a tab on job time slice
            // do some real work with the job
            currentTime = System.currentTimeMillis();
        }
        job.timeElapsed += endTime - startTime;
        String message = String.format("%s time elapsed so far %x", job.name, job.timeElapsed);
        Logging.log(message, this.getClass().getName(), LogLevel.DEBUG);
        return job.timeElapsed >= job.expectedTimeToComplete ? true : false;
    }

    public void finishJob(Job job) throws Exception{
        // update job status to finished. Delete the double linked list node containing the job from the Bucket
        Node nodeToRemove = jobNodeLookUp.get(job);
        Bucket b = nodeBucketLookUp.get(nodeToRemove);
        if (b.removeJob(nodeToRemove) == null) {
            String message = String.format("%s There was a problem updating job %s to finished status ", job.name);
            Logging.log(message, this.getClass().getName(), LogLevel.ERROR);
            throw new Exception (message);
        }
        job.status = JobStatus.FINISHED;
        // remove this job from all the jobs that has a dependency on it, since this job is completed and hence the
        // dependency is over.
        for (Job j : job.parents) {
            j.children.remove(job);
            String message = String.format("job %s removed from %s", job.name, j.name);
            Logging.log(message, this.getClass().getName(), LogLevel.DEBUG);
        }
        // add job to the list of scheduled jobs
        String message = String.format("%s job finished, adding to output", job.name);
        Logging.log(message, this.getClass().getName(), LogLevel.INFO);
        scheduled.add(job);
    }

    public void printScheduleRoutine() {
        for (Job j : scheduled) {
            System.out.println(j.name);
        }
    }

}
