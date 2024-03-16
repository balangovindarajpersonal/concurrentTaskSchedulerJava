package app;


public enum Priority {

    LOW(1),    // Custom value 1
    MEDIUM(2), // Custom value 2
    HIGH(3);   // Custom value 3

    private final int value;

    Priority(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    // Custom comparator method
    public int compare(Priority other) {
        return Integer.compare(this.value, other.value);
    }
}
