package SchedulerJob;

/**
 * Created by amritachowdhury on 8/5/17.
 */
public class Node {
    public Job data;
    public Node next;
    public Node prev;

    public Node(Job data, Node next, Node prev) {
        this.data = data;
        this.next = next;
        this.prev = prev;
    }
}
