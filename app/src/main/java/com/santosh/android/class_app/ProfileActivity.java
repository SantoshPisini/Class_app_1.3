package com.santosh.android.class_app;

        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.support.annotation.NonNull;
        import android.support.design.widget.FloatingActionButton;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.text.TextUtils;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuInflater;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.auth.UserInfo;
        import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListner;
    TextView tv1,tv2,tv3,tv4,tv5;
    EditText et1,et2;
    ImageView imageView;
    static String name;
    String email="";
    String sigin_provider="";
    String profile_picture="";
    int t,a,tt,aa;
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().equals("signout")) {
            mAuth.signOut();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_profile, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle("Profile");

        mAuth = FirebaseAuth.getInstance();
        tv1 = (TextView) findViewById(R.id.textView);
        tv2 = (TextView) findViewById(R.id.textView2);
        tv3 = (TextView) findViewById(R.id.tc);
        tv4 = (TextView) findViewById(R.id.ta);
        tv5 = (TextView) findViewById(R.id.per);
        et1 = (EditText) findViewById(R.id.tc1);
        et2 = (EditText) findViewById(R.id.ta1);

        imageView = (ImageView) findViewById(R.id.circleImageView);

        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null){
                    startActivity(new Intent(ProfileActivity.this,LoginActivity.class));
                }
            }
        };
        FloatingActionButton fab1 = findViewById(R.id.fab1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Todo :submit to db checking valid details or not
                attendance();
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

        displayAttendance();
        Toast.makeText(this,"You can update attendance from last time you updated !",Toast.LENGTH_LONG).show();
    }

    private void displayAttendance() {
        SharedPreferences sp = getSharedPreferences("Attendance", Context.MODE_PRIVATE);
        t = sp.getInt("total",0);
        a = sp.getInt("attended",0);
        tv3.setText("Total number of classes  : "+t);
        tv4.setText("Total number of attended : "+a);
        tv5.setText("Percentage of attendance : "+((double)a/t)*100);
        et1.setText("");
        et2.setText("");
    }

    private void attendance() {

        if(TextUtils.isEmpty(et1.getText().toString()) | TextUtils.isEmpty(et2.getText().toString())){
            et1.setError("Must to update");
            et2.setError("Must to update");
            return ;
        }
        try{
            tt = Integer.parseInt(et1.getText().toString());
            aa = Integer.parseInt(et2.getText().toString());
        }catch(Exception e){
            Toast.makeText(this,"Integer should be entered !",Toast.LENGTH_SHORT).show();
            displayAttendance();
        }
        if(tt < aa){
            Toast.makeText(this,"At this moment can't attend more classes than conducted.",Toast.LENGTH_SHORT).show();
            return;
        }
        SharedPreferences sp = getSharedPreferences("Attendance", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("total",t+tt);
        editor.putInt("attended",a+aa);
        editor.commit();
        Toast.makeText(this,"Attendance updated !!!",Toast.LENGTH_SHORT).show();
        displayAttendance();
    }

    //to send name to feedback
    public static String getNname(){
        return name;
    }
    //end
}
