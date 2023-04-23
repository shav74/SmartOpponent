package com.example.boxingproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import kotlin.jvm.internal.Ref;

public class reaction extends AppCompatActivity {

    private DatabaseReference rootDatabaseRef;
    private DatabaseReference reactionTimeRef;
    private ImageView back_btn;
    private Button btnSend;
    private Button btnClear;
    private TextView inputCombo;
    private TextView titleNtime;

    private String data_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reaction);

        btnSend = findViewById(R.id.btnSend);
        btnClear = findViewById(R.id.btnClear);
        back_btn = findViewById(R.id.go_back_react);
        inputCombo = findViewById(R.id.txt_reaction_combos);
        titleNtime = findViewById(R.id.title_react);

        rootDatabaseRef = FirebaseDatabase.getInstance().getReference().child("combinations");
        reactionTimeRef = FirebaseDatabase.getInstance().getReference().child("reaction");

        reactionTimeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String data = snapshot.getValue().toString();
                        titleNtime.setText(data + " ms");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data_value = inputCombo.getText().toString() + "0";
                rootDatabaseRef.setValue(data_value);
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputCombo.setText("");
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Dash.class);
                startActivity(i);
            }
        });

    }


    public void setInput(View v){
        data_value = v.getTag().toString();
        rootDatabaseRef.setValue(data_value);
    }
}