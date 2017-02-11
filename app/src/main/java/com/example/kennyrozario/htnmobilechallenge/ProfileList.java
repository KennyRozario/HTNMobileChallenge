package com.example.kennyrozario.htnmobilechallenge;

import java.util.ArrayList;

/**
 * Created by Kenny on 2017-02-10.
 */

public class ProfileList extends ArrayList<Profile> {
    private static ProfileList sInstance = null;

    private ProfileList() {}

    public static ProfileList getInstance() {
        if (sInstance == null){
            sInstance = new ProfileList();
        }
        return sInstance;
    }
}
