package com.eng.asu.adaptivelearning.view.activity;

import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.eng.asu.adaptivelearning.R;
import com.eng.asu.adaptivelearning.viewmodel.UserViewModel;

public class RegisterActivity extends AppCompatActivity {

    EditText name, email, password;
    Button signup;
    Spinner type;
    UserViewModel userViewModel;
    String user_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        type = findViewById(R.id.type);
        signup = findViewById(R.id.signup);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.type));
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(spinnerAdapter);

        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                user_type = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        signup.setOnClickListener(view -> Register());
    }

    private void Register() {
        if (!userViewModel.isValidName(name.getText().toString())) {
            name.setError("Name is invalid");
        } else if (!userViewModel.isValidEmail(email.getText().toString())) {
            email.setError("Email address invalid");
        } else if (!userViewModel.isValidPassword(password.getText().toString())) {
            password.setError("Password is invalid");
        } else {
            //TODO - Register users in the server
            //save name.getText().toString()),email.getText().toString()),password.getText().toString()),user_type
        }
    }
}
