package com.example.puzzlegame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;


import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);

        // set banner
//        ActionBar bar = getActionBar();
//        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#39CF3F")));

        // Bottom Navigation Buttons setup
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        // Will open the home fragment at application start up
        getSupportFragmentManager().beginTransaction().replace(R.id.home_frag, new Home()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener(){

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // switching to selected fragment
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.account_frag:
                    selectedFragment = new Account();
                    break;
                case R.id.home_frag:
                    selectedFragment = new Home();
                    break;
                case R.id.leaderboard_frag:
                    selectedFragment = new Leaderboard();
                    break;

            }
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment, selectedFragment)
                    .commit();
            return true;
            }
        };



    final TextView textView = (TextView) findViewById(R.id.text);
    // ...

    // Instantiate the RequestQueue.
    RequestQueue queue = Volley.newRequestQueue(this);
    String url ="https://www.google.com";

    // Request a string response from the provided URL.
    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // Display the first 500 characters of the response string.
                    textView.setText("Response is: "+ response.substring(0,500));
                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            textView.setText("That didn't work!");
        }
    }
    );

    // Add a request to the RequestQueue.
    //queue.add(stringRequest);
    }