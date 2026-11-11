package com.codeinsight.app.data.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class UserPreferencesManager {

    private static final String PREF_NAME = "codeinsight_prefs";
    private static final String KEY_DARK_THEME = "dark_theme";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_AUTO_SYNC = "auto_sync";

    private final SharedPreferences prefs;
    private final MutableLiveData<Boolean> darkThemeLive = new MutableLiveData<>();
    private final MutableLiveData<String> userNameLive = new MutableLiveData<>();
    private final MutableLiveData<Boolean> autoSyncLive = new MutableLiveData<>();

    public UserPreferencesManager(Context context) {
        prefs = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        darkThemeLive.setValue(isDarkTheme());
        userNameLive.setValue(getUserName());
        autoSyncLive.setValue(isAutoSync());
    }

    public boolean isDarkTheme() {
        return prefs.getBoolean(KEY_DARK_THEME, true); // Dark theme default
    }

    public void setDarkTheme(boolean enabled) {
        prefs.edit().putBoolean(KEY_DARK_THEME, enabled).apply();
        darkThemeLive.postValue(enabled);
    }

    public LiveData<Boolean> getDarkThemeLive() {
        return darkThemeLive;
    }

    public String getUserName() {
        return prefs.getString(KEY_USER_NAME, "");
    }

    public void setUserName(String name) {
        prefs.edit().putString(KEY_USER_NAME, name).apply();
        userNameLive.postValue(name);
    }

    public LiveData<String> getUserNameLive() {
        return userNameLive;
    }

    private static final String KEY_LC_TOTAL = "lc_total_solved";
    private static final String KEY_LC_EASY = "lc_easy_solved";
    private static final String KEY_LC_MED = "lc_med_solved";
    private static final String KEY_LC_HARD = "lc_hard_solved";
    private static final String KEY_LC_RANK = "lc_rank";
    private static final String KEY_LC_TAG_COUNTS = "lc_tag_counts";
    private static final String KEY_ACTIVE_LEETCODE_USERNAME = "active_lc_username";

    public String getActiveLeetCodeUsername() {
        return prefs.getString(KEY_ACTIVE_LEETCODE_USERNAME, "");
    }

    public void setActiveLeetCodeUsername(String username) {
        prefs.edit().putString(KEY_ACTIVE_LEETCODE_USERNAME, username != null ? username.trim() : "").apply();
    }

    public void saveLeetCodeStats(int total, int easy, int med, int hard, int rank) {
        prefs.edit()
            .putInt(KEY_LC_TOTAL, total)
            .putInt(KEY_LC_EASY, easy)
            .putInt(KEY_LC_MED, med)
            .putInt(KEY_LC_HARD, hard)
            .putInt(KEY_LC_RANK, rank)
            .apply();
    }

    public void saveLeetCodeTagCounts(java.util.Map<String, Integer> tagCounts) {
        if (tagCounts == null || tagCounts.isEmpty()) return;
        StringBuilder sb = new StringBuilder();
        for (java.util.Map.Entry<String, Integer> e : tagCounts.entrySet()) {
            String raw = e.getKey();
            String canonical = com.codeinsight.app.domain.model.TopicNormalizer.normalize(raw);
            String key = canonical != null ? canonical : raw;

            if (sb.length() > 0) sb.append(";");
            sb.append(key).append("=").append(e.getValue());
        }
        prefs.edit().putString(KEY_LC_TAG_COUNTS, sb.toString()).apply();
    }

    public java.util.Map<String, Integer> getLeetCodeTagCounts() {
        java.util.Map<String, Integer> map = new java.util.HashMap<>();
        String raw = prefs.getString(KEY_LC_TAG_COUNTS, "");
        if (raw != null && !raw.isEmpty()) {
            String[] pairs = raw.split(";");
            for (String p : pairs) {
                String[] kv = p.split("=");
                if (kv.length == 2) {
                    try {
                        map.put(kv[0], Integer.parseInt(kv[1]));
                    } catch (Exception ignored) {}
                }
            }
        }
        return map;
    }

    public int getLeetCodeTotalSolved() { return prefs.getInt(KEY_LC_TOTAL, 0); }
    public int getLeetCodeEasySolved() { return prefs.getInt(KEY_LC_EASY, 0); }
    public int getLeetCodeMedSolved() { return prefs.getInt(KEY_LC_MED, 0); }
    public int getLeetCodeHardSolved() { return prefs.getInt(KEY_LC_HARD, 0); }
    public int getLeetCodeRank() { return prefs.getInt(KEY_LC_RANK, 0); }

    public boolean isAutoSync() {
        return prefs.getBoolean(KEY_AUTO_SYNC, false);
    }

    public void setAutoSync(boolean enabled) {
        prefs.edit().putBoolean(KEY_AUTO_SYNC, enabled).apply();
        autoSyncLive.postValue(enabled);
    }

    public LiveData<Boolean> getAutoSyncLive() {
        return autoSyncLive;
    }
}

