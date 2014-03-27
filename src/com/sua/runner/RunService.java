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

    public final static String CURRENT_RUN_EXTRA = "current_run";
    public final static String MESSAGE_EXTRA = "message";

    private final static int SECOND = 1000;
    private final static int TYPE_RUN = -1;
    private final static int TYPE_WALK_IN_RUN = 0;
    private final static int TYPE_BEFORE_WALK = 1;
    private final static int TYPE_AFTER_WALK = 2;

    private Timer timer;
    private CurrentRun currentRun;
    private int walkType = TYPE_BEFORE_WALK;
    private int repeatCount = -1;
    private int runBlock = 0;

    private PowerManager.WakeLock wakeLock;


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
        Toast.makeText(this, "The new Service was Created.", Toast.LENGTH_LONG).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "My Tag");
        wakeLock.acquire();

        walkType = TYPE_BEFORE_WALK;
        repeatCount = -1;
        runBlock = 0;

        currentRun = (CurrentRun) intent.getSerializableExtra(CURRENT_RUN_EXTRA);
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
        v.vibrate(SECOND);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();

    }

    private void sceduleTimer(int time) {
        timer.schedule(new EventTimerTask(), time * SECOND);
    }

    class EventTimerTask extends TimerTask {

        @Override
        public void run() {
            makeSoundNorification();

            switch (walkType){
                case TYPE_BEFORE_WALK:
                    startRun();
                    break;
                case TYPE_RUN:
                    startWalkInRun();
                   break;
                case TYPE_WALK_IN_RUN:
                    startRun();
                    break;
                case TYPE_AFTER_WALK:
                    sendMessage("FINISH!!!");
                    wakeLock.release();
                    break;
            }
        }

        private void startRun() {
            repeatCount++;
            if(currentRun.getRunBlocks().get(runBlock).getRepeat() > repeatCount) {
                walkType = TYPE_RUN;
                sceduleTimer(currentRun.getRunBlocks().get(runBlock).getRunTime());
                sendMessage("Start Run #" + repeatCount);
            } else if(currentRun.getWalkAfterTime() > 0){
                startAfterWalk();
            }
        }

        private void startWalkInRun() {
            if(currentRun.getRunBlocks().get(runBlock).getWalkTime() > 0) {
                walkType = TYPE_WALK_IN_RUN;
                sceduleTimer(currentRun.getRunBlocks().get(runBlock).getWalkTime());
                sendMessage("Start Walk in Run #" + repeatCount);
            } else {
                startRun();
            }
        }

        private void startAfterWalk() {
            walkType = TYPE_AFTER_WALK;
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