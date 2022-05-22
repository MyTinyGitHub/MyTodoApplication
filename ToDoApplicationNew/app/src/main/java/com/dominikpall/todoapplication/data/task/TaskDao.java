package com.dominikpall.todoapplication.data.task;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Data object class of Task
 */
@Dao
public interface TaskDao {

    /**
     * Insert operation for the task
     * @param task that should be inserted
     */
    @Insert
    void insertTask(Task task);

    /**
     * Delete operation for the task
     * @param task that should be deleted
     */
    @Delete
    void delete(Task task);

    /**
     * Update operation for the task
     * @param task that should be updated
     */
    @Update
    void update(Task task);

    /**
     * Delete content of whole table
     */
    @Query("DELETE FROM task_table")
    void deleteAll();

    /**
     * Operation to retrieve all tasks belonging to single user
     * @param username
     * @return Live data consisting of List of Tasks
     */
    @Query("SELECT * FROM task_table WHERE task_table.user == :username")
    LiveData<List<Task>> getTasks(String username);

    /**
     * Operation to retrieve a single task
     * @param id of the task we want to retrieve
     * @return Live data with a Task
     */
    @Query("SELECT * FROM task_table WHERE task_table.task_id == :id")
    LiveData<Task> get(long id);
}
