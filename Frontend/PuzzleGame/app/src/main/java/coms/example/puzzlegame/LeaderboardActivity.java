package coms.example.puzzlegame;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import coms.example.puzzlegame.app.AppController;

public class LeaderboardActivity extends AppCompatActivity {
    private String tag_string_req = "string_req";

    private ProgressDialog pDialog;
    private Button btnSubmitID, btnLHome;
    private JSONObject searchPuzzle;

    private String nameToReturn;
    private static boolean remembering = false;

    public static String IDVal;
    public EditText searchID;
    private JSONObject temp;

    private TextView puzzleNameLabel, puzzleName, rank, timeLabel, time2, time3, time4, time5,
            playerNameLabel, playerName2, playerName3, playerName4, playerName5;

    public TextView time1, playerName1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard);

        searchID = (EditText) findViewById(R.id.searchID);

        btnSubmitID = (Button) findViewById(R.id.btnSubmitID);
        btnSubmitID.setOnClickListener((v) -> { findPuzzle(); });
        btnLHome = (Button) findViewById(R.id.btnLHome);
        btnLHome.setOnClickListener((v) -> { goHome(); });

        /**
         * Progress bar that displays "Loading..."
         */
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        puzzleNameLabel = (TextView) findViewById(R.id.puzzleNameLabel);
        puzzleName = (TextView) findViewById(R.id.puzzleName);
        rank = (TextView) findViewById(R.id.rank);
        timeLabel = (TextView) findViewById(R.id.timeLabel);
        time1 = (TextView) findViewById(R.id.time1);
        time2 = (TextView) findViewById(R.id.time2);
        time3 = (TextView) findViewById(R.id.time3);
        time4 = (TextView) findViewById(R.id.time4);
        time5 = (TextView) findViewById(R.id.time5);
        playerNameLabel = (TextView) findViewById(R.id.playerNameLabel);
        playerName1 = (TextView) findViewById(R.id.playerName1);
        playerName2 = (TextView) findViewById(R.id.playerName2);
        playerName3 = (TextView) findViewById(R.id.playerName3);
        playerName4 = (TextView) findViewById(R.id.playerName4);
        playerName5 = (TextView) findViewById(R.id.playerName5);

        puzzleNameLabel.setVisibility(View.GONE);
        puzzleName.setVisibility(View.GONE);
        rank.setVisibility(View.GONE);
        timeLabel.setVisibility(View.GONE);
        time1.setVisibility(View.GONE);
        time2.setVisibility(View.GONE);
        time3.setVisibility(View.GONE);
        time4.setVisibility(View.GONE);
        time5.setVisibility(View.GONE);
        playerNameLabel.setVisibility(View.GONE);
        playerName1.setVisibility(View.GONE);
        playerName2.setVisibility(View.GONE);
        playerName3.setVisibility(View.GONE);
        playerName4.setVisibility(View.GONE);
        playerName5.setVisibility(View.GONE);

        if(remembering) {
            searchID.setText(IDVal);
            findPuzzle();
        }
    }

    private void goHome() {
        startActivity(new Intent(LeaderboardActivity.this,
                MainHomeActivity.class));
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

    /**
     * Upon clicking the submit button, this method tries to get puzzle information for the puzzle
     * with the code typed in the editable textbox.
     * If a user is accessing the leaderboard from the puzzle completed screen, this is
     * called automatically as soon as the leaderboard page loads.
     */
    private void findPuzzle() {
        IDVal = searchID.getText().toString();
        String url = "http://coms-309-010.cs.iastate.edu:8080/getPuzzle/" + IDVal;

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
                searchPuzzle = null;
                hideProgressDialog();

                try
                {
                    searchPuzzle = new JSONObject(response.toString());
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

                //If there is a valid puzzle with the given ID
                if (searchPuzzle != null)
                {
                    //Resets all text values to their defaults in case a second puzzle is viewed
                    time1.setText("N/A");
                    time2.setText("N/A");
                    time3.setText("N/A");
                    time4.setText("N/A");
                    time5.setText("N/A");
                    playerName1.setText("N/A");
                    playerName2.setText("N/A");
                    playerName3.setText("N/A");
                    playerName4.setText("N/A");
                    playerName5.setText("N/A");

                    //Makes all leaderboard information visible
                    puzzleNameLabel.setVisibility(View.VISIBLE);
                    puzzleName.setVisibility(View.VISIBLE);
                    rank.setVisibility(View.VISIBLE);
                    timeLabel.setVisibility(View.VISIBLE);
                    time1.setVisibility(View.VISIBLE);
                    time2.setVisibility(View.VISIBLE);
                    time3.setVisibility(View.VISIBLE);
                    time4.setVisibility(View.VISIBLE);
                    time5.setVisibility(View.VISIBLE);
                    playerNameLabel.setVisibility(View.VISIBLE);
                    playerName1.setVisibility(View.VISIBLE);
                    playerName2.setVisibility(View.VISIBLE);
                    playerName3.setVisibility(View.VISIBLE);
                    playerName4.setVisibility(View.VISIBLE);
                    playerName5.setVisibility(View.VISIBLE);

                    //Sets the puzzle name to the name of the puzzle with the passed ID
                    try {
                        puzzleName.setText((String) searchPuzzle.get("puzzleName"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    };

                    //Sets the scores and scoreholders in the leaderboard
                    setScores();
                }
                else //If no valid puzzle is given
                {
                    //alert - no puzzle found
                    Toast.makeText(getApplicationContext(), "The puzzle code you entered was not found!",Toast.LENGTH_SHORT).show();

                    puzzleNameLabel.setVisibility(View.GONE);
                    puzzleName.setVisibility(View.GONE);
                    rank.setVisibility(View.GONE);
                    timeLabel.setVisibility(View.GONE);
                    time1.setVisibility(View.GONE);
                    time2.setVisibility(View.GONE);
                    time3.setVisibility(View.GONE);
                    time4.setVisibility(View.GONE);
                    time5.setVisibility(View.GONE);
                    playerNameLabel.setVisibility(View.GONE);
                    playerName1.setVisibility(View.GONE);
                    playerName2.setVisibility(View.GONE);
                    playerName3.setVisibility(View.GONE);
                    playerName4.setVisibility(View.GONE);
                    playerName5.setVisibility(View.GONE);
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

    //Sets the top 5 scores and scoreholders for the given puzzle ID
    private void setScores() {
        //Gets an iterable of all scores for the given puzzle
        String URL = "http://coms-309-010.cs.iastate.edu:8080/scoreByPID/" + IDVal;

        StringRequest strReq = new StringRequest(Request.Method.GET,
                URL, new Response.Listener<String>() {

            /**
             * If successful, sorts all scores into best five times
             * and displays them, along with the score holder.
             *
             * @param response - the received information from the server
             */
            @Override
            public void onResponse(String response) {

                JSONArray unsortedArray = null;
                try {
                    unsortedArray = new JSONArray(response.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONArray sortedArray = new JSONArray();
                List<JSONObject> jsonList = new ArrayList<JSONObject>();

                for (int i = 0; i < unsortedArray.length(); i++) {
                    try {
                        jsonList.add(unsortedArray.getJSONObject(i));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                Collections.sort(jsonList, new Comparator<JSONObject>() {

                    @Override
                    public int compare(JSONObject a, JSONObject b) {
                        int val1 = 0;
                        int val2 = 0;

                        //Sorts based on the timeToComplete key
                        try {
                            val1 = (int) a.get("timeToComplete");
                            val2 = (int) b.get("timeToComplete");
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (val1 == val2) {
                            String sVal1 = null;
                            String sVal2 = null;
                            //Uses the time of completion as a tiebreaker if both puzzles were
                            //completed with the same time down to the second.
                            try {
                                sVal1 = (String) a.get("timeCompleted");
                                sVal2 = (String) b.get("timeCompleted");
                                return sVal1.compareTo(sVal2);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        return val1 - val2;
                    }
                });

                //Puts the sorted items into sortedArray
                for (int i = 0; i < unsortedArray.length(); i++) {
                    sortedArray.put(jsonList.get(i));
                }

                //Sets the five lowest times, if there are that many. If fewer, prints "N/A"
                try {
                    time1.setText(sortedArray.getJSONObject(0).get("timeToComplete").toString() + "s");
                    time2.setText(sortedArray.getJSONObject(1).get("timeToComplete").toString() + "s");
                    time3.setText(sortedArray.getJSONObject(2).get("timeToComplete").toString() + "s");
                    time4.setText(sortedArray.getJSONObject(3).get("timeToComplete").toString() + "s");
                    time5.setText(sortedArray.getJSONObject(4).get("timeToComplete").toString() + "s");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //Prints the usernames of the holders of the top 5 times next to their scores
                try {
                    retrieveUsername(sortedArray.getJSONObject(0).get("scoreMaker").toString(),1);
                    retrieveUsername(sortedArray.getJSONObject(1).get("scoreMaker").toString(),2);
                    retrieveUsername(sortedArray.getJSONObject(2).get("scoreMaker").toString(),3);
                    retrieveUsername(sortedArray.getJSONObject(3).get("scoreMaker").toString(),4);
                    retrieveUsername(sortedArray.getJSONObject(4).get("scoreMaker").toString(),5);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            /**
             * If unsuccessful, prints error message
             *
             * @param error - the error received
             */
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgressDialog();
            }
        });

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public void retrieveUsername(String id, int i)
    {
        String tag_string_req = "string_req";
        Integer idi=Integer.parseInt(id);

        String URL= "http://coms-309-010.cs.iastate.edu:8080/getUsernameWithID/"+idi;

        StringRequest strReq = new StringRequest(Request.Method.GET,URL, new Response.Listener<String>()
        {

            /**
             * Converts the information received from the server and displays username
             * associated with the received account information.
             * @param response - the string received from the server
             */
            @Override
            public void onResponse(String response)
            {

                switch(i)
                {
                    case 1: playerName1.setText(response);break;
                    case 2: playerName2.setText(response);break;
                    case 3: playerName3.setText(response);break;
                    case 4: playerName4.setText(response);break;
                    case 5: playerName5.setText(response);break;
                }

            }
        }, new Response.ErrorListener() {

            /**
             * Displays error message and hides progress bar upon receiving an error.
             * @param error
             */
            @Override
            public void onErrorResponse(VolleyError error)
            {
                //VolleyLog.d(TAG, "Error: " + error.getMessage());
                //hideProgressDialog();
            }
        });
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
        //return nameToReturn;

    }

    /**
     * This method is only called in PuzzleCompleteActivity, and only if the Leaderboard
     * shortcut is clicked on that page.
     * Sets IDVal to the ID of the completed puzzle, meaning the editable text box will
     * automatically be filled in with an ID. Also sets a flag to indicate this.
     * @param remID
     */
    public static void rememberingID(String remID) {
        IDVal = remID;
        remembering = true;
    }

    //Used for mockito testing
    public boolean getRemembering() {
        return remembering;
    }

    //Used for mockito testing
    public String getIDVal() {
        return IDVal;
    }

}
