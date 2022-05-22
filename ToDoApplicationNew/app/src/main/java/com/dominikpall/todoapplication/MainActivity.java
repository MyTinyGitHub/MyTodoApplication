package com.dominikpall.todoapplication;

import android.content.Intent;
import android.os.Bundle;


import com.dominikpall.todoapplication.adapter.OnTodoClickListener;
import com.dominikpall.todoapplication.adapter.RecyclerViewAdapter;
import com.dominikpall.todoapplication.data.task.Task;
import com.dominikpall.todoapplication.data.task.TaskViewModel;
import com.dominikpall.todoapplication.model.SharedViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.List;

/**
 * Class that handles main activity, the hearth of the program
 */
public class MainActivity extends AppCompatActivity implements OnTodoClickListener {

    private static final String TAG = "MainActivity:";

    private TaskViewModel taskViewModel;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private BottomSheetFragment bottomSheetFragment;
    private SharedViewModel sharedViewModel;

    /**
     * OnCreate method that gets executed when the activity is created
     * @param savedInstanceState saved states of the instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        bottomSheetFragment = new BottomSheetFragment();

        ConstraintLayout constraintLayout = findViewById(R.id.bottomSheet);
        BottomSheetBehavior<ConstraintLayout> bottomSheetBehavior = BottomSheetBehavior.from(constraintLayout);
        bottomSheetBehavior.setPeekHeight(BottomSheetBehavior.STATE_HIDDEN);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        taskViewModel = new ViewModelProvider.AndroidViewModelFactory(MainActivity.this.getApplication()).create(TaskViewModel.class);
        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        this.initializeAndLoadDataForUser();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> createBottomSheet());

        findViewById(R.id.btn_profile).setOnClickListener(this::goToProfileSettings);
    }

    /**
     * Method to go to different activity
     * @param view corresponding view
     */
    private void goToProfileSettings(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("user", getIntent().getExtras().getString("user"));
        startActivity(intent);
    }

    /**
     *Method to initialize and load data for user
     */
    private void initializeAndLoadDataForUser() {
        String username = getIntent().getExtras().getString("user");
        TaskViewModel.getAllTasks(username).observe(this, this::createRecyclerAdapter);
        sharedViewModel.setUsername(username);
    }

    /**
     * Creates a fragment on the bottom of page that's used to add new tasks
     */
    private void createBottomSheet() {
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }

    /**
     * Creates recycler view
     * @param tasks that should fill the view
     */
    private void createRecyclerAdapter(List<Task> tasks) {
        recyclerViewAdapter = new RecyclerViewAdapter(tasks, this);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    /**
     * Method that handles click on a single task
     * @param task corresponding task
     */
    @Override
    public void onTodoClick(Task task) {
        sharedViewModel.setTaskToEdit(task);
        sharedViewModel.setIsEdit(true);
        createBottomSheet();
    }

    /**
     * Method that handles click on radio button next to the task
     * @param task corresponding task
     */
    @Override
    public void onTodoRadioButtonClick(Task task) {
        TaskViewModel.delete(task);
        recyclerViewAdapter.notifyItemRemoved(2);
    }
}