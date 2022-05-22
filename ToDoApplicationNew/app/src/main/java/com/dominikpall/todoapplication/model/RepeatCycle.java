package com.dominikpall.todoapplication.model;

/**
 * Class that represents the type for repeat cycle
 */
public enum RepeatCycle {
    NO_REPEAT,
    DAILY,
    WEEKLY,
    MONTHLY;

    /**
     * Method to get text to the enum RepeatCycle
     * @return Text representing the repeat cycle
     */
    public String getText() {
        switch (this) {
            case NO_REPEAT:
                return "No repeat";
            case DAILY:
                return "Daily";
            case WEEKLY:
                return "Weekly";
            case MONTHLY:
                return "Monthly";
            default:
                return "";
        }
    }
}
