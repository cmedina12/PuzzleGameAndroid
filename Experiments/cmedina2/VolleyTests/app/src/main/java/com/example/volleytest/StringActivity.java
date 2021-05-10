package com.example.volleytest;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.example.volleytest.app.AppController;
import com.example.volleytest.utils.Const;

public class StringActivity extends AppCompatActivity {
    private String TAG = StringActivity.class.getSimpleName();
    private Button stringReqBtn;
    private ProgressDialog pDialog;
    private String tag_string_req = "string_req";

    private TextView msgResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_string);

        stringReqBtn = (Button) findViewById(R.id.stringReqBtn);
        msgResponse = (TextView) findViewById(R.id.textView2);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        stringReqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                makeStringReq();
            }
        });
    }

    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }

    /*
        Make json object request
     */
    private void makeStringReq() {
        showProgressDialog();
        StringRequest strReq = new StringRequest(Request.Method.GET,
                Const.URL_STRING_REQ, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                msgResponse.setText(response.toString());
                hideProgressDialog();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hideProgressDialog();
            }
        });
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}