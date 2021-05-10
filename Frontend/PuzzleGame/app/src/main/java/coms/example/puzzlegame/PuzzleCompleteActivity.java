package coms.example.puzzlegame;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import coms.example.puzzlegame.app.AppController;

/**
 * @author Levi Jansen
 * @author Eric Kirch
 *
 * Handles the screen for after a puzzle has been completed.
 * Stores your time in the puzzle's database.
 * Increases the play count of the completed puzzle.
 */
public class PuzzleCompleteActivity extends AppCompatActivity {

    private TextView time;
    long timeInSeconds = (PlayPuzzleActivity.endTime - PlayPuzzleActivity.startTime)/1000;
    private String tag_string_req = "string_req";
    private Button btnHome, btnLeaderboardShortcut;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.puzzle_complete);
        time = (TextView) findViewById(R.id.finalTime);
        btnHome = (Button) findViewById(R.id.btnHome);
        btnHome.setOnClickListener((v) -> { goHome(); });
        btnLeaderboardShortcut = (Button) findViewById(R.id.btnLeaderboardShortcut);
        btnLeaderboardShortcut.setOnClickListener((v) -> { goLeaderboard(); });

        int t = (int) Math.floor(timeInSeconds);
        time.setText("Final Time: " + Integer.toString(t) + " Second(s)!");

        try {
            //Handles submitting your score to the puzzle's database
            String URL = "http://coms-309-010.cs.iastate.edu:8080/addScore/" +
                    PlayPuzzleActivity.getData().get("id") + "/" +
                    MainHomeActivity.getUser().get("id") + "/" + t;

            StringRequest strReq = new StringRequest(Request.Method.GET,
                    URL, new Response.Listener<String>() {

                /**
                 * If successful, prints what email is changed to
                 * @param response - the received information from the server
                 */
                @Override
                public void onResponse(String response) {
                    //Log.d(TAG, response.toString());
                    //msgEmailResponse.setText(response.toString() + " to " + newEmail);
                    //hideProgressDialog();
                }
            }, new Response.ErrorListener() {

                /**
                 * If unsuccessful, prints error message
                 * @param error - the error received
                 */
                @Override
                public void onErrorResponse(VolleyError error) {
                    //VolleyLog.d(TAG, "Error: " + error.getMessage());
                    //hideProgressDialog();
                }
            });

            //Handles incrementing the number of times a puzzle has been completed
            String URL2 = "http://coms-309-010.cs.iastate.edu:8080/increasePlayCount/" +
                    PlayPuzzleActivity.getData().get("id");

            StringRequest strReq2 = new StringRequest(Request.Method.GET,
                    URL2, new Response.Listener<String>() {

                /**
                 * If successful, prints what email is changed to
                 * @param response - the received information from the server
                 */
                @Override
                public void onResponse(String response) {
                    //Log.d(TAG, response.toString());
                    //msgEmailResponse.setText(response.toString() + " to " + newEmail);
                    //hideProgressDialog();
                }
            }, new Response.ErrorListener() {

                /**
                 * If unsuccessful, prints error message
                 * @param error - the error received
                 */
                @Override
                public void onErrorResponse(VolleyError error) {
                    //VolleyLog.d(TAG, "Error: " + error.getMessage());
                    //hideProgressDialog();
                }
            });

            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
            AppController.getInstance().addToRequestQueue(strReq2, tag_string_req);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void goLeaderboard() {
        try {
            LeaderboardActivity.rememberingID(PlayPuzzleActivity.getData().get("id").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        startActivity(new Intent(PuzzleCompleteActivity.this,
                LeaderboardActivity.class));
    }

    void goHome() {
        startActivity(new Intent(PuzzleCompleteActivity.this,
                MainHomeActivity.class));
    }

    public void shortcutLeaderboardTest(String testID) {
        LeaderboardActivity.rememberingID(testID);
        startActivity(new Intent(PuzzleCompleteActivity.this,
                LeaderboardActivity.class));
    }
}