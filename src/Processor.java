import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by amritachowdhury on 8/6/17.
 */
public class Processor {
    private static int processorId = 0;
    int id;


    public Processor() {
        try {
            this.id = getProcessorId();
        } catch(Exception ex) {

        }

    }

    private int getProcessorId() throws Exception{
        if (processorId == Config.NO_OF_PROCESSORS) {
            throw new Exception ("no more processors available");
        }
        int id = processorId;
        processorId++;
        return id;
    }
}
