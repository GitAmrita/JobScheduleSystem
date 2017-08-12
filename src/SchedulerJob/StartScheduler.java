package SchedulerJob;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by amritachowdhury on 8/12/17.
 */
public class StartScheduler {
    public Scheduler scheduler = new Scheduler();

    public void start(List<Input> inputs) throws Exception {
        createJobs(inputs);
        setJobPriority();
        if (!canJobsBeCompletedInTime()) {
            throw new Exception("The scheduler cannot be completed within the given deadline." +
                    " Check the logs for more information.");
        }
        if (scheduler.run()) {
            scheduler.printScheduleRoutine();
        } else {
            System.out.println("There is some error in scheduling. Check logs for more info.");
        }
    }

    private void createJobs(List<Input> inputs) throws Exception {
        for (Input input : inputs) {
            Job job = new Job(input);
            scheduler.jobNameObjectMapping.put(job.name, job);
            childToParentMapping(job);
        }
    }

    private void childToParentMapping(Job job) {
        for (String childName : job.children) {
            List<String> parentName = scheduler.childParentMapping.get(childName);
            if (parentName == null) {
                parentName = new ArrayList<>();
            }
            parentName.add(job.name);
            scheduler.childParentMapping.put(childName, parentName);
        }
    }

    private void setJobPriority() {
        for (Map.Entry<String, Job> entry : scheduler.jobNameObjectMapping.entrySet()) {
            Job job = entry.getValue();
            job.setPriority(scheduler);
        }
    }

    private boolean canJobsBeCompletedInTime() {
        List<SchedulerJob.Job> jobs = new ArrayList<>(scheduler.jobNameObjectMapping.size());
        for (Map.Entry<String, Job> entry  : scheduler.jobNameObjectMapping.entrySet()) {
            jobs.add(entry.getValue());
        }
        RequestValidity request = new RequestValidity(jobs);
        return request.canScheduleBeCompleted();
    }
}
