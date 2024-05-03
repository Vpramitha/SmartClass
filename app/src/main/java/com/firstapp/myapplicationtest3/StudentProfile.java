package com.firstapp.myapplicationtest3;

import static java.lang.Integer.parseInt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class StudentProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        String UserType = getIntent().getStringExtra("UserType"); //taking the user type from previous activity
        String SId = getIntent().getStringExtra("SId"); //taking the kry from previous activity
        String SName = getIntent().getStringExtra("SName"); //taking the kry from previous activity

        LoadProfile(SId);

        ImageView Backbtn = findViewById(R.id.ImgBack);

        Button Save = findViewById(R.id.Save);
        //Save.setVisibility(0%);

        Backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStudentDashBoard(UserType,SId,SName);
            }
        });

        TextView EditProfile =findViewById(R.id.EditProfile);

        EditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditProfile.setEnabled(Boolean.parseBoolean("false"));
                EditProfile.setVisibility(View.INVISIBLE);
                EnableEdit(SId);
            }
        });

    }

    public void EnableEdit(String SId){

        EditText ID = findViewById(R.id.StudentName);
        ID.setEnabled(Boolean.parseBoolean("true"));
        ID.setTextColor(Color.parseColor("#494849"));

        EditText Grade = findViewById(R.id.StudentGrade);
        Grade.setEnabled(Boolean.parseBoolean("true"));
        Grade.setTextColor(Color.parseColor("#494849"));

        EditText Contact = findViewById(R.id.StudentContact);
        Contact.setEnabled(Boolean.parseBoolean("true"));
        Contact.setTextColor(Color.parseColor("#494849"));

        EditText Class = findViewById(R.id.StudentClass);
        Class.setEnabled(Boolean.parseBoolean("true"));
        Class.setTextColor(Color.parseColor("#494849"));

        Button Save = findViewById(R.id.Save);
        Save.setVisibility(View.VISIBLE);
        Save.setEnabled(Boolean.parseBoolean("true"));

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   UpdateProfile(SId);
                    Save.setEnabled(Boolean.parseBoolean("false"));
                    Save.setVisibility(View.INVISIBLE);

                TextView EditProfile =findViewById(R.id.EditProfile);
                EditProfile.setEnabled(Boolean.parseBoolean("true"));
                EditProfile.setVisibility(View.VISIBLE);
                };

        });

    }

    public void UpdateProfile(String SId){


        EditText SName = findViewById(R.id.StudentName);
        String Name=SName.getText().toString();

        EditText SGrade = findViewById(R.id.StudentGrade);
        String Grade=SGrade.getText().toString();

        EditText SContact = findViewById(R.id.StudentContact);
        String Contact=SContact.getText().toString();

        EditText SClass = findViewById(R.id.StudentClass);
        String Class=SClass.getText().toString();


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> updates = new HashMap<>();
        updates.put("StudentName", Name);
        updates.put("StudentGrade",Grade);
        updates.put("StudentClass",Class);
        updates.put("StudentContact",Contact);

        db.collection("StudentProfile")
                .whereEqualTo("StudentId",SId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        DocumentSnapshot documentSnapshot=task.getResult().getDocuments().get(0);
                        String docId = documentSnapshot.getId();
                        db.collection("StudentProfile").document(docId)
                                .update(updates)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(StudentProfile.this,"Your Account is Updated",Toast.LENGTH_SHORT).show();
                                        LoadProfile(SId);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(StudentProfile.this,"Your Account Updating is failed",Toast.LENGTH_SHORT).show();
                                    }
                                });

                    }
                });


    }

    public void LoadProfile(String SId){

        EditText StudentName = findViewById(R.id.StudentName);
        EditText StudentGrade = findViewById(R.id.StudentGrade);
        EditText StudentClass = findViewById(R.id.StudentClass);
        EditText StudentContact = findViewById(R.id.StudentContact);
        EditText StudentId = findViewById(R.id.StudentID);


            FirebaseFirestore db = FirebaseFirestore.getInstance();


            db.collection("StudentProfile")
                    .whereEqualTo("StudentId",SId)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for(QueryDocumentSnapshot document: task.getResult()){

                                    String Name=document.get("StudentName").toString();
                                    StudentName.setText(Name);
                                    StudentName.setTextColor(Color.parseColor("#000000"));
                                    StudentName.setEnabled(Boolean.parseBoolean("false"));

                                    String ID=document.get("StudentId").toString();
                                    StudentId.setText(ID);
                                    StudentId.setTextColor(Color.parseColor("#000000"));
                                    StudentId.setEnabled(Boolean.parseBoolean("false"));

                                    String Contact=document.get("StudentContact").toString();
                                    StudentContact.setText(Contact);
                                    StudentContact.setTextColor(Color.parseColor("#000000"));
                                    StudentContact.setEnabled(Boolean.parseBoolean("false"));

                                    String Class=document.get("StudentClass").toString();
                                    StudentClass.setText(Class);
                                    StudentClass.setTextColor(Color.parseColor("#000000"));
                                    StudentClass.setEnabled(Boolean.parseBoolean("false"));

                                    String Grade=document.get("StudentGrade").toString();
                                    StudentGrade.setText(Grade);
                                    StudentGrade.setTextColor(Color.parseColor("#000000"));
                                    StudentGrade.setEnabled(Boolean.parseBoolean("false"));

                                }
                            }else{
                                Toast.makeText(StudentProfile.this,"Loading Failed ! Please, Check Your Internet Connection",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(StudentProfile.this,"Loading Failed ! Please, Check Your Internet Connection "+e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });

        }

    public void openStudentDashBoard(String UserType,String SId,String SName){
        Intent intent1 = new Intent(this, StudentDashBoard.class);
        intent1.putExtra("UserType", UserType);
        intent1.putExtra("SId", SId); //attaching the user type as variable with intent1
        intent1.putExtra("SName", SName);
        startActivity(intent1);
    }
}