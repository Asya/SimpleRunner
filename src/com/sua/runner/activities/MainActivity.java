package com.sua.runner.activities;


import android.app.*;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import com.sua.runner.*;
import com.sua.runner.fragments.CurrentRunFragment;
import com.sua.runner.fragments.NewRunFragment;
import com.sua.runner.fragments.SampleFragment;
import com.sua.runner.model.CurrentRun;
import com.sua.runner.utilities.Config;
import com.sua.runner.utilities.PreferencesManager;

import java.util.Calendar;

public class MainActivity extends Activity {

    private ActionBar actionBar;
    private ViewPager viewPager;
    private TabsAdapter tabsAdapter;

    private BroadcastReceiver actionFinishedReceiverReceiver =
            new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    setNextActionUI();
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        initViewPager();
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Config.INTENT_ACTION_FINISHED);
        registerReceiver(actionFinishedReceiverReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(actionFinishedReceiverReceiver);
    }

    private void initViewPager() {
        viewPager = (ViewPager) findViewById(R.id.pager);
        tabsAdapter = new TabsAdapter(this, viewPager);
        tabsAdapter.addTab(actionBar.newTab().setText(getString(R.string.new_run)),
                NewRunFragment.class, null);
        tabsAdapter.addTab(actionBar.newTab().setText(getString(R.string.current_run)),
                CurrentRunFragment.class, null);
        tabsAdapter.addTab(actionBar.newTab().setText(getString(R.string.statistics)),
                SampleFragment.class, null);

        viewPager.setAdapter(tabsAdapter);
        viewPager.setOffscreenPageLimit(3);
    }

    public void startRun(CurrentRun currentRun) {
        PreferencesManager prefs = new PreferencesManager(this);
        prefs.resetRun();
        prefs.setCurrentRun(currentRun);

        startService();
        selectCurrentRunTab();
    }

    private void startService() {
        Context ctx = getApplicationContext();
        Calendar cal = Calendar.getInstance();
        AlarmManager am = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        Intent serviceIntent = new Intent(ctx, RunService.class);
        PendingIntent servicePendingIntent = PendingIntent.getService(ctx, RunService.SERVICE_ID, serviceIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), servicePendingIntent);
    }

    private void selectCurrentRunTab() {
        int currentRunTabPosition = tabsAdapter.getFragmentPosition(CurrentRunFragment.class);
        viewPager.setCurrentItem(currentRunTabPosition);
        CurrentRunFragment currentRunFragment = (CurrentRunFragment) tabsAdapter.getItem(currentRunTabPosition);
        currentRunFragment.initCurrentRun();
    }

    private void setNextActionUI() {
        int currentRunTabPosition = tabsAdapter.getFragmentPosition(CurrentRunFragment.class);
        CurrentRunFragment currentRunFragment = (CurrentRunFragment) tabsAdapter.getItem(currentRunTabPosition);
        currentRunFragment.setNextActionUI();
    }

}
