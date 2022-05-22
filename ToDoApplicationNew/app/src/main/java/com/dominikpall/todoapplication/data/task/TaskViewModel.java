package com.dominikpall.todoapplication.data.task;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * Class to handle View Model of Task
 */
public class TaskViewModel extends AndroidViewModel {
    private static TaskRepository repository;

    /**
     * Constructor for the view of task
     * @param application to chose corresponding repository
     */
    public TaskViewModel(@NonNull Application application) {
        super(application);
        repository = new TaskRepository(application);
    }

    /**
     * Method to insert task
     * @param task to be inserted
     */
    public static void insert(Task task) {
        repository.insert(task);
    }

    /**
     * Method to delete task
     * @param task to be deleted
     */
    public static void delete(Task task) {
        repository.delete(task);
    }

    /**
     * Method to update task
     * @param task to be updated
     */
    public static void update(Task task) {
        repository.update(task);
    }

    /**
     * Method to get all tasks of a particular user
     * @param username whom tasks should be retrieved
     * @return LiveData consisting of list of retrieved tasks
     */
    public static LiveData<List<Task>> getAllTasks(String username) {
        return repository.getAllTasks(username);
    }

    /**
     * Method to get a single Task
     * @param id which task to get
     * @return LiveData which contains a task
     */
    public static LiveData<Task> get(long id) {
        return repository.get(id);
    }
}