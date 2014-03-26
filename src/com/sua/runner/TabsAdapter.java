package com.sua.runner;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class TabsAdapter extends FragmentPagerAdapter implements ActionBar.TabListener, ViewPager.OnPageChangeListener {

    private final Context mContext;
    private final ActionBar mActionBar;
    private final ViewPager mViewPager;
    private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();

    private final List<Fragment> fragments = new ArrayList<Fragment>();

    static final class TabInfo {
        private final Class<?> clss;
        private final Bundle args;

        TabInfo(Class<?> _class, Bundle _args) {
            clss = _class;
            args = _args;
        }
    }

    public TabsAdapter(Activity activity, ViewPager pager) {
        super(activity.getFragmentManager());
        mContext = activity;
        mActionBar = activity.getActionBar();
        mViewPager = pager;
        mViewPager.setAdapter(this);
        mViewPager.setOnPageChangeListener(this);
    }

    public void addTab(ActionBar.Tab tab, Class<?> clss, Bundle args) {
        TabInfo info = new TabInfo(clss, args);
        tab.setTag(info);
        tab.setTabListener(this);
        mTabs.add(info);
        mActionBar.addTab(tab);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mTabs.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public int getFragmentPosition(Class<?> clss) {
        for (int i=0; i<mTabs.size(); i++) {
            if (mTabs.get(i).clss == clss) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Fragment getItem(int position) {
        TabInfo info = mTabs.get(position);

        for(Fragment fragment : fragments) {
            if(fragment.getClass().getName().equals(info.clss.getName())) {
                return fragment;
            }
        }

        Fragment fragment = Fragment.instantiate(mContext, info.clss.getName(), info.args);
        addFragment(fragment);
        return fragment;
    }

    public void addFragment(Fragment f) {
        fragments.add(f);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        mActionBar.setSelectedNavigationItem(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {
        Object tag = tab.getTag();
        for (int i=0; i<mTabs.size(); i++) {
            if (mTabs.get(i) == tag) {
                //updateDatasetMovies (i);
                mViewPager.setCurrentItem(i);
            }
        }
    }



    @Override
    public void onTabReselected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {
        // TODO Auto-generated method stub

    }


//    public void updateDatasetMovies (int pos)
//    {
//        //Let's update the dataset for the selected genre
//        TabFragment fragment =
//                (TabFragment) ((FragmentActivity)mContext).getSupportFragmentManager().findFragmentByTag(
//                        "android:switcher:"+R.id.pager+":"+pos);
//
//        //TabFragment fragment = (TabFragment) getItem(pos);
//        if(fragment != null)  // could be null if not instantiated yet
//        {
//            if(fragment.getView() != null)
//            {
//                // no need to call if fragment's onDestroyView()
//                //has since been called.
//                fragment.updateDisplay(pos); // do what updates are required
//            }
//        }

    }

//public class TabsAdapter extends FragmentPagerAdapter
//        implements ActionBar.TabListener, ViewPager.OnPageChangeListener {
//    private final Context mContext;
//    private final ActionBar mActionBar;
//    private final ViewPager mViewPager;
//    private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();
//
//    static final class TabInfo {
//        private final Class<?> clss;
//        private final Bundle args;
//
//        TabInfo(Class<?> _class, Bundle _args) {
//            clss = _class;
//            args = _args;
//        }
//    }
//
//    public TabsAdapter(Activity activity, ViewPager pager) {
//        super(activity.getFragmentManager());
//        mContext = activity;
//        mActionBar = activity.getActionBar();
//        mViewPager = pager;
//        mViewPager.setAdapter(this);
//        mViewPager.setOnPageChangeListener(this);
//    }
//
//    public void addTab(ActionBar.Tab tab, Class<?> clss, Bundle args) {
//        TabInfo info = new TabInfo(clss, args);
//        tab.setTag(info);
//        tab.setTabListener(this);
//        mTabs.add(info);
//        mActionBar.addTab(tab);
//        notifyDataSetChanged();
//    }
//
//    @Override
//    public int getCount() {
//        return mTabs.size();
//    }
//
//    @Override
//    public Fragment getItem(int position) {
//        TabInfo info = mTabs.get(position);
//        return Fragment.instantiate(mContext, info.clss.getName(), info.args);
//    }
//
//    @Override
//    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//    }
//
//    @Override
//    public void onPageSelected(int position) {
//        mActionBar.setSelectedNavigationItem(position);
//    }
//
//    @Override
//    public void onPageScrollStateChanged(int state) {
//    }
//
//    @Override
//    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
//        Object tag = tab.getTag();
//        for (int i=0; i<mTabs.size(); i++) {
//            if (mTabs.get(i) == tag) {
//                mViewPager.setCurrentItem(i);
//            }
//        }
//    }
//
//    public int getFragmentPosition(Class<?> clss) {
//        for (int i=0; i<mTabs.size(); i++) {
//            if (mTabs.get(i).clss == clss) {
//                return i;
//            }
//        }
//        return -1;
//    }
//
//    @Override
//    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
//    }
//
//    @Override
//    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
//    }
//}
