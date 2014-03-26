package com.sua.runner.model;

import java.io.Serializable;
import java.util.ArrayList;

public class CurrentRun implements Serializable {

    private int walkBeforeTime;
    private int walkAfterTime;
    private ArrayList<RunBlock> runBlocks;

    public CurrentRun(int walkBeforeTime, int walkAfterTime, ArrayList<RunBlock> runBlocks) {
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
}
