package app;

import java.util.concurrent.TimeUnit;

public interface ScheduledTask extends Task{
    long getPeriod();
    TimeUnit getTimeUnit();
}

