package com.firstapp.myapplicationtest3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class StudentMarks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_marks);



        String UserType = getIntent().getStringExtra("UserType");    //taking the user type from previous activity
        String SId = getIntent().getStringExtra("SId");    //taking the kry from previous activity
        String SName = getIntent().getStringExtra("SName"); //taking the kry from previous activity



        ImageView Backbtn = findViewById(R.id.ImgBack);

        Backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStudentDashBoard(UserType,SId,SName);
            }
        });

        Spinner Year = findViewById(R.id.Year);

        ArrayAdapter<CharSequence> adapter1 =ArrayAdapter.createFromResource(this,R.array.Year, android.R.layout.simple_dropdown_item_1line);
        adapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        Year.setAdapter(adapter1);

        Spinner Sem = findViewById(R.id.Term);

        ArrayAdapter<CharSequence> adapter2 =ArrayAdapter.createFromResource(this,R.array.Sem, android.R.layout.simple_dropdown_item_1line);
        adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        Sem.setAdapter(adapter2);

        Button Searchbtn = findViewById(R.id.Search);

        Searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TableLayout marksTable = findViewById(R.id.MarksTabel);
                // Calculate the number of rows excluding the header
                int rowCount = marksTable.getChildCount() - 1; // Subtract 1 for the header
                // Remove rows from the 2nd row to the last row
                for (int i = rowCount; i > 0; i--) {
                    marksTable.removeViewAt(i);
                }

                Selection("IM_2020_088");
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
///////////////////////////////////////
    public void SearchMarks(String UserType,String SId,String SName,String Year,String Sem){
        // Assuming you have a Firestore database instance
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionRef = db.collection(SId+Year+"MarksTable"); // Replace with your collection name

        collectionRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Map<String, Object> data = document.getData();

                    for (Map.Entry<String, Object> entry : data.entrySet()) {
                        String fieldName = entry.getKey(); // Field name
                        Object value = entry.getValue();   // Field value

                        // Do something with the field name and value
                        // For example, print them
                        System.out.println("Field Name: " + fieldName);
                        System.out.println("Field Value: " + value);
                    }
                }
            } else {
                // Handle the query failure here
            }
        });

    }

    public void getMarks(String Year,String Sem,String SId){
        TableLayout MarksTable =findViewById(R.id.MarksTabel);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("StudentProfile")
                .document(SId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    Map<String, Object> friendsMap = document.getData();

                                    for (Map.Entry<String, Object> entry : friendsMap.entrySet()) {
                                        if (entry.getKey().equals("Marks")) {
                                            Map<String, Object> newFriend0Map = (Map<String, Object>) entry.getValue();

                                            for (Map.Entry<String, Object> year : newFriend0Map.entrySet()) {
                                                if (year.getKey().equals(Year)) {
                                                    Map<String, Object> YearMap = (Map<String, Object>) year.getValue();

                                                    for (Map.Entry<String, Object> sem : YearMap.entrySet()) {
                                                        if (sem.getKey().equals(Sem)) {
                                                            Map<String, Object> MarksMap = (Map<String, Object>) sem.getValue();

                                                            TextView SubjectHead = findViewById(R.id.SubjectHead); // Replace with your TextView's ID
                                                            // Set the TextView to be visible
                                                            SubjectHead.setVisibility(View.VISIBLE);

                                                            TextView MarksHead = findViewById(R.id.MarksHead); // Replace with your TextView's ID
                                                            // Set the TextView to be visible
                                                            MarksHead.setVisibility(View.VISIBLE);

                                                            // Create a new TableRow

                                                            for (Map.Entry<String, Object> dataEntry : MarksMap.entrySet()) {

                                                                String key = dataEntry.getKey();
                                                                Object value = dataEntry.getValue();

                                                                // Create a new TableRow
                                                                TableRow tableRow = new TableRow(StudentMarks.this);

                                                                // Create TextView for the subject and set its text
                                                                TextView subjectTextView = new TextView(StudentMarks.this);
                                                                subjectTextView.setText(key);
                                                                subjectTextView.setTypeface(null, Typeface.BOLD);
                                                                subjectTextView.setGravity(Gravity.CENTER);

                                                                // Create TextView for the mark and set its text
                                                                TextView markTextView = new TextView(StudentMarks.this);
                                                                markTextView.setText(value.toString());
                                                                markTextView.setTypeface(null, Typeface.BOLD);
                                                                markTextView.setGravity(Gravity.CENTER);

                                                                // Add the TextViews to the TableRow
                                                                tableRow.addView(subjectTextView);
                                                                tableRow.addView(markTextView);

                                                                // Add the TableRow to the TableLayout

                                                                MarksTable.addView(tableRow);

                                                            }
                                                        }
                                                        else{

                                                        }
                                                    }
                                                }
                                                else{

                                                }
                                            }} }
                                } else {

                                }
                            } else {
                               
                            }
                        }

                    ///////////////////////////////////////////////////////////////////////////////////
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(StudentMarks.this, "Your Marks Loading failed ", Toast.LENGTH_SHORT).show();
                    }
                });
            }




    public void Selection(String SId){

        Spinner spinnerYear = findViewById(R.id.Year);
        String Year = spinnerYear.getSelectedItem().toString();

        Spinner spinnerSem = findViewById(R.id.Term);
        String Sem = spinnerSem.getSelectedItem().toString();

        getMarks(Year,Sem,SId);
    }

}