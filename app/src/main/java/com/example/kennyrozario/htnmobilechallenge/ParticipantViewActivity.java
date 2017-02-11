package com.example.kennyrozario.htnmobilechallenge;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ParticipantViewActivity extends FragmentActivity implements Serializable, OnMapReadyCallback {

    public static final String EXTRA = "PVA";
    private Profile mProfile;
    private double mLat;
    private double mLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_view);

        // Get Profile Object and extract its values to populate the Views on the screen
        Intent intent = getIntent();
        mProfile = (Profile) intent.getSerializableExtra(EXTRA);

        CircleImageView circleImageView = (CircleImageView) findViewById(R.id.p_view_avatar);
        Picasso.with(this).load(mProfile.getPicture()).into(circleImageView);

        TextView name = (TextView) findViewById(R.id.p_view_name);
        name.setText(mProfile.getName());

        TextView company = (TextView) findViewById(R.id.p_view_company);
        company.setText(mProfile.getCompany());

        TextView phone = (TextView) findViewById(R.id.phone_number);
        phone.setText(mProfile.getPhone());

        TextView email = (TextView) findViewById(R.id.email_address);
        email.setText(mProfile.getEmail());

        String s = "";
        Map<String, Integer> map = mProfile.getSkills();
        Iterator<Map.Entry<String, Integer>> iterator = map.entrySet().iterator();

        while (iterator.hasNext()){
            Map.Entry<String, Integer> entry = iterator.next();
            String skill = entry.getKey();
            int rating = entry.getValue();

            if (iterator.hasNext()) {
                s += skill + " (" + rating + ")\n";
            } else {
                s += skill + " (" + rating + ")";
            }
        }

        TextView skills = (TextView) findViewById(R.id.skills_list);
        skills.setText(s);

        mLat = mProfile.getLat();
        mLong= mProfile.getLong();
        // Setting up the map fragment at the bottom of the screen
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        // Setting up marker at the participant's location
        LatLng latLng = new LatLng(mLat, mLong);
        googleMap.addMarker(new MarkerOptions().position(latLng));
    }
}
