import java.util.LinkedList;
import java.util.List;

/**
 * Created by amritachowdhury on 8/5/17.
 */
public class TestInput {
    public int expectedCompletion; public int deadline; List<TestInput> dependencies; public String name;

    public TestInput(String name, int expectedCompletion, int deadline) {
        this.name = name;
        this.expectedCompletion = expectedCompletion;
        this.deadline = deadline;
        this.dependencies = new LinkedList<>();
    }

    public void setDependencies(List<TestInput> dependencies) {
        this.dependencies = dependencies;
    }
}
