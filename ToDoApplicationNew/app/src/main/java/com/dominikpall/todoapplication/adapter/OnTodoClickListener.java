package com.dominikpall.todoapplication.adapter;


import com.dominikpall.todoapplication.data.task.Task;

/**
 * Interface for the click listener on todo tasks
 */
public interface OnTodoClickListener {
    /**
     * An interface method what should happen after clicking on todo
     * @param task The task that's supposed to be modified
     */
    void onTodoClick(Task task);

    /**
     * An interface method what should happen after clicking on radio button next to todo
     * @param task Task to be marked as done
     */
    void onTodoRadioButtonClick(Task task);
}
