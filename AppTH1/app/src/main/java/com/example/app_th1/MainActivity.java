package com.example.app_th1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.example.app_th1.model.SpinnerAdapter;

public class MainActivity extends AppCompatActivity {

    private Spinner spinnerImg;
    private Spinner spinnerText;
    private EditText eName, eDate, eDes, search;
    private RadioButton male, female, both;
    private CheckBox java, python, golang;
    private Button add, update;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void initView () {
        spinnerImg = findViewById(R.id.spinnerImage);
        spinnerImg.setAdapter(new SpinnerAdapter(this));
        spinnerText = findViewById(R.id.spinnerText);
        eName = findViewById(R.id.name);
        eDate = findViewById(R.id.date);
        eDes = findViewById(R.id.description);
        search = findViewById(R.id.search);
        male = findViewById(R.id.male);
        female = findViewById(R.id.famale);
        both = findViewById(R.id.both);
        java = findViewById(R.id.java);
        python = findViewById(R.id.python);
        golang = findViewById(R.id.golang);
        add = findViewById(R.id.btnAdd);
        update = findViewById(R.id.btnUpdate);
        recyclerView = findViewById(R.id.recyclerView);
    }
}