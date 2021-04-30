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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AniKayitActivty extends AppCompatActivity {


    private Toolbar actionbarNot;
    private Button not_ekle, not_iptal_btn;

    private DatabaseReference veriyolu;
    private FirebaseAuth auth;
    private EditText not, not_baslik;
    FirebaseDatabase db;
    String userID;


    public void init() {
        //ACTİONBAR TANIMLAMALARI
        actionbarNot = (Toolbar) findViewById(R.id.actionbarNotlar);
        setSupportActionBar(actionbarNot);
        getSupportActionBar().setTitle("Anını Yaz");

        //VERİ TABANI TANIMLAMALARI
        auth = FirebaseAuth.getInstance();
        veriyolu = FirebaseDatabase.getInstance().getReference();
        userID = auth.getCurrentUser().getUid();
        db = FirebaseDatabase.getInstance();

        //XML EDİTTEXTLER TANIMLAMALARI
        not = findViewById(R.id.ani);
        not_baslik = findViewById(R.id.ani_baslik);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ani_kayit_activty);
        init();

        not_ekle = (Button) findViewById(R.id.not_ekle);
        not_iptal_btn = (Button) findViewById(R.id.not_iptal);

        not_iptal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iptalIntent = new Intent(AniKayitActivty.this, AppActivity.class);
                startActivity(iptalIntent);
                finish();
            }
        });

        not_ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ekleIntent = new Intent(AniKayitActivty.this, AppActivity.class);

                String nott = not.getText().toString().trim();
                String baslikk = not_baslik.getText().toString().trim();
                if (TextUtils.isEmpty(nott)) {
                    if (TextUtils.isEmpty(baslikk)) {
                        startActivity(ekleIntent);
                        finish();
                    } else {
                        Toast.makeText(AniKayitActivty.this, "Anınızı Girmediniz!", Toast.LENGTH_LONG).show();

                    }
                } else if (TextUtils.isEmpty(baslikk)) {
                    if (TextUtils.isEmpty(nott)) {
                        startActivity(ekleIntent);
                        finish();
                    } else {
                        Toast.makeText(AniKayitActivty.this, "Anınıza Başlık Girmediniz!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    VerileriGir();
                    startActivity(ekleIntent);
                    finish();
                }
            }
        });
    }

    private void VerileriGir() {
        String bas = not_baslik.getText().toString();
        String hikaye = not.getText().toString();
        String key = veriyolu.push().getKey();
        DatabaseReference vy = db.getReference("anilar").child(userID);


        HashMap<String, String> aniharitasi = new HashMap<>();
        aniharitasi.put("key", key);
        aniharitasi.put("bas", bas);
        aniharitasi.put("hikaye", hikaye);

        vy.child(key).setValue(aniharitasi).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(AniKayitActivty.this, "Bilgileriniz Başarılı Şekilde Kayıt Edildi...", Toast.LENGTH_LONG).show();

                    Intent intentLoginApp = new Intent(AniKayitActivty.this, AppActivity.class);
                    startActivity(intentLoginApp);
                    finish();
                } else {
                    String mesaj = task.getException().toString();
                    Toast.makeText(AniKayitActivty.this, "Hata: " + mesaj, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}