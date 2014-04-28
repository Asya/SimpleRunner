package com.sua.runner.utilities;


public abstract class Config {

    //Shared preferences params
    public static final String PARAM_RUN_TYPE = "walk_type";
    public static final String PARAM_REPEAT_COUNT = "repeat_count";
    public static final String PARAM_RUN_BLOCK = "run_block";
    public static final String PARAM_CURRENT_RUN = "current_run";
    public static final String PARAM_TIME_STARTED_ACTION = "time_started_action";

    //Walk types
    public final static int TYPE_NONE = -1;
    public final static int TYPE_RUN = 0;
    public final static int TYPE_WALK_IN_RUN = 1;
    public final static int TYPE_BEFORE_WALK = 2;
    public final static int TYPE_AFTER_WALK = 3;

    //Dimentions
    public final static int SECOND = 1000;

    //Run item height
    public final static double RUN_ITEM_HEIGHT_MULTIPLIER = 10;//0.7;
    public final static int RUN_ITEM_MIN_HEIGHT = 60;
    public final static int RUN_ITEM_MAX_HEIGHT = 60*5;

    //Run JSON
    public final static String JSON_WALK_BEFORE_TIME = "walk_before_time";
    public final static String JSON_WALK_AFTER_TIME = "walk_after_time";
    public final static String JSON_RUN_BLOCKS = "run_blocks";
    public final static String JSON_RUN_TIME = "run_time";
    public final static String JSON_WALK_TIME = "walk_time";
    public final static String JSON_REPEAT = "repeat";

    public final static String INTENT_ACTION_FINISHED = "com.sua.runner.ACTION_FINISHED";

}
