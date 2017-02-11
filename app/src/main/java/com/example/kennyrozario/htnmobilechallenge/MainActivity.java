package com.example.kennyrozario.htnmobilechallenge;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private String mUrl = "https://htn-interviews.firebaseio.com/users.json";
    private JSONArray mJSONArray;
    private ProfileList mProfiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProfiles = ProfileList.getInstance();

        // Http Request
        new AsyncTask<Void, Void, String>(){
            @Override
            protected String doInBackground(Void... params) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(mUrl)
                        .build();

                try {
                    Response response = client.newCall(request).execute();
                    String responseString = response.body().string();
                    Log.d(TAG, responseString);
                    try {
                        mJSONArray = new JSONArray(responseString);
                        Log.d(TAG, "JSON Array Created");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                if (mJSONArray != null) {
                    jsonToProfileList();


                } else {
                    Log.d(TAG, "null JSON Array");
                }
            }
        }.execute();


    }

    private void jsonToProfileList() {
        for (int i = 0; i < mJSONArray.length(); i++) {
            Profile profile = new Profile();
            JSONObject object;
            JSONArray skills;
            TreeMap<String, Integer> skillRatings = new TreeMap<>();
            try {
                object = mJSONArray.getJSONObject(i);
                skills = object.getJSONArray("skills");

                for (int j = 0; j < skills.length(); j++){
                    JSONObject skill;
                    try {
                        skill = skills.getJSONObject(j);
                        String name = skill.getString("name");
                        int rating = skill.getInt("rating");
                        skillRatings.put(name, rating);
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                }

                profile.setCompany(object.getString("company"));
                profile.setEmail(object.getString("email"));
                profile.setLat(object.getDouble("latitude"));
                profile.setLong(object.getDouble("longitude"));
                profile.setName(object.getString("name"));
                profile.setPhone(object.getString("phone"));
                profile.setPicture(object.getString("picture"));
                profile.setSkills(skillRatings);
                mProfiles.add(profile);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
