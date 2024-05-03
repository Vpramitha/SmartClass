package com.firstapp.myapplicationtest3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TeacherTimeTable extends AppCompatActivity {

    String day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_time_table);
        String UserType = getIntent().getStringExtra("UserType"); //taking the user type from previous activity
        String SId = getIntent().getStringExtra("TId"); //taking the kry from previous activity
        String SName = getIntent().getStringExtra("TName"); //taking the kry from previous activity

        ImageView Backbtn = findViewById(R.id.ImgBack);

        Backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTeacherDashBoard(UserType,SId,SName);
            }
        });


        TextView Mon=findViewById(R.id.Mon);
        TextView Tue=findViewById(R.id.Tue);
        TextView Wen=findViewById(R.id.Wen);
        TextView Thu =findViewById(R.id.Thu);
        TextView Fri=findViewById(R.id.Fri);


        Mon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                day="Mon";
                ViewTimeTable(day);
            }
        });

        Tue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                day="Tue";
                ViewTimeTable(day);
            }
        });

        Wen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                day="Wen";
                ViewTimeTable(day);
            }
        });

        Thu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                day="Thu";
                ViewTimeTable(day);
            }
        });

        Fri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                day="Fri";
                ViewTimeTable(day);
            }
        });
    }

    public void openTeacherDashBoard(String UserType,String SId,String SName){
        Intent intent1 = new Intent(this, TeacherDashBoard.class);
        intent1.putExtra("UserType", UserType);
        intent1.putExtra("TId", SId); //attaching the user type as variable with intent1
        intent1.putExtra("TName", SName);
        startActivity(intent1);
    }

    public boolean ViewTimeTable(String day){

        TextView Mon=findViewById(R.id.Mon);
        TextView Tue=findViewById(R.id.Tue);
        TextView Wen=findViewById(R.id.Wen);
        TextView Thu =findViewById(R.id.Thu);
        TextView Fri=findViewById(R.id.Fri);

        Teacher st1=new Teacher();

        TextView[] Subject = new TextView[8];

        Subject[0]=findViewById(R.id.Subject1);
        Subject[1]=findViewById(R.id.Subject2);
        Subject[2]=findViewById(R.id.Subject3);
        Subject[3]=findViewById(R.id.Subject4);
        Subject[4]=findViewById(R.id.Subject5);
        Subject[5]=findViewById(R.id.Subject6);
        Subject[6]=findViewById(R.id.Subject7);
        Subject[7]=findViewById(R.id.Subject8);

        Mon.setBackgroundResource(R.drawable.backtext);
        Tue.setBackgroundResource(R.drawable.backtext);
        Wen.setBackgroundResource(R.drawable.backtext);
        Thu.setBackgroundResource(R.drawable.backtext);
        Fri.setBackgroundResource(R.drawable.backtext);

        Mon.setTextColor(Color.parseColor("#000000"));
        Tue.setTextColor(Color.parseColor("#000000"));
        Wen.setTextColor(Color.parseColor("#000000"));
        Thu.setTextColor(Color.parseColor("#000000"));
        Fri.setTextColor(Color.parseColor("#000000"));

        if(day=="Mon"){
            for (int n = 0; n < 8; n++) {
                Subject[n].setText(st1.Mon[n]);
            }
            Mon.setBackgroundResource(R.drawable.day_back_2);
            Mon.setTextColor(Color.parseColor("#FFFFFF"));
        }else if(day=="Tue") {
            for (int n = 0; n < 8; n++) {
                Subject[n].setText(st1.Tue[n]);
            }
            Tue.setBackgroundResource(R.drawable.day_back_2);
            Tue.setTextColor(Color.parseColor("#FFFFFF"));
        }else if(day=="Wen"){
            for (int n = 0; n < 8; n++) {
                Subject[n].setText(st1.Wen[n]);
            }
            Wen.setBackgroundResource(R.drawable.day_back_2);
            Wen.setTextColor(Color.parseColor("#FFFFFF"));
        }else if(day=="Thu"){
            for (int n = 0; n < 8; n++) {
                Subject[n].setText(st1.Thu[n]);
            }
            Thu.setBackgroundResource(R.drawable.day_back_2);
            Thu.setTextColor(Color.parseColor("#FFFFFF"));
        }else if(day=="Fri"){
            for (int n = 0; n < 8; n++) {
                Subject[n].setText(st1.Fri[n]);
            }
            Fri.setBackgroundResource(R.drawable.day_back_2);
            Fri.setTextColor(Color.parseColor("#FFFFFF"));
        }else{
            return false;
        }
        return true;
    }

}