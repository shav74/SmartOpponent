package com.example.boxingproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class Dash extends AppCompatActivity {
    //state variables

    private int activated_mode;
    private int prev_active_mode;
    Button button_comb;
    Button button_free;
    Button button_react;
    Button button_rand;
    Button button_pow;

    Button[] btn_array;

    private DatabaseReference rootDatabaseRef;
    Random random = new Random();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);

        button_comb = findViewById(R.id.btn_comb);
        button_free = findViewById(R.id.btn_free_mode);
        button_react = findViewById(R.id.btn_react);
        button_rand = findViewById(R.id.btn_rand);
        button_pow = findViewById(R.id.btn_pow);

        btn_array = new Button[]{button_free, button_comb, button_react, button_rand, button_pow};

        //set the first mode here
        Toast.makeText(this, "free mode activated", Toast.LENGTH_SHORT).show();
        btn_array[0].setBackgroundColor(getColor(androidx.cardview.R.color.cardview_dark_background));
        //send first mode

        rootDatabaseRef = FirebaseDatabase.getInstance().getReference().child("combinations");
        rootDatabaseRef.setValue("1");

        //set default mode to free mode


        button_free.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set_mode(0);
                try {
                    Thread.sleep(1000);
                    rootDatabaseRef.setValue("1");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        button_comb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set_mode(1);
                Intent intent = new Intent(getApplicationContext(), combinations.class);
                startActivity(intent);
            }
        });

        button_react.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set_mode(2);
                Intent ii = new Intent(getApplicationContext(), reaction.class);
                startActivity(ii);
                rootDatabaseRef.setValue("4");
            }
        });

        button_rand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set_mode(3);
                rootDatabaseRef.setValue("3");

                new Thread(new Runnable() {
                    public void run() {
                        while (activated_mode == 3) {
                            try {
                                int value1 = random.nextInt(6)+1;
                                int value2 = random.nextInt(6)+1;

                                Integer final_random_value = (value1*10 + value2) * 10;
                                String rand_string = final_random_value.toString();

                                Thread.sleep(1000);
                                rootDatabaseRef.setValue(rand_string);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                        }
                    }
                }).start();
            }
        });

        button_pow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set_mode(4);
                rootDatabaseRef.setValue("5");
                Intent intent = new Intent(getApplicationContext(), pow.class);
                startActivity(intent);
            }
        });

    }

    protected void set_mode(int _activated_mode){
        prev_active_mode = activated_mode;
        activated_mode = _activated_mode;
        String message;

        if(activated_mode == prev_active_mode){
            message = btn_array[activated_mode].getText() + " already activated";
        }else{
            message = btn_array[activated_mode].getText() + " activated";
            btn_array[prev_active_mode].setBackgroundColor(getColor(R.color.primary));
            if(activated_mode == 0 || activated_mode == 3){
                btn_array[activated_mode].setBackgroundColor(getColor(androidx.cardview.R.color.cardview_dark_background));
            }
        }
        Toast message_toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        message_toast.show();
    }
}