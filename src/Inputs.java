import java.util.ArrayList;
import java.util.List;

/**
 * Created by amritachowdhury on 8/6/17.
 */
public class Inputs {
    // job dependency with no time out
    public static List<TestInput> inputTest_1() {
        TestInput t6 = new TestInput("T6",2, 46);
        TestInput t5 = new TestInput("T5",1, 45);
        TestInput t4 = new TestInput("T4",4, 44);
        TestInput t3 = new TestInput("T3",3, 43);
        TestInput t2 = new TestInput("T2",2, 42);
        TestInput t1 = new TestInput("T1",1, 41);
        List<TestInput> t4Dependencies = new ArrayList<>(); t4Dependencies.add(t5); t4Dependencies.add(t6);
        t4.setDependencies(t4Dependencies);
        List<TestInput> t3Dependencies = new ArrayList<>(); t3Dependencies.add(t4);
        t3.setDependencies(t3Dependencies);
        List<TestInput> t2Dependencies = new ArrayList<>(); t2Dependencies.add(t4);
        t2.setDependencies(t2Dependencies);
        List<TestInput> t1Dependencies = new ArrayList<>(); t1Dependencies.add(t4); t1Dependencies.add(t5);
        t1.setDependencies(t1Dependencies);
        List<TestInput> input = new ArrayList<>(); input.add(t5);  input.add(t6); input.add(t1); input.add(t2);  input.add(t4);
        input.add(t3);
        return input;
    }

    // no job dependency no time out
    public static List<TestInput> inputTest_2() {
        TestInput t3 = new TestInput("T3",3, 43);
        TestInput t2 = new TestInput("T2",2, 42);
        TestInput t1 = new TestInput("T1",1, 41);
        List<TestInput> input = new ArrayList<>(); input.add(t1);  input.add(t2); input.add(t3);
        return input;
    }

    // no job dependency with time out
    public static List<TestInput> inputTest_3() {
        TestInput t3 = new TestInput("T3",3, 2);
        TestInput t2 = new TestInput("T2",2, 1);
        TestInput t1 = new TestInput("T1",1, 41);
        List<TestInput> input = new ArrayList<>(); input.add(t1);  input.add(t2); input.add(t3);
        return input;
    }

    // job dependency with time out
    public static List<TestInput> inputTest_4() {
        TestInput t6 = new TestInput("T6",2, 5);
        TestInput t5 = new TestInput("T5",1, 3);
        TestInput t4 = new TestInput("T4",4, 6);
        TestInput t3 = new TestInput("T3",3, 5);
        TestInput t2 = new TestInput("T2",2, 8);
        TestInput t1 = new TestInput("T1",1, 9);
        List<TestInput> t4Dependencies = new ArrayList<>(); t4Dependencies.add(t5); t4Dependencies.add(t6);
        t4.setDependencies(t4Dependencies);
        List<TestInput> t3Dependencies = new ArrayList<>(); t3Dependencies.add(t4);
        t3.setDependencies(t3Dependencies);
        List<TestInput> t2Dependencies = new ArrayList<>(); t2Dependencies.add(t4);
        t2.setDependencies(t2Dependencies);
        List<TestInput> t1Dependencies = new ArrayList<>(); t1Dependencies.add(t4); t1Dependencies.add(t5);
        t1.setDependencies(t1Dependencies);
        List<TestInput> input = new ArrayList<>(); input.add(t5);  input.add(t6); input.add(t1); input.add(t2);  input.add(t4);
        input.add(t3);
        return input;
    }

    // job dependency with time out
    public static List<TestInput> inputTest_5() {
        TestInput t3 = new TestInput("T3",3, 5);
        TestInput t2 = new TestInput("T2",2, 8);
        TestInput t1 = new TestInput("T1",1, 9);
        List<TestInput> t2Dependencies = new ArrayList<>(); t2Dependencies.add(t3);
        t2.setDependencies(t2Dependencies);
        List<TestInput> t1Dependencies = new ArrayList<>(); t1Dependencies.add(t3);
        t1.setDependencies(t1Dependencies);
        List<TestInput> input = new ArrayList<>();  input.add(t1); input.add(t2); input.add(t3);
        return input;
    }
}
