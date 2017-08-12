import SchedulerJob.Input;
import SchedulerJob.StartScheduler;
import junit.framework.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amritachowdhury on 8/12/17.
 */
public class StartSchedulerTests {

    // job dependency with no time out // T5 -> T6 -> T4 -> T3 -> T1
    public static List<Input> inputList_with_dependency_no_timeout() {
        Input t6 = new Input("T6",2, 46);
        Input t5 = new Input("T5",1, 45);
        Input t4 = new Input("T4",4, 44);
        Input t3 = new Input("T3",3, 43);
        Input t2 = new Input("T2",2, 42);
        Input t1 = new Input("T1",1, 41);
        List<String> t4Dependencies = new ArrayList<>(); t4Dependencies.add("T5"); t4Dependencies.add("T6");
        t4.setDependencies(t4Dependencies);
        List<String> t3Dependencies = new ArrayList<>(); t3Dependencies.add("T4");
        t3.setDependencies(t3Dependencies);
        List<String> t2Dependencies = new ArrayList<>(); t2Dependencies.add("T4");
        t2.setDependencies(t2Dependencies);
        List<String> t1Dependencies = new ArrayList<>(); t1Dependencies.add("T4"); t1Dependencies.add("T5");
        t1.setDependencies(t1Dependencies);
        List<Input> input = new ArrayList<>(); input.add(t6); input.add(t1);  input.add(t4); input.add(t2);  input.add(t5);
        input.add(t3);
        return input;
    }

    // job dependency with no time out T3 -> T1 -> T2
    public static List<Input> inputList_with_dependency_no_timeout_1() {
        Input t3 = new Input("T3",3, 5);
        Input t2 = new Input("T2",2, 8);
        Input t1 = new Input("T1",1, 9);
        List<String> t2Dependencies = new ArrayList<>(); t2Dependencies.add("T3");
        t2.setDependencies(t2Dependencies);
        List<String> t1Dependencies = new ArrayList<>(); t1Dependencies.add("T3");
        t1.setDependencies(t1Dependencies);
        List<Input> input = new ArrayList<>();  input.add(t1); input.add(t2); input.add(t3);
        return input;
    }

    // no job dependency no time out // T1 -> T2 -> T3
    public List<Input> inputList_no_dependency_no_timeout() {
        Input t3 = new Input("T3",3, 43);
        Input t2 = new Input("T2",2, 42);
        Input t1 = new Input("T1",1, 41);
        List<Input> input = new ArrayList<>(); input.add(t2);  input.add(t3); input.add(t1);
        return input;
    }

    // no job dependency with time out
    public static List<Input> inputList_no_dependency_with_timeout() {
        Input t3 = new Input("T3",3, 2);
        Input t2 = new Input("T2",2, 1);
        Input t1 = new Input("T1",1, 41);
        List<Input> input = new ArrayList<>(); input.add(t1);  input.add(t2); input.add(t3);
        return input;
    }

    // job dependency with time out
    public static List<Input> inputList_with_dependency_with_timeout() {
        Input t6 = new Input("T6",2, 5);
        Input t5 = new Input("T5",1, 3);
        Input t4 = new Input("T4",4, 6);
        Input t3 = new Input("T3",3, 5);
        Input t2 = new Input("T2",2, 8);
        Input t1 = new Input("T1",1, 9);
        List<String> t4Dependencies = new ArrayList<>(); t4Dependencies.add("T5"); t4Dependencies.add("T6");
        t4.setDependencies(t4Dependencies);
        List<String> t3Dependencies = new ArrayList<>(); t3Dependencies.add("T4");
        t3.setDependencies(t3Dependencies);
        List<String> t2Dependencies = new ArrayList<>(); t2Dependencies.add("T4");
        t2.setDependencies(t2Dependencies);
        List<String> t1Dependencies = new ArrayList<>(); t1Dependencies.add("T4"); t1Dependencies.add("T5");
        t1.setDependencies(t1Dependencies);
        List<Input> input = new ArrayList<>(); input.add(t5);  input.add(t6); input.add(t1); input.add(t2);  input.add(t4);
        input.add(t3);
        return input;
    }

    @Test
    public void start_inputList_with_dependency_no_timeout_test() throws Exception {
        StartScheduler startScheduler = new StartScheduler();
        startScheduler.start(inputList_with_dependency_no_timeout());
        Assert.assertTrue(startScheduler.scheduler.scheduled.size() == 6);
        Assert.assertTrue(startScheduler.scheduler.scheduled.get(0).equals("T5"));
        Assert.assertTrue(startScheduler.scheduler.scheduled.get(1).equals("T6"));
        Assert.assertTrue(startScheduler.scheduler.scheduled.get(2).equals("T4"));
        Assert.assertTrue(startScheduler.scheduler.scheduled.get(3).equals("T1"));
        Assert.assertTrue(startScheduler.scheduler.scheduled.get(4).equals("T2"));
        Assert.assertTrue(startScheduler.scheduler.scheduled.get(5).equals("T3"));
    }

    @Test
    public void start_inputList_with_dependency_no_timeout_1_test() throws Exception {
        StartScheduler startScheduler = new StartScheduler();
        startScheduler.start(inputList_with_dependency_no_timeout_1());
        Assert.assertTrue(startScheduler.scheduler.scheduled.size() == 3);
        Assert.assertTrue(startScheduler.scheduler.scheduled.get(0).equals("T3"));
        Assert.assertTrue(startScheduler.scheduler.scheduled.get(1).equals("T1"));
        Assert.assertTrue(startScheduler.scheduler.scheduled.get(2).equals("T2"));
    }

    @Test
    public void start_inputList_no_dependency_no_timeout_test() throws Exception {
        StartScheduler startScheduler = new StartScheduler();
        startScheduler.start(inputList_no_dependency_no_timeout());
        Assert.assertTrue(startScheduler.scheduler.scheduled.size() == 3);
        Assert.assertTrue(startScheduler.scheduler.scheduled.get(0).equals("T1"));
        Assert.assertTrue(startScheduler.scheduler.scheduled.get(1).equals("T2"));
        Assert.assertTrue(startScheduler.scheduler.scheduled.get(2).equals("T3"));
    }

    @Test(expected = Exception.class)
    public void start_inputList_no_dependency_with_timeout_test() throws Exception {
        StartScheduler startScheduler = new StartScheduler();
        startScheduler.start(inputList_no_dependency_with_timeout());
    }

    @Test(expected = Exception.class)
    public void start_inputList_with_dependency_with_timeout_test() throws Exception {
        StartScheduler startScheduler = new StartScheduler();
        startScheduler.start(inputList_with_dependency_with_timeout());
    }
}
