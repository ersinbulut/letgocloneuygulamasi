package com.example.letgo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
//    ListView listView;
//    ArrayList<Urun> urunArrayList;
//    OzelAdapter ozelAdapter;

    private Fragment1 f1;
    private Fragment2 f2;
    private Fragment3 f3;

//    RecyclerView recyclerView;
//    ArrayList<Urun> urunArrayList;
//    OzelAdapterRV ozelAdapterRV;
//    View view;
//
//    TextView tv;
//
//    FirebaseDatabase db;
//    DatabaseReference ref;
//    ValueEventListener urunlerListener;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // f1=new MainActivity();
        f1=new Fragment1();
        f2=new Fragment2();
        f3=new Fragment3();

        //1.fragment gözükecek
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout,f1)
                .commit();

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem Item) {
                if (Item.getItemId()==R.id.Item1) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frameLayout,f1)
                            .commit();
                }
                if (Item.getItemId()==R.id.Item2) {
                    //2.fragment gözükecek
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frameLayout,f2)
                            .commit();
                }
                if (Item.getItemId()==R.id.Item3) {
                    //3.fragment gözükecek
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frameLayout,f3)
                            .commit();
                }
                return true;
            }
        });

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

//        tv=findViewById(R.id.textView2);
//        tv.setText("Hoşgeldin " + mUser.getEmail());
//
//        urunArrayList = new ArrayList<>();
//
//
//
//        recyclerView=findViewById(R.id.recyclerView);
//        ozelAdapterRV=new OzelAdapterRV(view,urunArrayList);
//        recyclerView.setAdapter(ozelAdapterRV);
//
//        StaggeredGridLayoutManager manager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(manager);
//
//
//
//
//        db = FirebaseDatabase.getInstance();
//        ref = db.getReference("urunler");
//
//        urunlerListener = ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                urunArrayList.clear();
//                for (DataSnapshot data : dataSnapshot.getChildren()) {
//                    Urun u = data.getValue(Urun.class);
//                    urunArrayList.add(u);
//                }
//                ozelAdapterRV.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        ref.removeEventListener(urunlerListener);
//    }

    //Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //+ ya basıldığında FilmEkleActivty aç
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //favori ekranı eklenecek
        if (item.getItemId() == R.id.item_ekle) {
            Intent i = new Intent(this, EkleActivity.class);
            //i.putExtra("kullaniciAdi", kadi);
            startActivity(i);
        } else if (item.getItemId() == R.id.item_cikis) {
            mAuth.signOut();
            Intent i = new Intent(this, GirisActivity.class);
            startActivity(i);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
