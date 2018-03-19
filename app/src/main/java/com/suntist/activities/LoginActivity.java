package com.suntist.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.suntist.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by Dell on 3/18/2018.
 */

public class LoginActivity extends AppCompatActivity {



    LoginButton btn_fb;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();


        PackageInfo info;
        try {
            info = getPackageManager().getPackageInfo("com.suntist", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.e("hash key", something);

                Log.v("##Suntist LA","FB KeyHash : "+something);


            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }


        setContentView(R.layout.activity_login);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.login_toolbar);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.login_toolbar);


        setSupportActionBar(toolbar);




        btn_fb = (LoginButton) findViewById(R.id.login_button);
        btn_fb.setReadPermissions(Arrays.asList("public_profile", "email"));


        btn_fb.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                final AccessToken accessToken = loginResult.getAccessToken();

                GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        try {




                            Log.v("##Suntist LA","FB ID : "+object.getString("id"));
                            Log.v("##Suntist LA","FB Name : "+object.getString("name"));
                            Log.v("##Suntist LA","FB Email : "+object.getString("email"));


                            String fb_name, fb_email;

                            fb_name = object.getString("name");
                            fb_email = object.getString("email");



                            Intent it = new Intent(LoginActivity.this,HomeActivity.class);
                            it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            it.putExtra("FB_NAME", fb_name);
                            it.putExtra("FB_EMAIL", fb_email);
                            startActivity(it);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }
                });


                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();



            }

            @Override
            public void onCancel() {

                Toast.makeText(LoginActivity.this,"Cancelled",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(FacebookException error) {



            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);

        super.onActivityResult(requestCode, resultCode, data);
    }
}
