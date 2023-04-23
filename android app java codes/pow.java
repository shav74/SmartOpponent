package com.example.boxingproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

public class pow extends AppCompatActivity {

    ProgressBar pbPower;
    ImageView back_btn;
    private DatabaseReference rootDatabaseRef;
    private DatabaseReference reactionTimeRef;
    private String data_value;
    TextView txtPower;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pow);

        rootDatabaseRef = FirebaseDatabase.getInstance().getReference().child("combinations");
        reactionTimeRef = FirebaseDatabase.getInstance().getReference().child("reaction");
        txtPower = findViewById(R.id.txtPower);
        back_btn = findViewById(R.id.go_back_pow);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Dash.class);
                startActivity(i);
            }
        });

        reactionTimeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String data = snapshot.getValue().toString();
                    txtPower.setText(data + "%");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void sendInput(View v) {
        data_value = v.getTag().toString();
        rootDatabaseRef.setValue(data_value);
    }
}