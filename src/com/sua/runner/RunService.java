package com.sua.runner;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.*;
import android.widget.Toast;
import com.sua.runner.model.CurrentRun;

import java.util.Timer;
import java.util.TimerTask;

public class RunService extends Service {

    public final static String MESSAGE_EXTRA = "message";

    private Timer timer;
    private CurrentRun currentRun;

    private PowerManager.WakeLock wakeLock;
    private PreferencesManager prefs;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Toast.makeText(getApplicationContext(), msg.getData().getString(MESSAGE_EXTRA), Toast.LENGTH_LONG).show();
        }
    };

    public RunService() {
    }

    public CurrentRun getCurrentRun() {
        return currentRun;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this, " Service Binded. BeforeWalk = " + currentRun.getWalkBeforeTime(), Toast.LENGTH_LONG).show();
        return null;
    }

    @Override
    public void onCreate() {
        timer = new Timer();
        prefs = new PreferencesManager(this);
        Toast.makeText(this, "The new Service was Created.", Toast.LENGTH_LONG).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "My Tag");
        wakeLock.acquire();

        currentRun = new PreferencesManager(this).getCurrentRun();
        makeSoundNorification();
        sceduleTimer(currentRun.getWalkBeforeTime());
        return super.onStartCommand(intent, flags, startId);
    }

    private void makeSoundNorification() {
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

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();

    }

    private void sceduleTimer(int time) {
        timer.schedule(new EventTimerTask(), time * Config.SECOND);
    }

    class EventTimerTask extends TimerTask {

        @Override
        public void run() {
            makeSoundNorification();

            switch (prefs.getWalkType()){
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
                    sendMessage("FINISH!!!");
                    wakeLock.release();
                    break;
            }
        }

        private void startRun() {
            prefs.setRepeatCount(prefs.getRepeatCount() + 1);
            if(currentRun.getRunBlocks().get(prefs.getRunBlock()).getRepeat() > prefs.getRepeatCount()) {
                prefs.setWalkType(Config.TYPE_RUN);
                sceduleTimer(currentRun.getRunBlocks().get(prefs.getRunBlock()).getRunTime());
                sendMessage("Start Run #" + prefs.getRepeatCount());
            } else if(currentRun.getWalkAfterTime() > 0){
                startAfterWalk();
            }
        }

        private void startWalkInRun() {
            if(currentRun.getRunBlocks().get(prefs.getRunBlock()).getWalkTime() > 0) {
                prefs.setWalkType(Config.TYPE_WALK_IN_RUN);
                sceduleTimer(currentRun.getRunBlocks().get(prefs.getRunBlock()).getWalkTime());
                sendMessage("Start Walk in Run #" + prefs.getRepeatCount());
            } else {
                startRun();
            }
        }

        private void startAfterWalk() {
            prefs.setWalkType(Config.TYPE_AFTER_WALK);
            sceduleTimer(currentRun.getWalkAfterTime());
            sendMessage("Start After Walk");
        }

        private void sendMessage(String msg) {
            Message message=new Message();
            Bundle resBundle = new Bundle();
            resBundle.putString(MESSAGE_EXTRA, msg);
            message.setData(resBundle);
            handler.sendMessage(message);
        }
    };

}