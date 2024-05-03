package com.firstapp.myapplicationtest3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TeacherDashBoard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dash_board);

        String UserType = getIntent().getStringExtra("UserType"); //taking the user type from previous activity
        String TId = getIntent().getStringExtra("TId"); //taking the kry from previous activity
        String TName = getIntent().getStringExtra("TName"); //taking the kry from previous activity

        TextView StudentName = findViewById(R.id.TeacherName);  //setting the student name

        StudentName.setText("Hi, Welcome "+TName);      //displaying the user name

        TextView currentTD = findViewById(R.id.Date);    //creating a TextView object by the Date textview element in student dash board layout
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy, MMMM d,'  ' EEEE "); // selecting a format for represent the date
        String currentDateAndTime = sdf.format(new Date());          //creating a string variable for date
        currentTD.setText(currentDateAndTime);                       //set the string variable to the Date text view in Student dash board Layout


        ImageView Backbtn = findViewById(R.id.ImgBack);

        Backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openStudentLogin(UserType);
            }
        });
/*
        TextView SearchStudent = findViewById(R.id.SearchStudent);

        SearchStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStudentSearch(UserType,key);
            }
        });

        View TimeTableView = findViewById(R.id.TimeTableView);
        TextView TimeTableTextView = findViewById(R.id.TimeTableTextView);

        TimeTableView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTimeTable(UserType,key);
            }
        });

        TimeTableTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTimeTable(UserType,key);
            }
        });
*/
        ImageView ProfileView = findViewById(R.id.ProfileView);
        TextView ProfileTextView = findViewById(R.id.ProfileTextView);

        ProfileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openTeacherProfile(UserType,TId,TName);
            }
        });

        ProfileTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openTeacherProfile(UserType,TId,TName);
            }
        });

        ImageView NoticesView = findViewById(R.id.Notices);
        TextView NoticesTextView = findViewById(R.id.NoticesText);

        NoticesView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openTeacherNoticeBoard(UserType,TId,TName);
            }
        });

        NoticesTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openTeacherNoticeBoard(UserType,TId,TName);
            }
        });

        ImageView TimeTableView = findViewById(R.id.TimeTableView);
        TextView TimeTableTextView = findViewById(R.id.TimeTableTextView);

        TimeTableView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTimeTable(UserType,TId,TName);
            }
        });

        TimeTableTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTimeTable(UserType,TId,TName);
            }
        });

        ImageView SearchView = findViewById(R.id.SearchStudent);
        TextView SearchTextView = findViewById(R.id.SearchStudentTextView);

        SearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStudentSearch(UserType,TId,TName);
            }
        });

        SearchTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStudentSearch(UserType,TId,TName);
            }
        });



    }

    public void openStudentLogin(String UserType){
        Intent intent1 = new Intent(this,Login.class);
        intent1.putExtra("UserType",UserType);
        startActivity(intent1);
    }

    public void openStudentSearch(String UserType,String TId,String TName){
        Intent intent1 = new Intent(this, StudentSearch.class);
        intent1.putExtra("UserType",UserType);
        intent1.putExtra("TId",TId);
        intent1.putExtra("TName",TName);
        startActivity(intent1);
    }

    public void openTimeTable(String UserType,String TId,String TName){
        Intent intent1 = new Intent(this, TeacherTimeTable.class);
        intent1.putExtra("UserType",UserType);
        intent1.putExtra("TId",TId);
        intent1.putExtra("TName",TName);
        startActivity(intent1);
    }

    public void openTeacherProfile(String UserType,String TId,String TName){
        Intent intent1 = new Intent(this, TeacherProfile.class);
        intent1.putExtra("UserType",UserType);
        intent1.putExtra("TId",TId);
        intent1.putExtra("TName",TName);
        startActivity(intent1);
    }

    public void openTeacherNoticeBoard(String UserType,String TId,String TName){
        Intent intent1 = new Intent(this, TeacherNoticeBoard.class);
        intent1.putExtra("UserType", UserType);
        intent1.putExtra("TId", TId); //attaching the user type as variable with intent1
        intent1.putExtra("TName", TName);
        startActivity(intent1);
    }
}