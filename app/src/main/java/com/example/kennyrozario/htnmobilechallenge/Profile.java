package com.example.kennyrozario.htnmobilechallenge;

import java.io.Serializable;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * Created by Kenny on 2017-02-10.
 */

public class Profile implements Serializable{
    private String mCompany;
    private String mEmail;
    private double mLat;
    private double mLong;
    private String mName;
    private String mPhone;
    private String mPicture;
    private TreeMap<String, Integer> mSkills;

    public String getCompany() {
        return mCompany;
    }

    public void setCompany(String company) {
        mCompany = company;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public double getLat() {
        return mLat;
    }

    public void setLat(double lat) {
        mLat = lat;
    }

    public double getLong() {
        return mLong;
    }

    public void setLong(double aLong) {
        mLong = aLong;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public String getPicture() {
        return mPicture;
    }

    public void setPicture(String picture) {
        mPicture = picture;
    }

    public TreeMap<String, Integer> getSkills() {
        return mSkills;
    }

    public void setSkills(TreeMap<String, Integer> skills) {
        mSkills = skills;
    }
}
