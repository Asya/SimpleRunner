package com.sua.runner.activities;


import android.app.*;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.view.ViewPager;
import com.sua.runner.RunService;
import com.sua.runner.TabsAdapter;
import com.sua.runner.R;
import com.sua.runner.fragments.CurrentRunFragment;
import com.sua.runner.fragments.NewRunFragment;
import com.sua.runner.fragments.SampleFragment;
import com.sua.runner.model.CurrentRun;

public class MainActivity extends Activity {

    private ActionBar actionBar;
    private ViewPager viewPager;
    private TabsAdapter tabsAdapter;

    private RunService runService;
    boolean isBound = false;
    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            RunService.RunServiceBinder binder = (RunService.RunServiceBinder) service;
            runService = binder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            isBound = false;
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

    public void startRun(CurrentRun currentRun){
        Intent intent = new Intent(this, RunService.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(RunService.CURRENT_RUN_EXTRA, currentRun);
        intent.putExtras(bundle);
        this.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        isBound = true;

        int currentRunTabPosition = tabsAdapter.getFragmentPosition(CurrentRunFragment.class);
        viewPager.setCurrentItem(currentRunTabPosition);
        if(currentRun != null) {
            CurrentRunFragment currentRunFragment = (CurrentRunFragment)tabsAdapter.getItem(currentRunTabPosition);
            currentRunFragment.initCurrentRun(currentRun);
        }
    }
}
