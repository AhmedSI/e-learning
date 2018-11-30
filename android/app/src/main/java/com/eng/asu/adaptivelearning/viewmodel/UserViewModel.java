package com.eng.asu.adaptivelearning.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;

import com.eng.asu.adaptivelearning.view.LoginActivity;
import com.eng.asu.adaptivelearning.view.MainActivity;

import java.util.regex.Pattern;

public class UserViewModel extends AndroidViewModel {

    public UserViewModel(@NonNull Application application) {
        super(application);
    }

    public Class<? extends AppCompatActivity> getActivityToOpen() {
        if (isUserLoggedIn())
            return MainActivity.class;
        else
            return LoginActivity.class;
    }

    private boolean isUserLoggedIn() {
        //TODO --- check for the current access token if it's valid or not
        return false;
    }

    public boolean isValidPassword(String password) {
        return !TextUtils.isEmpty(password) && password.length() >= 8 && !password.contains(" ") && password.length() <= 15;
    }

    public boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public boolean isValidName(String name) {
        Pattern name_pattern = Pattern.compile("[a-zA-Z\\s]+");
        return !TextUtils.isEmpty(name) && name_pattern.matcher(name).matches();
    }
}
