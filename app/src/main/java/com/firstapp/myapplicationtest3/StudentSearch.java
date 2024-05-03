package com.firstapp.myapplicationtest3;

import static com.google.firebase.firestore.Filter.equalTo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;

public class StudentSearch extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_search);
        String UserType = getIntent().getStringExtra("UserType"); //taking the user type from previous activity
        String TId = getIntent().getStringExtra("TId"); //taking the kry from previous activity
        String TName = getIntent().getStringExtra("TName"); //taking the kry from previous activity

        ImageView Backbtn = findViewById(R.id.ImgBack);

        Backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTeacherDashBoard(UserType,TId,TName);
            }
        });

        Button StudentSearch = findViewById(R.id.StudentSearch);

        StudentSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CloseKeyBoard();
                Search();
            }
        });


    }
    public void openTeacherDashBoard(String UserType,String TId,String TName){
        Intent intent1 = new Intent(this, TeacherDashBoard.class);
        intent1.putExtra("UserType", UserType);
        intent1.putExtra("TId", TId); //attaching the user type as variable with intent1
        intent1.putExtra("TName", TName);
        startActivity(intent1);
    }

    public void Search(){

        EditText StudentID=findViewById(R.id.StudentID);

        String SID = StudentID.getText().toString();


        FirebaseFirestore db = FirebaseFirestore.getInstance(); // Initialize the Firestore instance

        DocumentReference documentReference = db.collection("StudentProfile").document(SID);

        documentReference.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            // Document exists, retrieve data into a HashMap
                            HashMap<String, Object> StudentData = (HashMap<String, Object>) documentSnapshot.getData();

                            if (StudentData != null) {
                                // Update TextViews with retrieved data
                                TextView Name = findViewById(R.id.Name);
                                Name.setText((String) StudentData.get("StudentName"));

                                TextView Contact = findViewById(R.id.Contact);
                                Contact.setText((String) StudentData.get("StudentContact"));

                                TextView Grade = findViewById(R.id.Grade);
                                String grade=StudentData.get("StudentGrade").toString();
                                Grade.setText(grade);

                                TextView Class = findViewById(R.id.Class);
                                Class.setText((String) StudentData.get("StudentClass"));

                            }
                        } else {
                            Toast.makeText(StudentSearch.this, "There is no such Student in our system", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(StudentSearch.this, "Please check Student id that you provided", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void CloseKeyBoard(){                //key board closing function
        View view=this.getCurrentFocus();
        if(view != null){
            InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }
}