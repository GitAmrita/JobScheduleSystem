package SchedulerJob;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by amritachowdhury on 8/12/17.
 */
public class Input {
    public int expectedCompletion; public int deadline; List<String> dependencies; public String name;

    public Input(String name, int expectedCompletion, int deadline) {
        this.name = name;
        this.expectedCompletion = expectedCompletion;
        this.deadline = deadline;
        this.dependencies = new LinkedList<>();
    }

    public void setDependencies(List<String> dependencies) {
        this.dependencies = dependencies;
    }
}
