package com.firstapp.myapplicationtest3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisteringPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registering_page);

        String UserType = getIntent().getStringExtra("UserType");

        ImageView Back = findViewById(R.id.BackImg);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openLoginPage(UserType);
            }
        });

        Button Next = findViewById(R.id.Next);

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Confirmation()){
                Register(UserType);
                }
                else{
                    Toast.makeText(RegisteringPage.this,"Please,check your data and try again",Toast.LENGTH_SHORT).show();
                }

            }
        });

        boolean eye[]=new boolean[2];

        ImageView Eye1 = findViewById(R.id.Eye1);

        eye[0]=true;

        Eye1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (eye[0]) {
                    // Change the image to the "off" state
                    Eye1.setImageResource(R.drawable.view); // Replace with your off-state image resource
                    EditText StudentPassword = findViewById(R.id.StudentPassword);
                    StudentPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                    eye[0]=false;
                } else {
                    // Change the image to the "on" state
                    Eye1.setImageResource(R.drawable.hide); // Replace with your on-state image resource
                    EditText StudentPassword = findViewById(R.id.StudentPassword); // Replace with your EditText ID
                    StudentPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    eye[0]=true;
                }
            }
        });



        ImageView Eye2 =findViewById(R.id.Eye2);
        eye[1]=true;
        Eye2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (eye[1]) {
                                            // Change the image to the "off" state
                                            Eye2.setImageResource(R.drawable.view); // Replace with your off-state image resource
                                            EditText ConfirmPassword = findViewById(R.id.ConfirmPassword);
                                            ConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                                            eye[1]=false;
                                        } else {
                                            // Change the image to the "on" state
                                            Eye2.setImageResource(R.drawable.hide); // Replace with your on-state image resource
                                            EditText ConfirmPassword = findViewById(R.id.ConfirmPassword); // Replace with your EditText ID
                                            ConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                                            eye[1]=true;
                                        }
                                    }
                                }
        );

    }

    public void openLoginPage(String UserType){
        Intent intent1 = new Intent(this, Login.class);
        intent1.putExtra("UserType",UserType);
        startActivity(intent1);
    }

    public void Register(String UserType) {
        FirebaseFirestore db = FirebaseFirestore.getInstance(); // Initialize the Firestore instance

        EditText StudentName = findViewById(R.id.StudentName);
        EditText StudentID = findViewById(R.id.StudentID);
        EditText StudentGrade = findViewById(R.id.StudentGrade);
        EditText StudentClass = findViewById(R.id.StudentClass);
        EditText StudentContact = findViewById(R.id.StudentContact);
        EditText StudentPassword = findViewById(R.id.StudentPassword);

        String SName = StudentName.getText().toString();
        String SId = StudentID.getText().toString();
        int SGrade = Integer.parseInt(StudentGrade.getText().toString());
        String SClass = StudentClass.getText().toString();
        String SContact = StudentContact.getText().toString();
        String SPassword = StudentPassword.getText().toString();

        Map<String, Object> StudentProfile = new HashMap<>();
        StudentProfile.put("StudentId", SId);
        StudentProfile.put("StudentName", SName);
        StudentProfile.put("StudentGrade", SGrade);
        StudentProfile.put("StudentClass", SClass);
        StudentProfile.put("StudentContact", SContact);
        StudentProfile.put("StudentPassword", SPassword);

        db.collection("StudentProfile").document(SId)
                .set(StudentProfile)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(RegisteringPage.this, "Your account is registered.", Toast.LENGTH_SHORT).show();
                        openLoginPage(UserType);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisteringPage.this, "Your account is not registered.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public boolean Confirmation() {
        EditText StudentName = findViewById(R.id.StudentName);
        EditText StudentID = findViewById(R.id.StudentID);
        EditText StudentGrade = findViewById(R.id.StudentGrade);
        EditText StudentClass = findViewById(R.id.StudentClass);
        EditText StudentContact = findViewById(R.id.StudentContact);
        EditText StudentPassword = findViewById(R.id.StudentPassword);
        EditText ConfirmPassword = findViewById(R.id.ConfirmPassword);

        String SName = StudentName.getText().toString();
        String SId = StudentID.getText().toString();
        String SGrade = StudentGrade.getText().toString();
        String SClass = StudentClass.getText().toString();
        String SContact = StudentContact.getText().toString();
        String SP = StudentPassword.getText().toString();
        String CP = ConfirmPassword.getText().toString();

        if (SP.equals(CP)) {

            if (SName.isEmpty() || SId.isEmpty() || SGrade.isEmpty()||SClass.isEmpty()||SContact.isEmpty()||CP.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return false;
            } else if (!SName.matches("^[a-zA-Z ]*$")) {
                Toast.makeText(this, "Invalid Student Name", Toast.LENGTH_SHORT).show();
                return false;
            } else if (SP.length() < 6) {
                Toast.makeText(this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show();
                return false;
            }else if(SGrade.equals("0")||Integer.parseInt(SGrade) > 13||Integer.parseInt(SGrade) < 1){
                Toast.makeText(this, "This is a invalid Grade", Toast.LENGTH_SHORT).show();
                return false;
            } else if(!SClass.matches("^[a-zA-Z]*$")){
                Toast.makeText(this, "Your Class should be a English letter or Word", Toast.LENGTH_SHORT).show();
                return false;
            }else{
                return true;
            }

        } else {
            Toast.makeText(this, "Please confirm your password", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}