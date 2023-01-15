package uac.imsp.clockingapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import androidx.annotation.NonNull;

import java.util.Locale;

public class LocalHelper {
    private static final String LANGUAGE_KEY = "lang";
    final  static String PREFS_NAME="MyPrefsFile";
    public static void changeAppLanguage(Context context, String language) {
        saveSelectedLanguage(context, language);
        updateResources(context, language);
    }

    private static void updateResources(@NonNull Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
    }

    private static void saveSelectedLanguage(@NonNull Context context, String language) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(LANGUAGE_KEY, language);
        editor.apply();
    }

    public static String getSelectedLanguage(@NonNull Context context) {
        SharedPreferences prefs =context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        return prefs.getString(LANGUAGE_KEY, Locale.getDefault().getLanguage());
    }
}
