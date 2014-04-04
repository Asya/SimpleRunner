package com.sua.runner;

import android.content.Context;
import android.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.sua.runner.model.CurrentRun;

public class PreferencesManager {

    private static SharedPreferences preferences;

	public PreferencesManager(Context ctx) {
        preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
    }

	public int getWalkType() {
		return getInt(Config.PARAM_WALK_TYPE, Config.TYPE_BEFORE_WALK);
	}

	public void setWalkType(int walkType) {
		setInt(Config.PARAM_WALK_TYPE, walkType);
	}

    public int getRepeatCount() {
        return getInt(Config.PARAM_REPEAT_COUNT, -1);
    }

    public void setRepeatCount(int repeatCount) {
        setInt(Config.PARAM_REPEAT_COUNT, repeatCount);
    }

    public int getRunBlock() {
        return getInt(Config.PARAM_RUN_BLOCK, 0);
    }

    public void setRunBlock(int runBlock) {
        setInt(Config.PARAM_RUN_BLOCK, runBlock);
    }

    public CurrentRun getCurrentRun() {
        return CurrentRun.getCurrentRun(getString(Config.PARAM_CURRENT_RUN, ""));
    }

    public void setCurrentRun(CurrentRun currentRun) {
        if(currentRun == null) {
            setString(Config.PARAM_CURRENT_RUN, "");
        } else {
            setString(Config.PARAM_CURRENT_RUN, currentRun.toString());
        }
    }

    public void resetRun() {
        setWalkType(Config.TYPE_BEFORE_WALK);
        setRepeatCount(-1);
        setRunBlock(0);
        setCurrentRun(null);
    }

	private String getString(String key, String defaultValue) {
		return preferences.getString(key, defaultValue);
	}

    private int getInt(String key, int defaultValue) {
        return preferences.getInt(key, defaultValue);
    }

    private boolean getBoolean(String key, boolean defaultValue) {
        return preferences.getBoolean(key, defaultValue);
    }

	private void setString(String key, String value) {
		Editor editor = preferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

    private void setInt(String key, int value) {
        Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    private void setBoolean(String key, boolean value) {
        Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }
}
