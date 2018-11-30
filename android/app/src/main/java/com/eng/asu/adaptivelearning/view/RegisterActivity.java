package com.eng.asu.adaptivelearning.view;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.eng.asu.adaptivelearning.R;
import com.eng.asu.adaptivelearning.viewmodel.UserViewModel;

public class RegisterActivity extends AppCompatActivity {

    EditText name , email , password;
    Button signup;
    Spinner type;
    UserViewModel userViewModel;
    String user_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        type = (Spinner) findViewById(R.id.type);
        signup = (Button) findViewById(R.id.signup);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_list_item_1,
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
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register();
            }
        });
    }

    private void Register() {
        if(!userViewModel.isValidName(name.getText().toString())){
            name.setError("Name is invalid");
        }
        else if(!userViewModel.isValidEmail(email.getText().toString())){
            email.setError("Email address invalid");
        }
        else if(!userViewModel.isValidPassword(password.getText().toString())){
            password.setError("Password is invalid");
        }
        else{
            //save name.getText().toString()),email.getText().toString()),password.getText().toString()),user_type
        }
    }
}
