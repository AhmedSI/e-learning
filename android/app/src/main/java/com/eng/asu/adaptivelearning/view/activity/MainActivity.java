package com.eng.asu.adaptivelearning.view.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import com.eng.asu.adaptivelearning.R;
import com.eng.asu.adaptivelearning.databinding.ActivityMainBinding;
import com.eng.asu.adaptivelearning.view.adapter.CoursesAdapter;
import com.eng.asu.adaptivelearning.viewmodel.UserViewModel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mainBinding;
    UserViewModel userViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        createWelcomeToast();
        initializeActivity();
    }

    private void initializeActivity() {
        mainBinding.coursesList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mainBinding.coursesList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mainBinding.coursesList.setAdapter(new CoursesAdapter(this, userViewModel.getCourses()));
    }

    private void createWelcomeToast() {
        Toast.makeText(this, "Welcome " + userViewModel.getUserName() + " :)", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = this.getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
