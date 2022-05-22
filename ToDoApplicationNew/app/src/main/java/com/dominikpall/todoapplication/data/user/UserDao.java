package com.dominikpall.todoapplication.data.user;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

/**
 * User data object class
 */
@Dao
public interface UserDao {
    /**
     * Method to create user
     * @param user to be created
     */
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void createUser(User user);

    /**
     * Method to delete user
     * @param user to be deleted
     */
    @Delete
    void delete(User user);

    /**
     * Method to update usr
     * @param user to be updated
     */
    @Update
    void update(User user);


    /**
     * Method to clear the whole user table
     */
    @Query("DELETE FROM user_table")
    void deleteAll();

    /**
     * Method to get a single user
     * @param username for which to find a user
     * @return corresponding user for the username
     */
    @Query("SELECT * FROM user_table WHERE username = :username")
    User get(String username);
}
