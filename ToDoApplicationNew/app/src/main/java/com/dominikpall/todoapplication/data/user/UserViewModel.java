package com.dominikpall.todoapplication.data.user;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

/**
 * Class that handles inputs from screen and moves them to repository
 */
public class UserViewModel extends AndroidViewModel {
        private static UserRepository repository;

    /**
     * Constructor
     * @param application set the corresponding repository
     */
    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
    }

    /**
     * Method to handle login, returns true if login was successful
     * @param user that should be logged in
     * @return if the login was successful
     */
    public static boolean login(User user) {
        User dbUser = repository.get(user.getUsername());
        Log.d("UserViewModel", user.getUsername());
        if(dbUser != null && dbUser.getPassword().equals(user.getPassword())) {
            return true;
        }
        return false;
    }

    /**
     * Method to insert User into the database
     * @param user that should be inserted
     * @return the result of the insert, false if user already exists
     */
    public static boolean insert(User user) {
        return repository.insert(user);
    }

    /**
     * Method to delete user
     * @param user to be deleted
     */
    public static void delete(User user) {
        repository.delete(user);
    }

    /**
     * Method to update user
     * @param user to be updated with new data
     */
    public static void update(User user) {
        repository.update(user);
    }

    /**
     * Method to get user based on username
     * @param userName of the user which should be retrieved
     * @return User account
     */
    public static User get(String userName) {
        return repository.get(userName);
    }
}
