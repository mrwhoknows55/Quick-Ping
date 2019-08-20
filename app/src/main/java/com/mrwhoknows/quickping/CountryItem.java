package com.mrwhoknows.quickping;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class CountryItem {

    @SerializedName("name")
    private String mCountryName;
    @SerializedName("dial_code")
    private String mCountryCode;

    public CountryItem(String mCountryName, String mCountryCode) {
        this.mCountryName = mCountryName;
        this.mCountryCode = mCountryCode;
    }

    String getCountryName() {
        return mCountryName;
    }

    String getCountryCode() {
        return mCountryCode;
    }

    @NonNull
    @Override
    public String toString() {
        return mCountryName+ " " +mCountryCode;
    }
}
