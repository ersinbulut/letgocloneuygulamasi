package com.example.letgo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

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

import java.util.ArrayList;

public class FdetayActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_fdetay);

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

            begen.setImageResource(R.drawable.begen_dolu);

        }
    }
    public void sil(View view){
        db=FirebaseDatabase.getInstance();
        ref=db.getReference("favoriler");
        String key=txt_key.getText().toString();

        //Firebase silme kodları
        ref.child(key).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                Toast.makeText(FdetayActivity.this, "Favori ürün silindi.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    public void btnGonder(View view){
        Toast.makeText(this, "Mesaj Gönderildi..", Toast.LENGTH_SHORT).show();
        et.setText("");
    }
}
