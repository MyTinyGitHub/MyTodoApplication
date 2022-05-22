package com.dominikpall.todoapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;


import com.dominikpall.todoapplication.R;
import com.dominikpall.todoapplication.data.task.Task;
import com.dominikpall.todoapplication.util.Utils;
import com.google.android.material.chip.Chip;

import java.util.List;

/**
 * Class RecyclerViewAdapter
 * Adapter to display todo list
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private final List<Task> taskList;
    private final OnTodoClickListener todoClickListener;

    /**
     * Constructor for RecyclerViewAdapter
     * @param taskList list of tasks which should be displayed
     * @param onTodoClickListener listener for the todo
     */
    public RecyclerViewAdapter(List<Task> taskList, OnTodoClickListener onTodoClickListener) {
        this.taskList = taskList;
        this.todoClickListener = onTodoClickListener;
    }

    /**
     *
     * @param parent parent for the Recycle view
     * @param viewType view type for the Recycle view
     * @return holder of the todo view
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_row, parent, false);

        return new ViewHolder(view);
    }

    /**
     * Method to bind item to layout
     * @param holder View holder of the Todo Item
     * @param position position in the Recycler view
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = taskList.get(position);
        String formattedDate = Utils.formatDate(task.getDueDate());

        holder.task.setText(task.getTask());
        holder.todayChip.setText(formattedDate);
        holder.repeatChip.setText(task.getRepeatCycle().getText());
    }

    /**
     * Returns the Item count in the recycler view
     * @return number of items in the list that's displayed
     */
    @Override
    public int getItemCount() {
        return taskList.size();
    }


    /**
     * Class ViewHolder
     * class that holds the recycler view
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public AppCompatRadioButton radioButton;
        public AppCompatTextView task;
        public Chip todayChip;
        public Chip repeatChip;

        OnTodoClickListener onTodoClickListener;

        /**
         * Constructor to create view holder for todo task
         * @param itemView
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            radioButton = itemView.findViewById(R.id.todo_radio_button);
            todayChip = itemView.findViewById(R.id.todo_row_chip);
            task = itemView.findViewById(R.id.todo_row_todo);
            repeatChip = itemView.findViewById(R.id.todo_repeat_cycle);

            this.onTodoClickListener = todoClickListener;
            itemView.setOnClickListener(this);
            radioButton.setOnClickListener(this);
        }

        /**
         * Method that takes care of the onClick event for the todo item
         * @param view view of the
         */
        @Override
        public void onClick(View view) {
            int id = view.getId();
            int adapterPosition = getAdapterPosition();
            Task task = taskList.get(adapterPosition);

            if(id == R.id.todo_row_layout) onTodoClickListener.onTodoClick(task);
            if(id == R.id.todo_radio_button) onTodoClickListener.onTodoRadioButtonClick(task);
        }
    }
}
