package com.paper.smartcafe.data.repositories;

import android.content.SharedPreferences;

/**
 * Created by Chudofom on 31.10.17.
 */

public class UserRepository {
    public final static String USER_ID_TITLE = "userId";
    public SharedPreferences sharedPreferences;

    public UserRepository(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void setUserId(String userId) {
        sharedPreferences.edit().putString(USER_ID_TITLE, userId).apply();
    }

    public String getUserId() {
        return sharedPreferences.getString(USER_ID_TITLE, null);
    }

}
