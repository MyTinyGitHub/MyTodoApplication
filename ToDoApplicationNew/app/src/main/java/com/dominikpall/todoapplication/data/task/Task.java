package com.dominikpall.todoapplication.data.task;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.dominikpall.todoapplication.model.RepeatCycle;

import java.util.Date;


/**
 * Task class representing a single task
 */
@Entity(tableName = "task_table")
public class Task {

    @ColumnInfo(name = "task_id")
    @PrimaryKey(autoGenerate = true)
    public long taskId;

    public String task;

    @ColumnInfo(name = "repeat_cycle")
    public RepeatCycle repeatCycle;


    String user;

    @ColumnInfo(name = "due_date")
    public Date dueDate;
    @ColumnInfo(name = "created_date")
    public Date dateCrated;
    @ColumnInfo(name = "is_done")
    public boolean isDone;

    /**
     * Constructor for the Task
     * @param task task description
     * @param user user for task
     * @param repeatCycle how often it should be repeated
     * @param dueDate till when it should be finished
     * @param dateCrated when created
     * @param isDone if task is done
     */
    public Task(String task, String user, RepeatCycle repeatCycle, Date dueDate, Date dateCrated, boolean isDone) {
        this.task = task;
        this.user = user;
        this.repeatCycle = repeatCycle;
        this.dueDate = dueDate;
        this.dateCrated = dateCrated;
        this.isDone = isDone;
    }

    /**
     * Method to convert attributes to representing string
     * @return String representation of object
     */
    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", task='" + task + '\'' +
                ", priority=" + repeatCycle +
                ", dueDate=" + dueDate +
                ", dateCrated=" + dateCrated +
                ", isDone=" + isDone +
                '}';
    }


    /**
     * Get task text
     * @return String definition of task
     */
    public String getTask() {
        return task;
    }

    /**
     * Get repeat cycle
     * @return RepeatCycle
     */
    public RepeatCycle getRepeatCycle() {
        return repeatCycle;
    }

    /**
     * Get due date
     * @return dueDate
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * Change task description
     * @param task change task description
     */
    public void setTask(String task) {
        this.task = task;
    }

    /**
     * Change repeat cycle
     * @param repeatCycle type of repeat cycle
     */
    public void setRepeatCycle(RepeatCycle repeatCycle) {
        this.repeatCycle = repeatCycle;
    }

    /**
     * Change due date for task
     * @param dueDate due date
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * change date when the task was created
     * @param dateCrated created date
     */
    public void setDateCrated(Date dateCrated) {
        this.dateCrated = dateCrated;
    }

    /**
     * Get user that owns the task
     * @return User assigned to task
     */
    public String getUser() {
        return user;
    }

    /**
     * Change user that owns the task
     * @param username of the user
     */
    public void setUser(String username) {
        this.user = username;
    }
}
