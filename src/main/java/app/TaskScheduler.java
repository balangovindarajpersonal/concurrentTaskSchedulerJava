package app;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicLong;

public class TaskScheduler {

    final PriorityBlockingQueue<Task> queue = new PriorityBlockingQueue<>(10);
    final ExecutorService executorServiceWorkers = Executors.newFixedThreadPool(10);
    final ExecutorService executorServiceScheduler = Executors.newFixedThreadPool(1);
    private final AtomicLong completedTaskCount = new AtomicLong(0);
    private  List<String> executionOrder = new CopyOnWriteArrayList<>();
    private final ConcurrentHashMap<String, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

    void start() {
        Runnable schedulerTask = () -> {
            while (!Thread.interrupted()) {
                try {
                    Task task = queue.take();
                    executorServiceWorkers.submit(() -> {
                        System.out.println("Task Execution Started : "+ task.getId());
                        task.execute();
                        System.out.println("Task Execution Completed : "+ task.getId());
                        completedTaskCount.incrementAndGet();
                        executionOrder.add(task.getId());
                    });
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        };
        executorServiceScheduler.submit(schedulerTask);
    }

    boolean registerTask(Task task) {
        return queue.offer(task);
    }

    boolean registerTask(ScheduledTask task) {
        return queue.offer(task);
    }

    void stop() {
        executorServiceWorkers.shutdown();
        executorServiceScheduler.shutdown();
    }

    public String getCompletedTaskCount(){
        return String.valueOf(this.completedTaskCount);
    }

    public List<String> getExecutionOrder(){
        return new CopyOnWriteArrayList<>(this.executionOrder);
    }
}
