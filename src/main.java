import java.util.ArrayList;
import java.util.List;

/**
 * Created by amritachowdhury on 8/5/17.
 */
public class main {
    public static void main(String a[]){
        jobCreationTest();
    }

    public static void jobCreationTest() {
        try {
            List<TestInput> input = inputTest();
            for (TestInput i : input) {
                Job j = new Job(i);
                Helper.allJobs.add(j);
            }
            for (Job job : Helper.allJobs) {
                job.setParentsAndChildren();
            }
            for (Job job : Helper.allJobs) {
                job.setPriority();
            }
            Scheduler s = new Scheduler();
            s.run();
        } catch(Exception e) {}
    }

    public static List<TestInput> inputTest() {
        TestInput t5 = new TestInput(5, 5);
        TestInput t4 = new TestInput(4, 4);
        TestInput t3 = new TestInput(3, 3);
        TestInput t2 = new TestInput(2, 2);
        TestInput t1 = new TestInput(1, 1);
        List<TestInput> t4Dependencies = new ArrayList<>(); t4Dependencies.add(t5);
        t4.setDependencies(t4Dependencies);
        List<TestInput> t3Dependencies = new ArrayList<>(); t3Dependencies.add(t4);
        t3.setDependencies(t3Dependencies);
        List<TestInput> t2Dependencies = new ArrayList<>(); t2Dependencies.add(t4);
        t2.setDependencies(t2Dependencies);
        List<TestInput> t1Dependencies = new ArrayList<>(); t1Dependencies.add(t4); t1Dependencies.add(t5);
        t1.setDependencies(t1Dependencies);
        List<TestInput> input = new ArrayList<>(); input.add(t1); input.add(t2); input.add(t3); input.add(t4); input.add(t5);
        return input;
    }
}
