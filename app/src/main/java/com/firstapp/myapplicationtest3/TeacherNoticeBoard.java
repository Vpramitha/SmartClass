package com.firstapp.myapplicationtest3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
public class TeacherNoticeBoard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_notice_board);

        String UserType = getIntent().getStringExtra("UserType"); //taking the user type from previous activity
        String TId = getIntent().getStringExtra("TId"); //taking the kry from previous activity
        String TName = getIntent().getStringExtra("TName"); //taking the kry from previous activity

        ImageView BackButton = findViewById(R.id.BackImg);

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenTeacherBoard(UserType,TId,TName);
            }
        });

        LoadingMassages();


        TextView Sent = findViewById(R.id.Sent);

        Sent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SendMassage(TId,TName);

                Sent.setEnabled(false);
            }
        });

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

    }
    public void SendMassage(String SId,String SName){
        FirebaseFirestore db = FirebaseFirestore.getInstance(); // Initialize the Firestore instance

        EditText Massage = findViewById(R.id.Massage);
        Massage.setEnabled(false);
        String Msg = Massage.getText().toString();

        // Get the current date and time
        Date currentDate = new Date();

// Create a SimpleDateFormat instance to format the date and time
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd       HH:mm:ss"); // for date


// Format the date and time as separate variables
        String DateAndTime = dateFormat.format(currentDate);



        Map<String, Object> TeacherProfile = new HashMap<>();
        TeacherProfile.put("Massage", Msg);
        TeacherProfile.put("TeacherName", SName);
        TeacherProfile.put("TeacherID", SId);
        TeacherProfile.put("DateAndTime", DateAndTime);


        db.collection("Notices")
                .add(TeacherProfile)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(TeacherNoticeBoard.this, "Your Massage is sent.Please, Wait a minute for loading", Toast.LENGTH_SHORT).show();
                        LoadingMassages();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(TeacherNoticeBoard.this, "Your account was not sent.", Toast.LENGTH_SHORT).show();
                        Massage.setEnabled(true);
                    }
                });
    }

    public void OpenTeacherBoard(String UserType,String TId,String TName){
        Intent intent1 = new Intent(this, TeacherDashBoard.class);
        intent1.putExtra("UserType", UserType);
        intent1.putExtra("TId", TId); //attaching the user type as variable with intent1
        intent1.putExtra("TName", TName);
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
                EditText Massage = findViewById(R.id.Massage);

                Massage.setText("");
                Massage.setEnabled(true);

                TextView Sent = findViewById(R.id.Sent);
                Sent.setEnabled(true);

                Toast.makeText(TeacherNoticeBoard.this, "Massage loading is Completed.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(TeacherNoticeBoard.this, "Massage loading is failed.", Toast.LENGTH_SHORT).show();

                // Handle the query failure
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