package com.example.widgetapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.widgetlibrary.DiffuseView;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private Button button2;
    private DiffuseView diffuseView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        diffuseView = findViewById(R.id.diffuseView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diffuseView.start();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diffuseView.stop();
            }
        });
    }
}