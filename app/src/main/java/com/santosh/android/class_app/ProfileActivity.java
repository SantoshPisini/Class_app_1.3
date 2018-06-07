package com.santosh.android.class_app;

        import android.content.Intent;
        import android.support.annotation.NonNull;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.auth.UserInfo;
        import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {

    Button button;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListner;
    TextView tv1,tv2;
    ImageView imageView;
    static String name;
    String email="";
    String sigin_provider="";
    String profile_picture="";
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle("Profile");

        button = (Button) findViewById(R.id.button_logout);
        mAuth = FirebaseAuth.getInstance();
        tv1 = (TextView) findViewById(R.id.textView);
        tv2 = (TextView) findViewById(R.id.textView2);
        imageView = (ImageView) findViewById(R.id.circleImageView);


        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null){
                    startActivity(new Intent(ProfileActivity.this,LoginActivity.class));
                }
            }
        };
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
            }
        });

        FirebaseUser user = mAuth.getCurrentUser();
        Log.d("user ",profile_picture);

        name = user.getDisplayName();
        email = user.getEmail();
        String photoUrl = user.getPhotoUrl().toString();

        for (UserInfo profile : user.getProviderData()) {
            System.out.println(profile.getProviderId());
            // check if the provider id matches "facebook.com"-------skipped by me
            if (profile.getProviderId().equals("google.com")) {
                sigin_provider = profile.getProviderId();
                //((second) getActivity()).loadGoogleUserDetails();
            }
        }
        profile_picture = photoUrl;

        tv1.setText(name);
        tv2.setText(email);
        //imageView.setImageURI(Uri.parse(profile_picture));
        Picasso.with(this).load(user.getPhotoUrl()).into(imageView);
        //startActivity(new Intent(ProfileActivity.this,FeedbackActivity.class));
    }

    //to send name to feedback
    public static String getNname(){
        return name;
    }
    //end
}
