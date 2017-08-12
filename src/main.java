import SchedulerJob.*;
import SchedulerJob.LogLevel;
import SchedulerJob.Logging;
import java.util.List;


/**
 * Created by amritachowdhury on 8/5/17.
 */
public class main {

    public static void main(String a[]) throws Exception{
        long startTime = System.currentTimeMillis();
        List<Input> inputs = TestInputs.inputTest_1();
        StartScheduler startScheduler = new StartScheduler();
        startScheduler.start(inputs);
        long endTime = System.currentTimeMillis();
        long totalTimeTakenToScheduleJobs = (endTime - startTime);
        String message = String.format("total time elapsed in milliseconds %s", String.valueOf(
                totalTimeTakenToScheduleJobs));
        Logging.log(message, "main", LogLevel.INFO);
    }
}
