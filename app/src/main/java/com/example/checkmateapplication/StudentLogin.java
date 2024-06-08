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

import com.example.checkmateapplication.R;

public class StudentLogin extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private EditText txtLogStudUser, txtLogStudPass;
    private Button btnStudLogin, btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        dbHelper = new DatabaseHelper(this);
        txtLogStudUser = findViewById(R.id.txtLogStudUser);
        txtLogStudPass = findViewById(R.id.txtLogStudPass);
        btnStudLogin = findViewById(R.id.btnStudLogin);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnStudLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginStudent();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the SignUpActivity
                Intent intent = new Intent(StudentLogin.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loginStudent() {
        String email = txtLogStudUser.getText().toString();
        String password = txtLogStudPass.getText().toString();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_STUDENTS,
                new String[]{DatabaseHelper.COLUMN_ID},
                DatabaseHelper.COLUMN_EMAIL + "=? AND " + DatabaseHelper.COLUMN_PASSWORD + "=?",
                new String[]{email, password},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
            cursor.close();
            Intent intent = new Intent(StudentLogin.this, HomeStud.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
        }
    }
}
