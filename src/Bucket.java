
/**
 * Created by amritachowdhury on 8/5/17.
 */
public class Bucket implements Comparable<Bucket>{
   int id; DoubleLinkedList jobs;
    int priority;


    public Bucket(int id, int priority) {
        this.id = id;
        this.priority = priority;
        this.jobs = new DoubleLinkedList();
    }

    public Node addJob(Job job) {
        Node n = jobs.addLast(job);
        return n;
    }

    public Node removeJob(Node n) {
       return jobs.removeNode(n);
    }

    // sort bucket in reverse order of priority
    @Override
    public int compareTo(Bucket o) {
        if (this.priority < o.priority) {
            return 1;
        } else if (this.priority > o.priority) {
            return -1;
        }
        return 0;
    }
}
