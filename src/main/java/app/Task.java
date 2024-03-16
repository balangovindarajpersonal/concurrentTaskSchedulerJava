package app;


import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public abstract class Task implements Comparable<Task> {
    private final String id;
    private final Priority priority;
    abstract void execute();

    public Task(Priority priority) {
        this.id = String.valueOf(UUID.randomUUID());
        this.priority = priority;
    }

    public String getId(){
        return this.id;
    }

    public Priority getPriority(){
        return this.priority;
    }

    @Override
    public int compareTo(Task otherTask) {
        return Integer.compare(this.getPriority().getValue(), otherTask.getPriority().getValue()) ;
    }

}
