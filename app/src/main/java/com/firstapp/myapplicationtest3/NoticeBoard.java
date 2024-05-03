package com.firstapp.myapplicationtest3;

import static com.firstapp.myapplicationtest3.R.id.ImgBack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class NoticeBoard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_board);

        String UserType = getIntent().getStringExtra("UserType"); //taking the user type from previous activity
        String SId = getIntent().getStringExtra("SId"); //taking the kry from previous activity
        String SName = getIntent().getStringExtra("SName"); //taking the kry from previous activity


        ImageView BackButton = findViewById(R.id.BackImg);

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OpenStudentDashBoard(UserType,SId,SName);
            }
        });

        LoadingMassages();
    }


    public void OpenStudentDashBoard(String UserType,String SId,String SName){
        Intent intent1 = new Intent(this, StudentDashBoard.class);
        intent1.putExtra("UserType", UserType);
        intent1.putExtra("SId", SId); //attaching the user type as variable with intent1
        intent1.putExtra("SName", SName);
        startActivity(intent1);
    }

    public void LoadingMassages(){
        RelativeLayout l = findViewById(R.id.relativeLayout);
        FirebaseFirestore db = FirebaseFirestore.getInstance();


// Define the condition you want to filter by
        //   String conditionField = "yourConditionFieldValue";

// Create a FireStore query to filter documents based on the condition
        Query query = db.collection("Notices")
                // .orderBy("Date")
                .orderBy("DateAndTime");//.whereEqualTo("conditionField", conditionField);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                int i=0;
                for (QueryDocumentSnapshot document : task.getResult()) {
                    // Retrieve the desired field from each document
                    String Massage = document.getString("Massage");
                    String Teacher = document.getString("TeacherName");
                    String DateAndTime =document.getString("DateAndTime");


                    //int id = View.generateViewId();
                    TextView Writer = new TextView(this);
                    Writer.setText(Teacher+"   "+DateAndTime);
                    Writer.setTextSize(10);
                    Writer.setId(i);
                    Writer.setPadding(5,5,5,5);


                    TextView text = new TextView(this);
                    // Generate a unique ID for each TextView
                    text.setId(i+1);

                    text.setText(Massage);
                    text.setBackground(getResources().getDrawable(R.drawable.massage));
                    text.setTextSize(20);
                    text.setPadding(50,5,50,5);


                    RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT
                    );
                    RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT
                    );

                    if (i == 0) {
                        params2.addRule(RelativeLayout.BELOW,R.id.Heading);// Align the first TextView to the top
                        params1.addRule(RelativeLayout.BELOW, i);
                        params1.setMargins(50, 70, 50, 15);
                    } else {
                        params2.addRule(RelativeLayout.BELOW, i-1);
                        params1.addRule(RelativeLayout.BELOW, i); // Align subsequent TextViews below the previous one
                        params1.setMargins(50, 1, 50, 15);
                    }
                    // Set margins for the TextView (left, top, right, bottom)
                    params2.setMargins(30, 15, 50, 1);

                    Writer.setLayoutParams(params2);
                    text.setLayoutParams(params1);

                    l.addView(text);
                    l.addView(Writer);

                    i=i+2;

                }

                Toast.makeText(NoticeBoard.this, "Massage loading is Completed.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(NoticeBoard.this, "Massage loading is failed.", Toast.LENGTH_SHORT).show();

            }

            final ScrollView scrollView = (ScrollView) findViewById(R.id.ScrollNotices);
            scrollView.post(new Runnable() {
                public void run() {
                    scrollView.fullScroll(View.FOCUS_DOWN);
                }
            });

        });

    }

}