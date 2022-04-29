package com.example.letgo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment1 extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Urun> urunArrayList;
    OzelAdapterRV ozelAdapterRV;

    TextView tv;

    FirebaseDatabase db;
    DatabaseReference ref;
    ValueEventListener urunlerListener;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    public Fragment1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_1, container, false);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        tv=view.findViewById(R.id.textView12);
        tv.setText("Hoşgeldin " + mUser.getEmail());

        urunArrayList = new ArrayList<>();

        recyclerView=view.findViewById(R.id.recyclerView1);
        ozelAdapterRV=new OzelAdapterRV(view,urunArrayList);
        recyclerView.setAdapter(ozelAdapterRV);

        StaggeredGridLayoutManager manager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);




        db = FirebaseDatabase.getInstance();
        ref = db.getReference("urunler");

        urunlerListener = ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                urunArrayList.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Urun u = data.getValue(Urun.class);
                    urunArrayList.add(u);
                }
                ozelAdapterRV.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        ref.removeEventListener(urunlerListener);
    }
}
