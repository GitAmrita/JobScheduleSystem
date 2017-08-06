import java.util.Comparator;

/**
 * Created by amritachowdhury on 8/5/17.
 */
public class Bucket implements Comparable<Bucket>{
   int id; DoubleLinkedList jobs; int minPriority; int maxPriority;


    public Bucket(int id, int minPriority, int maxPriority) {
        this.id = id;
        this.minPriority = minPriority;
        this.maxPriority = maxPriority;
        this.jobs = new DoubleLinkedList();
    }

    public Node addJob(Job job) {
        Node n = jobs.addLast(job);
        return n;
    }

    public Node removeJob(Node n) {
       return jobs.removeNode(n);
    }

    @Override
    public int compareTo(Bucket o) {
        if (this.minPriority < o.minPriority) {
            return 1;
        } else if (this.minPriority > o.minPriority) {
            return -1;
        }
        return 0;
    }
}
