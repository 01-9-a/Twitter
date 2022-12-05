package timedelayqueue;

import java.sql.Timestamp;
import java.util.*;

// TODO: write a description for this class
// TODO: complete all methods, irrespective of whether there is an explicit TODO or not
// TODO: write clear specs
// TODO: State the rep invariant and abstraction function
// TODO: what is the thread safety argument?
public class TimeDelayQueue {

    private final PriorityQueue<PubSubMessage> timeDelayQueue;
    private final int delay;
    private int count;
    private List<Timestamp> operations;

    /**
     * Create a new TimeDelayQueue
     *
     * @param delay the delay, in milliseconds, that the queue can tolerate, >= 0
     */
    public TimeDelayQueue(int delay) {
        timeDelayQueue = new PriorityQueue<>(new PubSubMessageComparator());
        this.delay = delay;
        count = 0;
        operations = new ArrayList<>();
    }

    // add a message to the TimeDelayQueue
    // if a message with the same id exists then
    // return false
    public boolean add(PubSubMessage msg) {
        for (PubSubMessage pubSubMessage : timeDelayQueue) {
            if (pubSubMessage.getId().equals(msg.getId())) {
                return false;
            }
        }
        timeDelayQueue.add(msg);
        count++;
        operations.add(msg.getTimestamp());
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    /**
     * Get the count of the total number of messages processed
     * by this TimeDelayQueue
     *
     * @return
     */
    public long getTotalMsgCount() {
        return count;
    }

    // return the next message and PubSubMessage.NO_MSG
    // if there is ni suitable message
    public PubSubMessage getNext() {
        if (!timeDelayQueue.isEmpty()) {
            Timestamp nowTime = new Timestamp(System.currentTimeMillis());
            while (timeDelayQueue.peek().isTransient() &&
                    nowTime.getTime()-timeDelayQueue.peek().getTimestamp().getTime()
                            >((TransientPubSubMessage) timeDelayQueue.peek()).getLifetime()) {
                timeDelayQueue.remove(timeDelayQueue.peek());
            }
            assert timeDelayQueue.peek() != null;
            if (nowTime.getTime()-timeDelayQueue.peek().getTimestamp().getTime()>delay) {
                operations.add(nowTime);
                return timeDelayQueue.poll();
            }
        }
        return PubSubMessage.NO_MSG;
    }

    // return the maximum number of operations
    // performed on this TimeDelayQueue over
    // any window of length timeWindow
    // the operations of interest are add and getNext
    public int getPeakLoad(int timeWindow) {
        return -1;
    }

    // a comparator to sort messages
    private class PubSubMessageComparator implements Comparator<PubSubMessage> {
        public int compare(PubSubMessage msg1, PubSubMessage msg2) {
            return msg1.getTimestamp().compareTo(msg2.getTimestamp());
        }
    }

}