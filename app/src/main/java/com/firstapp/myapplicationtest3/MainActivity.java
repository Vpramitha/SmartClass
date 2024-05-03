package com.firstapp.myapplicationtest3;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        RadioButton selectedRadioButton = findViewById(radioGroup.getCheckedRadioButtonId());

// You can use selectedRadioButton to access the selected option.


        Button btn = findViewById(R.id.Button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openRegisteringPage();
            }
        });
    }


    public void openRegisteringPage(){

            RadioButton radioStudent = (RadioButton) findViewById(R.id.radioStudent); // initiate a student radio button
            Boolean radioStudentState = radioStudent.isChecked(); // check current state of the student radio button

            RadioButton radioTeacher = (RadioButton) findViewById(R.id.radioTeacher); // initiate a student radio button
            Boolean radioTeacherState = radioTeacher.isChecked(); // check current state of the teacher radio button .

        String UserType;

            if(radioStudentState == true && radioTeacherState == false ){
                UserType="Student";
                Intent intent1 = new Intent(this, Login.class);
                intent1.putExtra("UserType",UserType);
                startActivity(intent1);
            }

            else if(radioTeacherState == true && radioStudentState == false) {
                UserType="Teacher";
                Intent intent1 = new Intent(this, Login.class);
                intent1.putExtra("UserType",UserType);
                startActivity(intent1);
            }
            else {
                Toast.makeText(MainActivity.this,"You should select a type of them",Toast.LENGTH_SHORT).show();
            }

            }

}

