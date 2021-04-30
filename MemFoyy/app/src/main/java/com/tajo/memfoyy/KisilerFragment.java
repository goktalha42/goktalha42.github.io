package com.tajo.memfoyy;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class KisilerFragment extends Fragment {

    private DatabaseReference veriyolu;
    private FirebaseAuth auth;
    private String userID;
    private FirebaseDatabase db;
    private RecyclerView recyclerView;
    private KisiAdapter adapter;
    private List<Kisi> kisiList;
    private ProgressBar progressBar;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View k = inflater.inflate(R.layout.fragment_kisiler, container, false);
        auth = FirebaseAuth.getInstance();
        userID = auth.getCurrentUser().getUid();
        db = FirebaseDatabase.getInstance();
        veriyolu = db.getReference();
        String key = veriyolu.push().getKey();
        veriyolu = db.getReference("kisiler").child(userID);

        progressBar = k.findViewById(R.id.progges);
        recyclerView = k.findViewById(R.id.kisilerinhepsi);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Objects.requireNonNull(getActivity())));
        kisiList = new ArrayList<>();
        adapter = new KisiAdapter(Objects.requireNonNull(getActivity()), kisiList);
        recyclerView.setAdapter(adapter);

        veriyolu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressBar.setVisibility(View.GONE);
                if (snapshot.exists()) {
                    Iterable<DataSnapshot> list = snapshot.getChildren();
                    for (DataSnapshot ds : list) {
                        Kisi kisi = ds.getValue(Kisi.class);
                        kisiList.add(kisi);
                    }
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return k;
    }


}
