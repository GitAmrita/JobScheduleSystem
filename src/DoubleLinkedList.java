/**
 * Created by amritachowdhury on 8/5/17.
 */
public class DoubleLinkedList {

    private Node head;
    private Node tail;
    public int size;

    public DoubleLinkedList() {
        size = 0;
    }

    public int size() { return size; }
    public boolean isEmpty() { return size == 0; }
    public void addFirst(Job data) {
        Node tmp = new Node(data, head, null);
        if(head != null ) {head.prev = tmp;}
        head = tmp;
        if(tail == null) { tail = tmp;}
        size++;
    }
    public Node addLast(Job data) {

        Node tmp = new Node(data, null, tail);
        if(tail != null) {tail.next = tmp;}
        tail = tmp;
        if(head == null) { head = tmp;}
        size++;
        return tmp;
    }
    public void iterateForward(){
        Node tmp = head;
        while(tmp != null){
            System.out.println(tmp.data);
            tmp = tmp.next;
        }
    }
    public Node iterateTill(int idx ){
        int i = 1;
        Node tmp = head;
        while(i < idx){
            tmp = tmp.next;
            i++;
        }
        return tmp;
    }
    public void iterateBackward(){
        Node tmp = tail;
        while(tmp != null){
            System.out.println(tmp.data);
            tmp = tmp.prev;
        }
    }
    public Job removeFirst() {
        if (size == 0) return null;
        Node tmp = head;
        head = head.next;
        if (head != null) {
            head.prev = null;
        }
        size--;
        return tmp.data;
    }

    public Node removeNode(Node node) {
        if (node == head ) {
            removeFirst();
        } else if (node == tail) {
            removeLast();
        } else {
            node.prev = node.next;
            size--;
        }
        return node;
    }

    public Job removeLast() {
        if (size == 0) return null;
        Node tmp = tail;
        tail = tail.prev;
        if (tail != null) {
            tail.next = null;
        }
        size--;
        return tmp.data;
    }

}
