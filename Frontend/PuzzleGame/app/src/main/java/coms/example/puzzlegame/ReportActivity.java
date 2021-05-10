package coms.example.puzzlegame;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import coms.example.puzzlegame.app.AppController;

public class ReportActivity extends AppCompatActivity
{
    private String tag_string_req = "string_req";

    private Button submit;
    private EditText id, reason;
    /**
     * A method that defines the user view and button to move to {@link StringRequestActivity}.
     * @param savedInstanceState - the current state of the bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_activity);

        submit = (Button) findViewById(R.id.btnSubmit);
        id = (EditText) findViewById(R.id.puzzleID);
        reason = (EditText) findViewById(R.id.reportDesc);

        // button click listeners
        submit.setOnClickListener((v) -> { submitReportData(); });
    }

    private void submitReportData()
    {
        if(id!=null && reason!=null)
        {
            //This ensures we won't have issues with the path
            String pid=id.getText().toString().replace("/","");
            String message=reason.getText().toString().replace("/","");

            String URL="http://coms-309-010.cs.iastate.edu:8080/addReport/" + pid + "/" + message;

            StringRequest strReq = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

                /**
                 * Converts the information received from the server and displays username
                 * associated with the received account information.
                 * @param response - the string received from the server
                 */
                @Override
                public void onResponse(String response)
                {
                    Toast.makeText(getApplicationContext(), "Report filed successfully, Thank You!",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ReportActivity.this, MainHomeActivity.class));

                }
            }, new Response.ErrorListener() {

                /**
                 * Displays error message and hides progress bar upon receiving an error.
                 * @param error
                 */
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "An error has occurred",Toast.LENGTH_SHORT).show();
                    //VolleyLog.d(TAG, "Error: " + error.getMessage());
                    //hideProgressDialog();
                }
            });

            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

        }
        else
        {
            Toast.makeText(getApplicationContext(), "Please fill out both fields",Toast.LENGTH_SHORT).show();
        }
    }

}
