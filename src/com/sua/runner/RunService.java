package com.sua.runner;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;
import com.sua.runner.model.CurrentRun;

public class RunService extends Service {

    public static String CURRENT_RUN_EXTRA = "current_run";

    private final IBinder runServiceBinder = new RunServiceBinder();
    private CurrentRun currentRun;

    public RunService() {
    }

    public CurrentRun getCurrentRun() {
        return currentRun;
    }

    @Override
    public IBinder onBind(Intent intent) {
        currentRun = (CurrentRun) intent.getSerializableExtra(CURRENT_RUN_EXTRA);
        Toast.makeText(this, " Service Binded. BeforeWalk = " + currentRun.getWalkBeforeTime(), Toast.LENGTH_LONG).show();
        return runServiceBinder;
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "The new Service was Created.", Toast.LENGTH_LONG).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();

    }

    public class RunServiceBinder extends Binder {
        public RunService getService() {
            return RunService.this;
        }
    }
}