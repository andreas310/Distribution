package distribution.com.distribution;

/**
 * Created by POPO on 1/21/2018.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static java.lang.Integer.valueOf;

public class ProfileActivity extends AppCompatActivity {

    TextView textViewId, textViewUsername, textViewEmail, textViewGender;
    Context context =this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //if the user is not logged in
        //starting the login activity
        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }


        textViewId = (TextView) findViewById(R.id.textViewId);
        textViewUsername = (TextView) findViewById(R.id.textViewUsername);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        textViewGender = (TextView) findViewById(R.id.textViewGender);


        //getting the current user
        User user = SharedPrefManager.getInstance(this).getUser();

        String role = user.getRole();
        switch(role) {
            case "admin":
                startActivity(new Intent(this, AdminActivity.class));
                finish();
                break;

            case "superadmin":
                startActivity(new Intent(this, AdminActivity.class));
                finish();
                break;

            case "dealer":

                startActivity(new Intent(this, DealerActivity.class));
                finish();
                break;

            default:
                startActivity(new Intent(this, SalesActivity.class));
                finish();
                break;
        }

        //setting the values to the textviews
        textViewId.setText(user.getSalt());
        textViewUsername.setText(user.getUsername());
        textViewEmail.setText(user.getEmail());
        textViewGender.setText(user.getRole());

        //when the user presses logout button
        //calling the logout method



    }


}