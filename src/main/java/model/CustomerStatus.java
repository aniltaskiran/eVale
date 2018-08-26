package model;

public enum CustomerStatus {
    PARKED(0), WAITING(1), COMPLETED(2);
    private final int value;

    private CustomerStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}