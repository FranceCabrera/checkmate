package com.example.checkmateapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.checkmateapplication.R;

import java.util.ArrayList;

public class TeachersDash extends AppCompatActivity {

    private EditText editTextCourseName, editTextCourseTime;
    private Button btnAddClass, btnViewClasses, btnLogout;
    private ListView listViewClasses;
    private ArrayList<String> classList;
    private ArrayAdapter<String> classAdapter;
    private DatabaseHelper dbHelper;

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

        btnAddClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addClass();
            }
        });

        btnViewClasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewClasses();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logout and redirect to Main Activity
                finish();
            }
        });
    }

    private void addClass() {
        String courseName = editTextCourseName.getText().toString();
        String courseTime = editTextCourseTime.getText().toString();
        if (courseName.isEmpty() || courseTime.isEmpty()) {
            Toast.makeText(this, "Please enter course name and time", Toast.LENGTH_SHORT).show();
            return;
        }

        String classCode = dbHelper.generateClassCode();
        boolean isInserted = dbHelper.addClass(classCode, courseName, courseTime);
        if (isInserted) {
            Toast.makeText(this, "Class added successfully", Toast.LENGTH_SHORT).show();
            editTextCourseName.setText("");
            editTextCourseTime.setText("");
        } else {
            Toast.makeText(this, "Failed to add class", Toast.LENGTH_SHORT).show();
        }
    }

    private void viewClasses() {
        classList.clear();
        classList.addAll(dbHelper.getAllClasses());
        classAdapter.notifyDataSetChanged();
    }
}
