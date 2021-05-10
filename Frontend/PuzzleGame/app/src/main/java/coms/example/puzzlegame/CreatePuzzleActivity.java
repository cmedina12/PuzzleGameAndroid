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
public class CreatePuzzleActivity  extends android.app.Activity {
    //private String TAG = CreatePuzzleActivity.class.getSimpleName();
    private int imageCount = 0;

    //used to cancel request
    //private String tag_string_req = "string_req";

    //Does nothing currently
    String url = "http://coms-309-010.cs.iastate.edu:8080/puzzles";
    private Button btnUploadImage;
    //private TextView msgResponse, msgEmailResponse, msgPwdResponse;

    private ProgressDialog pDialog;

    /**
     * Defines elements in the view, such as the buttons and progress bar.
     * @param savedInstanceState - the current instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_puzzle);

        /**
         * Buttons and associated response initializations
         */
        btnUploadImage = (Button) findViewById(R.id.btnUploadImage);
        /*
        btnStringReq = (Button) findViewById(R.id.btnStringReq);
        msgResponse = (TextView) findViewById(R.id.msgResponse);

        btnChangeEmailReq = (Button) findViewById(R.id.btnChangeEmailRequest);
        msgEmailResponse = (TextView) findViewById(R.id.msgEmailResponse);

        btnChangePwdReq = (Button) findViewById(R.id.btnChangePwdRequest);
        msgPwdResponse = (TextView) findViewById(R.id.msgPwdResponse);
*/
        /**
         * Progress bar that displays "Loading..."
         */
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        /**
         * Define what happens on button presses
         */
        btnUploadImage.setOnClickListener((v) -> { selectImage(); });
/*
        btnStringReq.setOnClickListener((v) -> { makeStringReq(); });
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
        });*/
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

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, imageCount);
        imageCount++;
    }

}
