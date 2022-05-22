package com.dominikpall.todoapplication.data.task;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.dominikpall.todoapplication.util.TaskRoomDatabase;

import java.util.List;

/**
 * Class that handles repository of tasks
 */
public class TaskRepository {
    private final TaskDao taskDao;

    /**
     * Contructor for Task Repository
     * @param application representing a database on which should the repository be created
     */
    public TaskRepository(Application application) {
        TaskRoomDatabase database = TaskRoomDatabase.getDatabase(application);
        this.taskDao = database.taskDao();
    }

    /**
     * Method to retrieve all tasks of a user
     * @param username whom tasks should be retrieved
     * @return  LiveData consisting of List of Tasks
     */
    public LiveData<List<Task>> getAllTasks(String username) {
        return this.taskDao.getTasks(username);
    }

    /**
     * Method to insert a task
     * @param task to be inserted
     */
    public void insert(Task task) {
        TaskRoomDatabase.databaseWriterExecutor.execute(() -> taskDao.insertTask(task));
    }

    /**
     * Method to get a task
     * @param id which tasks should be retrieved
     * @return LiveData data consisting of task
     */
    public LiveData<Task> get(long id) {
        return taskDao.get(id);
    }

    /**
     * Method to update task
     * @param task to be updated
     */
    public void update(Task task) {
        TaskRoomDatabase.databaseWriterExecutor.execute(() -> taskDao.update(task));
    }

    /**
     * Method to delete Task
     * @param task to be deleted
     */
    public void delete(Task task) {
        TaskRoomDatabase.databaseWriterExecutor.execute(() -> taskDao.delete(task));
    }
}
