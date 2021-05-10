package coms.example.puzzlegame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import coms.example.puzzlegame.app.AppController;

/**
 * @author Levi Jansen
 * @author Cris Medina
 * @author Eric Kirch
 *
 * Defines the screen for entering a puzzle ID and generating the puzzle JSONobject.
 * Errors on invalid ID when "Play Puzzle" button is clicked.
 * Moves to PlayPuzzleActivity on valid ID when "Play Puzzle" button is clicked.
 */
public class SelectPuzzleActivity extends AppCompatActivity {
    private String tag_string_req = "string_req";

    private Button btnSubmit,btnRandom;
    private static JSONObject puzzle;

    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_puzzle);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnRandom = (Button) findViewById(R.id.btnRandom);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        btnSubmit.setOnClickListener((v) -> { findPuzzle(); });
        btnRandom.setOnClickListener((v) -> { findRandomPuzzle(); });
    }

    /**
     * Shows the progress bar with loading circle symbol and "Loading...".
     */
    private void showProgressDialog() {
        if (!pDialog.isShowing()) {
            pDialog.show();
        }
    }

    /**
     * Hides the progress bar if it is being displayed.
     */
    private void hideProgressDialog() {
        if (pDialog.isShowing()) {
            pDialog.hide();
        }
    }

    private void findPuzzle() {
        EditText simpleEditText = (EditText) findViewById(R.id.puzzleID);
        String strValue = simpleEditText.getText().toString();
        String url = "http://coms-309-010.cs.iastate.edu:8080/getPuzzle/" + strValue;

        showProgressDialog();

        StringRequest strReq = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {

            /**
             * Converts the information received from the server and displays username
             * associated with the received account information.
             * @param response - the string received from the server
             */
            @Override
            public void onResponse(String response) {
                //Log.d(TAG, response.toString());
                //msgResponse.setText(response.toString());
                hideProgressDialog();

                try
                {
                    puzzle = new JSONObject(response.toString());
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

                if (puzzle != null)
                {
                    //Can continue
                    startActivity(new Intent(SelectPuzzleActivity.this, PlayPuzzleActivity.class));
                }
                else
                {
                    //alert - no puzzle found
                    Toast.makeText(getApplicationContext(), "The puzzle code you entered was not found!",Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {

            /**
             * Displays error message and hides progress bar upon receiving an error.
             * @param error
             */
            @Override
            public void onErrorResponse(VolleyError error) {
                //VolleyLog.d(TAG, "Error: " + error.getMessage());
                hideProgressDialog();
            }
        });

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }

    private void findRandomPuzzle()
    {
        String url = "http://coms-309-010.cs.iastate.edu:8080/allPuzzles";

        showProgressDialog();

        StringRequest strReq = new StringRequest(Request.Method.GET, url, new Response.Listener<String>()
        {

            /**
             * Converts the information received from the server and displays username
             * associated with the received account information.
             * @param response - the string received from the server
             */
            @Override
            public void onResponse(String response)
            {
                //Log.d(TAG, response.toString());
                //msgResponse.setText(response.toString());
                hideProgressDialog();
                JSONArray puzzles=null;
                try
                {
                    puzzles= new JSONArray(response.toString());;
                    //puzzle = new JSONObject(response.toString());
                    //System.out.println("Amount of puzzles: "+puzzles.length());
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

                if (puzzles != null)
                {
                    //Can continue
                    Random r=new Random();

                    int rng=r.nextInt(puzzles.length());

                    try
                    {
                        puzzle=puzzles.getJSONObject(rng);
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }

                    startActivity(new Intent(SelectPuzzleActivity.this, PlayPuzzleActivity.class));
                }
                else
                {
                    //alert - no puzzle found
                    Toast.makeText(getApplicationContext(), "No puzzles exist yet!",Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {

            /**
             * Displays error message and hides progress bar upon receiving an error.
             * @param error
             */
            @Override
            public void onErrorResponse(VolleyError error) {
                //VolleyLog.d(TAG, "Error: " + error.getMessage());
                hideProgressDialog();
            }
        });

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }

    public static JSONObject getPuzzle()
    {
        return puzzle;
    }

}