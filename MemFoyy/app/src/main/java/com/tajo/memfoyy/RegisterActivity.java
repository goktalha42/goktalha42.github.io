package com.tajo.memfoyy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {


    private Toolbar actionbarRegister;
    private TextView login;
    private FirebaseAuth auth;
    private DatabaseReference realtime;
    private EditText mail, parola, parola2;
    private Button buton;

    public void init(){
        actionbarRegister = (androidx.appcompat.widget.Toolbar) findViewById(R.id.actionbarRegister);
        setSupportActionBar(actionbarRegister);
        getSupportActionBar().setTitle("Hesap Oluştur");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth = FirebaseAuth.getInstance();
        realtime = FirebaseDatabase.getInstance().getReference();

        mail = (EditText) findViewById(R.id.r_txtEmail);
        parola = (EditText) findViewById(R.id.r_txtPassword);
        parola2 = (EditText) findViewById(R.id.r_txtPassword2);
        login = (TextView) findViewById(R.id.r_txtLogin);
        buton = (Button) findViewById(R.id.r_btnRegister);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginActivity = new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(loginActivity);
                finish();
            }
        });

        buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewACcount();
            }
        });
    }
    private void createNewACcount() {

        String email = mail.getText().toString();
        String password = parola.getText().toString();
        String password2 = parola2.getText().toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(password2)){
            Toast.makeText(this,"Boş Alan Bırakmayınız!!!", Toast.LENGTH_LONG).show();
        } else if ((password.toString().length() <= 5)) {
            Toast.makeText(this, "Parolanız En Az 6 Haneden Oluşmalı!", Toast.LENGTH_LONG).show();
        } else if (!password.equals(password2)) {
            Toast.makeText(this, "Parolalar Uyuşmuyor!", Toast.LENGTH_LONG).show();
        } else {
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "Hesabınız Başarılı Şekilde Oluşturuldu", Toast.LENGTH_LONG).show();
                        String mevcutID = auth.getCurrentUser().getUid();
                        realtime.child("Users").child(mevcutID).setValue("");

                        Intent loginIntent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(loginIntent);
                        finish();
                    }else{
                        Toast.makeText(RegisterActivity.this,"Bir Hata Oluştu! Lütfen Tekrar Deneyin", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }

    }
}