package com.dominikpall.todoapplication;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;



import com.dominikpall.todoapplication.data.task.Task;
import com.dominikpall.todoapplication.data.task.TaskViewModel;
import com.dominikpall.todoapplication.model.RepeatCycle;
import com.dominikpall.todoapplication.model.SharedViewModel;
import com.dominikpall.todoapplication.util.Utils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import androidx.lifecycle.ViewModelProvider;

import java.util.Calendar;
import java.util.Date;

/**
 * Class that handles the bottom sheet fragment to add and modify task
 */
public class BottomSheetFragment extends BottomSheetDialogFragment {
    private SharedViewModel sharedViewModel;
    private ImageButton calendarButton;
    private ImageButton repeatButton;
    private ImageButton saveButton;

    private RadioGroup repeatCycleButton;
    private CalendarView calendarView;
    private final Calendar calendar;
    private Group calendarGroup;
    private EditText enterTodo;
    private Date dueDate;

    private boolean editing;

    private RepeatCycle repeatCycle = RepeatCycle.NO_REPEAT;

    /**
     * Bottom fragment used to edit and add new tasks
     */
    public BottomSheetFragment() {
        calendar = Calendar.getInstance();
    }

    /**
     * OnCreateView method that get's executed when the view is created
     * @param inflater inflater to be used to inflate the view
     * @param container to hold the inflated task
     * @param savedInstanceState bundle of saved instances
     * @return View
     */
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.bottom_sheet, container, false);

        repeatCycleButton = view.findViewById(R.id.radioGroup_priority);
        calendarButton = view.findViewById(R.id.today_calendar_button);
        repeatButton = view.findViewById(R.id.repeat_cycle_todo_button);
        calendarGroup = view.findViewById(R.id.calendar_group);
        calendarView = view.findViewById(R.id.calendar_view);
        saveButton = view.findViewById(R.id.save_todo_button);
        enterTodo = view.findViewById(R.id.enter_todo_et);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        return view;
    }

    /**
     * Method that resumes the fragment.
     */
    @Override
    public void onResume() {
        super.onResume();

        Task task = sharedViewModel.getTaskToEdit().getValue();
        String username = sharedViewModel.getUsername();

        if(task == null) {
            task = new Task("", username, repeatCycle, dueDate, Calendar.getInstance().getTime(), false);
            sharedViewModel.setTaskToEdit(task);
        }

        enterTodo.setText(task.getTask());
        editing = sharedViewModel.getIsEdit();
    }

    /**
     * Method that handles onPause
     */
    @Override
    public void onPause() {
        super.onPause();
        clearTask();
        dismissAllowingStateLoss();
    }

    /**
     * Method that gets executed when view is created
     * @param view corresponding view
     * @param savedInstanceState bundle of saved instances
     */
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        saveButton.setOnClickListener(this::onSaveClick);
        calendarButton.setOnClickListener(this::onCalendarClick);
        calendarView.setOnDateChangeListener(this::changeDateOnChange);
        repeatButton.setOnClickListener(this::triggerVisibilityForToDoRepeat);
    }

    /**
     * Method that handles on click on save button
     * @param view corresponding view
     */
    private void onSaveClick(View view) {
        String taskText = enterTodo.getText().toString().trim();

        if(dueDate == null) {
            dueDate = calendar.getTime();
        }

        if(!TextUtils.isEmpty(taskText)) {
            Task task = sharedViewModel.getTaskToEdit().getValue();
            fillTaskWithData(task, taskText);
            if(editing) {
                TaskViewModel.update(task);
            } else {
                TaskViewModel.insert(task);
            }
            clearTask();
        }

        if(this.isVisible()) dismiss();
    }

    /**
     * Method that clears Task
     */
    private void clearTask() {
        String username = sharedViewModel.getUsername();
        sharedViewModel.setIsEdit(false);
        sharedViewModel.setTaskToEdit(new Task("",username, repeatCycle, dueDate, Calendar.getInstance().getTime(), false));
        enterTodo.setText("");
    }

    /**
     * Method that fills task with data
     * @param task task to be filled
     * @param taskText task text to be filled
     */
    private void fillTaskWithData(Task task, String taskText) {
        task.setTask(taskText);
        task.setDateCrated(Calendar.getInstance().getTime());
        task.setRepeatCycle(repeatCycle);
        task.setDueDate(dueDate);
    }


    /**
     * Method that handles on calendar click
     * @param view correspoding view
     */
    private void onCalendarClick(View view ) {
        Utils.hideKeyboard(this.getView());
        repeatCycleButton.setVisibility(View.GONE);
        calendarGroup.setVisibility(
                calendarGroup.getVisibility() == View.GONE ? View.VISIBLE : View.GONE
        );
    }

    /**
     * Method that handles change date on change
     * @param calendarView corresponding calendar view
     * @param year to be set in calendar
     * @param month to be set in calendar
     * @param day to be set in calendar
     */
    private void changeDateOnChange(View calendarView, int year, int month, int day) {
        calendar.clear();
        calendar.set(year, month, day);
        dueDate = calendar.getTime();
    }

    /**
     * Method to trigger visibility for button press of repeat;
     * @param view corresponding view
     */
    private void triggerVisibilityForToDoRepeat(View view) {
        Utils.hideKeyboard(view);
        calendarGroup.setVisibility(View.GONE);

        int visible = repeatCycleButton.getVisibility() == View.GONE ? View.VISIBLE : View.GONE;

        repeatCycleButton.setVisibility(visible);
        repeatCycleButton.setOnCheckedChangeListener(this::checkedChangeForTodoRepeat);
    }

    /**
     * Method to handle checked change for to do repeat
     * @param radioGroup corresponding radio group
     * @param checkedId id of selected button
     */
    private void checkedChangeForTodoRepeat(RadioGroup radioGroup, int checkedId) {
        repeatCycle = RepeatCycle.NO_REPEAT;
        if(repeatCycleButton.getVisibility() == View.VISIBLE) {
            View selectedRadioButton = getView().findViewById(checkedId);

            if(selectedRadioButton.getId() == R.id.radioButton_daily_repeat) repeatCycle = RepeatCycle.DAILY;
            if(selectedRadioButton.getId() == R.id.radioButton_weekly_repeat) repeatCycle = RepeatCycle.WEEKLY;
            if(selectedRadioButton.getId() == R.id.radioButton_monthly_repeat) repeatCycle = RepeatCycle.MONTHLY;
        }
    }

}