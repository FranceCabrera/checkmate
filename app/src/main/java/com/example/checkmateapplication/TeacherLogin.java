package com.example.checkmateapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TeacherLogin extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button btnSignIn;
    private Button btnSignUp;
    private DatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);

        dbHelper = new DatabaseHelper(this); // Initialize dbHelper

        editTextEmail = findViewById(R.id.txtLogTeachUser);
        editTextPassword = findViewById(R.id.txtLogTeachPass);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignIn = findViewById(R.id.btnTeachLog);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(TeacherLogin.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (authenticateUser(email, password)) {
                    // Navigate to teacher dashboard
                    Intent intent = new Intent(TeacherLogin.this, TeachersDash.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(TeacherLogin.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle sign-up functionality
                Intent intent = new Intent(TeacherLogin.this, TeacherSignUp.class);
                startActivity(intent);
            }
        });


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle sign-up functionality
                Intent intent = new Intent(TeacherLogin.this, TeacherSignUp.class);
                startActivity(intent);
            }
        });
    }

    private boolean authenticateUser(String email, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {DatabaseHelper.COLUMN_ID};
        String selection = DatabaseHelper.COLUMN_EMAIL + "=? AND " + DatabaseHelper.COLUMN_PASSWORD + "=? AND " + DatabaseHelper.COLUMN_ROLE + "=?";
        String[] selectionArgs = {email, password, "teacher"}; // Change "student" to "teacher"
        Cursor cursor = db.query(DatabaseHelper.TABLE_USERS, columns, selection, selectionArgs, null, null, null);

        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        return cursorCount > 0;
    }
}