package com.example.checkmateapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class HomeStud extends AppCompatActivity {

    private TextView textViewUserName;
    private EditText editTextClassCode;
    private Button btnAddClass, btnLogout;
    private ListView listViewClasses;
    private CalendarView calendarView;
    private ArrayList<String> classList;
    private ArrayAdapter<String> classAdapter;
    private DatabaseHelper dbHelper;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_stud);

        textViewUserName = findViewById(R.id.textViewUserName);
        editTextClassCode = findViewById(R.id.editTextClassCode);
        btnAddClass = findViewById(R.id.btnAddClass);
        btnLogout = findViewById(R.id.btnLogout);
        listViewClasses = findViewById(R.id.listViewClasses);
        calendarView = findViewById(R.id.calendarView);

        dbHelper = new DatabaseHelper(this);
        classList = new ArrayList<>();
        classAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, classList);
        listViewClasses.setAdapter(classAdapter);



        String userName = dbHelper.getUserName(userEmail);
        if (userName == null) {
            Toast.makeText(this, "Error: User not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        textViewUserName.setText("Welcome, " + userName);

        btnAddClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addClass();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear login state and redirect to Main Activity
                SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                Intent intent = new Intent(HomeStud.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        listViewClasses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String classCode = classList.get(position);
                Intent intent = new Intent(HomeStud.this, AttendanceChecker.class);
                intent.putExtra("classCode", classCode);
                intent.putExtra("email", userEmail);
                startActivity(intent);
            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // Handle date change
                Toast.makeText(HomeStud.this, "Selected date: " + dayOfMonth + "/" + (month + 1) + "/" + year, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addClass() {
        String classCode = editTextClassCode.getText().toString();
        if (classCode.isEmpty()) {
            Toast.makeText(this, "Please enter a class code", Toast.LENGTH_SHORT).show();
            return;
        }

        classList.add(classCode);
        classAdapter.notifyDataSetChanged();
        editTextClassCode.setText("");
        Toast.makeText(this, "Class added", Toast.LENGTH_SHORT).show();
    }
}
