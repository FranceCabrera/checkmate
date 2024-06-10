//package com.example.checkmateapplication;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//public class SignUpActivity extends AppCompatActivity {
//
//    private EditText editTextEmail, editTextPassword;
//    private CheckBox chkTeacher, chkStudent;
//    private Button btnRegister;
//    private DatabaseHelper dbHelper;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_sign_up);
//
//        editTextEmail = findViewById(R.id.txtSignUser);
//        editTextPassword = findViewById(R.id.txtSignPass);
//        chkTeacher = findViewById(R.id.chkTeacher);
//        chkStudent = findViewById(R.id.chkStudent);
//        btnRegister = findViewById(R.id.btnSignIn);
//
//        dbHelper = new DatabaseHelper(this);
//
//        chkTeacher.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                chkStudent.setChecked(false);
//            }
//        });
//
//        chkStudent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                chkTeacher.setChecked(false);
//            }
//        });
//
//        btnRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                registerUser();
//            }
//        });
//    }
//
//    private void registerUser() {
//        String email = editTextEmail.getText().toString().trim();
//        String password = editTextPassword.getText().toString().trim();
//
//        if (TextUtils.isEmpty(email)) {
//            editTextEmail.setError("Please enter email");
//            return;
//        }
//
//        if (TextUtils.isEmpty(password)) {
//            editTextPassword.setError("Please enter password");
//            return;
//        }
//
//        String role = null;
//        if (chkTeacher.isChecked()) {
//            role = "teacher";
//        } else if (chkStudent.isChecked()) {
//            role = "student";
//        } else {
//            Toast.makeText(this, "Please select a user type", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        // Default values for other user attributes
//        String name = "User Name"; // You can modify to take user input if required
//        String gender = "Not Specified"; // You can modify to take user input if required
//        String section = ""; // Only relevant for students
//
//        if (role.equals("student")) {
//            // You can add a UI element to get the section and set it here
//            section = "Default Section"; // Example, replace with actual section if needed
//        }
//
//        boolean result = dbHelper.addUser(name, email, password, role, gender, section);
//        if (result) {
//            Toast.makeText(this, role.substring(0, 1).toUpperCase() + role.substring(1) + " Registration Successful", Toast.LENGTH_SHORT).show();
//            Intent intent;
//            if (role.equals("teacher")) {
//                intent = new Intent(SignUpActivity.this, TeacherProfile.class);
//            } else {
//                intent = new Intent(SignUpActivity.this, StudentProfileActivity.class);
//            }
//            startActivity(intent);
//        } else {
//            Toast.makeText(this, "Registration Failed. Email may already be in use.", Toast.LENGTH_SHORT).show();
//        }
//    }
//}
