package com.sua.runner.model;

import java.io.Serializable;

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
}
