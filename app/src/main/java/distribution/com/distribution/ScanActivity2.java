package distribution.com.distribution;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by POPO on 1/29/2018.
 */

public class ScanActivity2 extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    public static TextView kode;
    private ZXingScannerView mScannerView;
    private static final int REQUEST_CAMERA = 0;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        kode = new TextView(this);
        setContentView(mScannerView);                // Set the scanner view as the content view
    }

    @Override
    public void onResume() {
        super.onResume();
        String cameraPermission = android.Manifest.permission.CAMERA;
        int permissionCheck = ContextCompat.checkSelfPermission(ScanActivity2.this, cameraPermission);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
            mScannerView.startCamera();          // Start camera on resume
        } else {
            requestPermissions(new String[]{cameraPermission}, REQUEST_CAMERA);
        }       // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    public void handleResult(final Result rawResult) {
        // Do something with the result here
        // Log.v("tag", rawResult.getText()); // Prints scan results
        // Log.v("tag", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)


//        kode.setText(rawResult.getText());
        // If you would like to resume scanning, call this method below:
        //mScannerView.resumeCameraPreview(this);


        class UserLogin2 extends AsyncTask<Void, Void, String> {



            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);


                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    String status = obj.getString("status");

                    switch(status) {
                        //if no error in response
                        case "1":


                            //getting the user from the response

                            JSONObject json= obj.getJSONObject("data");
                            //JSONObject w = json.getJSONObject("product");


                            //creating a new user object
                            User4 user3 = new User4(
                                    json.getString("id"),
                                    json.getString("activation_qr"),
                                    json.getString("serial_code"),
                                    json.getString("initial_code"),
                                    json.getString("name"),
                                    json.getString("status")

                            );

                            SharedPrefManager2.getInstance(getApplicationContext()).getAct(user3);

                            finish();
                            startActivity(new Intent(getApplicationContext(), Actphone.class));





                            //starting the profile activity


                            break;

                        default:

                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                            break;
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                String kode1 = rawResult.toString();
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("activation_qr", kode1);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_ACTINFO, params);
            }
        }

        UserLogin2 ul = new UserLogin2();
        ul.execute();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CAMERA) {
            for (int i = 0; i < permissions.length; i++) {
                if (permissions[i].equals(android.Manifest.permission.CAMERA)
                        && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    mScannerView.startCamera();
                }
            }
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        else {
            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }
    }
}