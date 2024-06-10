//package com.example.checkmateapplication;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.Toast;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.zxing.integration.android.IntentIntegrator;
//import com.google.zxing.integration.android.IntentResult;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Locale;
//
//public class AttendanceChecker extends AppCompatActivity {
//
//    private Button btnScanQRCode;
//    private DatabaseHelper dbHelper;
//    private String classCode;
//    private String studentEmail;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_attendance_checker);
//
//        btnScanQRCode = findViewById(R.id.btnScanQRCode);
//        dbHelper = new DatabaseHelper(this);
//
//        // Retrieve class code and student email from intent
//        classCode = getIntent().getStringExtra("classCode");
//        studentEmail = getIntent().getStringExtra("email");
//
//        btnScanQRCode.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startQRCodeScanner();
//            }
//        });
//    }
//
//    private void startQRCodeScanner() {
//        IntentIntegrator integrator = new IntentIntegrator(this);
//        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
//        integrator.setPrompt("Scan a QR code for attendance");
//        integrator.setCameraId(0);
//        integrator.setBeepEnabled(true);
//        integrator.setBarcodeImageEnabled(true);
//        integrator.initiateScan();
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
//        if (result != null) {
//            if (result.getContents() == null) {
//                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
//            } else {
//                handleQRCodeResult(result.getContents());
//            }
//        } else {
//            super.onActivityResult(requestCode, resultCode, data);
//        }
//    }
//
//    private void handleQRCodeResult(String qrCodeContent) {
//        // Assuming the QR code content is the class code
//        if (qrCodeContent.equals(classCode)) {
//            int studentId = dbHelper.getStudentIdByEmail(studentEmail);
//            int classId = dbHelper.getClassIdByCode(classCode);  // Assuming classCode can be used to get classId
//            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
//            String status = "Present";
//
//            boolean isInserted = dbHelper.markAttendance(classId, studentId, date, status);
//            if (isInserted) {
//                Toast.makeText(this, "Attendance marked successfully", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this, "Failed to mark attendance", Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            Toast.makeText(this, "Invalid QR code", Toast.LENGTH_SHORT).show();
//        }
//    }
//}
