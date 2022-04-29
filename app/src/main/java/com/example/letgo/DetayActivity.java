package com.example.letgo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.UUID;

import me.relex.circleindicator.CircleIndicator;

public class DetayActivity extends AppCompatActivity {
    EditText et;
    ArrayList<Fragment> fragmentArrayList;
    MyPagerAdapter adapter;
    ViewPager viewPager;
    TextView txt_key;
    TextView edt_tarih;
    TextView edt_urun_adi;
    TextView edt_fiyat;
    TextView edt_aciklama;
    //ImageView img1,img2,img3;
    ImageView begen;

    FirebaseDatabase db;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detay);

        begen=findViewById(R.id.img_begen2);

        et = findViewById(R.id.editText5);

        fragmentArrayList = new ArrayList<>();
        adapter = new MyPagerAdapter(getSupportFragmentManager(), fragmentArrayList);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);

        //CircleIndicator indicator=(CircleIndicator) findViewById(R.id.indicator);
        //indicator.setViewPager(viewPager);

        //Optional
        //adapter.registerDataSetObserver(indicator.getDataSetObserver());


        txt_key=findViewById(R.id.textView4);
        edt_tarih = findViewById(R.id.textView5);
        edt_urun_adi = findViewById(R.id.textView7);
        edt_fiyat = findViewById(R.id.textView9);
        edt_aciklama = findViewById(R.id.textView11);



        final Urun gelenUrun = (Urun) getIntent().getSerializableExtra("urunNesnesi");
        if (gelenUrun != null) {
            txt_key.setText(gelenUrun.getKey());
            edt_tarih.setText(gelenUrun.getTarih());
            edt_urun_adi.setText(gelenUrun.getUrunAdi());
            edt_fiyat.setText(String.valueOf(gelenUrun.getFiyat()) + " ₺");
            edt_aciklama.setText(gelenUrun.getAciklama());

            fragmentArrayList.clear();
            fragmentArrayList.add(new FragmentResimler(gelenUrun.getResim1()));
            fragmentArrayList.add(new FragmentResimler(gelenUrun.getResim2()));
            fragmentArrayList.add(new FragmentResimler(gelenUrun.getResim3()));
            adapter.notifyDataSetChanged();

            begen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Firabase ekleme kodları
                    db=FirebaseDatabase.getInstance();
                    ref=db.getReference("favoriler");

                    String tarih=gelenUrun.getTarih();
                    String urunAdi=gelenUrun.getUrunAdi();
                    Double fiyat=gelenUrun.getFiyat();
                    String aciklama=gelenUrun.getAciklama();
                    String resim1=gelenUrun.getResim1();
                    String resim2=gelenUrun.getResim2();
                    String resim3=gelenUrun.getResim3();
                    int puan=gelenUrun.getPuan();


                    final Favori favoriUrun = new Favori(null,tarih,urunAdi,fiyat ,aciklama,resim1,resim2,resim3,puan);

                    String yeniKey = ref.push().getKey();
                    favoriUrun.setKey(yeniKey);

                    ref.child(yeniKey).setValue(favoriUrun)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(DetayActivity.this, "Favorilere Eklendi.", Toast.LENGTH_SHORT).show();
                                    begen.setImageResource(R.drawable.begen_dolu);
                                    //finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(DetayActivity.this, "Hata Oluştu."+e.getMessage() ,Toast.LENGTH_SHORT).show();
                                   // finish();
                                }
                            });
                }
            });

        }



    }

    public void sil(View view){
        db=FirebaseDatabase.getInstance();
        ref=db.getReference("urunler");
        String key=txt_key.getText().toString();

        //Firebase silme kodları
        ref.child(key).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                Toast.makeText(DetayActivity.this, "İlan silindi.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

        public void btnGonder(View view){
            Toast.makeText(this, "Mesaj Gönderildi..", Toast.LENGTH_SHORT).show();
            et.setText("");
        }

}

