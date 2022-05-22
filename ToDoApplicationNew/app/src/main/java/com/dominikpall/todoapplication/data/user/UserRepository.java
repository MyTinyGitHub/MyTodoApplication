package com.dominikpall.todoapplication.data.user;

import android.app.Application;
import com.dominikpall.todoapplication.util.TaskRoomDatabase;

/**
 * Class to handle user repository, middle step between View and Dao
 */
public class UserRepository {
    private final UserDao userDao;

    /**
     * Constructor for the repository
     * @param application class of the corresponding database
     */
    public UserRepository(Application application) {
        TaskRoomDatabase database = TaskRoomDatabase.getDatabase(application);
        this.userDao = database.userDao();
    }

    /**
     * Method to insert user
     * @param user to be inserted
     * @return the result of the insertion, false if user already exists
     */
    public boolean insert(User user) {
        User dbUser = get(user.getUsername());
        if(dbUser != null) return false;
        TaskRoomDatabase.databaseWriterExecutor.execute(() -> userDao.createUser(user));
        return true;
    }

    /**
     * Method to get a user based on username
     * @param username to be searched by
     * @return corresponding user to the username
     */
    public User get(String username) {
        return userDao.get(username);
    }

    /**
     * Method to update a user
     * @param user to be updated with new data
     */
    public void update(User user) {
        TaskRoomDatabase.databaseWriterExecutor.execute(() -> userDao.update(user));
    }

    /**
     * Method to delete a user
     * @param user to be dleeted
     */
    public void delete(User user) {
        TaskRoomDatabase.databaseWriterExecutor.execute(() -> userDao.delete(user));
    }
}
