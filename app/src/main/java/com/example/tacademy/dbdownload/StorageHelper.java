package com.example.tacademy.dbdownload;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 앱 구동간 필요에 의해서 저장하는 저장소 기능
 */
public class StorageHelper {
    public static final String STORAGE_KEY = "pref";
    private static StorageHelper ourInstance = new StorageHelper();

    public static StorageHelper getInstance() {
        return ourInstance;
    }

    private StorageHelper() {
    }

    public void setString(Context context, String key, String value) {
        // 저장소 획득
        SharedPreferences.Editor editor = context.getSharedPreferences(STORAGE_KEY, 0).edit();
        // 저장
        editor.putString(key, value);
        // 커밋
        editor.commit();
    }

    public String getString(Context context, String key) {
        return
                context.getSharedPreferences(STORAGE_KEY, 0).getString(key, "");
    }

    public void setBoolean(Context context, String key, Boolean value) {
        // 저장소 획득
        SharedPreferences.Editor editor = context.getSharedPreferences(STORAGE_KEY, 0).edit();
        // 저장
        editor.putBoolean(key, value);
        // 커밋
        editor.commit();
    }

    public Boolean getBoolean(Context context, String key) {
        return
                context.getSharedPreferences(STORAGE_KEY, 0).getBoolean(key, false);
    }
}
