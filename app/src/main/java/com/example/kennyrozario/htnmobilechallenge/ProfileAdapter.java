package com.example.kennyrozario.htnmobilechallenge;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Kenny on 2017-02-10.
 */

public class ProfileAdapter extends ArrayAdapter<Profile> {

    ProfileAdapter(ArrayList<Profile> profiles, Context context) {
        super(context, R.layout.participant_list_row, profiles);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = super.getView(position, convertView, parent);

        Profile profile = getItem(position);

        TextView name = (TextView) convertView.findViewById(R.id.participant_name);
        name.setText(profile.getName());

        CircleImageView avatar = (CircleImageView) convertView.findViewById(R.id.avatar);
        Picasso.with(getContext()).load(profile.getPicture()).into(avatar);

        String s = "";
        Map<String, Integer> map = profile.getSkills();
        Iterator<Map.Entry<String, Integer>> iterator = map.entrySet().iterator();

        while (iterator.hasNext()){
            String skill = iterator.next().getKey();
            int rating = iterator.next().getValue();

            if (iterator.hasNext()) {
                s += skill + " (" + rating + ") | ";
            } else {
                s += skill + " (" + rating + ")";
            }
        }

        TextView skills = (TextView) convertView.findViewById(R.id.participant_list_skills);
        skills.setText(s);

        return convertView;
    }
}
