package com.firstapp.myapplicationtest3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class TeacherRegistration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_registration);

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
                    Toast.makeText(TeacherRegistration.this,"Please,check your data and try again",Toast.LENGTH_SHORT).show();
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
                    EditText TeacherPassword = findViewById(R.id.TeacherPassword);
                    TeacherPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                    eye[0]=false;
                } else {
                    // Change the image to the "on" state
                    Eye1.setImageResource(R.drawable.hide); // Replace with your on-state image resource
                    EditText TeacherPassword = findViewById(R.id.TeacherPassword); // Replace with your EditText ID
                    TeacherPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
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
                                            EditText TeacherConfirmPassword = findViewById(R.id.ConfirmPassword);
                                            TeacherConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                                            eye[1]=false;
                                        } else {
                                            // Change the image to the "on" state
                                            Eye2.setImageResource(R.drawable.hide); // Replace with your on-state image resource
                                            EditText TeacherConfirmPassword = findViewById(R.id.ConfirmPassword); // Replace with your EditText ID
                                            TeacherConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
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

        EditText TeacherName = findViewById(R.id.TeacherName);
        EditText TeacherID = findViewById(R.id.TeacherID);
        EditText TeacherNIC = findViewById(R.id.TeacherNIC);
        EditText TeacherContact = findViewById(R.id.TeacherContact);
        EditText TeacherPassword = findViewById(R.id.TeacherPassword);

        String TName = TeacherName.getText().toString();
        String TId = TeacherID.getText().toString();
        String TNIC = TeacherNIC.getText().toString();
        String TContact = TeacherContact.getText().toString();
        String TPassword = TeacherPassword.getText().toString();

        Map<String, Object> TeacherProfile = new HashMap<>();
        TeacherProfile.put("TeacherId", TId);
        TeacherProfile.put("TeacherName", TName);
        TeacherProfile.put("TeacherNIC", TNIC);
        TeacherProfile.put("TeacherContact", TContact);
        TeacherProfile.put("TeacherPassword", TPassword);

        db.collection("TeacherProfile").document(TId)
                .set(TeacherProfile)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(TeacherRegistration.this, "Your account is registered.", Toast.LENGTH_SHORT).show();
                        openLoginPage(UserType);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(TeacherRegistration.this, "Your account is not registered.", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    public boolean Confirmation() {
        EditText TeacherPassword = findViewById(R.id.TeacherPassword);
        String TP = TeacherPassword.getText().toString();
        EditText ConfirmPassword = findViewById(R.id.ConfirmPassword);
        String CP = ConfirmPassword.getText().toString();

        if (TP.equals(CP)) {
            EditText TeacherName = findViewById(R.id.TeacherName);
            EditText TeacherID = findViewById(R.id.TeacherID);
            EditText TeacherNIC = findViewById(R.id.TeacherNIC);
            EditText TeacherContact = findViewById(R.id.TeacherContact);

            String TName = TeacherName.getText().toString();
            String TId = TeacherID.getText().toString();
            String TNIC = TeacherNIC.getText().toString();
            String TContact = TeacherContact.getText().toString();

            if (TName.isEmpty() || TId.isEmpty() || TNIC.isEmpty()||TContact.isEmpty()||TP.isEmpty()||CP.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return false;
            }else if (!TName.matches("^[a-zA-Z ]*$")) {
                Toast.makeText(this, "Invalid Teacher Name", Toast.LENGTH_SHORT).show();
                return false;

            } else if (TP.length() < 6) {
                Toast.makeText(this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show();
                return false;
            } else{
                return true;
            }

        } else {
            Toast.makeText(this, "Please,Confirm your password", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


}