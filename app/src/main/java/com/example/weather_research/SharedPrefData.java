package com.example.weather_research;

public class SharedPrefData {
    private static android.content.SharedPreferences sharedPrefSettingsAndData;

    public static final String SETTINGS_AND_DATA = "settingsAndData";
    public static final String KEY_FIRST_RUN = "firstRun";
    public static final String KEY_LAST_DATA_DATE = "lastDataDate";
    public static final String KEY_LAST_ERROR_TEXT = "lastErrorText";

    public static final String KEY_MORNING_EXERCISE = "morningExercise";
    public static final String KEY_SECOND_EXERCISE = "secondExercise";
    public static final String KEY_HOURS_SECOND_EXERCISE = "hoursSecondExercise";
    public static final String KEY_MIN_SECOND_EXERCISE = "minSecondExercise";
    public static final String KEY_NUMBER_LANGUAGE = "languageNameNumber";

    public static void setSharedPrefSettingsAndData(android.content.SharedPreferences sharedPrefSettingsAndData) {
        SharedPrefData.sharedPrefSettingsAndData = sharedPrefSettingsAndData;

    }

    public static android.content.SharedPreferences getSharedPrefSettingsAndData() {
        return sharedPrefSettingsAndData;
    }

    public static void savePreferenceUsingKey(String key, String value){
        android.content.SharedPreferences.Editor editor = sharedPrefSettingsAndData.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void savePreferenceUsingKey(String key, int value){
        android.content.SharedPreferences.Editor editor = sharedPrefSettingsAndData.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static void savePreferenceUsingKey(String key, boolean value){
        android.content.SharedPreferences.Editor editor = sharedPrefSettingsAndData.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static String getPreferenceUsingKey(String key, String defValue){
        return sharedPrefSettingsAndData.getString(key, defValue);
    }

    public static int getPreferenceUsingKey(String key, int defValue){
        return sharedPrefSettingsAndData.getInt(key, defValue);
    }

    public static boolean getPreferenceUsingKey(String key, boolean defValue){
        return sharedPrefSettingsAndData.getBoolean(key, defValue);
    }

    public static void setTrueFirstRun(){
        android.content.SharedPreferences.Editor editor = sharedPrefSettingsAndData.edit();
        editor.putBoolean(KEY_FIRST_RUN, true);
        editor.apply();
    }

    public static boolean getFirstRun(){
        return sharedPrefSettingsAndData.getBoolean(KEY_FIRST_RUN, false);
    }

    public static void setLastDataDate(String lastDataDate){
        android.content.SharedPreferences.Editor editor = sharedPrefSettingsAndData.edit();
        editor.putString(KEY_LAST_DATA_DATE, lastDataDate);
        editor.apply();
    }

    public static String getLastDataDate(){
        return sharedPrefSettingsAndData.getString(KEY_LAST_DATA_DATE, "-");
    }

    public static void setLastErrorText(String lastDataDate){
        android.content.SharedPreferences.Editor editor = sharedPrefSettingsAndData.edit();
        editor.putString(KEY_LAST_ERROR_TEXT, lastDataDate);
        editor.apply();
    }

    public static String getLastErrorText(){
        return sharedPrefSettingsAndData.getString(KEY_LAST_ERROR_TEXT, "not error message");
    }
}
