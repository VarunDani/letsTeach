package edu.gatech.mas.letsteach;

import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RequestFormActivity extends AppCompatActivity {

    int count;
    String domain = "";
    boolean[] mon = new boolean[3];
    boolean[] tues = new boolean[3];
    boolean[] wed = new boolean[3];
    boolean[] thur = new boolean[3];
    boolean[] fri = new boolean[3];
    boolean[] sat = new boolean[3];
    boolean[] sun = new boolean[3];
    OkHttpClient httpclient;
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_form);

        //Dropdown Menu
        Spinner spinner = (Spinner) findViewById(R.id.domains_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.domains_array, android.R.layout.simple_spinner_item);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar11);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        httpclient = new OkHttpClient();
        StrictMode.setThreadPolicy(policy);

        final EditText countForm = (EditText) findViewById(R.id.count);
        final Spinner domainForm = (Spinner) findViewById(R.id.domains_spinner);


        final CheckBox monMornForm = (CheckBox) findViewById(R.id.monMorn);
        final CheckBox monAftForm = (CheckBox) findViewById(R.id.monAft);
        final CheckBox monEveForm = (CheckBox) findViewById(R.id.monEve);
        final CheckBox tuesMornForm = (CheckBox) findViewById(R.id.tuesMorn);
        final CheckBox tuesAftForm = (CheckBox) findViewById(R.id.tuesAft);
        final CheckBox tuesEveForm = (CheckBox) findViewById(R.id.tuesEve);
        final CheckBox wedMornForm = (CheckBox) findViewById(R.id.wedMorn);
        final CheckBox wedAftForm = (CheckBox) findViewById(R.id.wedAft);
        final CheckBox wedEveForm = (CheckBox) findViewById(R.id.wedEve);
        final CheckBox thurMornForm = (CheckBox) findViewById(R.id.thurMorn);
        final CheckBox thurAftForm = (CheckBox) findViewById(R.id.thurAft);
        final CheckBox thurEveForm = (CheckBox) findViewById(R.id.thurEve);
        final CheckBox friMornForm = (CheckBox) findViewById(R.id.friMorn);
        final CheckBox friAftForm = (CheckBox) findViewById(R.id.friAft);
        final CheckBox friEveForm = (CheckBox) findViewById(R.id.friEve);
        final CheckBox satMornForm = (CheckBox) findViewById(R.id.satMorn);
        final CheckBox satAftForm = (CheckBox) findViewById(R.id.satAft);
        final CheckBox satEveForm = (CheckBox) findViewById(R.id.satEve);
        final CheckBox sunMornForm = (CheckBox) findViewById(R.id.sunMorn);
        final CheckBox sunAftForm = (CheckBox) findViewById(R.id.sunAft);
        final CheckBox sunEveForm = (CheckBox) findViewById(R.id.sunEve);


        Button submitBtn = (Button) findViewById(R.id.submitbtn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = Integer.parseInt(countForm.getText().toString());
                domain = domainForm.getSelectedItem().toString();
                mon[0] = monMornForm.isChecked();
                mon[1] = monAftForm.isChecked();
                mon[2] = monEveForm.isChecked();
                tues[0] = tuesMornForm.isChecked();
                tues[1] = tuesAftForm.isChecked();
                tues[2] = tuesEveForm.isChecked();
                wed[0] = wedMornForm.isChecked();
                wed[1] = wedAftForm.isChecked();
                wed[2] = wedEveForm.isChecked();
                thur[0] = thurMornForm.isChecked();
                thur[1] = thurAftForm.isChecked();
                thur[2] = thurEveForm.isChecked();
                fri[0] = friMornForm.isChecked();
                fri[1] = friAftForm.isChecked();
                fri[2] = friEveForm.isChecked();
                sat[0] = satMornForm.isChecked();
                sat[1] = satAftForm.isChecked();
                sat[2] = satEveForm.isChecked();
                sun[0] = sunMornForm.isChecked();
                sun[1] = sunAftForm.isChecked();
                sun[2] = sunEveForm.isChecked();

                System.out.println(
                        " Count: " + count +
                                " Domain: " + domain +
                                " Monday: " + mon +
                                " Tuesday: " + tues +
                                " Wednesday: " + wed +
                                " Thursday: " + thur +
                                " Friday: " + fri +
                                " Saturday: " + sat +
                                " Sunday: " + sun
                );

                sendToDb();
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    void sendToDb() {
        String url = "https://test-45590.firebaseio.com/requirements.json";
        String json = formJSON().toString();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try {
            Response response = httpclient.newCall(request).execute();
            System.out.println(response.body().string());
        } catch (Exception e) {

        }

    }


    void getFromDb() {

        try {
            String url = "https://test-45590.firebaseio.com/volunteers.json";
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = httpclient.newCall(request).execute();
            System.out.println(response.body().string());
        } catch (Exception e) {

        }

    }

//    public JSONObject formJSON(){
//
//        ArrayList<JSONObject> allDays = new ArrayList<JSONObject> ();
////        HashMap<String, JSONObject> map = new HashMap<String, JSONObject>();
////        map.put("Monday", formAvail(mon, 1));
////        map.put("Tuesday", formAvail(mon, 1));
////        map.put("Wednesday", formAvail(mon, 1));
////        map.put("Thursday", formAvail(mon, 1));
////        map.put("Friday", formAvail(mon, 1));
////        map.put("Saturday", formAvail(mon, 1));
////        map.put("Sunday", formAvail(mon, 1));
//
///*        allDays.add(formAvail(mon, 1));
//        allDays.add(formAvail(tues, 2));
//        allDays.add(formAvail(wed, 3));
//        allDays.add(formAvail(thur, 4));
//        allDays.add(formAvail(fri, 5));
//        allDays.add(formAvail(sat, 6));
//        allDays.add(formAvail(sun, 7));*/
//
//
//        JSONObject availability = new JSONObject();
//
//        JSONObject days = new JSONObject();
//        try {
//            //days.put("days", new JSONArray(allDays));
//
//        }catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        JSONObject parentJSON = new JSONObject();
//
//        try {
//            parentJSON.put("availability", days);
//            parentJSON.put("count", count);
//            parentJSON.put("domain", domain);
//            parentJSON.put("organisation", "Care USA");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println(parentJSON);
//        return parentJSON;
//    }
//
//
//    public String getDayName(int i) {
//        switch (i)
//        {
//            case 1: {
//                return "Monday";
//            }
//            case 2: {
//                return "Tuesday";
//            }
//            case 3: {
//                return "Wednesday";
//            }
//            case 4: {
//                return "Thursday";
//            }
//            case 5: {
//                return "Friday";
//            }
//            case 6: {
//                return "Saturday";
//            }
//            case 7: {
//                return "Sunday";
//            }
//        }
//
//        return "";
//    }
//
//    public JSONObject formAvail(boolean[] dayArray, int dayNum) {
//        String day = getDayName(dayNum);
//        ArrayList<String> list = new ArrayList<String>();
//        JSONObject avails = new JSONObject();
//        if (dayArray[0])
//            list.add("Morning");
//        if (dayArray[1])
//            list.add("Afternoon");
//        if (dayArray[2])
//            list.add("Evening");
//
//        JSONObject dayAvail = new JSONObject();
//
//        try {
//            dayAvail.put(day, new JSONArray(list));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return dayAvail;
//
//    }

    public JSONObject formJSON(){

    /*ArrayList<JSONObject> allDays = new ArrayList<JSONObject>();
    allDays.put(formAvail(mon, 1));
    allDays.put(formAvail(tues, 2));
    allDays.put(formAvail(wed, 3));
    allDays.put(formAvail(thu, 4));
    allDays.put(formAvail(fri, 5));
    allDays.put(formAvail(sat, 6));
    allDays.put(formAvail(sun, 7));
    JSONObject days = new JSONObject();
    days.put("days", allDays);*/

        JSONObject newTry = new JSONObject();
        JSONObject parentJSON = new JSONObject();

        try {
            newTry.put(getDayName(1), formAvail(mon));
            newTry.put(getDayName(2), formAvail(tues));
            newTry.put(getDayName(3), formAvail(wed));
            newTry.put(getDayName(4), formAvail(thur));
            newTry.put(getDayName(5), formAvail(fri));
            newTry.put(getDayName(6), formAvail(sat));
            newTry.put(getDayName(7), formAvail(sun));
            JSONObject days = new JSONObject();
            days.put("days", newTry);

            parentJSON.put("availability", days);
            parentJSON.put("count", count);
            parentJSON.put("domain", domain);
            parentJSON.put("organisation", "Care USA");
            System.out.println(parentJSON);
        } catch (Exception e)
        {

        }

        return parentJSON;
    }

    public String getDayName(int i){
        switch(i)
        {
            case 1:{
                return "Monday";
            }
            case 2:{
                return "Tuesday";
            }
            case 3:{
                return "Wednesday";
            }
            case 4:{
                return "Thursday";
            }
            case 5:{
                return "Friday";
            }
            case 6:{
                return "Saturday";
            }
            case 7:{
                return "Sunday";
            }
        }

        return "";
    }

    public JSONArray formAvail(boolean[] dayArray){
        ArrayList<String> list = new ArrayList<String>();
        JSONObject avails = new JSONObject();
        if(dayArray[0])
            list.add("Morning");
        if(dayArray[1])
            list.add("Afternoon");
        if(dayArray[2])
            list.add("Evening");

        JSONArray answer = new JSONArray(list);
        //JSONObject dayAvail = new JSONObject();

        //dayAvail.put(day, new JSONArray(list));

        return answer;

    }
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("RequestForm Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}

