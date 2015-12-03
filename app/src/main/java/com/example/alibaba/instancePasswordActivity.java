package com.example.alibaba;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class instancePasswordActivity extends Activity implements View.OnClickListener{
    TextView txt_password_value;
    Button btn_create;
    private String passSHA1;
    private String passString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instance_password);

        txt_password_value = (TextView)findViewById(R.id.txt_password_value);
        btn_create = (Button)findViewById(R.id.btn_create);

        btn_create.setOnClickListener(this);

        passString = makeRandom(5);
        passSHA1 = sha1(passString);

        txt_password_value.setText(passString);


    }


    public String makeRandom(int length){
        String returnvalue = "";
        Random customRandom = new Random();

        for(int i=0; i<length; i++){
            returnvalue += customRandom.nextInt(10);
        }


        Log.d("password",returnvalue+"");

        return returnvalue;
    }

    public String sha1(String password) {
        int salt = 930817;
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(password.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i])+salt);
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
    private void setPassword() {
        final String testURL = "http://minshu.iptime.org:2510/alibaba/setPassword.php?password="+passSHA1;

        JsonArrayRequest jsonObjReq = new JsonArrayRequest(testURL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Parsing json object response
                            // response will be a json object
                            //set global object
                            response.get(0);

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("BottomBar", "Error: " + error.getMessage());
                // hide the progress dialog
            }
        });
        // Adding request to request queue
        try {
            MyApplication.getInstance().addToRequestQueue(jsonObjReq);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        setPassword();
    }
}
