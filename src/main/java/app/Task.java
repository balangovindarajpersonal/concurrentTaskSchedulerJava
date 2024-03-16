package app;


public interface Task extends Comparable {

    Priority getPriority();
    void execute();

}
