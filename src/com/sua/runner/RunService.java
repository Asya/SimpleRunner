package com.sua.runner;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.*;
import android.widget.Toast;
import com.sua.runner.model.Training;
import com.sua.runner.utilities.Config;
import com.sua.runner.utilities.PreferencesManager;

import java.util.Calendar;

public class RunService extends IntentService {

    public final static String MESSAGE_EXTRA = "message";
    public static final int SERVICE_ID = 1;  // integer constant used to identify the service

    private Training training;

    private PreferencesManager prefs;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Toast.makeText(getApplicationContext(), msg.getData().getString(MESSAGE_EXTRA), Toast.LENGTH_LONG).show();
        }
    };

    public RunService() {
        super(RunService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        prefs = new PreferencesManager(this);
        training = prefs.getTraining();
        makeSoundNotification();
        prefs.setTimeStartedAction(System.currentTimeMillis());

        switch (prefs.getCurrentActionType()){
            case Config.TYPE_NONE:
                startBeforeWalk();
                break;
            case Config.TYPE_BEFORE_WALK:
                startRun();
                break;
            case Config.TYPE_RUN:
                startWalkInRun();
                break;
            case Config.TYPE_WALK_IN_RUN:
                startRun();
                break;
            case Config.TYPE_AFTER_WALK:
                prefs.resetRun();
                sendMessage("FINISH!!!");
                break;
        }

        sendBroadcast(new Intent(Config.INTENT_ACTION_FINISHED));
    }

    private void makeSoundNotification() {
        try {
            Uri notification = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.beep);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(Config.SECOND);
    }

    private void startRun() {
        prefs.setCurrentRepeatCount(prefs.getCurrentRepeatCount() + 1);
        if(training.getRunBlocks().get(prefs.getCurrentRunBlockIndex()).getRepeat() > prefs.getCurrentRepeatCount()) {
            prefs.setCurrentActionType(Config.TYPE_RUN);
            setAlarm(training.getRunBlocks().get(prefs.getCurrentRunBlockIndex()).getRunTime());
            sendMessage("Start Training #" + prefs.getCurrentRepeatCount());
        } else if(training.getWalkAfterTime() > 0){
            startAfterWalk();
        }
    }

    private void startWalkInRun() {
        if(training.getRunBlocks().get(prefs.getCurrentRunBlockIndex()).getWalkTime() > 0) {
            prefs.setCurrentActionType(Config.TYPE_WALK_IN_RUN);
            setAlarm(training.getRunBlocks().get(prefs.getCurrentRunBlockIndex()).getWalkTime());
            sendMessage("Start Walk in Training #" + prefs.getCurrentRepeatCount());
        } else {
            startRun();
        }
    }

    private void startBeforeWalk() {
       prefs.setCurrentActionType(Config.TYPE_BEFORE_WALK);
       setAlarm(training.getWalkBeforeTime());
       sendMessage("Start Before Walk");
    }

    private void startAfterWalk() {
        prefs.setCurrentActionType(Config.TYPE_AFTER_WALK);
        setAlarm(training.getWalkAfterTime());
        sendMessage("Start After Walk");
    }

    private void sendMessage(String msg) {
        Message message=new Message();
        Bundle resBundle = new Bundle();
        resBundle.putString(MESSAGE_EXTRA, msg);
        message.setData(resBundle);
        handler.sendMessage(message);
    }

    private void setAlarm(int time) {
        Context ctx = getApplicationContext();
        Calendar cal = Calendar.getInstance();
        AlarmManager am = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        Intent serviceIntent = new Intent(ctx, RunService.class);
        PendingIntent servicePendingIntent = PendingIntent.getService(ctx, RunService.SERVICE_ID, serviceIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis() + time * Config.SECOND, servicePendingIntent);
    }
}