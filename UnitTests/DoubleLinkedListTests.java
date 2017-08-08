import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by amritachowdhury on 8/7/17.
 */
public class DoubleLinkedListTests {

    public void setUp() {

    }
    @Test
    public void addLast_test() {
        DoubleLinkedList testObj = new DoubleLinkedList();
        testObj.addLast(new Job(1, 1, "j1"));
        Assert.assertFalse(testObj.isEmpty());
        Assert.assertEquals(testObj.size ,1);
    }

    @Test
    public void getNext_test() {
        DoubleLinkedList testObj = new DoubleLinkedList();
        Job j1 = new Job(1, 1, "j1"); Job j2 = new Job(2, 2, "j2");
        Job j3 = new Job(3, 3, "j3"); Job j4 = new Job(4, 4, "j4");
        Node n1 = testObj.addLast(j1);
        Node n2 = testObj.addLast(j2);
        Node n3 = testObj.addLast(j3);
        Node n4 = testObj.addLast(j4);
        Node n = testObj.getNext(n3);
        Assert.assertEquals(n ,n4);
        n = testObj.getNext(null);
        Assert.assertEquals(n ,n1);
        n = testObj.getNext(n4);
        Assert.assertNull(n);
    }

    @Test
    public void removeFirst_test() {
        DoubleLinkedList testObj = new DoubleLinkedList();
        Job j1 = new Job(1, 1, "j1"); Job j2 = new Job(2, 2, "j2");
        Job j3 = new Job(3, 3, "j3"); Job j4 = new Job(4, 4, "j4");
        Node n1 = testObj.addLast(j1);
        Node n2 = testObj.addLast(j2);
        Node n3 = testObj.addLast(j3);
        Node n4 = testObj.addLast(j4);
        Assert.assertEquals(testObj.size(), 4);
        Job job = testObj.removeFirst();
        Assert.assertEquals(testObj.size(), 3);
        Assert.assertEquals(job.jobId, 1);
    }

    @Test
    public void removeLast_test() {
        DoubleLinkedList testObj = new DoubleLinkedList();
        Job j1 = new Job(1, 1, "j1"); Job j2 = new Job(2, 2, "j2");
        Job j3 = new Job(3, 3, "j3"); Job j4 = new Job(4, 4, "j4");
        Node n1 = testObj.addLast(j1);
        Node n2 = testObj.addLast(j2);
        Node n3 = testObj.addLast(j3);
        Node n4 = testObj.addLast(j4);
        Assert.assertEquals(testObj.size(), 4);
        Job job = testObj.removeLast();
        Assert.assertEquals(testObj.size(), 3);
        Assert.assertEquals(job.jobId, 4);
    }

    @Test
    public void removeNode_test() {
        DoubleLinkedList testObj = new DoubleLinkedList();
        Job j1 = new Job(1, 1, "j1"); Job j2 = new Job(2, 2, "j2");
        Job j3 = new Job(3, 3, "j3"); Job j4 = new Job(4, 4, "j4");
        Node n1 = testObj.addLast(j1);
        Node n2 = testObj.addLast(j2);
        Node n3 = testObj.addLast(j3);
        Node n4 = testObj.addLast(j4);
        Assert.assertEquals(testObj.size(), 4);
        Node node = testObj.removeNode(n3);
        Assert.assertEquals(testObj.size(), 3);
        Assert.assertEquals(node, n3);
    }
}
