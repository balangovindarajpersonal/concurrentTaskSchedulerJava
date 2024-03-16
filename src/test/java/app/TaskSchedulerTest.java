package app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskSchedulerTest {


    @Test
    void testTaskScheduler(){

        TaskScheduler taskScheduler = new TaskScheduler();

        taskScheduler.start();

        Thread thread1 = new Thread(() -> {

            for(int i=0; i< 10; i++){
                int finalI = i;
                taskScheduler.registerTask(new Task() {
                    @Override
                    public int compareTo(Object o) {
                        Task otherTask = (Task) o;
                        return Integer.compare(this.getPriority().getValue(), otherTask.getPriority().getValue()) ;
                    }

                    @Override
                    public Priority getPriority() {
                        return Priority.MEDIUM;
                    }

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
                taskScheduler.registerTask(new Task() {
                    @Override
                    public int compareTo(Object o) {
                        Task otherTask = (Task) o;
                        return Integer.compare(this.getPriority().getValue(), otherTask.getPriority().getValue()) ;
                    }

                    @Override
                    public Priority getPriority() {
                        return Priority.MEDIUM;
                    }

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