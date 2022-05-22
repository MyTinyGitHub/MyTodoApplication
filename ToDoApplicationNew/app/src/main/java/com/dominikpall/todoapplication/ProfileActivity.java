package com.dominikpall.todoapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.dominikpall.todoapplication.data.user.User;
import com.dominikpall.todoapplication.data.user.UserViewModel;


/**
 * Class that handles user profile view
 */
public class ProfileActivity extends AppCompatActivity {
    private TextView welcomeText;
    private Button logOut;
    private Button changePassword;
    private EditText password;
    private EditText repeatPassword;

    /**
     * Method that gets executed when Activity is created
     * @param savedInstanceState bundle of saved states
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.profile_activity);

        welcomeText = findViewById(R.id.txt_welcome);
        logOut = findViewById(R.id.btn_logout);
        changePassword = findViewById(R.id.btn_change_password);

        password = findViewById(R.id.password);
        repeatPassword = findViewById(R.id.repeat_password);

        greetTheUser();
        logOut.setOnClickListener(this::logOut);
        changePassword.setOnClickListener(this::changePassword);
    }

    /**
     * Method used to save state when rotating the device
     * @param savedInstantState bundle of saved instant states
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstantState) {
        super.onSaveInstanceState(savedInstantState);
        savedInstantState.putInt("view", password.getVisibility());
    }

    /**
     * Method used to retrieve information when rotating the device
     * @param savedInstantState bundle of instant states
     */
    @Override
    public void onRestoreInstanceState(Bundle savedInstantState) {
        super.onRestoreInstanceState(savedInstantState);
        int show = savedInstantState.getInt("view");
        password.setVisibility(show);
        repeatPassword.setVisibility(show);
    }

    /**
     * Method that should respond on click on change password button
     * @param view corresponding view
     */
    private void changePassword(View view) {
        if(password.getVisibility() == View.GONE) {
            password.setVisibility(View.VISIBLE);
            repeatPassword.setVisibility(View.VISIBLE);
            changePassword.setText("Confirm");
        } else {
            String newPassword =password.getText().toString();
            String newPasswordRepeated = password.getText().toString();
            if ( newPassword.equals(newPasswordRepeated)) {
                User user = new User(getIntent().getExtras().getString("user"), newPassword);
                UserViewModel.update(user);
                super.onBackPressed();
                Toast.makeText(getBaseContext(), "Password successfully changed", Toast.LENGTH_SHORT).show();
            }
        }

    }

    /**
     * Method to greet the user
     */
    private void greetTheUser() {
        String username = getIntent().getExtras().getString("user");
        String greeting = String.format("Welcome %s", username);
        welcomeText.setText(greeting);
    }

    /**
     * Method to take care of logging out
     * @param view corresponding view
     */
    private void logOut(View view) {
        startActivity(new Intent(this, LoginActivity.class));
        super.finish();
    }
}
