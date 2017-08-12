package SchedulerJob;

/**
 * Created by amritachowdhury on 8/6/17.
 */
public enum LogLevel {
    DEBUG(0), INFO(1), ERROR(2);

    private final int value;

     LogLevel(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public  String getName() {
        switch (this) {
            case DEBUG:
                return "Debug";
            case ERROR:
                return "Error";
            case INFO:
                return "Info";
            default:
                return "Unknown";
        }
    }
}
