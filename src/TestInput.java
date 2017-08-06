import java.util.LinkedList;
import java.util.List;

/**
 * Created by amritachowdhury on 8/5/17.
 */
public class TestInput {
    public int expectedCompletion; public int deadline; List<TestInput> dependencies; public boolean visited;

    public TestInput(int expectedCompletion, int deadline) {
        this.expectedCompletion = expectedCompletion;
        this.deadline = deadline;
        this.dependencies = new LinkedList<>();
        this.visited = false;
    }

    public void setDependencies(List<TestInput> dependencies) {
        this.dependencies = dependencies;
    }
}
