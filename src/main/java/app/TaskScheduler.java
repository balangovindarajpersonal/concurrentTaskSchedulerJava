package app;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;

public class TaskScheduler {

    final PriorityBlockingQueue<Task> queue = new PriorityBlockingQueue<>(20);
    final ExecutorService executorServiceWorkers = Executors.newFixedThreadPool(10);
    final ExecutorService executorServiceScheduler = Executors.newFixedThreadPool(1);

    void start() {
        Runnable schedulerTask = () -> {
            while (true) {
                try {
                    Task task = queue.take();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Preserve interrupt status
                    break; // Exit the loop to handle the interrupt properly
                }
            }
        };
        executorServiceScheduler.submit(schedulerTask);
    }

    boolean registerTask(Task task) {
        return queue.offer(task);
    }

    void stop() {
        executorServiceWorkers.shutdown();
        executorServiceScheduler.shutdown();
    }
}
