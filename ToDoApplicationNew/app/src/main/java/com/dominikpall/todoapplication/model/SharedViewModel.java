package com.dominikpall.todoapplication.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dominikpall.todoapplication.data.task.Task;

/**
 * Class to handle sharing values between views
 */
public class SharedViewModel extends ViewModel {
    private final MutableLiveData<Task> taskToEdit = new MutableLiveData<>();
    private String username;
    private boolean isEdit;

    /**
     * Method to handle username
     * @param username username to be handled
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Method to get the username
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Method to set the task to be edited
     * @param task task to be handled
     */
    public void setTaskToEdit(Task task) {
        this.taskToEdit.setValue(task);
    }

    /**
     * Method to get the task
     * @return LiveData with corresponding task
     */
    public LiveData<Task> getTaskToEdit() {
        return this.taskToEdit;
    }

    /**
     * Method to set if we are in edit mode
     * @param isEdit boolean value that represents editing
     */
    public void setIsEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }

    /**
     * Method to get editing value
     * @return edit
     */
    public boolean getIsEdit() {
        return this.isEdit;
    }
}
