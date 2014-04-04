package com.sua.runner.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.sua.runner.utilities.PreferencesManager;
import com.sua.runner.R;
import com.sua.runner.utilities.Utils;
import com.sua.runner.model.CurrentRun;
import com.sua.runner.model.RunBlock;
import com.sua.runner.views.FloatingTimeView;

import java.util.ArrayList;

public class CurrentRunFragment extends Fragment {

    private LinearLayout runsLayout;
    private FloatingTimeView layoutTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_current_run, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        runsLayout = (LinearLayout)view.findViewById(R.id.layout_runs);
        layoutTime = (FloatingTimeView)view.findViewById(R.id.layout_time);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();    //To change body of overridden methods use File | Settings | File Templates.
    }

    private void addWalkItem(int duration) {
        View walkView = getActivity().getLayoutInflater().inflate(R.layout.current_list_item, runsLayout, false);
        TextView nameText = (TextView)walkView.findViewById(R.id.name);
        nameText.setBackgroundColor(getResources().getColor(R.color.dark_blue_transparent));
        nameText.setText("Walk #" + duration);
        TextView timeText = (TextView)walkView.findViewById(R.id.time);
        timeText.setText(Utils.timeInString(duration, getActivity()));
        ViewGroup.LayoutParams layoutParams = walkView.getLayoutParams();
        layoutParams.height = Utils.getRunItemHeight(getActivity(), duration);
        walkView.setLayoutParams(layoutParams);
        runsLayout.addView(walkView);
    }

    private void addRunItem(int duration) {
        View runView = getActivity().getLayoutInflater().inflate(R.layout.current_list_item, runsLayout, false);
        TextView nameText = (TextView)runView.findViewById(R.id.name);
        nameText.setBackgroundColor(getResources().getColor(R.color.orange_transparent));
        nameText.setText("Run #" + duration);
        TextView timeText = (TextView)runView.findViewById(R.id.time);
        timeText.setText(Utils.timeInString(duration, getActivity()));
        ViewGroup.LayoutParams layoutParams = runView.getLayoutParams();
        layoutParams.height = Utils.getRunItemHeight(getActivity(), duration);
        runView.setLayoutParams(layoutParams);
        runsLayout.addView(runView);
    }



    public void initCurrentRun() {
        CurrentRun currentRun = new PreferencesManager(getActivity()).getCurrentRun();
        runsLayout.removeAllViews();

        if(currentRun != null) {
            addWalkItem(currentRun.getWalkBeforeTime());

            ArrayList<RunBlock> runBlocks = currentRun.getRunBlocks();
            for(int i = 0; i < runBlocks.size(); i++) {
                for(int j = 0; j < runBlocks.get(i).getRepeat(); j++) {
                    addRunItem(runBlocks.get(i).getRunTime());
                    addWalkItem(runBlocks.get(i).getWalkTime());
                }
            }

            addWalkItem(currentRun.getWalkAfterTime());
            runsLayout.invalidate();
        }
    }

    public void setNextActionUI() {
        layoutTime.setNextAnimation();
    }
}