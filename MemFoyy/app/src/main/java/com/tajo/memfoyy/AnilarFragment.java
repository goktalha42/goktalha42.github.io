package com.tajo.memfoyy;

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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AnilarFragment extends Fragment {

    private DatabaseReference veriyolu;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private String userID;
    private FirebaseDatabase db;
    private RecyclerView recyclerView;
    private AniAdapter adapter;
    private List<Ani> aniList;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View a = inflater.inflate(R.layout.fragment_anilar, container, false);

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        userID = auth.getCurrentUser().getUid();
        db = FirebaseDatabase.getInstance();
        veriyolu = db.getReference();
        veriyolu = db.getReference("anilar").child(userID);

        progressBar = a.findViewById(R.id.prog);
        recyclerView = a.findViewById(R.id.hepsi);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Objects.requireNonNull(getActivity())));
        aniList = new ArrayList<>();
        adapter = new AniAdapter(Objects.requireNonNull(getActivity()), aniList);
        recyclerView.setAdapter(adapter);

        veriyolu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressBar.setVisibility(View.GONE);
                if (snapshot.exists()) {
                    Iterable<DataSnapshot> list = snapshot.getChildren();
                    for (DataSnapshot ds : list) {
                        Ani ani = ds.getValue(Ani.class);
                        aniList.add(ani);
                    }
                    recyclerView.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return a;
    }
}
