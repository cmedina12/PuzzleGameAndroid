package coms.example.puzzlegame;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
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
 * An {@link android.app.Activity} subclass.
 * User view for this screen is defined in string_request.xml.
 *
 * Defines the account screen, including all buttons and their operations.
 * User can check their username in the database with the Get User button.
 * User can change their account's associated email with the Change Email button.
 * User can change their account's associated password with the Change Password button.
 *
 * @author ldjansen
 * @author cmedina2
 */
public class StringRequestActivity  extends android.app.Activity
{
    private String TAG = StringRequestActivity.class.getSimpleName();


    //used to cancel request
    private String tag_string_req = "string_req";

    String newEmail = "Default";

    private Button btnStringReq, btnChangeEmailReq, btnChangePwdReq, btnDeleteAccount;
    private TextView msgResponse, msgEmailResponse, msgPwdResponse;

    private ProgressDialog pDialog;

    /**
     * Defines elements in the view, such as the buttons and progress bar.
     * @param savedInstanceState - the current instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.string_request);

        /**
         * Buttons and associated response initializations
         */

        btnStringReq = (Button) findViewById(R.id.btnStringReq);
        msgResponse = (TextView) findViewById(R.id.msgResponse);

        btnChangeEmailReq = (Button) findViewById(R.id.btnChangeEmailRequest);
        msgEmailResponse = (TextView) findViewById(R.id.msgEmailResponse);

        btnChangePwdReq = (Button) findViewById(R.id.btnChangePwdRequest);
        msgPwdResponse = (TextView) findViewById(R.id.msgPwdResponse);

        btnDeleteAccount = (Button) findViewById(R.id.btnDeleteAccount);

        /**
         * Progress bar that displays "Loading..."
         */
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        /**
         * Define what happens on button presses
         */

        btnStringReq.setOnClickListener((v) -> { getID(); });
        btnChangeEmailReq.setOnClickListener((v) -> {
            try {
                makeChangeEmailReq();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        btnChangePwdReq.setOnClickListener((v) -> {
            try {
                makeChangePwdReq();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        btnDeleteAccount.setOnClickListener((v) -> {
            try {
                deleteUserAccount();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Shows the progress bar with loading circle symbol and "Loading...".
     */
    public void showProgressDialog() {
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

    private void getID()
    {
        try
        {
            Toast.makeText(getApplicationContext(), "Your User ID is: "+MainHomeActivity.getUser().get("id"),Toast.LENGTH_SHORT).show();
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    private void deleteUserAccount() throws JSONException {
        showProgressDialog();

        //Creates onscreen user input prompt
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure you'd like to delete your account?");

        //Declares user input to be editable text of password type
        //final EditText input = new EditText(this);
        //input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        //builder.setView(input);

        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            /**
             * Handles logic if user selects Confirm
             * @param dialog
             * @param which
             */
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Sets the password to user input
                //String newPassword = input.getText().toString();
                String url = null;
                try {
                     url="http://coms-309-010.cs.iastate.edu:8080/deleteUser/" + MainHomeActivity.getUser().get("id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                StringRequest strReq = new StringRequest(Request.Method.GET,
                        url, new Response.Listener<String>() {

                    /**
                     * If successful, print what password was changed to
                     * @param response - received string associated with the user in the database
                     */
                    @Override
                    public void onResponse(String response) {

                        MainHomeActivity.logoutUser();
                        Toast.makeText(getApplicationContext(), response.toString(),Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(StringRequestActivity.this, MainHomeActivity.class));
                    }
                }, new Response.ErrorListener() {

                    /**
                     * If unsuccessful, print error message
                     * @param error - the error received
                     */
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        hideProgressDialog();
                    }
                });

                AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            /**
             * If user clicks Cancel, exit the input box
             * @param dialog - dialog to cancel
             * @param which
             */
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                hideProgressDialog();
            }
        });

        builder.show();
    }

    /**
     * Method for handling the database changes when the user attempts to change their email
     * @throws JSONException
     */
    private void makeChangeEmailReq() throws JSONException
    {
        showProgressDialog();

        //Create onscreen input prompt
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter new Email Address");

        //Declares type of user input to be an editable email address
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        builder.setView(input);

        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {

            /**
             * Set up the Confirm button's usage
             * @param dialog
             * @param which
             */
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Sets the email to user input
                String newEmail = input.getText().toString();
                String url = null;
                try {
                    url = "http://coms-309-010.cs.iastate.edu:8080/changeUserEmail/" + MainHomeActivity.getUser().get("email")
                            + "/" + newEmail;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                StringRequest strReq = new StringRequest(Request.Method.GET,
                        url, new Response.Listener<String>() {

                    /**
                     * If successful, prints what email is changed to
                     * @param response - the received information from the server
                     */
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        msgEmailResponse.setText(response.toString() + " to " + newEmail);
                        hideProgressDialog();

                        MainHomeActivity.logoutUser();
                        Toast.makeText(getApplicationContext(), "Email changed to "+newEmail,Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(StringRequestActivity.this, MainHomeActivity.class));
                    }
                }, new Response.ErrorListener() {

                    /**
                     * If unsuccessful, prints error message
                     * @param error - the error received
                     */
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        hideProgressDialog();
                    }
                });

                AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
            }
        });


        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            /**
             * Cancels menu upon clicking Cancel button
             * @param dialog - dialog to stop
             * @param which
             */
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                hideProgressDialog();
            }
        });

        builder.show();
    }

    /**
     * Method for handling the database changes when the user attempts to change their email
     * @throws JSONException
     */
    private void makeChangePwdReq() throws JSONException {
        showProgressDialog();

        //Creates onscreen user input prompt
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter new Password");

        //Declares user input to be editable text of password type
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            /**
             * Handles logic if user selects Confirm
             * @param dialog
             * @param which
             */
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Sets the password to user input
                String newPassword = input.getText().toString();
                String url = null;
                try {
                    url = "http://coms-309-010.cs.iastate.edu:8080/changeUserPassword/" + MainHomeActivity.getUser().get("email")
                            + "/" + MainHomeActivity.getUser().get("password") + "/" + newPassword;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                StringRequest strReq = new StringRequest(Request.Method.GET,
                        url, new Response.Listener<String>() {

                    /**
                     * If successful, print what password was changed to
                     * @param response - received string associated with the user in the database
                     */
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        msgPwdResponse.setText(response.toString() + " to " + newPassword);
                        hideProgressDialog();

                        MainHomeActivity.logoutUser();
                        Toast.makeText(getApplicationContext(), "Password changed!",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(StringRequestActivity.this, MainHomeActivity.class));
                    }
                }, new Response.ErrorListener() {

                    /**
                     * If unsuccessful, print error message
                     * @param error - the error received
                     */
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        hideProgressDialog();
                    }
                });

                AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            /**
             * If user clicks Cancel, exit the input box
             * @param dialog - dialog to cancel
             * @param which
             */
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                hideProgressDialog();
            }
        });

        builder.show();
    }

    /*
    public String getURL() {
        return url;
    }
    */

    public Button getBtnStringReq() {
        return btnStringReq;
    }

    public String getEmail() {
        return newEmail;
    }
}