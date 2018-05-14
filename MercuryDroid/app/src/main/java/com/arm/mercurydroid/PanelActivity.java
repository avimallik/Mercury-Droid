package com.arm.mercurydroid;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.nitri.gauge.Gauge;

public class PanelActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Handler mHandler;

    TextView tempCelsius, tempFahrenheit, tempKelvin, tempHumid, tempHeatIndex, weatherStatusTxt, tempThresholdDisp, ipTxtDisplay;

    //Variables For JsonObject Parsing
    String responseTempCelsius, responseTempfahrenheit, responseTempKelvin, responseHumid, responseHeatIndex;

    //Global Variable for store Weather Server IP Address
    String ip ;
    String decTemp;

    //Gauge View
    Gauge gaugeCelsius ;

    //Alart Resources
    public static MediaPlayer weatherAlart;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(toolbar.getTitle());

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Code Start From Here
        this.mHandler = new Handler();

        //ALL Components Declaration
        // Temperature - Fahrenheit, Kelvin, Celsius
        tempFahrenheit = (TextView) findViewById(R.id.tempFahrenheit);
        tempKelvin = (TextView) findViewById(R.id.tempKelvin);
        tempCelsius = (TextView) findViewById(R.id.tempCelsius);

        //Humidity
        tempHumid = (TextView) findViewById(R.id.tempHumid);

        //HeatIndex
        tempHeatIndex = (TextView) findViewById(R.id.tempHeatIndex);

        //Home Weather Status
        weatherStatusTxt = (TextView) findViewById(R.id.weatherStatusTxt);

        //Max Temperature Thresh hold
        tempThresholdDisp = (TextView) findViewById(R.id.tempThresholdDisp);

        //IP Address Display String
        ipTxtDisplay = (TextView) findViewById(R.id.ipTxtDisplay);

        //Gauge of Celsius
        gaugeCelsius = (Gauge) findViewById(R.id.gauge);

        //Alart Sound
        weatherAlart =  MediaPlayer.create(PanelActivity.this,R.raw.alartbeeptwo);

            try{
                URL myUrl = new URL("http://"+ip+"/data");
                URLConnection connection = myUrl.openConnection();
                connection.setConnectTimeout(100);
                connection.connect();
            } catch (Exception e) {
                // Handle your exceptions
                Toast.makeText(getApplication(),"Mercury Droid Started",Toast.LENGTH_SHORT).show();
            }


        networkStateCheck();
        m_Runnable.run();
        new MercuryDroidServer().execute();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.panel, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_start_server) {
            SharedPreferences sharedPref = getSharedPreferences("ipInfoWeather", Context.MODE_PRIVATE);
            ip = sharedPref.getString("ipaddressWeather", "");
            decTemp = sharedPref.getString("decInfo658", "");
            ipTxtDisplay.setText(ip);
            tempThresholdDisp.setText(decTemp);
            return true;
        } if (id == R.id.action_ip_add) {

            ipAdderFunc();
            return true;

        } if (id == R.id.action_threshold) {

            thresholdAdder();
            return true;

        } if (id == R.id.action_exit) {

            System.exit(1);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_start_server) {
            
            SharedPreferences sharedPref = getSharedPreferences("ipInfoWeather",Context.MODE_PRIVATE);
            ip = sharedPref.getString("ipaddressWeather",null);
            decTemp = sharedPref.getString("decInfo658","");
            ipTxtDisplay.setText(ip);
            tempThresholdDisp.setText(decTemp);

        } else if (id == R.id.nav_add_ip) {

            ipAdderFunc();

        } else if (id == R.id.nav_add_threshold) {

            thresholdAdder();

        } else if (id == R.id.nav_about) {

            Intent aboutIntent = new Intent(getApplicationContext(), About.class);
            startActivity(aboutIntent);

        } else if (id == R.id.nav_build_mercury_droid) {

            String mercuryDroidBuildingInstructionUrl = "https://www.google.com/";
            Intent mercuryDroidBuildIntent = new Intent(getApplicationContext(), ConfigureActivity.class);
            mercuryDroidBuildIntent.putExtra("key_build_111",mercuryDroidBuildingInstructionUrl);
            startActivity(mercuryDroidBuildIntent);

        } else if (id == R.id.nav_add_configuration) {
            String mercuryDroidConfigUrl = "file:///android_asset/index.html";
            Intent mercuryDroidConfigIntent = new Intent(getApplicationContext(), ConfigureActivity.class);
            mercuryDroidConfigIntent.putExtra("key_config_122",mercuryDroidConfigUrl);
            startActivity(mercuryDroidConfigIntent);
        }
        else if (id == R.id.nav_share) {

            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Mercury Droid";
            String shareSub = "IoT Home Weather Monitoring System";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share By Following"));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class MercuryDroidServer extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            String out = null;
            JSONObject jObject;
            try {
                DefaultHttpClient httpClient = new DefaultHttpClient();

                final HttpParams httpParameters = httpClient.getParams();

                    HttpConnectionParams.setConnectionTimeout(httpParameters, 15000);
                    HttpConnectionParams.setSoTimeout(httpParameters, 15000);
                if(ip == ""){
                    ip = "test";
                }
                    HttpGet httpPost = new HttpGet("http://"+ip+"/data");
                    HttpResponse httpResponse = httpClient.execute(httpPost);
                    HttpEntity httpEntity = httpResponse.getEntity();

                    out = EntityUtils.toString(httpEntity, org.apache.http.protocol.HTTP.UTF_8);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return out;
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try{

                JSONObject object = new JSONObject(result);

                responseTempCelsius = object.getString("temperature");
                responseTempfahrenheit = object.getString("fahrenheit");
                responseTempKelvin = object.getString("kelvin");
                responseHumid = object.getString("Humidity");
                responseHeatIndex = object.getString("heatindex");

                tempCelsius.setText(responseTempCelsius);
                tempKelvin.setText(responseTempKelvin);
                tempFahrenheit.setText(responseTempfahrenheit);
                tempHumid.setText(responseHumid);
                tempHeatIndex.setText(responseHeatIndex);

                float responseTempConvInt = Float.parseFloat(responseTempCelsius);
                float tempIndexConvInt = Float.parseFloat(tempThresholdDisp.getText().toString());


                if(responseTempConvInt >= tempIndexConvInt){
                    weatherStatusTxt.setText("Warning ! Out of threshold");
                    weatherStatusTxt.setBackgroundColor(Color.parseColor("#ff0019"));
                    weatherAlart.start();
                }else if(responseTempConvInt <= 22){
                    weatherStatusTxt.setText("Cool Weather");
                    weatherStatusTxt.setBackgroundColor(Color.TRANSPARENT);
                }else if(responseTempConvInt <= 30){
                    weatherStatusTxt.setText("Moderate Cool Weather");
                    weatherStatusTxt.setBackgroundColor(Color.TRANSPARENT);
                }else if(responseTempConvInt <= 40){
                    weatherStatusTxt.setText("Hot Weather");
                    weatherStatusTxt.setBackgroundColor(Color.TRANSPARENT);
                }else if (responseTempConvInt >= 41){
                    weatherStatusTxt.setText("Very Hot Weather");
                    weatherStatusTxt.setBackgroundColor(Color.TRANSPARENT);
                }else if(responseTempConvInt <= tempIndexConvInt){
                    weatherStatusTxt.setBackgroundColor(Color.TRANSPARENT);
                    weatherAlart.stop();
                }

                gaugeCelsius.moveToValue(responseTempConvInt);

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private final Runnable m_Runnable = new Runnable()
    {
        public void run()
        {
            new MercuryDroidServer().execute();
            PanelActivity.this.mHandler.postDelayed(m_Runnable,2000);
        }

    };

    public void ipAdderFunc(){
        LayoutInflater layoutInflater = LayoutInflater.from(PanelActivity.this);
        View promptView = layoutInflater.inflate(R.layout.ip_input_weather_server, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PanelActivity.this);
        alertDialogBuilder.setView(promptView);

        final String ipRegEx = "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";

        final EditText ipTxtField = (EditText) promptView.findViewById(R.id.tempMaxThreshHold);
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Pattern patternIp = Pattern.compile(ipRegEx);
                        Matcher matcherIp = patternIp.matcher(ipTxtField.getText().toString());

                        if(matcherIp.find()){

                            SharedPreferences sharedPref = getSharedPreferences("ipInfoWeather", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("ipaddressWeather",ipTxtField.getText().toString());
                            editor.apply();

                        }else{
                            Toast.makeText(getApplicationContext(),"Wrong IP Addrsss !", Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    public void thresholdAdder(){
        LayoutInflater layoutInflater = LayoutInflater.from(PanelActivity.this);
        View promptView = layoutInflater.inflate(R.layout.threshold_input_field, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PanelActivity.this);
        alertDialogBuilder.setView(promptView);

        final EditText tempMaxThreshHold = (EditText) promptView.findViewById(R.id.tempMaxThreshHold);

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                            SharedPreferences sharedPref = getSharedPreferences("ipInfoWeather", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("decInfo658",tempMaxThreshHold.getText().toString());
                            editor.apply();
                            tempThresholdDisp.setText(tempMaxThreshHold.getText().toString());
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    public void networkStateCheck(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo wifi = cm
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        android.net.NetworkInfo datac = cm
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifi != null & datac != null)
                && (wifi.isConnected() | datac.isConnected())) {
        }else{

            Context context = getApplicationContext();
            AlertDialog.Builder builder = new AlertDialog.Builder(PanelActivity.this);
            builder.setTitle("Confirm");
            builder.setMessage("No internet Connection ! are You sure to connect internet ?");

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    final Intent intent = new Intent(Intent.ACTION_MAIN, null);
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    final ComponentName cn = new ComponentName("com.android.settings", "com.android.settings.wifi.WifiSettings");
                    intent.setComponent(cn);
                    intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });

            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
        }
    }
}
