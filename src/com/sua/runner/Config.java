package com.sua.runner;


public abstract class Config {

    //Shared preferences params
    public static final String PARAM_WALK_TYPE = "walk_type";
    public static final String PARAM_REPEAT_COUNT = "repeat_count";
    public static final String PARAM_RUN_BLOCK = "run_block";
    public static final String PARAM_CURRENT_RUN = "current_run";

    //Walk types
    public final static int TYPE_RUN = -1;
    public final static int TYPE_WALK_IN_RUN = 0;
    public final static int TYPE_BEFORE_WALK = 1;
    public final static int TYPE_AFTER_WALK = 2;

    //Dimentions
    public final static int SECOND = 1000;

    //Current run JSON
    public final static String JSON_WALK_BEFORE_TIME = "walk_before_time";
    public final static String JSON_WALK_AFTER_TIME = "walk_after_time";
    public final static String JSON_RUN_BLOCKS = "run_blocks";
    public final static String JSON_RUN_TIME = "run_time";
    public final static String JSON_WALK_TIME = "walk_time";
    public final static String JSON_REPEAT = "repeat";

}
