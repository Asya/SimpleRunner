package com.sua.runner.activities;


import android.app.*;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import com.sua.runner.PreferencesManager;
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
        PreferencesManager prefs = new PreferencesManager(this);
        prefs.resetRun();
        prefs.setCurrentRun(currentRun);

        Intent intent = new Intent(this, RunService.class);
        this.startService(intent);

        int currentRunTabPosition = tabsAdapter.getFragmentPosition(CurrentRunFragment.class);
        viewPager.setCurrentItem(currentRunTabPosition);
        if(currentRun != null) {
            CurrentRunFragment currentRunFragment = (CurrentRunFragment)tabsAdapter.getItem(currentRunTabPosition);
            currentRunFragment.initCurrentRun(currentRun);
        }
    }
}
