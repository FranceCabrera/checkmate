package com.example.checkmateapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class StudentSignUp extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button btnSignUp;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_sign_up);

        dbHelper = new DatabaseHelper(this);

        editTextEmail = findViewById(R.id.txtSignUser);
        editTextPassword = findViewById(R.id.txtSignPass);
        btnSignUp = findViewById(R.id.btnSignIn);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerStudent();
            }
        });
    }

    private void registerStudent() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String role = "student"; // Set the role to "student"

        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Please enter email");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Please enter password");
            return;
        }

        // Default values for other user attributes
        String name = "User Name"; // You can modify to take user input if required
        String gender = "Not Specified"; // You can modify to take user input if required
        String section = "Default Section"; // You can modify to take user input if required

        boolean result = dbHelper.addStudentUser(email, password);
        if (result) {
            Toast.makeText(this, "Student registered successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(StudentSignUp.this, StudentProfileActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Registration Failed. Email may already be in use.", Toast.LENGTH_SHORT).show();
        }
    }
}
