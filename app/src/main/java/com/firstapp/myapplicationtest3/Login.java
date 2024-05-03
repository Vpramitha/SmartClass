package com.firstapp.myapplicationtest3;

import static android.widget.Toast.*;


import static com.firstapp.myapplicationtest3.R.id.BackImg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import android.os.Bundle;
import android.view.ViewTreeObserver;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;


public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        String UserType = getIntent().getStringExtra("UserType");

        TextView name= findViewById(R.id.Heading);
        name.setText(UserType + " Login");

        // Initialize the ScrollView
        ScrollView scrollView = findViewById(R.id.scrollView);

        // Add a listener to the ScrollView's view tree observer
        scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // Check if the keyboard is open
                int heightDiff = scrollView.getRootView().getHeight() - scrollView.getHeight();
                if (heightDiff > 100) { // Adjust this threshold as needed
                    // Scroll down the ScrollView
                    scrollView.fullScroll(View.FOCUS_DOWN);
                }
             }});

        ImageView Back = findViewById(R.id.ImgBack);

            Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openMainPage();
            }
        });

        TextView SignUp = findViewById(R.id.SignUp);


        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openSignUp(UserType);
            }
        });

        Button SignIn = findViewById(R.id.Login);

        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CloseKeyBoard();

                if(UserType.equals("Teacher")) {
                    TeacherLogin(UserType); }
                else if(UserType.equals("Student")){
                    StudentLogin(UserType); }
                else{
                    Toast.makeText(Login.this, "Your user type is incorrect", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
    public void openMainPage(){
        Intent intent1 = new Intent(this, MainActivity.class);
        startActivity(intent1);
    }

    public void openSignUp(String UserType){
        CloseKeyBoard();

        if(UserType.equals("Teacher")) {
            Intent intent1 = new Intent(this, TeacherRegistration.class);       //creating intent1 Intent object with Student dash board class
            intent1.putExtra("UserType", UserType);
            startActivity(intent1); }
        else if(UserType.equals("Student")){
            Intent intent1 = new Intent(this, RegisteringPage.class);       //creating intent1 Intent object with Student dash board class
            intent1.putExtra("UserType", UserType);
            startActivity(intent1); }
        else{
            Toast.makeText(Login.this, "Your user type is incorrect", Toast.LENGTH_SHORT).show();
        }

        }



    public void CloseKeyBoard(){                //key board closing function
        View view=this.getCurrentFocus();
        if(view != null){
            InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }
//////////////////////////////////////////////////////////////////////////////////////

    public void TeacherLogin(String UserType){
        EditText UserId = findViewById(R.id.UserId);
        String ID = UserId.getText().toString(); // get text from EditText name view

        EditText UserPassword =findViewById(R.id.UserPassword);
        String Password1 = UserPassword.getText().toString();// get text from EditText password view


        FirebaseFirestore db = FirebaseFirestore.getInstance();


        db.collection("TeacherProfile")
                .whereEqualTo("TeacherId",ID)
                .whereEqualTo("TeacherPassword",Password1)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            DocumentSnapshot document = queryDocumentSnapshots.getDocuments().get(0);
                            String TeacherName =document.getString("TeacherName");
                            Toast.makeText(Login.this,"Login complete",Toast.LENGTH_SHORT).show();
                            LoadTeacherProfile(UserType,ID,TeacherName);


                        } else {
                            Toast.makeText(Login.this,"Your Username or password is incorrect",Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    public void StudentLogin(String UserType){
        EditText UserId = findViewById(R.id.UserId);
        String ID = UserId.getText().toString(); // get text from EditText name view

        EditText UserPassword =findViewById(R.id.UserPassword);
        String Password = UserPassword.getText().toString();// get text from EditText password view

        FirebaseFirestore db = FirebaseFirestore.getInstance();


        db.collection("StudentProfile")
                .whereEqualTo("StudentId",ID)
                .whereEqualTo("StudentPassword",Password)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            DocumentSnapshot document = queryDocumentSnapshots.getDocuments().get(0);
                            String StudentName = document.getString("StudentName");
                            Toast.makeText(Login.this,"Login complete",Toast.LENGTH_SHORT).show();
                            LoadStudentProfile(UserType,ID,StudentName);

                        } else {
                            Toast.makeText(Login.this,"Your Username or password is incorrect",Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }


    public void LoadTeacherProfile(String UserType,String TId,String TName){
        Intent intent1 = new Intent(this, TeacherDashBoard.class);       //creating intent1 Intent object with Student dash board class
        intent1.putExtra("UserType", UserType);
        intent1.putExtra("TId", TId); //attaching the user type as variable with intent1
        intent1.putExtra("TName", TName);
        startActivity(intent1);
    }

    public void LoadStudentProfile(String UserType,String SId,String SName){
        Intent intent1 = new Intent(this, StudentDashBoard.class);       //creating intent1 Intent object with Student dash board class
        intent1.putExtra("UserType", UserType);
        intent1.putExtra("SId", SId); //attaching the user type as variable with intent1
        intent1.putExtra("SName", SName);
        startActivity(intent1);
    }


}