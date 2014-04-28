package com.sua.runner.utilities;

import android.content.Context;
import android.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.sua.runner.model.Run;

public class PreferencesManager {

    private static SharedPreferences preferences;

	public PreferencesManager(Context ctx) {
        preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    /*************************************************************/

    /**Information about current run is stored in 4 params:
     ** 1 - runType:  TYPE_NONE | TYPE_RUN | TYPE_WALK_IN_RUN | TYPE_BEFORE_WALK | TYPE_AFTER_WALK;
     ** 2 - repeatCount: number of current loop in running block;
     ** 3 - runBlock: number of running block;
     ** 4 - timeStartedAction: time when started current action in UTC.
     **/

	public int getRunTypeType() {
		return getInt(Config.PARAM_RUN_TYPE, Config.TYPE_NONE);
	}

	public void setRunType(int runType) {
		setInt(Config.PARAM_RUN_TYPE, runType);
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

    public long getTimeStartedAction() {
        return getLong(Config.PARAM_TIME_STARTED_ACTION, 0);
    }

    public void setTimeStartedAction(long time) {
        setLong(Config.PARAM_TIME_STARTED_ACTION, time);
    }

    public void resetRun() {
        setRunType(Config.TYPE_NONE);
        setRepeatCount(-1);
        setRunBlock(0);
        setRun(null);
        setTimeStartedAction(0);
    }

    /*************************************************************/

    public Run getRun() {
        return Run.getRun(getString(Config.PARAM_CURRENT_RUN, ""));
    }

    public void setRun(Run run) {
        if(run == null) {
            setString(Config.PARAM_CURRENT_RUN, "");
        } else {
            setString(Config.PARAM_CURRENT_RUN, run.toString());
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
