package com.dominikpall.todoapplication.data.user;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Class representing user data
 */
@Entity(tableName = "user_table")
public class User {

    @PrimaryKey()
    @NonNull
    private String username;

    @NonNull
    private String password;

    /**
     * Constructor for user
     * @param username to be saved for the user
     * @param password to be saved for the user
     */
    public User(@NonNull String username, @NonNull String password) {
        this.username = username;
        this.password = password;
    }


    /**
     * Method to get Username of user
     * @return username
     */
    @NonNull
    public String getUsername() {
        return username;
    }

    /**
     * Method to get password of user
     * @return password
     */
    @NonNull
    public String getPassword() {
        return password;
    }

    /**
     * Method to change username of user
     * @param username to what username to change the username
     */
    public void setUsername(@NonNull String username) {
        this.username = username;
    }
}
