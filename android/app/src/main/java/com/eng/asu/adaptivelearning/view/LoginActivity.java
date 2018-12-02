package com.eng.asu.adaptivelearning.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;

import com.eng.asu.adaptivelearning.EditTextWatcher;
import com.eng.asu.adaptivelearning.R;
import com.eng.asu.adaptivelearning.databinding.ActivityLoginBinding;
import com.eng.asu.adaptivelearning.viewmodel.UserViewModel;

public class LoginActivity extends AppCompatActivity {
    UserViewModel userViewModel;
    ActivityLoginBinding loginBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        initializeActivity();
    }

    private void initializeActivity() {
        loginBinding.setLoginHandler(this);
        new EditTextWatcher(loginBinding.passwordTextInput);
        new EditTextWatcher(loginBinding.emailTextInput);
    }

    public void onLoginButtonClicked() {
        if (!userViewModel.isValidEmail(loginBinding.emailEditText.getText().toString()))
            invalidInput(loginBinding.emailEditText, "Email address invalid");
        else if (!userViewModel.isValidPassword(loginBinding.passwordEditText.getText().toString())) {
            invalidInput(loginBinding.passwordEditText, "Password is invalid");
        } else {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    private void invalidInput(TextInputEditText editText, String errorMessage) {
        editText.requestFocus();
        editText.setError(errorMessage);
    }

    public void onRegisterClicked() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

}
