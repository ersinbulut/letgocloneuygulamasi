package com.example.letgo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OzelAdapterRV extends RecyclerView.Adapter<OzelAdapterRV.MyViewHolder> {
//    private Context context;
    private View view;
    private ArrayList<Urun> urunler;

    private FirebaseDatabase db;
    private DatabaseReference ref;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;


//    public OzelAdapterRV(Context context,ArrayList<Urun> urunler) {
//        this.context=context;
//        this.urunler = urunler;
//    }

    public OzelAdapterRV(View view,ArrayList<Urun> urunler) {
        this.view=view;
        this.urunler = urunler;
        db = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.satir_layout, parent, false);
        MyViewHolder vh=new MyViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Urun u= urunler.get(position);
        holder.txt_tarih.setText(u.getTarih());
        holder.txt_urunAdi.setText(u.getUrunAdi());
        holder.txt_fiyat.setText(String.valueOf(u.getFiyat())+ " ₺");
        holder.txt_puan.setText(String.valueOf(u.getPuan()));
        Picasso.get().load(u.getResim1()).into(holder.img_resim);
        if (u.getBegenler().containsKey(mUser.getUid())){
            holder.img_begen.setImageResource(R.drawable.begen_dolu);
            holder.txt_puan.setTextColor(Color.WHITE);
        }
        else{
            holder.img_begen.setImageResource(R.drawable.begen_bos);
            holder.txt_puan.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return urunler.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txt_tarih;
        TextView txt_urunAdi;
        TextView txt_fiyat;
        TextView txt_puan;

        ImageView img_resim;
        ImageView img_begen;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

             txt_tarih = itemView.findViewById(R.id.satir_tarih);
             txt_urunAdi = itemView.findViewById(R.id.satir_urun_adi);
             txt_fiyat = itemView.findViewById(R.id.satir_fiyat);
             txt_puan=itemView.findViewById(R.id.satir_puan);

             img_resim=itemView.findViewById(R.id.resim3);
             img_begen=itemView.findViewById(R.id.img_begen);



             img_resim.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     //Toast.makeText(context, "Resme Tıklandı", Toast.LENGTH_SHORT).show();
                     Urun secilenUrun = urunler.get(getAdapterPosition());
                     Intent i = new Intent(view.getContext(), DetayActivity.class);
                     i.putExtra("urunNesnesi", secilenUrun);
                     view.getContext().startActivity(i);
                 }
             });


            img_begen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(view.getContext(), "beğenildi" + getAdapterPosition(), Toast.LENGTH_SHORT).show();

                    String urunKey = urunler.get(getAdapterPosition()).getKey();
                    ref = db.getReference("urunler").child(urunKey);
                    ref.runTransaction(new Transaction.Handler() {
                        @NonNull
                        @Override
                        public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                            Urun secilenurun=mutableData.getValue(Urun.class);

                            if (secilenurun.getBegenler().containsKey(mUser.getUid())){
                                //daha önce beğenmiş
                                int p=secilenurun.getPuan();
                                p--;
                                secilenurun.setPuan(p);
                                secilenurun.getBegenler().remove(mUser.getUid());
                            }
                            else{
                                //daha önce begeni yok
                                int p=secilenurun.getPuan();
                                p++;
                                secilenurun.setPuan(p);
                                secilenurun.getBegenler().put(mUser.getUid(),true);
                            }
                            mutableData.setValue(secilenurun);
                            return Transaction.success(mutableData);
                        }

                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {

                        }
                    });
                }
            });
        }
    }
}
