package com.example.agried;
import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.StrictMode;
import android.os.SystemClock;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Method;
import java.sql.Array;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.MenuItem;



public class Choice extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Button b1, b2;
    NavigationView nav;
    TextView timer, txt, prediction,cityy,tempp,humi,ph_value,rain,field_info;
    private long startTime = 0L;
    private Handler customHandler = new Handler();
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    GPSTracker gps;
    double latitude;
    double longitude;
    private RequestQueue mRequestQueue;
    private RequestQueue queue;
    private String url;
    String u;
    int humidity;
    int temp;
    double ph = 6.26;
    double rainfall = 90.38;
    String city;
    public JSONObject postparams = new JSONObject();
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    JsonObjectRequest jsonObjReq;
    String url1 = "http://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid={3f670ade32f131df0b0b45141ffc33ae}";

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.choice);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //b1 = (Button) findViewById(R.id.motor);
        b2 = (Button) findViewById(R.id.predict);
        //timer = (TextView) findViewById(R.id.timer);
        //txt = (TextView) findViewById(R.id.txt);
        prediction = (TextView) findViewById(R.id.prediction);
        field_info = (TextView) findViewById(R.id.fieldInfo);
        cityy=(TextView)findViewById(R.id.city);
        tempp=(TextView)findViewById(R.id.temp);
        humi=(TextView)findViewById(R.id.humidity);
        ph_value=(TextView)findViewById(R.id.ph);
        rain=(TextView)findViewById(R.id.rainfall);
        nav=(NavigationView) findViewById(R.id.navigation);
        nav.setNavigationItemSelectedListener(this);
        // drawer layout instance to toggle the menu icon to open
        // drawer and back button to close drawer
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.setVisibility(View.VISIBLE);
                txt.setVisibility(View.VISIBLE);
                CharSequence txt = b1.getText();
                if (txt.equals("Motor On")) {
                    b1.setText("Motor Off");
                    startTime = SystemClock.uptimeMillis();
                    customHandler.postDelayed(updateTimerThread, 0);
                } else {
                    b1.setText("Motor On");
                    timeSwapBuff += timeInMilliseconds;
                    customHandler.removeCallbacks(updateTimerThread);
                }

            }
        });*/

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prediction.setVisibility(View.VISIBLE);
                field_info.setVisibility(View.VISIBLE);
                tempp.setVisibility(View.VISIBLE);
                humi.setVisibility(View.VISIBLE);
                ph_value.setVisibility(View.VISIBLE);
                rain.setVisibility(View.VISIBLE);
                gps = new GPSTracker(Choice.this);
                if (gps.canGetLocation()) {
                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();
                    // \n is for new line
                    Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                } else {
                    // Can't get location.
                    // GPS or network is not enabled.
                    // Ask user to enable GPS/network in settings.
                    gps.showSettingsAlert();
                }
                predictCrop(postparams);
                sendAndRequestResponse();


            }
        });
    }


    // override the onOptionsItemSelected()
    // function to implement
    // the item click listener callback
    // to open and close the navigation
    // drawer when the icon is clicked
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void sendAndRequestResponse() {
        u = "https://api.weatherbit.io/v2.0/current?lat=" + latitude + "&lon=" + longitude + "&key=fe409b80c2444171bba5aa6cc460b4f2&include=minutely";
        //RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(this);
        // Log.v("url",u);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, u,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);
                            Log.v("aanal", String.valueOf(obj));
                            //we have the array named hero inside the object
                            //so here we are getting that json array
                            JSONArray heroArray = obj.getJSONArray("data");
                            //now looping through all the elements of the json array
                            for (int i = 0; i < heroArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject heroObject = heroArray.getJSONObject(i);

                                //creating a hero object and giving them the values from json object
                                 humidity = heroObject.getInt("rh");
                                 temp = heroObject.getInt("temp");
                                 city = heroObject.getString("city_name");
                                Log.v("aanal", String.valueOf(humidity));
                                Log.v("aanal", String.valueOf(temp));
                                Log.v("aanal", String.valueOf(city));
                                 //postparams = new JSONObject();
                                try {
                                    postparams.put("temperature", temp);
                                    postparams.put("humidity",humidity);
                                    postparams.put("ph",ph);
                                    postparams.put("rainfall",rainfall);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            //Log.v("getenvironmentvalues", String.valueOf(postparams));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        mRequestQueue.add(stringRequest);

    }

    public void predictCrop(JSONObject postparams2) {
        Log.v("aanal", String.valueOf(postparams2));
        url = "http://192.168.43.9:12345/predict";
        queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, postparams2,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String a =response.toString();
                        a=a.replace("{","");
                        a=a.replace("}","");
                        a =a.replace(":","");
                        String ab =a.replace("\"","");
                        cityy.setText(city+",Gujarat");
                        tempp.setText("Temp"+"\n"+temp+"\u2103");
                        humi.setText("Humid"+"\n"+humidity+"%");
                        ph_value.setText("PH"+"\n"+ph);
                        rain.setText("Rainfall"+"\n"+rainfall+"mm");
                        prediction.setText(ab);
                        //prediction.setText("The predicted crop is mango");

                        //Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

        queue.add(jsonObjReq);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment frag = null; // create a Fragment Object
        int itemId = item.getItemId();
        if (itemId == R.id.nav_contact)
        {
            Intent i = new Intent(getApplicationContext(), ContactUs.class);
            startActivity(i);
        }
        else if (itemId == R.id.nav_bug)
        {
            Intent i = new Intent(getApplicationContext(), Bug.class);
            startActivity(i);
        }
        else if (itemId == R.id.nav_suggestion)
        {
            Intent i = new Intent(getApplicationContext(), Suggestion.class);
            startActivity(i);
        }
        else if (itemId == R.id.nav_logout)
        {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
        super.onBackPressed();
    }}
    /* //String Request initialized
       mStringRequest = new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
                prediction.setText(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("myApp","Error :" + error.toString());
            }
        });
        mStringRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(mStringRequest);*/
//}




    /*private Runnable updateTimerThread = new Runnable() {

        public void run() {

            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;

            updatedTime = timeSwapBuff + timeInMilliseconds;

            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            int hours = mins / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            timer.setText("" + hours + ":"
                    + String.format("%02d", mins) + ":"
                    + String.format("%02d", secs));
            customHandler.postDelayed(this, 0);
        }*/

    //};

}
