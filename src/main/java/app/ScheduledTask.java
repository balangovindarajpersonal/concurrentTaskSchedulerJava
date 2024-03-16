package app;

import java.util.concurrent.TimeUnit;

public abstract class ScheduledTask extends Task {
    private final long period;
    private final TimeUnit timeUnit;

    public ScheduledTask(Priority priority, long period1, TimeUnit timeUnit1) {
        super(priority);
        this.period = period1;
        this.timeUnit = timeUnit1;
    }

    public long getPeriod() {
        return this.period;
    }

    public TimeUnit getTimeUnit() {
        return this.timeUnit;
    }

}

