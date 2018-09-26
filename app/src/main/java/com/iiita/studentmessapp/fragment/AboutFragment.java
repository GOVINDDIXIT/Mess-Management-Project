package com.iiita.studentmessapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.iiita.studentmessapp.MainActivity;
import com.iiita.studentmessapp.R;
import com.iiita.studentmessapp.UserSignIn.Login;

import java.io.File;


public class AboutFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser currentFirebaseUser;
    private View view;
    private static Button signOutButton;
    private String mParam1;
    private String mParam2;
    private TextView usernameTv;
    private TextView usermailId;
    private ImageView userDisplayPicture;
    public AboutFragment() {
        // Required empty public constructor
    }

    public static AboutFragment newInstance(String param1, String param2) {
        AboutFragment fragment = new AboutFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private void signOut() {
        mAuth = FirebaseAuth.getInstance();
        AuthUI.getInstance().signOut(getContext()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_about, container, false);
        setSignOutFunction();
        setupUserProfile();
        return view;
    }

    private void setupUserProfile() {
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        usernameTv = view.findViewById(R.id.user_name);
        usermailId = view.findViewById(R.id.user_mail_id);
        userDisplayPicture = view.findViewById(R.id.profile_image);
        usernameTv.setText(currentFirebaseUser.getDisplayName());
        usermailId.setText(currentFirebaseUser.getEmail());
        Uri userPhotoUrl  = currentFirebaseUser.getPhotoUrl();
        String originalPieceOfUrl = "s96-c/photo.jpg";
        String newUrl="";
        String newPieceOfUrlToAdd = "s400-c/photo.jpg";
        if (userPhotoUrl != null) {
            String photoPath = userPhotoUrl.toString();
             newUrl = photoPath.replace(originalPieceOfUrl, newPieceOfUrlToAdd);
        }
        RequestOptions options = new RequestOptions();
        options.circleCrop();
        options.format(DecodeFormat.PREFER_ARGB_8888);
        options.override(Target.SIZE_ORIGINAL);
        Glide.with(getContext()).load(newUrl).apply(options).into(userDisplayPicture);
    }

    private void setSignOutFunction() {
        signOutButton = view.findViewById(R.id.sign_out_button);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();

            }
        });
    }


}
