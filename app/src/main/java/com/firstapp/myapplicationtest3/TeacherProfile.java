package com.firstapp.myapplicationtest3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TeacherProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);

        String UserType = getIntent().getStringExtra("UserType"); //taking the user type from previous activity
        String TId = getIntent().getStringExtra("TId"); //taking the kry from previous activity
        String TName = getIntent().getStringExtra("TName"); //taking the kry from previous activity

        ImageView Backbtn = findViewById(R.id.ImgBack);

        Backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenTeacherDashBoard(UserType,TId,TName);
            }
        });

        LoadProfile(TId);

    }

    public void OpenTeacherDashBoard(String UserType,String TId,String TName){
        Intent intent1 = new Intent(this, TeacherDashBoard.class);
        intent1.putExtra("TId", TId);
        intent1.putExtra("UserType",UserType);
        intent1.putExtra("TName",TName);
        startActivity(intent1);
    }

    public void LoadProfile(String TId){
        EditText TeacherName =findViewById(R.id.TeacherName);
        EditText TeacherId =findViewById(R.id.TeacherID);
        EditText TeacherNIC =findViewById(R.id.TeacherNIC);
        EditText TeacherContact =findViewById(R.id.TeacherContact);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("TeacherProfile")
                .whereEqualTo("TeacherId",TId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document: task.getResult()){

                                String Name=document.get("TeacherName").toString();
                                TeacherName.setText(Name);

                                String ID=document.get("TeacherId").toString();
                                TeacherId.setText(ID);

                                String NIC=document.get("TeacherNIC").toString();
                                TeacherNIC.setText(NIC);

                                String Contact=document.get("TeacherContact").toString();
                                TeacherContact.setText(Contact);

                            }
                        }else{
                            Toast.makeText(TeacherProfile.this,"Loading Failed ! Please, Check Your Internet Connection",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


    }
