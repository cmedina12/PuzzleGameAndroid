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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import coms.example.puzzlegame.app.AppController;

/**
 * An {@link Activity} subclass
 * User view for this screen is defined in activity_main_home.xml.
 *
 * This class is our current functioning home menu, without the working navigation bar.
 * It contains a button to traverse to an {@link StringRequestActivity} java and xml screen.
 *
 * @author ldjansen
 * @author cmedina2
 */
public class MainHomeActivity extends Activity implements OnClickListener {
    private Button btnAccount, btnCreate, btnPlay, btnLeaderboard, btnReport;

    private static JSONObject user;

    private Button submit, logout, register;
    private EditText loginField, passwordField;
    private TextView loginText, passwordText, emailText;

    /**
     * A method that defines the user view and button to move to {@link StringRequestActivity}.
     * @param savedInstanceState - the current state of the bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);

        btnAccount = (Button) findViewById(R.id.btnAccountSettings);
        btnCreate = (Button) findViewById(R.id.btnCreatePuzzle);
        btnPlay = (Button) findViewById(R.id.btnPlayPuzzle);
        btnLeaderboard = (Button) findViewById(R.id.btnGoToLeaderboard);
        btnReport = (Button) findViewById(R.id.btnReport);


        loginText = (TextView) findViewById(R.id.loginNotif);
        passwordText = (TextView) findViewById(R.id.passwordNotif);
        submit = (Button) findViewById(R.id.btnSubmitUserInfo);
        loginField= (EditText) findViewById(R.id.login);
        passwordField= (EditText) findViewById(R.id.password);
        logout = (Button) findViewById(R.id.btnLogout);
        register = (Button) findViewById(R.id.btnRegister);
        emailText = (TextView) findViewById(R.id.emailNotif);

        // button click listeners
        btnAccount.setOnClickListener(this);
        //btnCreate.setOnClickListener(this);
        btnCreate.setOnClickListener((v) -> {bringToPage("http://coms-309-010.cs.iastate.edu:8080/uploadImage");});
        btnPlay.setOnClickListener(this);
        btnLeaderboard.setOnClickListener(this);
        btnReport.setOnClickListener(this);

        submit.setOnClickListener((v) -> { submitLoginData(); });
        logout.setOnClickListener((v) -> {
            logoutUser();
            checkLoginStatus();
        });
        register.setOnClickListener((v) -> {bringToPage("http://coms-309-010.cs.iastate.edu:8080/user");});
        checkLoginStatus();
    }

    /**
     * A method that changes the view to a different screen based on what button is pressed.
     * Currently only moves to the Account screen.
     * @param v - the view
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAccountSettings:
                startActivity(new Intent(MainHomeActivity.this,
                        StringRequestActivity.class));
                break;
            case R.id.btnCreatePuzzle:
                startActivity(new Intent(MainHomeActivity.this,
                        CreatePuzzleActivity.class));
                break;
            case R.id.btnPlayPuzzle:
                startActivity(new Intent(MainHomeActivity.this,
                        SelectPuzzleActivity.class));
                break;
            case R.id.btnGoToLeaderboard:
                startActivity(new Intent(MainHomeActivity.this,
                        LeaderboardActivity.class));
                break;
            case R.id.btnReport:
                startActivity(new Intent(MainHomeActivity.this,
                        ReportActivity.class));
                break;
            default:
                break;
        }
    }

    private void bringToPage(String url)
    {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    private void submitLoginData()
    {
        //showProgressDialog();
        String tag_string_req = "string_req";

        String log = loginField.getText().toString();
        String pass = passwordField.getText().toString();

        String URL= "http://coms-309-010.cs.iastate.edu:8080/getUser/"+log+"/"+pass;
        StringRequest strReq = new StringRequest(Request.Method.GET,URL, new Response.Listener<String>() {

            /**
             * Converts the information received from the server and displays username
             * associated with the received account information.
             * @param response - the string received from the server
             */
            @Override
            public void onResponse(String response) {
                //Log.d(TAG, response.toString());
                //msgResponse.setText(response.toString());
                //hideProgressDialog();

                try
                {
                    user = new JSONObject(response.toString());
                    System.out.println(user);
                    // = setString(R.string.Username);
                    //msgResponse.setText((CharSequence) user.get("name"));
                    if(user==null)
                    {
                        //Toast.makeText(getApplicationContext(), "User not found!",Toast.LENGTH_SHORT).show(); //Called a toast, can be SHORT or LONG
                    }
                    checkLoginStatus();

                }
                catch (JSONException e)
                {
                    Toast.makeText(getApplicationContext(), "User not found!",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
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
    }

    public static void logoutUser()
    {
        user=null;
        //checkLoginStatus();
    }

    public void checkLoginStatus()
    {


        if(user==null)
        {

            btnAccount.setVisibility(View.GONE);
            btnCreate.setVisibility(View.GONE);
            btnPlay.setVisibility(View.GONE);
            btnLeaderboard.setVisibility(View.GONE);
            btnReport.setVisibility(View.GONE);
            logout.setVisibility(View.GONE);

            submit.setVisibility(View.VISIBLE);
            loginField.setVisibility(View.VISIBLE);
            loginText.setVisibility(View.VISIBLE);
            passwordField.setVisibility(View.VISIBLE);
            passwordText.setVisibility(View.VISIBLE);
            emailText.setVisibility(View.VISIBLE);

        }
        else
        {
            /*btnAccount.setVisibility(View.VISIBLE);
            btnCreate.setVisibility(View.VISIBLE);
            btnPlay.setVisibility(View.VISIBLE);
            btnLeaderboard.setVisibility(View.VISIBLE);
            btnReport.setVisibility(View.VISIBLE);
            logout.setVisibility(View.VISIBLE);
            register.setVisibility(View.VISIBLE);

            submit.setVisibility(View.GONE);
            loginField.setVisibility(View.GONE);
            passwordField.setVisibility(View.GONE);
            loginText.setVisibility(View.GONE);
            passwordText.setVisibility(View.GONE);*/
            boolean banned= false;

            try
            {
                banned = (boolean)user.get("ban");
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

            if(banned)
            {
                Toast.makeText(getApplicationContext(), "The user you are trying to login as is banned!",Toast.LENGTH_SHORT).show();
            }
            else
            {
                btnAccount.setVisibility(View.VISIBLE);
                btnCreate.setVisibility(View.VISIBLE);
                btnPlay.setVisibility(View.VISIBLE);
                btnLeaderboard.setVisibility(View.VISIBLE);
                btnReport.setVisibility(View.VISIBLE);
                logout.setVisibility(View.VISIBLE);


                submit.setVisibility(View.GONE);
                loginField.setVisibility(View.GONE);
                passwordField.setVisibility(View.GONE);
                loginText.setVisibility(View.GONE);
                passwordText.setVisibility(View.GONE);
                emailText.setVisibility(View.GONE);
                register.setVisibility(View.GONE);
            }
        }
    }

    public static JSONObject getUser()
    {
        return user;
    }

}
