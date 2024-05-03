package com.firstapp.myapplicationtest3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class StudentDashBoard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dash_board);

        String UserType = getIntent().getStringExtra("UserType");  //taking the user type from previous activity
        String SId = getIntent().getStringExtra("SId");           //taking the key from previous activity
        String SName = getIntent().getStringExtra("SName");        //taking the key from previous activity

        TextView StudentName = findViewById(R.id.StudentName);  //setting the student name

        StudentName.setText("Hi, Welcome "+SName);      //displaying the user name

        TextView currentTD = findViewById(R.id.Date);    //creating a TextView object by the Date textview element in student dash board layout
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy, MMMM d,'  ' EEEE "); // selecting a format for represent the date
        String currentDateAndTime = sdf.format(new Date());          //creating a string variable for date
        currentTD.setText(currentDateAndTime);                       //set the string variable to the Date text view in Student dash board Layout


        ImageView Backbtn = findViewById(R.id.ImgBack);

        Backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openLogin(UserType);
            }
        });

        ImageView TimeTableView = findViewById(R.id.TimeTableView);
        TextView TimeTableTextView = findViewById(R.id.TimeTableTextView);

        TimeTableView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openTimeTable(UserType,SId,SName);
            }
        });

        TimeTableTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openTimeTable(UserType,SId,SName);
            }
        });

        ImageView MarksView = findViewById(R.id.MarksView);
        TextView MarksTextView = findViewById(R.id.MarksTextView);

        MarksView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openStudentMarks(UserType,SId,SName);
            }
        });

        MarksTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openStudentMarks(UserType,SId,SName);
            }
        });

        ImageView ProfileView = findViewById(R.id.ProfileView);
        TextView ProfileTextView = findViewById(R.id.ProfileTextView);

        ProfileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openStudentProfile(UserType,SId,SName);
            }
        });

        ProfileTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openStudentProfile(UserType,SId,SName);
            }
        });

        ImageView NoticeBoardView = findViewById(R.id.NoticeBoardView);
        TextView NoticeBoardTextView = findViewById(R.id.NoticeBoardTextView);

        NoticeBoardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openNoticeBoard(UserType,SId,SName);
            }
        });

        NoticeBoardTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openNoticeBoard(UserType,SId,SName);
            }
        });

    }


    public void openLogin(String UserType){
        Intent intent1 = new Intent(this, Login.class);
        intent1.putExtra("UserType", UserType);                  //attaching the user type as variable with intent1
        startActivity(intent1);
    }

    public void openTimeTable(String UserType,String SId,String SName){
        Intent intent1 = new Intent(this, TimeTable.class);
        intent1.putExtra("UserType", UserType);
        intent1.putExtra("SId", SId); //attaching the user type as variable with intent1
        intent1.putExtra("SName", SName);
        startActivity(intent1);
    }


    public void openStudentMarks(String UserType,String SId,String SName){
        Intent intent1 = new Intent(this, StudentMarks.class);
        intent1.putExtra("UserType", UserType);
        intent1.putExtra("SId", SId); //attaching the user type as variable with intent1
        intent1.putExtra("SName", SName);
        startActivity(intent1);
    }

    public void openStudentProfile(String UserType,String SId,String SName){
        Intent intent1 = new Intent(this, StudentProfile.class);
        intent1.putExtra("UserType", UserType);
        intent1.putExtra("SId", SId); //attaching the user type as variable with intent1
        intent1.putExtra("SName", SName);
        startActivity(intent1);
    }

    public void openNoticeBoard(String UserType,String SId,String SName){
        Intent intent1 = new Intent(this, NoticeBoard.class);
        intent1.putExtra("UserType", UserType);
        intent1.putExtra("SId", SId); //attaching the user type as variable with intent1
        intent1.putExtra("SName", SName);
        startActivity(intent1);
    }
}