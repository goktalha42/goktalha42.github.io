package com.tajo.memfoyy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class Bilgilerim extends AppCompatActivity {


    private static final String TAG = "Bilgilerim";
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Toolbar actionbarBilgilerim;
    private Button btnTamam;
    private EditText ad,soyad,tel,tc,kisi,kisitel;
    private FirebaseFirestore fStore;
    private DatabaseReference veriyolu;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    String userID;

    public void init(){
        actionbarBilgilerim = (Toolbar) findViewById(R.id.bilgiActionBar);
        setSupportActionBar(actionbarBilgilerim);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Bilgi Gir");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        currentUser = auth.getCurrentUser();
        veriyolu = FirebaseDatabase.getInstance().getReference();
        userID = currentUser.getUid();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bilgilerim);
        init();

        btnTamam = (Button) findViewById(R.id.kaydet);
        ad = findViewById(R.id.name);
        soyad = findViewById(R.id.surname);
        tel = findViewById(R.id.phoneNO);
        tc = findViewById(R.id.tcNO);
        kisi = findViewById(R.id.important);
        kisitel = findViewById(R.id.importantPhone);
        mDisplayDate = (TextView) findViewById(R.id.birthday);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(Bilgilerim.this, android.R.style.Theme_Black_NoTitleBar, mDateSetListener, year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyyy" + month + "/" + day + "/" + year);
                String date = month + "/" + day + "/" + year;
                mDisplayDate.setText(date);
            }
        };

        btnTamam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VerileriGir();
            }
        });
        VerileriAl();
    }


    private void VerileriAl() {
        veriyolu.child("Users").child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if ((snapshot.exists()) && (snapshot.hasChild("ad")) && (snapshot.hasChild("soyad")) && (snapshot.hasChild("tel")) &&
                        (snapshot.hasChild("tc")) && (snapshot.hasChild("kisi")) &&
                        (snapshot.hasChild("kisitel")) && (snapshot.hasChild("dgko")))
                {
                    String a_ad = snapshot.child("ad").getValue().toString().trim();
                    String a_soyad = snapshot.child("soyad").getValue().toString().trim();
                    String a_tel = snapshot.child("tel").getValue().toString().trim();
                    String a_tc = snapshot.child("tc").getValue().toString().trim();
                    String a_kisi = snapshot.child("kisi").getValue().toString().trim();
                    String a_kisiNo = snapshot.child("kisitel").getValue().toString().trim();
                    String a_dgko = snapshot.child("dgko").getValue().toString().trim();

                    ad.setText(a_ad);
                    soyad.setText(a_soyad);
                    tel.setText(a_tel);
                    tc.setText(a_tc);
                    kisi.setText(a_kisi);
                    kisitel.setText(a_kisiNo);
                    mDisplayDate.setText(a_dgko);
                }
                else {
                    Toast.makeText(Bilgilerim.this,"Lütfen Profil Bilgilerinizi Tamamen Doldurun",Toast.LENGTH_LONG);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void VerileriGir() {
        String e_ad = ad.getText().toString();
        String e_soyad = soyad.getText().toString();
        String e_tel = tel.getText().toString();
        String e_tc = tc.getText().toString();
        String e_kisi = kisi.getText().toString();
        String e_kisiNo = kisitel.getText().toString();
        String e_dgko = mDisplayDate.getText().toString();

        if (TextUtils.isEmpty(e_ad) || (TextUtils.isEmpty(e_soyad)) || (TextUtils.isEmpty(e_tel)) || (TextUtils.isEmpty(e_tc)) || (TextUtils.isEmpty(e_kisi))
                || (TextUtils.isEmpty(e_kisiNo)) || (TextUtils.isEmpty(e_dgko))){
            Toast.makeText(Bilgilerim.this, "Boş Alan Bırakmayınız", Toast.LENGTH_LONG).show();
        }else{
            HashMap<String,String> pp = new HashMap<>();
            pp.put("uid",userID);
            pp.put("ad",e_ad);
            pp.put("soyad",e_soyad);
            pp.put("tel",e_tel);
            pp.put("tc",e_tc);
            pp.put("kisi",e_kisi);
            pp.put("kisitel",e_kisiNo);
            pp.put("dgko",e_dgko);

            veriyolu.child("Users").child(userID).setValue(pp).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(Bilgilerim.this, "Bilgileriniz Başarılı Şekilde Kayıt Edildi...",Toast.LENGTH_LONG).show();

                        Intent intentBasari = new Intent(Bilgilerim.this, AppActivity.class);
                        startActivity(intentBasari);
                        finish();
                    }
                    else{
                        String mesaj = task.getException().toString();
                        Toast.makeText(Bilgilerim.this,"Hata: "+mesaj,Toast.LENGTH_LONG).show();
                    }
                }

            });
        }
    }
}