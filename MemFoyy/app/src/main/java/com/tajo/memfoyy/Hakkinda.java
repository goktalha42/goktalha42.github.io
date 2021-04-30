package com.tajo.memfoyy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class Hakkinda extends AppCompatActivity {

    private Toolbar actionbarHakkinda;

    public void init(){
        actionbarHakkinda = (Toolbar) findViewById(R.id.infoActionBar);
        setSupportActionBar(actionbarHakkinda);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Hakkında");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hakkinda);
        init();
    }
}