package com.tajo.memfoyy;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfilFragment extends Fragment {

    private FirebaseAuth auth;
    private TextView ad,soyad,tel,tc,kisi,kisitel,dgko;
    private TextView AAD,ASOYAD,ACITY,AANNE,ABABA,AOKUL,ADGKO;
    private DatabaseReference veriyolu;
    String userID;

    public void init(){
        auth = FirebaseAuth.getInstance();
        veriyolu = FirebaseDatabase.getInstance().getReference();
        userID = auth.getCurrentUser().getUid();
        VerileriAl();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profil, container,false);
        init();

        AAD = (TextView) v.findViewById(R.id.AD1);
        ASOYAD = (TextView) v.findViewById(R.id.SOYAD1);
        AANNE = (TextView) v.findViewById(R.id.ANA_AD1);
        ABABA = (TextView) v.findViewById(R.id.BABA_AD1);
        ACITY = (TextView) v.findViewById(R.id.SEHİR1);
        AOKUL = (TextView) v.findViewById(R.id.OKUL1);
        ADGKO = (TextView) v.findViewById(R.id.DGKO1);

        AAD.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        ASOYAD.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        AANNE.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        ABABA.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        ACITY.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        AOKUL.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        ADGKO.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        ad = (TextView) v.findViewById(R.id.AD);
        soyad = (TextView) v.findViewById(R.id.SOYAD);
        tel = (TextView) v.findViewById(R.id.ANA_AD);
        tc = (TextView) v.findViewById(R.id.BABA_AD);
        kisi = (TextView) v.findViewById(R.id.OKUL);
        kisitel = (TextView) v.findViewById(R.id.SEHİR);
        dgko = (TextView) v.findViewById(R.id.DGKO);

        return v;
    }

    private void VerileriAl() {
        veriyolu.child("Users").child(userID).addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
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
                    String a_kisiTel = snapshot.child("kisitel").getValue().toString().trim();
                    String a_dgko = snapshot.child("dgko").getValue().toString().trim();

                    ad.setText(a_ad);
                    soyad.setText(a_soyad);
                    tel.setText(a_tel);
                    tc.setText(a_tc);
                    kisi.setText(a_kisi);
                    kisitel.setText(a_kisiTel);
                    dgko.setText(a_dgko);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
}