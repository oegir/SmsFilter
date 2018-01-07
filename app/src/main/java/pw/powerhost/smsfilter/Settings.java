package pw.powerhost.smsfilter;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Alexey on 07.01.18.
 */

public class Settings {

    private static Settings settings;
    static final String APP_PREFERENCES = "appsettings";
    private SharedPreferences mPreferences;

    /**
     * Retrieve singleton instance
     * @param context
     * @return
     */
    public static Settings getInstance(Context context) {

        if (settings == null) {
            settings = new Settings(context);
        }
        return settings;
    }

    /**
     * Constructor
     * @param context
     */
    private Settings(Context context) {
        mPreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    /**
     * Get boolean value from settings file
     * @param key
     * @param defValue
     * @return
     */
    public boolean getBoolean(String key, boolean defValue) {

        try {
            return mPreferences.getBoolean(key, defValue);
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * Put value to settings file
     * @param key
     * @param value
     */
    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }
}
