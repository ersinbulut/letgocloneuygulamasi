package com.example.letgo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment3 extends Fragment {
    TextView tv;
    TextView email;
    TextView sifre;
    ImageView resim;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    public Fragment3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_3, container, false);
        tv=view.findViewById(R.id.textView14);
        tv.setText("Profil");

        resim=view.findViewById(R.id.imageView20);

        resim.setImageResource(R.drawable.user1);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        email=view.findViewById(R.id.textView16);
        sifre=view.findViewById(R.id.textView18);

        email.setText(": "+ mUser.getEmail());
        sifre.setText(": "+"******");

        return view;
    }
}
