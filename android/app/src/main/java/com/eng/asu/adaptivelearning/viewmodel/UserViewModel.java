package com.eng.asu.adaptivelearning.viewmodel;

import android.app.Application;
import android.text.TextUtils;
import android.util.Patterns;

import com.eng.asu.adaptivelearning.LearningApplication;
import com.eng.asu.adaptivelearning.model.Course;
import com.eng.asu.adaptivelearning.model.User;
import com.eng.asu.adaptivelearning.preferences.UserAccountStorage;
import com.eng.asu.adaptivelearning.view.activity.LoginActivity;
import com.eng.asu.adaptivelearning.view.activity.MainActivity;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;

public class UserViewModel extends AndroidViewModel {
    @Inject
    UserAccountStorage userAccountStorage;

    public UserViewModel(@NonNull Application application) {
        super(application);
        LearningApplication.getApplicationComponent().inject(this);
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

    public String getUserName() {
        return userAccountStorage.getUser().getName();
    }

    public void login(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setId(1);
        user.setName("Muhammed Sabry");
        userAccountStorage.setUser(user);
    }

    public List<Course> getCourses() {
        //TODO - Mona Medhat
        return Collections.emptyList();
    }
}
