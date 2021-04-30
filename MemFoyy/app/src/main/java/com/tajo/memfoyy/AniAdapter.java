package com.tajo.memfoyy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AniAdapter extends RecyclerView.Adapter<AniAdapter.MyViewHolder> {


    private Context mCtx;
    private List<Ani> aniList;
    FirebaseDatabase db;
    String userID;
    KisilerFragment kisilerFragment;

    public AniAdapter(Context mCtx, List<Ani> aniList){
        this.mCtx = mCtx;
        this.aniList = aniList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(
                LayoutInflater.from(mCtx).inflate(R.layout.ani_karti,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Ani ani = aniList.get(position);
        db = FirebaseDatabase.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        userID = auth.getCurrentUser().getUid();
        kisilerFragment = new KisilerFragment();
        final AppActivity appActivity = new AppActivity();

        holder.gafa.setText(ani.getBas());
        holder.govde.setText(ani.getHikaye());

        holder.btn_not_sil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("anilar").child(userID).child(ani.getKey());
                databaseReference.removeValue();
            }
        });
    }

    @Override
    public int getItemCount() {
        return aniList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView gafa,govde;
        Button btn_not_sil;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            gafa = itemView.findViewById(R.id.head);
            govde = itemView.findViewById(R.id.desc);
            btn_not_sil = itemView.findViewById(R.id.not_sil);
        }
    }
}