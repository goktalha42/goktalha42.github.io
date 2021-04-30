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

public class KisiAdapter extends RecyclerView.Adapter<KisiAdapter.MyViewHolder> {

    private Context mCtx;
    private List<Kisi> kisiList;
    FirebaseDatabase db;
    String userID;

    public KisiAdapter(Context mCtxx, List<Kisi> kisiList){
        this.mCtx = mCtxx;
        this.kisiList = kisiList;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mCtx).inflate(R.layout.kisi_karti,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Kisi kisi = this.kisiList.get(position);
        db = FirebaseDatabase.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        userID = auth.getCurrentUser().getUid();
        final AppActivity appActivity = new AppActivity();

        holder.isimler.setText(kisi.getK_isim() + " " + kisi.getK_soyisim());
        holder.yakinliik.setText(kisi.getK_yakinlik());
        holder.tell.setText(kisi.getK_tel());
        holder.yasadigiyeer.setText(kisi.getK_sehir());
        holder.adress.setText(kisi.getK_adres());
        holder.dogumm.setText(kisi.getK_dogum());

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("kisiler").child(userID).child(kisi.getKey());
                databaseReference.removeValue();
            }
        });
    }


    @Override
    public int getItemCount() {
        return kisiList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView isimler, yakinliik, tell, yasadigiyeer, adress, dogumm;
        Button btnDelete;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            isimler = itemView.findViewById(R.id.isimler);
            yakinliik = itemView.findViewById(R.id.yakinlik);
            tell = itemView.findViewById(R.id.telno);
            yasadigiyeer = itemView.findViewById(R.id.yasadigisehir);
            adress = itemView.findViewById(R.id.adres);
            dogumm = itemView.findViewById(R.id.dogumgunu);
            btnDelete = itemView.findViewById(R.id.veri_sil);
        }
    }
}
