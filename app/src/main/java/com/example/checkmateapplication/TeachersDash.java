package com.example.checkmateapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class TeachersDash extends AppCompatActivity {

    private EditText editTextCourseName, editTextCourseTime;
    private Button btnAddClass, btnViewClasses, btnLogout;
    private ListView listViewClasses;
    private ArrayList<String> classList;
    private ArrayAdapter<String> classAdapter;
    private DatabaseHelper dbHelper;
    private String loggedInTeacherEmail;
    private int loggedInTeacherId; // Add variable to store teacher ID
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers_dash);

        editTextCourseName = findViewById(R.id.editTextCourseName);
        editTextCourseTime = findViewById(R.id.editTextCourseTime);
        btnAddClass = findViewById(R.id.btnAddClass);
        btnViewClasses = findViewById(R.id.btnViewClasses);
        btnLogout = findViewById(R.id.btnLogout);
        listViewClasses = findViewById(R.id.listViewClasses);

        dbHelper = new DatabaseHelper(this);
        classList = new ArrayList<>();
        classAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, classList);
        listViewClasses.setAdapter(classAdapter);

        preferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        loggedInTeacherEmail = preferences.getString("email", "");
        loggedInTeacherId = dbHelper.getTeacherIdByEmail(loggedInTeacherEmail); // Get teacher ID

        btnAddClass.setOnClickListener(v -> {
            String courseName = editTextCourseName.getText().toString().trim();
            String courseTime = editTextCourseTime.getText().toString().trim();

            if (!courseName.isEmpty() && !courseTime.isEmpty()) {
                String classCode = dbHelper.generateClassCode();

                if (loggedInTeacherId != -1) {
                    boolean result = dbHelper.addClass(courseName, classCode, loggedInTeacherId, courseTime); // Use loggedInTeacherId
                    if (result) {
                        Toast.makeText(TeachersDash.this, "Class added successfully", Toast.LENGTH_SHORT).show();
                        classList.add("Code: " + classCode + ", Name: " + courseName + ", Time: " + courseTime);
                        classAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(TeachersDash.this, "Failed to add class", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(TeachersDash.this, "Teacher not found", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(TeachersDash.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            }
        });

        // Filter classes based on teacher ID when viewing classes
        btnViewClasses.setOnClickListener(v -> {
            if (loggedInTeacherId != -1) {
                // Clear the list before populating it again
                classList.clear();
                ArrayList<String> teacherClasses = dbHelper.getTeacherClasses(loggedInTeacherId);
                classList.addAll(teacherClasses);
                classAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(TeachersDash.this, "Teacher not found", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
