package com.sua.runner.utilities;

import android.content.Context;
import android.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.sua.runner.model.Training;

public class PreferencesManager {

    private static SharedPreferences preferences;

	public PreferencesManager(Context ctx) {
        preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    /*************************************************************/

    /**Information about current run is stored in 4 params:
     ** 1 - currentActionType:  TYPE_NONE | TYPE_RUN | TYPE_WALK_IN_RUN | TYPE_BEFORE_WALK | TYPE_AFTER_WALK;
     ** 2 - currentRepeatCount: number of current loop in running block;
     ** 3 - currentRunBlock: number of running block;
     ** 4 - timeStartedAction: time when started current action in UTC.
     **/

	public int getCurrentActionType() {
		return getInt(Config.PARAM_CURRENT_ACTION_TYPE, Config.TYPE_NONE);
	}

	public void setCurrentActionType(int runType) {
		setInt(Config.PARAM_CURRENT_ACTION_TYPE, runType);
	}

    public int getCurrentRepeatCount() {
        return getInt(Config.PARAM_REPEAT_COUNT, -1);
    }

    public void setCurrentRepeatCount(int repeatCount) {
        setInt(Config.PARAM_REPEAT_COUNT, repeatCount);
    }

    public int getCurrentRunBlockIndex() {
        return getInt(Config.PARAM_RUN_BLOCK, 0);
    }

    public void setCurrentRunBlockIndex(int index) {
        setInt(Config.PARAM_RUN_BLOCK, index);
    }

    public long getTimeStartedAction() {
        return getLong(Config.PARAM_TIME_STARTED_ACTION, 0);
    }

    public void setTimeStartedAction(long time) {
        setLong(Config.PARAM_TIME_STARTED_ACTION, time);
    }

    public void resetRun() {
        setCurrentActionType(Config.TYPE_NONE);
        setCurrentRepeatCount(-1);
        setCurrentRunBlockIndex(0);
        setTraining(null);
        setTimeStartedAction(0);
    }

    /*************************************************************/

    public Training getTraining() {
        return Training.getTraining(getString(Config.PARAM_CURRENT_TRAININIG, ""));
    }

    public void setTraining(Training training) {
        if(training == null) {
            setString(Config.PARAM_CURRENT_TRAININIG, "");
        } else {
            setString(Config.PARAM_CURRENT_TRAININIG, training.toString());
        }
    }

    /*************************************************************/

    private String getString(String key, String defaultValue) {
		return preferences.getString(key, defaultValue);
	}

    private int getInt(String key, int defaultValue) {
        return preferences.getInt(key, defaultValue);
    }


    private long getLong(String key, long defaultValue) {
        return preferences.getLong(key, defaultValue);
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

    private void setLong(String key, long value) {
        Editor editor = preferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    private void setBoolean(String key, boolean value) {
        Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }
}
