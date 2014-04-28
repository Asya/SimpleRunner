package com.sua.runner.model;

import com.sua.runner.utilities.Config;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Run implements Serializable {

    private int walkBeforeTime;
    private int walkAfterTime;
    private ArrayList<RunBlock> runBlocks;

    public Run(int walkBeforeTime, int walkAfterTime, ArrayList<RunBlock> runBlocks) {
        this.walkBeforeTime = walkBeforeTime;
        this.walkAfterTime = walkAfterTime;
        this.runBlocks = runBlocks;
    }

    public int getWalkBeforeTime() {
        return walkBeforeTime;
    }

    public int getWalkAfterTime() {
        return walkAfterTime;
    }

    public ArrayList<RunBlock> getRunBlocks() {
        return runBlocks;
    }

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        try {
            json.put(Config.JSON_WALK_BEFORE_TIME, walkBeforeTime);
            json.put(Config.JSON_WALK_AFTER_TIME, walkAfterTime);
            json.put(Config.JSON_RUN_BLOCKS, new JSONArray(runBlocks));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();
    }

    public static Run getRun(String runString) {
        int walkBefore = 0;
        int walkAfter = 0;
        ArrayList<RunBlock> runBlocks = new ArrayList<RunBlock>();

        try {
            JSONObject jObject = new JSONObject(runString);
            walkBefore = jObject.getInt(Config.JSON_WALK_BEFORE_TIME);
            walkAfter = jObject.getInt(Config.JSON_WALK_AFTER_TIME);
            JSONArray jArray = jObject.getJSONArray(Config.JSON_RUN_BLOCKS);
            for (int i = 0; i < jArray.length(); i++) {
                String runBlockString = jArray.getString(i);
                runBlocks.add(RunBlock.getRunBlock(runBlockString));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new Run(walkBefore, walkAfter, runBlocks);
    }
}
