package com.example.boxingproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class combinations extends AppCompatActivity {

    private EditText input;
    private Button btnReceive;
    private Button btnSend;

    ImageView btnGoBack;

    private DatabaseReference rootDatabaseRef;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input = findViewById(R.id.txt_combo_input);
        btnSend = findViewById(R.id.btnSend);
        btnReceive = findViewById(R.id.btnRead);
        btnGoBack = findViewById(R.id.go_back_comb);
        textView = findViewById(R.id.txt_combo_string);

        rootDatabaseRef = FirebaseDatabase.getInstance().getReference().child("combinations");

        //set the mode as combinations
        rootDatabaseRef.setValue("2");

        btnReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input.setText("");
            }
            //this is the clear button now
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = input.getText().toString() + "0";
                //set a 0 to the end to identify the end of the combo string
                rootDatabaseRef.setValue(data);

                rootDatabaseRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            String data = snapshot.getValue().toString();
                            textView.setText(data);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

        });

        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Dash.class);
                startActivity(i);
            }
        });
    }
}