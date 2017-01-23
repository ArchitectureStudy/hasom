package com.hasom.mvc.base.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by leejunho on 2017. 1. 16..
 */
public class SharedPreferenceUtil {

    private static final String prefName = "mvp";

    private SharedPreferences pref = null;
    private SharedPreferences.Editor mEditor = null;


    private static final String PREF_TOKEN = "token";

    private SharedPreferenceUtil(Context ctx) {
        pref = ctx.getSharedPreferences(prefName, ctx.MODE_PRIVATE);
    }

    public static SharedPreferenceUtil getInstance(Context ctx) {
        return new SharedPreferenceUtil(ctx);
    }

    public String getToken() {

        String accToken = pref.getString(PREF_TOKEN, "");

        return accToken;
    }

    public void setToken(String token) {
        if (mEditor == null) {
            mEditor = pref.edit();
        }

        mEditor.putString(PREF_TOKEN, token);
        mEditor.commit();

    }
}
