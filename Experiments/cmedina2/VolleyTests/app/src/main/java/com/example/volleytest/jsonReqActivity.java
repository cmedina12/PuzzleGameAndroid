package com.example.volleytest;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.volleytest.app.AppController;
import com.example.volleytest.utils.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class jsonReqActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = jsonReqActivity.class.getSimpleName();
    private Button btnJsonObj, btnJsonArray;
    private TextView msgResponse;
    private RequestQueue mQueue;

    private ProgressDialog pDialog;
    private String tag_json_obj = "jobj_req", tag_json_array = "jarray_req";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_req);

        btnJsonObj = (Button) findViewById(R.id.objReq);
        btnJsonArray = (Button) findViewById(R.id.arrayReq);
        msgResponse = (TextView) findViewById(R.id.textView);
        mQueue = Volley.newRequestQueue(this);
        JsonArrayRequest ja = new JsonArrayRequest("https://jsonplaceholder.typicode.com/todos/1", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        String userId = jsonObject.getString("userId");
                        String id = jsonObject.getString("id");
                        String title = jsonObject.getString("title");
                        String comp = jsonObject.getString("completed");

                        msgResponse.setText(msgResponse.getText() + userId + ", " + id + ", " + title + ", " + comp);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
    });
        mQueue.add(ja);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

//        btnJsonObj.setOnClickListener(this);
//        btnJsonArray.setOnClickListener(this);

        //btnJsonObj.setOnClickListener(this);
//        btnJsonObj.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                jsonParse();
//            }
//        });
    }
//    private void jsonParse() {
//        String url = "http://jsonplaceholder.typicode.com/users";
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            JSONArray jsonArray = response.getJSONArray("");
////                            for (int i = 0; i < 2; i++) {
////                                JSONObject sets = jsonArray.getJSONObject(i);
////
//
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//            }
//        });
//    }

    ////////////////////////////////////////

    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }
    /*
        Making json object request
     */
    private void makeJsonOnjReq() {
        showProgressDialog();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, Const.URL_JSON_OBJECT,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
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
        }) {
            /*
                passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", "Androidhive");
                params.put("name", "Androidhive");
                params.put("name", "Androidhive");
                return params;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
        // Cancelling request
        // AppController.getInstance().addToRequestQueue.cancelAll(tag_json_obj);
    }
    /*
        Making json array request
     */
    private void makeJsonArryReq() {
        showProgressDialog();
        JsonArrayRequest req = new JsonArrayRequest(Const.URL_JSON_ARRAY,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
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
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req, tag_json_array);
        }
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.objReq:
                    makeJsonOnjReq();
                    break;
                case R.id.arrayReq:
                    makeJsonArryReq();
                    break;
        }
    }
}