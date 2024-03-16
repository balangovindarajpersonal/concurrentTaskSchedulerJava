package app;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class TaskSchedulerTest {

    TaskScheduler taskScheduler;

    @BeforeEach
    void setUp() {
        taskScheduler = new TaskScheduler();
        taskScheduler.start();
    }

    @AfterEach
    void tearDown() throws InterruptedException {
        taskScheduler.stop();
    }

    @Test
    void testTaskRegistrationAndExecution() throws InterruptedException {
        Task task = new Task(Priority.MEDIUM){
            public void execute() {
                System.out.println(Thread.currentThread() + " : TASK : "+ this.getId());
            }
        };

        assertTrue(taskScheduler.registerTask(task));
        Thread.sleep(500);
        assertEquals("1", taskScheduler.getCompletedTaskCount());
    }

    @Test
    void testPriorityScheduling() throws InterruptedException {
        // Register tasks with different priorities
        Task lowPriorityTask = new Task(Priority.LOW){
            public void execute() {
                System.out.println(Thread.currentThread() + " : TASK : ");
            }
        };
        Task highPriorityTask = new Task(Priority.HIGH){
            public void execute() {
                System.out.println(Thread.currentThread() + " : TASK : ");
            }
        };
        taskScheduler.registerTask(lowPriorityTask);
        taskScheduler.registerTask(highPriorityTask);
        Thread.sleep(500);

        // Verify the first task in the execution order is the high priority task
        assertEquals(highPriorityTask.getId(), taskScheduler.getExecutionOrder().get(0));

        // Optionally, verify the second task is the low priority task
        assertEquals(lowPriorityTask.getId(), taskScheduler.getExecutionOrder().get(1));

    }

    @Test
    void testScheduledTaskRegistration() {
        ScheduledTask scheduledTask = new ScheduledTask( Priority.MEDIUM, 1, TimeUnit.SECONDS) {
            @Override
            void execute() {
                System.out.println(Thread.currentThread() + " : TASK : "+ this.getId());
            }
        };
        assertTrue(taskScheduler.registerTask(scheduledTask));
    }


    void testTaskScheduler(){

        Thread thread1 = new Thread(() -> {

            for(int i=0; i< 10; i++){
                int finalI = i;
                taskScheduler.registerTask(new Task(Priority.MEDIUM) {
                    @Override
                    public void execute() {
                        if(finalI %2 ==0){
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.println(Thread.currentThread() + " : TASK : "+ finalI);
                    }
                });
            }
        });

        Thread thread2 = new Thread(() -> {

            for(int i=0; i< 10; i++){
                int finalI = i;
                taskScheduler.registerTask(new Task(Priority.MEDIUM) {
                    @Override
                    public void execute() {
                        if(finalI %2 ==1){
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.println(Thread.currentThread() + " : TASK : "+ finalI);
                    }
                });
            }
        });
        thread1.start();
        thread2.start();

        taskScheduler.stop();

    }
}