import java.util.List;

/**
 * Created by amritachowdhury on 8/5/17.
 */
public class main {

    public static void main(String a[]) throws Exception{
        long startTime = System.currentTimeMillis();
        jobCreationTest();
        long endTime = System.currentTimeMillis();
        long totalTimeTakenToScheduleJobs = (endTime - startTime);
        String message = String.format("total time elapsed in milliseconds %s", String.valueOf(
                totalTimeTakenToScheduleJobs));
        Logging.log(message, "main", LogLevel.INFO);
    }

    public static void jobCreationTest() throws Exception {
        Scheduler scheduler = new Scheduler();
        // pre processing
        PreProcess(scheduler);
        if (scheduler.run()) {
            scheduler.printScheduleRoutine();
        }
    }

    private static void PreProcess(Scheduler scheduler) throws Exception {
            List<TestInput> input = Inputs.inputTest_1();
            // create a job id for all the jobs and insert job in the global all jobs
            for (TestInput i : input) {
                Job j = new Job(i, scheduler);
                scheduler.allJobs.add(j);
            }
            // set parent children relationships in the system
            for (Job job : scheduler.allJobs) {
                job.setParentsAndChildren();
            }
            // set priority of jobs
            for (Job job : scheduler.allJobs) {
                job.setPriority();
            }
            ValidRequest request = new ValidRequest(scheduler.allJobs);
            if (!request.canScheduleBeCompleted()) {
                throw new Exception("The scheduler cannot be completed within the given deadline." +
                        " Check the logs for more information.");
            }
        }
}
