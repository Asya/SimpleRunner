package com.sua.runner.model;

import com.sua.runner.Config;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class RunBlock implements Serializable {

    private int runTime;
    private int walkTime;
    private int repeat;

    public RunBlock(int runTime, int walkTime, int repeat) {
        this.runTime = runTime;
        this.walkTime = walkTime;
        this.repeat = repeat;
    }

    public int getRunTime() {
        return runTime;
    }

    public int getWalkTime() {
        return walkTime;
    }

    public int getRepeat() {
        return repeat;
    }

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        try {
            json.put(Config.JSON_RUN_TIME, runTime);
            json.put(Config.JSON_WALK_TIME, walkTime);
            json.put(Config.JSON_REPEAT, repeat);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();    }

    public static RunBlock getRunBlock(String runBlockString) {
        int run = 0;
        int walk = 0;
        int repeat = 0;

        try {
            JSONObject jObject = new JSONObject(runBlockString);
            run = jObject.getInt(Config.JSON_RUN_TIME);
            walk = jObject.getInt(Config.JSON_WALK_TIME);
            repeat = jObject.getInt(Config.JSON_REPEAT);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new RunBlock(run, walk, repeat);
    }
}
