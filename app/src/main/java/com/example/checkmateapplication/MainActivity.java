package com.example.checkmateapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.checkmateapplication.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnTeach = findViewById(R.id.btnTeach);
        Button btnStudent = findViewById(R.id.btnStudent);

        btnTeach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to Teacher Login Page
                Intent intent = new Intent(MainActivity.this, TeacherLogin.class);
                startActivity(intent);
            }
        });

        btnStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to Student Login Page
                Intent intent = new Intent(MainActivity.this, StudentLogin.class);
                startActivity(intent);
            }
        });
    }
}
