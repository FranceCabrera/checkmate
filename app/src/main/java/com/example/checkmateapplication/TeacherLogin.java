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

    private DatabaseHelper dbHelper;
    private EditText txtLogTeachUser, txtLogTeachPass;
    private Button btnTeachLog, btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);


        txtLogTeachUser = findViewById(R.id.txtLogTeachUser);
        txtLogTeachPass = findViewById(R.id.txtLogTeachPass);
        btnTeachLog = findViewById(R.id.btnTeachLog);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnTeachLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginTeacher();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to SignUpActivity
                Intent intent = new Intent(TeacherLogin.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loginTeacher() {
        String email = txtLogTeachUser.getText().toString();
        String password = txtLogTeachPass.getText().toString();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_TEACHERS,
                new String[]{DatabaseHelper.COLUMN_ID},
                DatabaseHelper.COLUMN_EMAIL + "=? AND " + DatabaseHelper.COLUMN_PASSWORD + "=?",
                new String[]{email, password},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
            cursor.close();
            // Navigate to TeachersDash activity
            Intent intent = new Intent(TeacherLogin.this, TeachersDash.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
        }
    }
}
