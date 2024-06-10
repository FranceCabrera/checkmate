package com.example.checkmateapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.security.SecureRandom;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Name and Version
    private static final String DATABASE_NAME = "checkmate.db";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    public static final String TABLE_USERS = "Users";
    public static final String TABLE_CLASSES = "Classes";
    public static final String TABLE_ENROLLMENTS = "Enrollments";
    public static final String TABLE_ATTENDANCE = "Attendance";
    public static final String TABLE_TEACHERS = "Teachers"; // Assign table name for teachers
    public static final String TABLE_STUDENTS = "Students"; // Assign table name for students


    // Common Column Names
    public static final String COLUMN_ID = "id";

    // Users Table Columns
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_ROLE = "role";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_STUDENT_SECTION = "student_section";
    public static final String COLUMN_TEACHER_ID = "teacher_id";

    // Classes Table Columns
    public static final String COLUMN_CLASS_NAME = "class_name";
    public static final String COLUMN_CLASS_CODE = "class_code";
    public static final String COLUMN_CLASS_TEACHER_ID = "teacher_id";
    public static final String COLUMN_COURSE_TIME = "courseTime";

    // Enrollments Table Columns
    public static final String COLUMN_CLASS_ID = "class_id";
    public static final String COLUMN_STUDENT_ID = "student_id";

    // Attendance Table Columns
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_STATUS = "status";

    // Table Create Statements
    // Users table create statement
// Users Table
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_EMAIL + " TEXT UNIQUE,"
            + COLUMN_PASSWORD + " TEXT,"
            + COLUMN_ROLE + " TEXT" + ")";

    // Teachers Table
    private static final String CREATE_TABLE_TEACHERS = "CREATE TABLE " + TABLE_TEACHERS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_EMAIL + " TEXT UNIQUE,"
            + COLUMN_PASSWORD + " TEXT,"
            + COLUMN_GENDER + " TEXT,"
            + COLUMN_TEACHER_ID + " TEXT" + ")";

    private static final String CREATE_TABLE_STUDENTS = "CREATE TABLE " + TABLE_STUDENTS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_EMAIL + " TEXT UNIQUE,"
            + COLUMN_PASSWORD + " TEXT,"
            + COLUMN_GENDER + " TEXT,"
            + COLUMN_STUDENT_SECTION + " TEXT" + ")";





    // Classes table create statement
    private static final String CREATE_TABLE_CLASSES = "CREATE TABLE " + TABLE_CLASSES + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_CLASS_NAME + " TEXT,"
            + COLUMN_CLASS_CODE + " TEXT UNIQUE,"
            + COLUMN_COURSE_TIME + " TEXT,"
            + COLUMN_CLASS_TEACHER_ID + " INTEGER,"
            + " FOREIGN KEY (" + COLUMN_CLASS_TEACHER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_ID + "))";

    // Enrollments table create statement
    private static final String CREATE_TABLE_ENROLLMENTS = "CREATE TABLE " + TABLE_ENROLLMENTS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_CLASS_ID + " INTEGER,"
            + COLUMN_STUDENT_ID + " INTEGER,"
            + " FOREIGN KEY (" + COLUMN_CLASS_ID + ") REFERENCES " + TABLE_CLASSES + "(" + COLUMN_ID + "),"
            + " FOREIGN KEY (" + COLUMN_STUDENT_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_ID + "))";

    // Attendance table create statement
    private static final String CREATE_TABLE_ATTENDANCE = "CREATE TABLE " + TABLE_ATTENDANCE + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_CLASS_ID + " INTEGER,"
            + COLUMN_STUDENT_ID + " INTEGER,"
            + COLUMN_DATE + " TEXT,"
            + COLUMN_STATUS + " TEXT,"
            + " FOREIGN KEY (" + COLUMN_CLASS_ID + ") REFERENCES " + TABLE_CLASSES + "(" + COLUMN_ID + "),"
            + " FOREIGN KEY (" + COLUMN_STUDENT_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_ID + "))";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create users table first
        db.execSQL(CREATE_TABLE_USERS);

        // Then create teachers and students tables
        db.execSQL(CREATE_TABLE_TEACHERS);
        db.execSQL(CREATE_TABLE_STUDENTS);

        // Finally, create classes, enrollments, and attendance tables
        db.execSQL(CREATE_TABLE_CLASSES);
        db.execSQL(CREATE_TABLE_ENROLLMENTS);
        db.execSQL(CREATE_TABLE_ATTENDANCE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLASSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENROLLMENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ATTENDANCE);

        // Create new tables
        onCreate(db);
    }

    // Method to add a user
    // Method to add a user
// Method to add a user
    public boolean addStudentUser(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean result = false;

        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_EMAIL, email);
            values.put(COLUMN_PASSWORD, password);

            long insertResult = db.insert(TABLE_STUDENTS, null, values);
            result = insertResult != -1;
        } catch (SQLiteConstraintException e) {
            // Log the exception and return false if email is already in use
            Log.e("DatabaseHelper", "Error inserting student user: Email already in use", e);
        } finally {
            db.close();
        }

        return result;
    }

    public boolean addTeacherUser(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean result = false;

        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_EMAIL, email);
            values.put(COLUMN_PASSWORD, password);
            long insertResult = db.insert(TABLE_TEACHERS, null, values);
            result = insertResult != -1;
        } catch (SQLiteConstraintException e) {
            // Log the exception and return false if email is already in use
            Log.e("DatabaseHelper", "Error inserting teacher user: Email already in use", e);
        } finally {
            db.close();
        }

        return result;
    }


    public boolean addTeacherUser(String email, String password, String teacherID) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean result = false;

        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_EMAIL, email);
            values.put(COLUMN_PASSWORD, password);
            values.put(COLUMN_ROLE, "teacher");
            values.put(COLUMN_TEACHER_ID, teacherID);

            long insertResult = db.insert(TABLE_TEACHERS, null, values);
            result = insertResult != -1;
        } catch (SQLiteConstraintException e) {
            // Log the exception and return false if email is already in use
            Log.e("DatabaseHelper", "Error inserting teacher user: Email already in use", e);
        } finally {
            db.close();
        }

        return result;
    }


    // Method to add a student profile
// Add method to add a teacher profile
    public boolean addTeacherProfile(String email, String teacherId, String name, String password, String gender) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_TEACHER_ID, teacherId);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_GENDER, gender);
        long result = db.insert(TABLE_TEACHERS, null, values);
        db.close();
        return result != -1;
    }

    // Add method to add a student profile
    public boolean addStudentProfile(String email, String studentSection, String name, String password, String gender) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_STUDENT_SECTION, studentSection);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_GENDER, gender);
        long result = db.insert(TABLE_STUDENTS, null, values);
        db.close();
        return result != -1;
    }


    // Method to generate a unique class code
    public String generateClassCode() {
        SQLiteDatabase db = this.getReadableDatabase();
        SecureRandom random = new SecureRandom();
        String classCode;
        boolean isUnique;

        do {
            // Generate a random alphanumeric string of at least 5 characters
            classCode = new java.math.BigInteger(30, random).toString(32);
            if (classCode.length() < 5) {
                classCode = classCode + new java.math.BigInteger(5 * 5, random).toString(32).substring(0, 5 - classCode.length());
            }

            // Check if the generated code is unique
            Cursor cursor = db.query(TABLE_CLASSES, null, COLUMN_CLASS_CODE + " = ?", new String[]{classCode}, null, null, null);
            isUnique = cursor.getCount() == 0;
            cursor.close();
        } while (!isUnique);

        return classCode;
    }

    // Method to get teacher ID by email
    public int getTeacherIdByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        int teacherId = -1;
        Cursor cursor = db.query(TABLE_USERS, new String[]{COLUMN_ID}, COLUMN_EMAIL + " = ? AND " + COLUMN_ROLE + " = ?", new String[]{email, "teacher"}, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                teacherId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            }
            cursor.close();
        }

        return teacherId;
    }
    // Add this method to your DatabaseHelper class
    public ArrayList<String> getTeacherClasses(int teacherId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> teacherClasses = new ArrayList<>();

        // Query the classes table to retrieve classes associated with the given teacher ID
        Cursor cursor = db.query(TABLE_CLASSES, new String[]{COLUMN_CLASS_CODE, COLUMN_CLASS_NAME, COLUMN_COURSE_TIME},
                COLUMN_CLASS_TEACHER_ID + " = ?", new String[]{String.valueOf(teacherId)}, null, null, null);

        // Iterate through the cursor and add each class to the ArrayList
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String classCode = cursor.getString(cursor.getColumnIndex(COLUMN_CLASS_CODE));
                String className = cursor.getString(cursor.getColumnIndex(COLUMN_CLASS_NAME));
                String courseTime = cursor.getString(cursor.getColumnIndex(COLUMN_COURSE_TIME));
                teacherClasses.add("Code: " + classCode + ", Name: " + className + ", Time: " + courseTime);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return teacherClasses;
    }

    // Method to add a class
// Method to add a class
    public boolean addClass(String className, String classCode, int teacherId, String courseTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean result = false;

        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_CLASS_NAME, className);
            values.put(COLUMN_CLASS_CODE, classCode);
            values.put(COLUMN_CLASS_TEACHER_ID, teacherId);
            values.put(COLUMN_COURSE_TIME, courseTime);

            long insertResult = db.insert(TABLE_CLASSES, null, values);
            result = insertResult != -1;
        } catch (SQLiteConstraintException e) {
            // Log the exception and return false if class code is already in use
            Log.e("DatabaseHelper", "Error adding class: Class code already in use", e);
        } finally {
            db.close();
        }

        return result;
    }




}

