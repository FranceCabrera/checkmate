package com.example.checkmateapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private EditText txtSignUser, txtSignPass, txtSignConPass;
    private CheckBox chkTeacher, chkStudent;
    private Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        dbHelper = new DatabaseHelper(this);
        txtSignUser = findViewById(R.id.txtSignUser);
        txtSignPass = findViewById(R.id.txtSignPass);
        txtSignConPass = findViewById(R.id.txtSignConPass);
        chkTeacher = findViewById(R.id.chkTeacher);
        chkStudent = findViewById(R.id.chkStudent);
        btnSignIn = findViewById(R.id.btnSignIn);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    private void signUp() {
        String email = txtSignUser.getText().toString();
        String password = txtSignPass.getText().toString();
        String confirmPassword = txtSignConPass.getText().toString();

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        if (chkTeacher.isChecked()) {
            boolean result = dbHelper.addUser(email, password, DatabaseHelper.TABLE_TEACHERS);
            if (result) {
                Toast.makeText(this, "Teacher Registration Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignUpActivity.this, TeacherProfile.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show();
            }
        } else if (chkStudent.isChecked()) {
            boolean result = dbHelper.addUser(email, password, DatabaseHelper.TABLE_STUDENTS);
            if (result) {
                Toast.makeText(this, "Student Registration Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignUpActivity.this, StudentProfileActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please select a user type", Toast.LENGTH_SHORT).show();
        }
    }
}
