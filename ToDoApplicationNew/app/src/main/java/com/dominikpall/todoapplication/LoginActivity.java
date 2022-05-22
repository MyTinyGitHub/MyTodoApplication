package com.dominikpall.todoapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.dominikpall.todoapplication.data.user.User;
import com.dominikpall.todoapplication.data.user.UserViewModel;
import com.dominikpall.todoapplication.model.SharedViewModel;

/**
 * Class that handles the login activity
 */
public class LoginActivity extends AppCompatActivity {
    private Button login;
    private Button register;
    private UserViewModel userViewModel;
    private SharedViewModel sharedViewModel;
    private Boolean doubleBackPressedOnce = false;

    /**
     * OnCreate method that gets executed when the activity is created
     * @param savedInstanceState bundle of saved states
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        userViewModel = new ViewModelProvider.AndroidViewModelFactory(LoginActivity.this.getApplication()).create(UserViewModel.class);
        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        login = findViewById(R.id.btn_login);
        register = findViewById(R.id.btn_register);

        login.setOnClickListener(this::onLoginClick);
        register.setOnClickListener(this::onRegisterClick);
    }

    /**
     * Method that handles press back button
     */
    @Override
    public void onBackPressed() {
        if(doubleBackPressedOnce) {
            this.finishAffinity();
        }

        Toast.makeText(getBaseContext(), "Press again to close the application", Toast.LENGTH_LONG).show();
        doubleBackPressedOnce = true;
    }

    /**
     * Method that handles onClick event of login button
     * @param view corresponding view
     */
    private void onLoginClick(View view) {
        User user = createUserFromData();
        if(UserViewModel.login(user)){
            login(user);
        } else {
            Toast.makeText(getBaseContext(), "Wrong password or the user does not exist", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Method to login user
     * @param user to be logged in
     */
    private void login(User user) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("user", user.getUsername());
        startActivity(intent);
    }

    /**
     * Method that handles click on register button
     * @param view
     */
    private void onRegisterClick(View view) {
        User user = createUserFromData();

        if(UserViewModel.insert(user)) {
            login(user);
        } else {
            Toast.makeText(getBaseContext(), "User already exists", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Method that created user from data on view
     * @return new User created from data
     */
    private User createUserFromData() {
        EditText password = findViewById(R.id.text_password);
        EditText userName = findViewById(R.id.text_username);
        return new User(userName.getText().toString(), password.getText().toString());
    }
}
