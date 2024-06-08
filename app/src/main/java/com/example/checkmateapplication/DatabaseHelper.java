package com.example.checkmateapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "CheckMateDatabase";

    // Table Names
    public static final String TABLE_STUDENTS = "students";
    public static final String TABLE_TEACHERS = "teachers";
    public static final String TABLE_CLASSES = "classes";
    public static final String TABLE_ATTENDANCE = "attendance";

    // Common column names
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";

    // Student Profile column names
    public static final String COLUMN_STUDENT_NUMBER = "student_number";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SECTION = "section";
    public static final String COLUMN_GENDER = "gender";

    // Teacher Profile column names
    public static final String COLUMN_TEACHER_ID = "teacher_id";
    public static final String COLUMN_TEACHER_NAME = "name";

    // Class column names
    public static final String COLUMN_CLASS_CODE = "class_code";
    public static final String COLUMN_COURSE_NAME = "course_name";
    public static final String COLUMN_COURSE_TIME = "course_time";

    // Attendance column names
    public static final String COLUMN_ATTENDANCE_DATE = "attendance_date";
    public static final String COLUMN_ATTENDANCE_STATUS = "attendance_status";
    public static final String COLUMN_CLASS_ID = "class_id";

    // Table Create Statements
    // Student table create statement
    private static final String CREATE_TABLE_STUDENTS = "CREATE TABLE "
            + TABLE_STUDENTS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_EMAIL + " TEXT,"
            + COLUMN_PASSWORD + " TEXT,"
            + COLUMN_STUDENT_NUMBER + " TEXT,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_SECTION + " TEXT,"
            + COLUMN_GENDER + " TEXT" + ")";

    // Teacher table create statement
    private static final String CREATE_TABLE_TEACHERS = "CREATE TABLE "
            + TABLE_TEACHERS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_EMAIL + " TEXT,"
            + COLUMN_PASSWORD + " TEXT,"
            + COLUMN_TEACHER_ID + " TEXT,"
            + COLUMN_TEACHER_NAME + " TEXT" + ")";

    // Class table create statement
    private static final String CREATE_TABLE_CLASSES = "CREATE TABLE "
            + TABLE_CLASSES + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_CLASS_CODE + " TEXT,"
            + COLUMN_COURSE_NAME + " TEXT,"
            + COLUMN_COURSE_TIME + " TEXT" + ")";

    // Attendance table create statement
    private static final String CREATE_TABLE_ATTENDANCE = "CREATE TABLE "
            + TABLE_ATTENDANCE + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_ATTENDANCE_DATE + " TEXT,"
            + COLUMN_ATTENDANCE_STATUS + " TEXT,"
            + COLUMN_STUDENT_NUMBER + " TEXT,"
            + COLUMN_CLASS_ID + " INTEGER,"
            + "FOREIGN KEY(" + COLUMN_STUDENT_NUMBER + ") REFERENCES " + TABLE_STUDENTS + "(" + COLUMN_ID + "),"
            + "FOREIGN KEY(" + COLUMN_CLASS_ID + ") REFERENCES " + TABLE_CLASSES + "(" + COLUMN_ID + "))";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_STUDENTS);
        db.execSQL(CREATE_TABLE_TEACHERS);
        db.execSQL(CREATE_TABLE_CLASSES);
        db.execSQL(CREATE_TABLE_ATTENDANCE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEACHERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLASSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ATTENDANCE);
        // create new tables
        onCreate(db);
    }

    public boolean addUser(String email, String password, String table) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);

        long result = db.insert(table, null, values);
        return result != -1; // return true if insert is successful
    }

    public boolean addStudentProfile(String email, String studentNumber, String name, String section, String gender) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_STUDENT_NUMBER, studentNumber);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_SECTION, section);
        values.put(COLUMN_GENDER, gender);

        long result = db.insert(TABLE_STUDENTS, null, values);
        return result != -1; // return true if insert is successful
    }

    public boolean addTeacherProfile(String email, String teacherID, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_TEACHER_ID, teacherID);
        values.put(COLUMN_TEACHER_NAME, name);

        long result = db.insert(TABLE_TEACHERS, null, values);
        return result != -1; // return true if insert is successful
    }

    public boolean addClass(String classCode, String courseName, String courseTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CLASS_CODE, classCode);
        values.put(COLUMN_COURSE_NAME, courseName);
        values.put(COLUMN_COURSE_TIME, courseTime);

        long result = db.insert(TABLE_CLASSES, null, values);
        return result != -1; // return true if insert is successful
    }

    public List<String> getAllClasses() {
        List<String> classList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CLASSES,
                new String[]{COLUMN_CLASS_CODE, COLUMN_COURSE_NAME, COLUMN_COURSE_TIME},
                null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String classCode = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLASS_CODE));
                String courseName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COURSE_NAME));
                String courseTime = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COURSE_TIME));
                classList.add("Code: " + classCode + ", Name: " + courseName + ", Time: " + courseTime);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return classList;
    }

    public String generateClassCode() {
        return UUID.randomUUID().toString();
    }

    // Method to mark attendance
    public boolean markAttendance(String studentEmail, String classCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ATTENDANCE_DATE, System.currentTimeMillis());
        values.put(COLUMN_ATTENDANCE_STATUS, "Present");
        values.put(COLUMN_STUDENT_NUMBER, getStudentNumberByEmail(studentEmail));
        values.put(COLUMN_CLASS_ID, getClassIdByCode(classCode));

        long result = db.insert(TABLE_ATTENDANCE, null, values);
        return result != -1; // return true if insert is successful
    }

    // Helper method to get student number by email
    private String getStudentNumberByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_STUDENTS,
                new String[]{COLUMN_STUDENT_NUMBER},
                COLUMN_EMAIL + "=?",
                new String[]{email},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndexOrThrow(COLUMN_STUDENT_NUMBER);
            String studentNumber = cursor.getString(columnIndex);
            cursor.close();
            return studentNumber;
        }
        if (cursor != null) {
            cursor.close();
        }
        return null;
    }

    // Helper method to get class ID by class code
    private int getClassIdByCode(String classCode) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CLASSES,
                new String[]{COLUMN_ID},
                COLUMN_CLASS_CODE + "=?",
                new String[]{classCode},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            try {
                int columnIndex = cursor.getColumnIndexOrThrow(COLUMN_ID);
                int classId = cursor.getInt(columnIndex);
                return classId;
            } finally {
                cursor.close();
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return -1;
    }

    // Method to get user name by email
    public String getUserName(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_STUDENTS,
                new String[]{COLUMN_NAME},
                COLUMN_EMAIL + "=?",
                new String[]{email},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            try {
                int columnIndex = cursor.getColumnIndexOrThrow(COLUMN_NAME);
                String userName = cursor.getString(columnIndex);
                return userName;
            } finally {
                cursor.close();
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return null;
    }

    // Method to get attendance for a specific class
    public List<String> getAttendanceByClassId(int classId) {
        List<String> attendanceList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ATTENDANCE,
                new String[]{COLUMN_STUDENT_NUMBER, COLUMN_ATTENDANCE_DATE, COLUMN_ATTENDANCE_STATUS},
                COLUMN_CLASS_ID + "=?",
                new String[]{String.valueOf(classId)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            try {
                do {
                    String studentNumber = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDENT_NUMBER));
                    String attendanceDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ATTENDANCE_DATE));
                    String attendanceStatus = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ATTENDANCE_STATUS));
                    attendanceList.add("Student: " + studentNumber + ", Date: " + attendanceDate + ", Status: " + attendanceStatus);
                } while (cursor.moveToNext());
            } finally {
                cursor.close();
            }
        }
        return attendanceList;
    }
}
