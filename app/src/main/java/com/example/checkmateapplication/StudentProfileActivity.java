package com.example.checkmateapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class StudentProfileActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private EditText txtWriteStudNum, txtWriteStudName, txtWriteSection;
    private CheckBox chkMale, chkFemale;
    private Button btnSubmitProfile;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        dbHelper = new DatabaseHelper(this);
        txtWriteStudNum = findViewById(R.id.txtWriteStudNum);
        txtWriteStudName = findViewById(R.id.txtWriteStudName);
        txtWriteSection = findViewById(R.id.txtWriteSection);
        chkMale = findViewById(R.id.chkMale);
        chkFemale = findViewById(R.id.chkFemale);
        btnSubmitProfile = findViewById(R.id.btnSubmitProfile);

        email = getIntent().getStringExtra("email");

        chkMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chkFemale.setChecked(false);
            }
        });

        chkFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chkMale.setChecked(false);
            }
        });

        btnSubmitProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitProfile();
            }
        });
    }
    private void submitProfile() {
        String studentNumber = txtWriteStudNum.getText().toString().trim();
        String name = txtWriteStudName.getText().toString().trim();
        String section = txtWriteSection.getText().toString().trim();
        String gender = chkMale.isChecked() ? "Male" : chkFemale.isChecked() ? "Female" : "";
        String email = "example@example.com"; // You need to set the email here or retrieve it from somewhere

        if (TextUtils.isEmpty(studentNumber)) {
            txtWriteStudNum.setError("Please enter student number");
            return;
        }

        if (TextUtils.isEmpty(name)) {
            txtWriteStudName.setError("Please enter name");
            return;
        }

        if (TextUtils.isEmpty(section)) {
            txtWriteSection.setError("Please enter section");
            return;
        }

        if (TextUtils.isEmpty(gender)) {
            Toast.makeText(this, "Please select gender", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isInserted = dbHelper.addStudentProfile(email, studentNumber, name, section, gender);
        if (isInserted) {
            Toast.makeText(this, "Profile Submitted Successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(StudentProfileActivity.this, HomeStud.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Failed to Submit Profile", Toast.LENGTH_SHORT).show();
        }
    }



}
