package com.santosh.android.class_app;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FeedbackActivity extends AppCompatActivity {
    private EditText message;
    private RatingBar ratingBar;
    private TextView textView;

    private static final String URL_DATA = "http://santosh36.ga/o2.php";
    public String temp = "", name = ProfileActivity.getNname();
    private float value = 3.0f;
    public int type;//0=bug 1=view

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        setTitle("Feedback");
        message = (EditText) findViewById(R.id.et_msg);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        textView = (TextView) findViewById(R.id.tv_rating);
        ratingBar.setRating(value);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                temp = "";
                value = v;
                if (v == 1.0)
                    temp = "Terrible";
                else if (v == 2.0)
                    temp = "Bad";
                else if (v == 3.0)
                    temp = "It's Ok";
                else if (v == 4.0)
                    temp = "Good";
                else if (v == 5.0)
                    temp = "Great";
                else
                    temp = "It's Ok";
                textView.setText(temp);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_feedback, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().equals("Send")) {
            temp = message.getText().toString();
            if (TextUtils.isEmpty(temp)) {
                message.setError("You left this field empty !");
                return super.onOptionsItemSelected(item);
            }
            if (temp.length() >= 201) {
                message.setError("You entered more than 200 characters !");
                message.setText("");
                return super.onOptionsItemSelected(item);
            }
            final GlobalClass globalClass = (GlobalClass) getApplicationContext();
            globalClass.setNa(name);
            globalClass.setTe(temp);
            globalClass.setTy(type + "");
            globalClass.setV(value + "");
            sendFeedback();
            GlobalClass.deleteCache(getApplicationContext());
        }
        return super.onOptionsItemSelected(item);
    }

    private void sendFeedback() {
        final GlobalClass globalClass = (GlobalClass) getApplicationContext();
        Toast.makeText(FeedbackActivity.this,globalClass.getNa()+"dvdjhvbdv",Toast.LENGTH_SHORT);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sending your feedback . . .");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Successfully submitted", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getApplicationContext(),response+"bsdsdjhsvdvhvsdjvjsdvjhsdvjsvd",Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameter = new HashMap<String, String>();
                parameter.put("name", globalClass.getNa());
                parameter.put("message", globalClass.getTe());
                parameter.put("type", globalClass.getTy());
                parameter.put("rating", globalClass.getV());

                return parameter;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void Rbuttonclick(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        if (checked == true)
            switch (view.getId()) {
                case R.id.rbt_1:
                    type = 0;
                    break;
                case R.id.rbt_2:
                    type = 1;
                    break;
            }
        else {
            type = 0;
        }
    }
}