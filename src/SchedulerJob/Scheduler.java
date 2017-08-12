package SchedulerJob;

import java.util.*;

/**
 * Created by amritachowdhury on 8/12/17.
 */
public class Scheduler {
    public HashMap<String, Job> jobNameObjectMapping;
    public HashMap<String, List<String>> childParentMapping;
    //tells which bucket has which priority jobs
    public  Map<Integer, Bucket> priorityBucketMapping;
    public List<Bucket> buckets;
    // hash maps for ease of finding the job from double linked list node of the bucket while removing
    Map<String, Node> jobNodeLookUp;
    // final list
    public List<String> scheduled;


    public Scheduler() {
        this.jobNameObjectMapping = new HashMap<>();
        this.childParentMapping = new HashMap<>();
        this.priorityBucketMapping = new HashMap<>();
        this.jobNodeLookUp = new HashMap<>();
        this.buckets = new ArrayList<>();
        this.scheduled = new ArrayList<>();
    }

    public boolean run() {
        try {
            String message = "Start the job scheduler";
            Logging.log(message, this.getClass().getName(), LogLevel.INFO);
            populateBuckets();
            // sorting the buckets so that bucket with the highest priority range comes first
            Collections.sort(buckets);
            pickBucketToProcess();
            message = "Scheduler pre process completed";
            Logging.log(message, this.getClass().getName(), LogLevel.INFO);
            return true;
        } catch (Exception e) {
            Logging.log(e.getMessage(), this.getClass().getName(), LogLevel.ERROR);
            return false;
        }
    }

    public void populateBuckets() throws Exception {
        for (Map.Entry<String, Job> entry : jobNameObjectMapping.entrySet()) {
            Job job = entry.getValue();
            Bucket bucket = getBucket(job.priority);
            Node n = bucket.addJob(job);
            jobNodeLookUp.put(job.name, n);
        }
    }

    public Bucket getBucket(int priority) {
        // Based on the job priority select the bucket
        Bucket b = priorityBucketMapping.get(priority);
        if (b == null) {
            b = new Bucket(priority);
            priorityBucketMapping.put(priority, b);
            buckets.add(b);
        }
        return b;
    }

    private Bucket nodeBucketLookUp(Node node) {
        Job job = node.data;
        int priority = job.priority;
        return priorityBucketMapping.get(priority);
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
            return false; // jobs in this bucket is all scheduled,go back fetch another bucket
        }
        boolean timeOver = false;
        long currentTime = System.currentTimeMillis();
        long endTime = currentTime + Config.BUCKET_TIME_SLICE;
        while (!nodes.isEmpty() && !timeOver) {
            // start from the head node which is the first job in the bucket
            Node currentNode = nodes.getNext(null);
            while (currentNode != null) {
                if (currentTime >= endTime) { // bucket time slice is over, get the next bucket
                    timeOver = true;
                    break;
                }
                // get the jobs that are not finished but all the dependencies are finished
                if (currentNode.data.status == JobStatus.TO_BE_STARTED && currentNode.data.children.isEmpty()) {
                    currentNode.data.status = JobStatus.IN_PROGRESS;
                    boolean isJobFinished = processJob(currentNode.data);
                    if (isJobFinished) {
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
        Node nodeToRemove = jobNodeLookUp.get(job.name);
        Bucket b = nodeBucketLookUp(nodeToRemove);
        if (b.removeJob(nodeToRemove) == null) {
            String message = String.format("%s There was a problem updating job %s to finished status ", job.name);
            Logging.log(message, this.getClass().getName(), LogLevel.ERROR);
            throw new Exception (message);
        }
        job.status = JobStatus.FINISHED;
        // remove this job from all the jobs that has a dependency on it, since this job is completed and hence the
        // dependency is over.
        List<String> parentNames = childParentMapping.get(job.name);
        if (parentNames != null) {
            for (String parentName : parentNames) {
                Job parent = jobNameObjectMapping.get(parentName);
                parent.children.remove(job.name);
                String message = String.format("job %s removed from %s", job.name, parent.name);
                Logging.log(message, this.getClass().getName(), LogLevel.DEBUG);
            }
        }

        childParentMapping.remove(job.name);
        jobNameObjectMapping.remove(job.name);

        // add job to the list of scheduled jobs
        String message = String.format("%s job finished, adding to output", job.name);
        Logging.log(message, this.getClass().getName(), LogLevel.INFO);
        scheduled.add(job.name);
    }

    public void printScheduleRoutine() {
        for (String jobName : scheduled ) {
            System.out.println(jobName);
        }
    }
}
