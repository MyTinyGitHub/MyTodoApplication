package com.dominikpall.todoapplication.util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.dominikpall.todoapplication.data.task.Task;
import com.dominikpall.todoapplication.data.task.TaskDao;
import com.dominikpall.todoapplication.data.user.User;
import com.dominikpall.todoapplication.data.user.UserDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Task.class, User.class},
        version = 7)
@TypeConverters({Converter.class})

/**
 * Class that represents the database
 */
public abstract class TaskRoomDatabase extends RoomDatabase {
    public static final int NUMBER_OF_THREADS = 4;
    public static final String DATABASE_NAME = "todo_database";

    private static volatile TaskRoomDatabase INSTANCE;
    public static final ExecutorService databaseWriterExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static final RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    databaseWriterExecutor.execute(() -> {
                        TaskDao taskDao = INSTANCE.taskDao();
                        UserDao userDao = INSTANCE.userDao();

                        userDao.deleteAll();
                        taskDao.deleteAll();
                    });
                }
            };

    /**
     * Method to created a singleton instance of the room database
     * @param context context of the database
     * @return TaskRoomDatabase return the Instances of database
     */
    public static TaskRoomDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (TaskRoomDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TaskRoomDatabase.class, DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .addCallback(sRoomDatabaseCallback)
                        .allowMainThreadQueries()
                        .build();
            }
        }
        return  INSTANCE;
    }

    /**
     * Method that returns the DAO of Task
     * @return TaskDao
     */
    public abstract TaskDao taskDao();

    /**
     * Method that returns the DAO of User
     * @return UserDao
     */
    public abstract UserDao userDao();
}
