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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class kisiKayitActivity extends AppCompatActivity {

    private static final String TAG = "Kisilerim";
    private Toolbar actionbarKisilerim;
    private TextView dogum;
    private EditText isim, soyisim, yakinlik, telno, sehiri, adres;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private DatabaseReference veriyolu;
    private FirebaseAuth auth;
    FirebaseDatabase db;
    String userID;
    private Button kisi_ekle_btn, kisi_iptal_btn;

    public void init() {
        actionbarKisilerim = (Toolbar) findViewById(R.id.actionbar_kisi_kayit);
        setSupportActionBar(actionbarKisilerim);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Kişi Bilgisi Kayıt Etme");

        db = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        veriyolu = FirebaseDatabase.getInstance().getReference();
        userID = auth.getCurrentUser().getUid();
        dogum = (TextView) findViewById(R.id.kisi_dogum);
        isim = (EditText) findViewById(R.id.kisi_adi);
        soyisim = (EditText) findViewById(R.id.kisi_soyadi);
        yakinlik = (EditText) findViewById(R.id.kisi_yakinlik);
        telno = (EditText) findViewById(R.id.kisi_yeri);
        sehiri = (EditText) findViewById(R.id.kisi_telno);
        adres = (EditText) findViewById(R.id.kisi_adres);
        kisi_ekle_btn = (Button) findViewById(R.id.kisi_ekle);
        kisi_iptal_btn = (Button) findViewById(R.id.iptal);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kisi_kayit);
        init();

        dogum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(kisiKayitActivity.this,
                        android.R.style.Theme_Wallpaper_NoTitleBar, mDateSetListener, year, month, day);
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
                dogum.setText(date);
            }
        };

        kisi_iptal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iptalIntent = new Intent(kisiKayitActivity.this, AppActivity.class);
                startActivity(iptalIntent);
                finish();
            }
        });

        kisi_ekle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verilerigirr();
            }
        });
    }

    private void verilerigirr() {
        final Intent kisikaydetIntent = new Intent(kisiKayitActivity.this, AppActivity.class);
        String key = veriyolu.push().getKey();
        DatabaseReference vy = db.getReference("kisiler").child(userID);

        String k_isim = isim.getText().toString();
        String k_soyisim = soyisim.getText().toString();
        String k_yakinlik = yakinlik.getText().toString();
        String k_tel = telno.getText().toString();
        String k_sehiri = sehiri.getText().toString();
        String k_adres = adres.getText().toString();
        String k_dogum = dogum.getText().toString();

        if (TextUtils.isEmpty(k_isim) || (TextUtils.isEmpty(k_soyisim) || (TextUtils.isEmpty(k_yakinlik)) || (TextUtils.isEmpty(k_yakinlik)) || (TextUtils.isEmpty(k_tel)) || (TextUtils.isEmpty(k_sehiri))
                || (TextUtils.isEmpty(k_adres)) || (TextUtils.isEmpty(k_dogum)))) {
            Toast.makeText(kisiKayitActivity.this, "Boş Alan Bırakmayınız!", Toast.LENGTH_LONG).show();
        }else {
            HashMap<String, String> kisiHaritasi = new HashMap<>();
            kisiHaritasi.put("key", key);
            kisiHaritasi.put("k_isim", k_isim);
            kisiHaritasi.put("k_soyisim", k_soyisim);
            kisiHaritasi.put("k_yakinlik", k_yakinlik);
            kisiHaritasi.put("k_tel", k_tel);
            kisiHaritasi.put("k_sehir", k_sehiri);
            kisiHaritasi.put("k_adres", k_adres);
            kisiHaritasi.put("k_dogum", k_dogum);

            vy.child(key).setValue(kisiHaritasi).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(kisiKayitActivity.this, "Kişi Bilgileriniz Başarılı Şekilde Kayıt Edildi...", Toast.LENGTH_LONG).show();
                        startActivity(kisikaydetIntent);
                        finish();
                    } else {
                        String mesaj = task.getException().toString();
                        Toast.makeText(kisiKayitActivity.this, "Hata: " + mesaj, Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}