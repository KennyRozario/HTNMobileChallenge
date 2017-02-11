package com.example.kennyrozario.htnmobilechallenge;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeMap;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private String mUrl = "https://htn-interviews.firebaseio.com/users.json";
    private ProfileAdapter mProfileAdapter;
    private JSONArray mJSONArray;
    private ProfileList mProfiles;
    private ListView mParticipants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProfiles = ProfileList.getInstance();

        mParticipants = (ListView) findViewById(R.id.participant_list_view);


        // Async Http Request
        new AsyncTask<Void, Void, String>(){
            @Override
            protected String doInBackground(Void... params) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(mUrl)
                        .build();

                // Try to get a response when requesting a response for participant info from FireBase
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

            // If the JSONArray is not empty, then sort the Profiles alphabetically and display
            //    them on the screen
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                if (mJSONArray != null) {
                    jsonToProfileList();
                    Collections.sort(mProfiles, new Comparator<Profile>() {
                        @Override
                        public int compare(Profile lhs, Profile rhs) {
                            return lhs.getName().compareTo(rhs.getName());
                        }
                    });
                    mProfileAdapter = new ProfileAdapter(mProfiles, MainActivity.this);
                    mParticipants.setAdapter(mProfileAdapter);

                    mParticipants.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Profile profileToPass = (Profile) parent.getItemAtPosition(position);
                            Intent intent = new Intent(MainActivity.this, ParticipantViewActivity.class);
                            intent.putExtra(ParticipantViewActivity.EXTRA, profileToPass);
                            startActivity(intent);
                        }
                    });
                } else {
                    Log.d(TAG, "Null JSON Array");
                }
            }
        }.execute();
    }

    // Go through the non-empty JSONArray and filter out the information to
    //    populate the ProfileList
    private void jsonToProfileList() {
        for (int i = 0; i < mJSONArray.length(); i++) {
            Profile profile = new Profile();
            JSONObject object;
            JSONArray skills;

            // Iterate through the skills sections as they're their own separate JSONArray
            //   and populate their own TreeMap (so that skills are sorted alphabetically)
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
