import java.util.List;

/**
 * Created by amritachowdhury on 8/5/17.
 */
public class main {

    public static void main(String a[]){
        long startTime = System.currentTimeMillis();
        jobCreationTest();
        long endTime = System.currentTimeMillis();
        long totalTimeTakenToScheduleJobs = (endTime - startTime);
        String message = String.format("total time elapsed in milliseconds %x",totalTimeTakenToScheduleJobs);
        Logging.log(message, "main", LogLevel.INFO);
    }

    public static void jobCreationTest() {
        Scheduler scheduler = new Scheduler();
        // pre processing
        if (PreProcess(scheduler)) {
            if (scheduler.run()) {
                scheduler.printScheduleRoutine();
            }
        }
    }

    private static boolean PreProcess(Scheduler scheduler) {
        try {
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
        } catch (Exception e) {
            Logging.log(e.getMessage(), "main", LogLevel.ERROR);
            return false;
        }
        return true;
    }
}
