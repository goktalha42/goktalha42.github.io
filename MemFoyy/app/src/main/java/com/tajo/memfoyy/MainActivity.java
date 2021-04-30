package com.tajo.memfoyy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    private TextView register;
    private FirebaseAuth auth;
    private EditText mail, parola;
    private Button buton;
    private FirebaseUser currentUser;

    public void init(){
        register = (TextView) findViewById(R.id.l_txtRegister);
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        mail = (EditText) findViewById(R.id.l_txtEmail);
        parola = (EditText) findViewById(R.id.l_txtPassword);
        buton = (Button) findViewById(R.id.l_btnLogin);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startRegister = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(startRegister);
            }
        });

        buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        String email = mail.getText().toString();
        String password = parola.getText().toString();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this,"Email Alanı Boş Olamaz",Toast.LENGTH_LONG).show();
        }else if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Parola Alanı Boş Olamaz",Toast.LENGTH_LONG).show();
        }else{
            buton.setEnabled(false);

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()){
                        Toast.makeText(MainActivity.this,"Giriş Başarılı",Toast.LENGTH_LONG).show();
                        Intent appIntent = new Intent(MainActivity.this, AppActivity.class);
                        startActivity(appIntent);
                        finish();
                    }else{
                        Toast.makeText(MainActivity.this,"Hatalı Parola veya Hatalı Mail!",Toast.LENGTH_LONG).show();
                        buton.setEnabled(true);
                    }
                }
            });
        }

    }
}