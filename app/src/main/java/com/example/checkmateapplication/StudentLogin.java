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

public class StudentLogin extends AppCompatActivity {

    private EditText emailField;
    private EditText passwordField;
    private Button btnLogin;
    private Button btnSignUp;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        emailField = findViewById(R.id.txtLogStudUser);
        passwordField = findViewById(R.id.txtLogStudPass);
        btnLogin = findViewById(R.id.btnStudLogin);
        btnSignUp = findViewById(R.id.btnSignUp);

        dbHelper = new DatabaseHelper(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailField.getText().toString().trim();
                String password = passwordField.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(StudentLogin.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (authenticateUser(email, password)) {
                    // Navigate to student dashboard or main activity
                    Intent intent = new Intent(StudentLogin.this, HomeStud.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(StudentLogin.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentLogin.this, StudentSignUp.class);
                startActivity(intent);
            }
        });
    }

    private boolean authenticateUser(String email, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = { DatabaseHelper.COLUMN_ID };
        String selection = DatabaseHelper.COLUMN_EMAIL + "=? AND " + DatabaseHelper.COLUMN_PASSWORD + "=? AND " + DatabaseHelper.COLUMN_ROLE + "=?";
        String[] selectionArgs = { email, password, "student" };
        Cursor cursor = db.query(DatabaseHelper.TABLE_USERS, columns, selection, selectionArgs, null, null, null);

        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        return cursorCount > 0;
    }
}
