/**
 * Copyright 2014 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.android.gms.location.sample.basiclocationsample;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Locale;

/**
 * Location sample.
 * <p/>
 * Demonstrates use of the Location API to retrieve the last known location for a device.
 * This sample uses Google Play services (GoogleApiClient) but does not need to authenticate a user.
 * See https://github.com/googlesamples/android-google-accounts/tree/master/QuickStart if you are
 * also using APIs that need authentication.
 */
public class MainActivity extends AppCompatActivity implements
        ConnectionCallbacks, OnConnectionFailedListener {

    protected static final String TAG = "MainActivity";

    /**
     * Provides the entry point to Google Play services.
     */
    protected GoogleApiClient mGoogleApiClient;

    /**
     * Represents a geographical location.
     */
    protected Location mLastLocation;


    protected String mLatitudeLabel;
    protected String mLongitudeLabel;
    protected TextView mLatitudeText;
    protected TextView mLongitudeText;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        mLatitudeLabel = getResources().getString(R.string.latitude_label);
        mLongitudeLabel = getResources().getString(R.string.longitude_label);
        mLatitudeText = (TextView) findViewById((R.id.latitude_text));
        //mLongitudeText = (TextView) findViewById((R.id.longitude_text));

        buildGoogleApiClient();


//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.rate_text_view, new CFragment())
//                    .commit();
//        }

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * Builds a GoogleApiClient. Uses the addApi() method to request the LocationServices API.
     */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        mGoogleApiClient.connect();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.google.android.gms.location.sample.basiclocationsample/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.google.android.gms.location.sample.basiclocationsample/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.disconnect();
    }

    /**
     * Runs when a GoogleApiClient object successfully connects.
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        // Provides a simple way of getting a device's location and is well suited for
        // applications that do not require a fine-grained location and that do not need location
        // updates. Gets the best and most recent location currently available, which may be null
        // in rare cases when a location is not available.
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mLastLocation != null) {
//            mLatitudeText.setText(String.format("%s: %f", mLatitudeLabel,
//                    mLastLocation.getLatitude()));
//            mLongitudeText.setText(String.format("%s: %f", mLongitudeLabel,
//                    mLastLocation.getLongitude()));

            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(this, Locale.getDefault());
            try {

                addresses = geocoder.getFromLocation(mLastLocation.getLatitude(),mLastLocation.getLongitude(),1);

                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();

                String completeAddress = address + ", " + city+", "+ state + " " + postalCode + ", "+country;
                Log.i(TAG,completeAddress);
                TextView add = (TextView) findViewById(R.id.full_adddress);
                add.setText(completeAddress);
            } catch (IOException e) {
                e.printStackTrace();
            }


        } else {
            Toast.makeText(this, R.string.no_location_detected, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }


    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    public void submitOrder(View view) {


        String message = "Get request";

        displayMessage(message);


    }

    private void displayMessage(String message) {

        FetchTask fetchTask = new FetchTask();
        fetchTask.execute(mLastLocation);

//
//        final ImageButton demo_button = (ImageButton) findViewById(R.id.submit_button);
//        demo_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                demo_button.setImageResource(R.drawable.meter2);
//            }
//        });
    }


    public class FetchTask extends AsyncTask<Location, Void, String> {

        private final String LOG_TAG = FetchTask.class.getSimpleName();

        @Override
        protected String doInBackground(Location... params) {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String forecastJsonStr = null;


            String format = "json";
            String units = "metric";
            int numDays = 7;

            try {

                //http://www.gregswebserver.com/api/crime/<lat>/<lon>
                String FORECAST_BASE_URL = "http://www.gregswebserver.com/api/crime";
                // "http://api.openweathermap.org/data/2.5/forecast/daily?";
                //final String QUERY_PARAM = "q";
                String SLASH = "/";
//                final String QUERY_LAT = "lat";
//                final String QUERY_LON = "lon";
//                final String FORMAT_PARAM = "mode";
//                final String UNITS_PARAM = "units";
//                final String DAYS_PARAM = "cnt";
//                final String APPID_PARAM = "APPID";

                params[0].getLongitude();
//                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon().append
//                        .appendQueryParameter(QUERY_LAT,String.valueOf(params[0].getLatitude()))
//                        .appendQueryParameter(QUERY_LON,String.valueOf(params[0].getLongitude()))
//                        .appendQueryParameter(FORMAT_PARAM, format)
//                        .appendQueryParameter(UNITS_PARAM, units)
//                        .appendQueryParameter(DAYS_PARAM, Integer.toString(numDays))
//                        .appendQueryParameter(APPID_PARAM, BuildConfig.OPEN_WEATHER_MAP_API_KEY)
//                        .build();
                //39.137076,-77.2028857
                String endPoint = FORECAST_BASE_URL + SLASH + Double.toString(params[0].getLatitude()) + SLASH + Double.toString(params[0].getLongitude());
                //String endPoint = FORECAST_BASE_URL + SLASH + Double.toString(39.137076) + SLASH + Double.toString(-77.2028857);
                URL url = new URL(endPoint);

                Log.v(LOG_TAG, "Built URI " + url);

                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
//                String baseUrl = "http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7";
//                String apiKey = "&APPID=" + BuildConfig.OPEN_WEATHER_MAP_API_KEY;
//                URL url = new URL(baseUrl.concat(apiKey));

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                forecastJsonStr = buffer.toString();
                Log.e(LOG_TAG, forecastJsonStr);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                return getDataFromJson(forecastJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }
            return null;
        }


        private String getDataFromJson(String forecastJsonStr)
                throws JSONException {

            final String OWM_DANGER = "danger";
            JSONObject forecastJson = new JSONObject(forecastJsonStr);

            String rate = forecastJson.getString(OWM_DANGER);

            Log.i(LOG_TAG, "TTTTTTTTTTT " + rate);
            return rate;
        }

        @Override
        protected void onPostExecute(String rate) {
            Log.i(LOG_TAG, rate);
//                TextView priceTextView = (TextView) findViewById(R.id.rate_text_view);
//                priceTextView.setText(rate);
            ImageView image = (ImageView) findViewById(R.id.meter);
            TextView textView2 = (TextView) findViewById(R.id.text2);
            TextView textView1 = (TextView) findViewById(R.id.text1);
            if (rate.equals("1")) {
                image.setImageResource(R.drawable.meter1);

                textView1.setText("1");

                textView2.setText("Nothing to worry about!");
            } else if (rate.equals("2")) {
                image.setImageResource(R.drawable.meter2);
                textView1.setText("2");

                textView2.setText("You shouldn't be too afraid.");
            } else if (rate.equals("3")) {
                image.setImageResource(R.drawable.meter3);
                textView1.setText("3");

                textView2.setText("Not so safe. Be careful.");
            } else if (rate.equals("4")) {
                image.setImageResource(R.drawable.meter4);
                textView1.setText("4");

                textView2.setText("Very unsafe. Leave ASAP.");
            } else if (rate.equals("5")) {
                image.setImageResource(R.drawable.meter5);
                textView1.setText("5");

                textView2.setText("You're going to get shot.");
            }
        }
    }
}
